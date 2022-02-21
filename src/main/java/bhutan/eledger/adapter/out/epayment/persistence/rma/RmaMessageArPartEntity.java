package bhutan.eledger.adapter.out.epayment.persistence.rma;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ep_rma_message_ar_part", schema = "epayment")
@NoArgsConstructor
@Getter
@Setter
class RmaMessageArPartEntity extends AbstractRmaMessagePartEntity {
    @Id
    @SequenceGenerator(name = "ep_rma_message_ar_part_id_seq", schema = "epayment", sequenceName = "ep_rma_message_ar_part_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_rma_message_ar_part_id_seq")
    @Column(name = "id")
    private Long id;

    RmaMessageArPartEntity(Long id, String msgType, LocalDateTime benfTxnTime, String orderNo, String benfId, String benfBankCode, String txnCurrency, BigDecimal txnAmount, String remitterEmail, String checkSum, String paymentDesc, Double version, LocalDateTime creationDateTime) {
        super(msgType, benfTxnTime, orderNo, benfId, benfBankCode, txnCurrency, txnAmount, remitterEmail, checkSum, paymentDesc, version, creationDateTime);
        this.id = id;
    }
}
