package bhutan.eledger.adapter.in.eledger.web.balance;

import bhutan.eledger.application.port.in.eledger.accounting.CalculationNetNegativeBalanceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taxpayer/balance/netnegative")
class CalculationNetNegativeBalanceController {

    private final CalculationNetNegativeBalanceUseCase calculationNetNegativeBalanceUseCase;

    @GetMapping(value = "/calculate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public BigDecimal calculateBalance(CalculationNetNegativeBalanceUseCase.CalculateBalanceCommand command) {
        return calculationNetNegativeBalanceUseCase.getNetNegativeBalance(command);
    }
}
