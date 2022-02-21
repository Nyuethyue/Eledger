package bhutan.eledger.adapter.out.epayment.persistence.rma;

import bhutan.eledger.domain.epayment.rma.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class RmaMessageMapper {

    RmaMessageEntity mapToEntity(RmaMessage rmaMessage) {
        var arPart = rmaMessage.getArPart();
        var asPart = rmaMessage.getAsPart();

        var rmaMessageEntity = new RmaMessageEntity(
                rmaMessage.getId(),
                rmaMessage.getOrderNo(),
                rmaMessage.getStatus().getValue(),
                rmaMessage.getPaymentAdviceId(),
                rmaMessage.getCreationDateTime(),
                new RmaMessageArPartEntity(
                        arPart.getId(),
                        arPart.getMsgType().getValue(),
                        arPart.getBenfTxnTime(),
                        arPart.getOrderNo(),
                        arPart.getBenfId(),
                        arPart.getBenfBankCode(),
                        arPart.getTxnCurrency(),
                        arPart.getTxnAmount(),
                        arPart.getRemitterEmail(),
                        arPart.getCheckSum(),
                        arPart.getPaymentDesc(),
                        arPart.getVersion(),
                        arPart.getCreationDateTime()
                ),
                asPart == null ? null : new RmaMessageAsPartEntity(
                        asPart.getId(),
                        asPart.getMsgType().getValue(),
                        asPart.getBenfTxnTime(),
                        asPart.getOrderNo(),
                        asPart.getBenfId(),
                        asPart.getBenfBankCode(),
                        asPart.getTxnCurrency(),
                        asPart.getTxnAmount(),
                        asPart.getRemitterEmail(),
                        asPart.getCheckSum(),
                        asPart.getPaymentDesc(),
                        asPart.getVersion(),
                        asPart.getCreationDateTime()
                )
        );

        rmaMessageEntity.setRmaMessageResponses(
                rmaMessage.getRmaMessageResponses()
                        .stream()
                        .map(rmr -> new RmaMessageResponseEntity(
                                        rmr.getId(),
                                        rmr.getMsgType().getValue(),
                                        rmr.getBfsTxnId(),
                                        rmr.getBfsTxnTime(),
                                        rmr.getBenfTxnTime(),
                                        rmr.getOrderNo(),
                                        rmr.getBenfId(),
                                        rmr.getTxnCurrency(),
                                        rmr.getTxnAmount(),
                                        rmr.getCheckSum(),
                                        rmr.getRemitterName(),
                                        rmr.getRemitterBankId(),
                                        rmr.getDebitAuthCode().getValue(),
                                        rmr.getDebitAuthNo(),
                                        rmr.getCreationDateTime(),
                                        rmaMessageEntity
                                )
                        )
                        .collect(Collectors.toSet())
        );

        return rmaMessageEntity;
    }

    RmaMessage mapToDomain(RmaMessageEntity rmaMessageEntity) {
        var arPartEntity = rmaMessageEntity.getArPart();
        var asPartEntity = rmaMessageEntity.getAsPart();

        return RmaMessage.withId(
                rmaMessageEntity.getId(),
                rmaMessageEntity.getOrderNo(),
                RmaMessageStatus.of(rmaMessageEntity.getStatus()),
                rmaMessageEntity.getPaymentAdviceId(),
                rmaMessageEntity.getCreationDateTime(),
                RmaMessagePart.withId(
                        arPartEntity.getId(),
                        RmaMsgType.of(arPartEntity.getMsgType()),
                        arPartEntity.getBenfTxnTime(),
                        arPartEntity.getOrderNo(),
                        arPartEntity.getBenfId(),
                        arPartEntity.getBenfBankCode(),
                        arPartEntity.getTxnCurrency(),
                        arPartEntity.getTxnAmount(),
                        arPartEntity.getRemitterEmail(),
                        arPartEntity.getCheckSum(),
                        arPartEntity.getPaymentDesc(),
                        arPartEntity.getVersion(),
                        arPartEntity.getCreationDateTime()
                ),
                asPartEntity == null ? null : RmaMessagePart.withId(
                        asPartEntity.getId(),
                        RmaMsgType.of(asPartEntity.getMsgType()),
                        asPartEntity.getBenfTxnTime(),
                        asPartEntity.getOrderNo(),
                        asPartEntity.getBenfId(),
                        asPartEntity.getBenfBankCode(),
                        asPartEntity.getTxnCurrency(),
                        asPartEntity.getTxnAmount(),
                        asPartEntity.getRemitterEmail(),
                        asPartEntity.getCheckSum(),
                        asPartEntity.getPaymentDesc(),
                        asPartEntity.getVersion(),
                        asPartEntity.getCreationDateTime()
                ),
                rmaMessageEntity.getRmaMessageResponses() == null ? null : rmaMessageEntity.getRmaMessageResponses()
                        .stream()
                        .map(rmre ->
                                RmaMessageResponse.withId(
                                        rmre.getId(),
                                        RmaMsgType.of(rmre.getMsgType()),
                                        rmre.getBfsTxnId(),
                                        rmre.getBfsTxnTime(),
                                        rmre.getBenfTxnTime(),
                                        rmre.getOrderNo(),
                                        rmre.getBenfId(),
                                        rmre.getTxnCurrency(),
                                        rmre.getTxnAmount(),
                                        rmre.getCheckSum(),
                                        rmre.getRemitterName(),
                                        rmre.getRemitterBankId(),
                                        DebitAuthCode.of(rmre.getDebitAuthCode()),
                                        rmre.getDebitAuthNo(),
                                        rmre.getCreationDateTime()
                                )
                        )
                        .collect(Collectors.toSet())
        );
    }
}
