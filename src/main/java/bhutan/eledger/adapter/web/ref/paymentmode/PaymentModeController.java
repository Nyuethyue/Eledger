package bhutan.eledger.adapter.web.ref.paymentmode;

import bhutan.eledger.application.port.in.ref.paymentmode.ReadPaymentModeUseCase;
import bhutan.eledger.domain.ref.paymentmode.PaymentMode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/ref/payment/mode")
@RequiredArgsConstructor
class PaymentModeController {

    private final ReadPaymentModeUseCase readPaymentModeUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<PaymentMode> getAll() {
        return readPaymentModeUseCase.readAll();
    }
}
