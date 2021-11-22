package bhutan.eledger.adapter.out.persistence.epayment.paymentadvice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ep_pa_payment_line", schema = "epayment")
@Getter
@Setter
class PaymentLineEntity {

    @Id
    @SequenceGenerator(name = "ep_pa_payment_line_id_seq", sequenceName = "ep_pa_payment_line_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_pa_payment_line_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "payment_advice_id")
    private PaymentAdviceEntity paymentAdvice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pa_gl_account_id", nullable = false)
    private PaymentAdviceGLAccountEntity glAccount;

    public PaymentAdviceEntity getPaymentAdvice() {
        return paymentAdvice;
    }
}
