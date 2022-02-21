package bhutan.eledger.adapter.out.epayment.persistence.rma;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
class AbstractRmaMessagePartEntity implements Serializable {

    @Column(name = "msg_type")
    private String msgType;
    @Column(name = "benf_txn_time")
    private LocalDateTime benfTxnTime;
    @Column(name = "order_no")
    private String orderNo;
    @Column(name = "benf_id")
    private String benfId;
    @Column(name = "benf_bank_code")
    private String benfBankCode;
    @Column(name = "txn_currency")
    private String txnCurrency;
    @Column(name = "txn_amount")
    private BigDecimal txnAmount;
    @Column(name = "remitter_email")
    private String remitterEmail;
    @Column(name = "checksum")
    private String checkSum;
    @Column(name = "payment_desc")
    private String paymentDesc;
    @Column(name = "bfs_version")
    private Double version;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;
}
