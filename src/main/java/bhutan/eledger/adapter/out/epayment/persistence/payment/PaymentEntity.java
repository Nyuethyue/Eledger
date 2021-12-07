package bhutan.eledger.adapter.out.epayment.persistence.payment;

import bhutan.eledger.domain.epayment.glaccount.EpGLAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ep_payment", schema = "epayment")
@Getter
@Setter
class PaymentEntity {

    @Id
    @SequenceGenerator(name = "ep_payment_id_seq", sequenceName = "ep_payment_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_payment_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "payable_line_id")
    private Long payableLineId;

    @Column(name = "el_target_transaction_id")
    private Long elTargetTransactionId;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @OneToOne
    @JoinColumn(name = "gl_account_id", nullable = false)
    private EpGLAccount glAccount;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private ReceiptEntity receipt;
}
