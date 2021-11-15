package bhutan.eledger.adapter.web.epayment.paymentmode;

import bhutan.eledger.application.port.in.epayment.paymentmode.ReadPaymentModeUseCase;
import bhutan.eledger.domain.epayment.PaymentMode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/payment/modes")
@RequiredArgsConstructor
class PaymentModeController {

    private final ReadPaymentModeUseCase readPaymentModeUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<PaymentMode> getAll() {

        return readPaymentModeUseCase.readAll();
    }
}
