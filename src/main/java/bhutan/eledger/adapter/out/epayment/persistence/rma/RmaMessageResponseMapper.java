package bhutan.eledger.adapter.out.epayment.persistence.rma;

import bhutan.eledger.domain.epayment.rma.DebitAuthCode;
import bhutan.eledger.domain.epayment.rma.RmaMessageResponse;
import bhutan.eledger.domain.epayment.rma.RmaMsgType;
import org.springframework.stereotype.Component;

@Component
class RmaMessageResponseMapper {

    RmaMessageResponseEntity mapToEntity(RmaMessageEntity rmaMessageEntity, RmaMessageResponse rmaMessageResponse) {
        return new RmaMessageResponseEntity(
                rmaMessageResponse.getId(),
                rmaMessageResponse.getMsgType().getValue(),
                rmaMessageResponse.getBfsTxnId(),
                rmaMessageResponse.getBfsTxnTime(),
                rmaMessageResponse.getBenfTxnTime(),
                rmaMessageResponse.getOrderNo(),
                rmaMessageResponse.getBenfId(),
                rmaMessageResponse.getTxnCurrency(),
                rmaMessageResponse.getTxnAmount(),
                rmaMessageResponse.getCheckSum(),
                rmaMessageResponse.getRemitterName(),
                rmaMessageResponse.getRemitterBankId(),
                rmaMessageResponse.getDebitAuthCode().getValue(),
                rmaMessageResponse.getDebitAuthNo(),
                rmaMessageResponse.getCreationDateTime(),
                rmaMessageEntity
        );
    }

    RmaMessageResponse mapToDomain(RmaMessageResponseEntity rmaMessageResponseEntity) {
        return RmaMessageResponse.withId(
                rmaMessageResponseEntity.getId(),
                RmaMsgType.of(rmaMessageResponseEntity.getMsgType()),
                rmaMessageResponseEntity.getBfsTxnId(),
                rmaMessageResponseEntity.getBfsTxnTime(),
                rmaMessageResponseEntity.getBenfTxnTime(),
                rmaMessageResponseEntity.getOrderNo(),
                rmaMessageResponseEntity.getBenfId(),
                rmaMessageResponseEntity.getTxnCurrency(),
                rmaMessageResponseEntity.getTxnAmount(),
                rmaMessageResponseEntity.getCheckSum(),
                rmaMessageResponseEntity.getRemitterName(),
                rmaMessageResponseEntity.getRemitterBankId(),
                DebitAuthCode.of(rmaMessageResponseEntity.getDebitAuthCode()),
                rmaMessageResponseEntity.getDebitAuthNo(),
                rmaMessageResponseEntity.getCreationDateTime()
        );
    }
}
