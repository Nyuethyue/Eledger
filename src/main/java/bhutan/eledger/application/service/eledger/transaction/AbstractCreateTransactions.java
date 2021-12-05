package bhutan.eledger.application.service.eledger.transaction;

import bhutan.eledger.application.port.in.eledger.transaction.CreateTransactionsUseCase;
import bhutan.eledger.application.port.out.eledger.accounting.formulation.FormulateAccountingPort;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeAttributeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeTransactionTypeAttributeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.taxpayer.ElTaxpayerRepositoryPort;
import bhutan.eledger.application.port.out.eledger.transaction.TransactionRepositoryPort;
import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeWithAttributes;
import bhutan.eledger.domain.eledger.taxpayer.ElTaxpayer;
import bhutan.eledger.domain.eledger.transaction.Transaction;
import bhutan.eledger.domain.eledger.transaction.TransactionAttribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
abstract class AbstractCreateTransactions implements CreateTransactionsUseCase {
    private final TransactionTypeTransactionTypeAttributeRepositoryPort transactionTypeTransactionTypeAttributeRepositoryPort;
    private final ElTaxpayerRepositoryPort taxpayerRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final TransactionTypeAttributeRepositoryPort transactionTypeAttributeRepositoryPort;
    private final FormulateAccountingPort formulateAccountingPort;

    private void afterCreateHandler(CreateTransactionsCommand transactionsCommand, LocalDateTime currentDateTime) {
        formulateAccountingPort.formulate(transactionsCommand.getTaxpayer().getTpn(), currentDateTime.toLocalDate());
        afterCreate(transactionsCommand, currentDateTime);
    }

    protected void afterCreate(CreateTransactionsCommand transactionsCommand, LocalDateTime currentDateTime) {
        log.trace("No-impl after transaction create");
    }

    @Override
    public void create(CreateTransactionsCommand transactionsCommand) {
        log.trace("Creating transactions with command: {}", transactionsCommand);

        LocalDateTime currentDateTime = LocalDateTime.now();

        var transactions = makeTransactions(transactionsCommand, currentDateTime);

        log.trace("Persisting transactions: {}", transactions);

        Collection<Long> ids = transactionRepositoryPort.createAll(transactions);

        log.debug("Transactions with ids: {} successfully created.", ids);

        afterCreateHandler(transactionsCommand, currentDateTime);
    }

    private Collection<Transaction> makeTransactions(CreateTransactionsCommand transactionsCommand, LocalDateTime currentDateTime) {
        Long taxpayerId = resolveTaxpayerId(transactionsCommand.getTaxpayer());

        return transactionsCommand.getTransactions()
                .stream()
                .map(command -> {
                    TransactionTypeWithAttributes transactionType =
                            transactionTypeTransactionTypeAttributeRepositoryPort.requiredReadTransactionWithAttributesByCode(
                                    command.getTransactionTypeCode()
                            );

                    validateTransactionTypeAttributes(command, transactionType);

                    return makeTransactionDomain(command, transactionsCommand.getDrn(), transactionType.getId(), taxpayerId, currentDateTime);
                })
                .collect(Collectors.toUnmodifiableList());
    }

    private Transaction makeTransactionDomain(
            TransactionCommand command,
            String drn,
            Long transactionTypeId,
            Long taxpayerId,
            LocalDateTime currentDateTime
    ) {
        return Transaction.withoutId(
                drn,
                taxpayerId,
                command.getGlAccountCode(),
                command.getSettlementDate(),
                command.getAmount(),
                currentDateTime,
                transactionTypeId,
                makeTransactionAttributes(command)
        );
    }

    private Collection<TransactionAttribute> makeTransactionAttributes(TransactionCommand command) {
        return CollectionUtils.isEmpty(command.getTransactionAttributes()) ?
                Collections.emptySet() : command.getTransactionAttributes()
                .stream()
                .map(tac ->
                        TransactionAttribute.withoutId(
                                transactionTypeAttributeRepositoryPort.requiredReadByCode(tac.getTransactionTypeAttributeCode()).getId(),
                                tac.getValue()
                        )
                )
                .collect(Collectors.toUnmodifiableSet());
    }

    private Long resolveTaxpayerId(TaxpayerCommand taxpayerCommand) {
        Long taxpayerId;

        var taxpayerOptional = taxpayerRepositoryPort.readByTpn(taxpayerCommand.getTpn());

        if (taxpayerOptional.isPresent()) {
            taxpayerId = taxpayerOptional.get().getId();
        } else {
            var taxpayer = ElTaxpayer.withoutId(
                    taxpayerCommand.getTpn(),
                    taxpayerCommand.getName(),
                    LocalDateTime.now()
            );

            log.trace("Persisting taxpayer info: {}", taxpayer);

            taxpayerId = taxpayerRepositoryPort.create(
                    taxpayer
            );

            log.debug("Taxpayer info with id: {} successfully created.", taxpayerId);
        }

        return taxpayerId;
    }


    private void validateTransactionTypeAttributes(TransactionCommand command, TransactionTypeWithAttributes transactionType) {
        //todo implement validation
        log.warn("Validation for transaction type attributes not implemented.");
    }
}
