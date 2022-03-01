package bhutan.eledger.adapter.in.epayment.api;

import bhutan.eledger.application.port.in.epayment.payment.rma.RmaTransactionCancelUseCase;
import bhutan.eledger.application.port.in.epayment.payment.rma.RmaTransactionFailUseCase;
import bhutan.eledger.application.port.in.epayment.payment.rma.RmaTransactionSuccessUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@Log4j2
@RestController
@RequestMapping("/ws/epayment/rma/transaction")
@RequiredArgsConstructor
class RmaTransactionRestController {

    private final RmaTransactionSuccessUseCase rmaTransactionSuccessUseCase;
    private final RmaTransactionFailUseCase rmaTransactionFailUseCase;
    private final RmaTransactionCancelUseCase rmaTransactionCancelUseCase;

    @PostMapping("/success")
    public ResponseEntity<Void> create(RmaTransactionSuccessUseCase.RmaTransactionSuccessCommand command) {

        rmaTransactionSuccessUseCase.processSuccess(command);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/failure")
    public ResponseEntity<Void> failure(RmaTransactionFailUseCase.RmaTransactionFailCommand command) {

        rmaTransactionFailUseCase.processFail(command);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = "/canceled", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<Void> canceled(RmaTransactionCancelUseCase.RmaTransactionCancelCommand command, @RequestHeader("Back-To") String backTo) {

        // the request sent by payment system:
        // bfs_bfsTxnId=206000011421&bfs_debitAuthNo=&bfs_remitterName=&bfs_txnCurrency=BTN&bfs_checkSum=31E857CE6E22E12A64DE6052C0F06091296AF3A7C6CD5D8C31AC10FDB5022A83AF28D95CA12AEACAFC6E1BC82794CC469C63CE738C88C73977D1540F94E3A8D6539861F5F643990C8DC2520CFF1149B9C6E2D233B8562BFC2B9DE7EB237C2FD94B893A1AC54BFEE42B4193B0FBFFDC7FAEA781D6EEB2EC69D56605C667DB90589630FADC12904F842B894370D6C98B74DA6AF5949FEA16C05385ED8D7C211EC00060D87BA9C3AE22AD59E49C8AF3545C8EA18361E913FF95F7353F7F5A97E84B82551CBA0F7DC047AC22FEDDF0BF0F77C398C91D8402380DD322C93A6C3F5B6DE539956B197CC22A5937AC0CBF07C7E39E0EC7BE0DF8ECAE14BFF01CD6A5270D&bfs_bfsTxnTime=20220301134337&bfs_benfId=BE10000169&bfs_remitterBankId=&bfs_orderNo=6eddce1ad2d14511905e3bd4de08c695&bfs_debitAuthCode=UC&bfs_txnAmount=1011636.6&bfs_benfTxnTime=20220301133551&bfs_msgType=AC

        log.debug("Cancel request received with mapped dto: {}", command);

        rmaTransactionCancelUseCase.processCancel(command);

        log.debug("Redirecting to {} with order no {}", backTo, command.getOrderNo());

        return ResponseEntity.status(HttpStatus.FOUND).location(
                UriComponentsBuilder.fromUriString(backTo).queryParam("orderNo", command.getOrderNo()).build().toUri()
        ).build();
    }
}
