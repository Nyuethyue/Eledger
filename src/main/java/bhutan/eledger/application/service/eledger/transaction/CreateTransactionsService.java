package bhutan.eledger.application.service.eledger.transaction;

import bhutan.eledger.application.port.in.eledger.transaction.CreateTransactionsUseCase;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateTransactionsService implements CreateTransactionsUseCase {
    private final TransactionTypeTransactionTypeAttributeRepositoryPort transactionTypeTransactionTypeAttributeRepositoryPort;
    private final ElTaxpayerRepositoryPort taxpayerRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final TransactionTypeAttributeRepositoryPort transactionTypeAttributeRepositoryPort;

    @Override
    public void create(CreateTransactionsCommand transactionsCommand) {
        log.trace("Creating transactions with command: {}", transactionsCommand);

        var transactions = makeTransactions(transactionsCommand);

        log.trace("Persisting transactions: {}", transactions);

        Collection<Long> ids = transactionRepositoryPort.createAll(transactions);

        log.debug("Transactions with ids: {} successfully created.", ids);
    }

    private Collection<Transaction> makeTransactions(CreateTransactionsCommand transactionsCommand) {
        Long taxpayerId = resolveTaxpayerId(transactionsCommand.getTaxpayer());

        return transactionsCommand.getTransactions()
                .stream()
                .map(command -> {
                    TransactionTypeWithAttributes transactionType =
                            transactionTypeTransactionTypeAttributeRepositoryPort.requiredReadTransactionWithAttributesByName(
                                    command.getTransactionTypeCode()
                            );

                    validateTransactionTypeAttributes(command, transactionType);

                    LocalDateTime currentDateTime = LocalDateTime.now();

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
                CollectionUtils.isEmpty(command.getTransactionAttributes()) ?
                        Collections.emptySet() : command.getTransactionAttributes()
                        .stream()
                        .map(tac ->
                                TransactionAttribute.withoutId(
                                        transactionTypeAttributeRepositoryPort.requiredReadByName(tac.getTransactionTypeAttributeCode()).getId(),
                                        tac.getValue()
                                )
                        )
                        .collect(Collectors.toUnmodifiableSet())
        );
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
