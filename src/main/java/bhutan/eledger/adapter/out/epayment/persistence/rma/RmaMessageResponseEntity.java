package bhutan.eledger.adapter.out.epayment.persistence.rma;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ep_rma_message_response", schema = "epayment")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
class RmaMessageResponseEntity {
    @Id
    @SequenceGenerator(name = "ep_rma_message_response_id_seq", schema = "epayment", sequenceName = "ep_rma_message_response_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_rma_message_response_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "msg_type")
    private String msgType;
    @Column(name = "bfs_txn_id")
    private String bfsTxnId;
    @Column(name = "bfs_txn_time")
    private LocalDateTime bfsTxnTime;
    @Column(name = "benf_txn_time")
    private LocalDateTime benfTxnTime;
    @Column(name = "order_no")
    private String orderNo;
    @Column(name = "benf_id")
    private String benfId;
    @Column(name = "txn_currency")
    private String txnCurrency;
    @Column(name = "txn_amount")
    private BigDecimal txnAmount;
    @Column(name = "checksum")
    private String checkSum;
    @Column(name = "remitter_name")
    private String remitterName;
    @Column(name = "remitter_bank_id")
    private String remitterBankId;
    @Column(name = "debit_auth_code")
    private String debitAuthCode;
    @Column(name = "debit_auth_no")
    private String debitAuthNo;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @ManyToOne
    @JoinColumn(name = "rma_message_id", nullable = false)
    private RmaMessageEntity rmaMessage;

}
