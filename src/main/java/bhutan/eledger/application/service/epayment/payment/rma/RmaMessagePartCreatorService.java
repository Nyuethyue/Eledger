package bhutan.eledger.application.service.epayment.payment.rma;

import bhutan.eledger.common.rma.BFSPKISigner;
import bhutan.eledger.configuration.epayment.rma.RmaProperties;
import bhutan.eledger.domain.epayment.rma.RmaMessagePart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RmaMessagePartCreatorService implements RmaMessagePartCreator {

    private final BFSPKISigner bfspkiSigner;
    private final RmaProperties rmaProperties;

    @Override
    public RmaMessagePart create(RmaRequestDataCreatorContext context) {
        var paymentAdvice = context.getPaymentAdvice();

        var rmaMessagePart = RmaMessagePart.createWithoutId(
                context.getMsgType(),
                context.getTxnTime(),
                context.getOrderUuid(),
                rmaProperties.getBeneficiary().getId(),
                rmaProperties.getBeneficiary().getBankCode(),
                context.getCurrencyCode(),
                paymentAdvice.getTotalToBePaidAmount(),
                "benik.arakelyan@iunetworks.am", //todo get from tp
                "PA payment",
                rmaProperties.getApi().getVersion(),
                context.getTxnTime()
        );

        var messageHash = rmaMessagePart.getMessageHash();

        var checksum = bfspkiSigner.sign(messageHash);

        rmaMessagePart.setCheckSum(checksum);

        return rmaMessagePart;
    }
}
