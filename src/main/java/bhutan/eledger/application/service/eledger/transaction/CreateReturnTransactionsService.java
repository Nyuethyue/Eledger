package bhutan.eledger.application.service.eledger.transaction;

import bhutan.eledger.application.port.out.eledger.accounting.GetPaymentAdviceDataPort;
import bhutan.eledger.application.port.out.eledger.accounting.formulation.FormulateAccountingPort;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeAttributeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeTransactionTypeAttributeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.epayment.UpsertEpaymentPaymentAdvicePort;
import bhutan.eledger.application.port.out.eledger.taxpayer.ElTaxpayerRepositoryPort;
import bhutan.eledger.application.port.out.eledger.transaction.TransactionRepositoryPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@Service
@Transactional
class CreateReturnTransactionsService extends AbstractCreateTransactions {

    private final GetPaymentAdviceDataPort getPaymentAdviceDataPort;
    private final UpsertEpaymentPaymentAdvicePort eledgerGeneratePaymentAdvicePort;

    public CreateReturnTransactionsService(TransactionTypeTransactionTypeAttributeRepositoryPort transactionTypeTransactionTypeAttributeRepositoryPort, ElTaxpayerRepositoryPort taxpayerRepositoryPort, TransactionRepositoryPort transactionRepositoryPort, TransactionTypeAttributeRepositoryPort transactionTypeAttributeRepositoryPort, FormulateAccountingPort formulateAccountingPort, GetPaymentAdviceDataPort getPaymentAdviceDataPort, UpsertEpaymentPaymentAdvicePort eledgerGeneratePaymentAdvicePort) {
        super(transactionTypeTransactionTypeAttributeRepositoryPort, taxpayerRepositoryPort, transactionRepositoryPort, transactionTypeAttributeRepositoryPort, formulateAccountingPort);
        this.getPaymentAdviceDataPort = getPaymentAdviceDataPort;
        this.eledgerGeneratePaymentAdvicePort = eledgerGeneratePaymentAdvicePort;
    }

    @Override
    protected void afterCreate(CreateTransactionsCommand transactionsCommand, LocalDateTime currentDateTime) {
        var paymentAdviceDatas = getPaymentAdviceDataPort.get(transactionsCommand.getTaxpayer().getTpn(), currentDateTime.toLocalDate());
        eledgerGeneratePaymentAdvicePort.upsert(paymentAdviceDatas);
    }
}
