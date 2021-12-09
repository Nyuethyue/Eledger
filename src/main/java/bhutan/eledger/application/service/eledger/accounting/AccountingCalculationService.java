package bhutan.eledger.application.service.eledger.accounting;

import bhutan.eledger.application.port.in.eledger.accounting.AccountingCalculationUseCase;
import bhutan.eledger.application.port.out.eledger.accounting.GetPaymentAdviceDataPort;
import bhutan.eledger.application.port.out.eledger.accounting.formulation.FormulateAccountingPort;
import bhutan.eledger.application.port.out.eledger.epayment.UpsertEpaymentPaymentAdvicePort;
import bhutan.eledger.application.port.out.eledger.taxpayer.ElTaxpayerRepositoryPort;
import bhutan.eledger.domain.eledger.taxpayer.ElTaxpayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class AccountingCalculationService implements AccountingCalculationUseCase {

    private final ElTaxpayerRepositoryPort elTaxpayerRepositoryPort;
    private final FormulateAccountingPort formulateAccountingPort;
    private final GetPaymentAdviceDataPort getPaymentAdviceDataPort;
    private final UpsertEpaymentPaymentAdvicePort upsertEpaymentPaymentAdvicePort;

    @Override
    public void calculate(LocalDate localDate) {

        elTaxpayerRepositoryPort.readAll()
                .stream()
                .map(ElTaxpayer::getTpn)
                .forEach(tpn -> {

                    formulateAccountingPort.formulate(tpn, localDate);

                    var paymentAdviceDatas = getPaymentAdviceDataPort.get(tpn, localDate);

                    log.trace("Upsert payment advice. Upsert data for payment advice for tpn: {}, at: {}, is: {}", tpn, localDate, paymentAdviceDatas);

                    upsertEpaymentPaymentAdvicePort.upsert(paymentAdviceDatas);
                });

    }
}
