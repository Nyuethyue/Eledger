package bhutan.eledger.application.service.eledger.accounting;

import bhutan.eledger.application.port.in.eledger.accounting.CalculationNetNegativeBalanceUseCase;
import bhutan.eledger.application.port.out.eledger.taxpayer.ElTaxpayerRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CalculationNetNegativeBalanceService implements CalculationNetNegativeBalanceUseCase {

    private final ElTaxpayerRepositoryPort elTaxpayerRepositoryPort;

    @Override
    public BigDecimal getNetNegativeBalance(CalculateBalanceCommand command) {
        return elTaxpayerRepositoryPort.getNetNegativeBalance(command.getTpn(), LocalDate.now());
    }
}
