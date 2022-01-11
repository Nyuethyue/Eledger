package bhutan.eledger.adapter.out.epayment.persistence.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ep_payment_payment_advice_info", schema = "epayment")
@Getter
@Setter
class PaymentPaymentAdviceInfoEntity {
    @Id
    @SequenceGenerator(name = "ep_payment_payment_advice_info_id_seq", sequenceName = "ep_payment_payment_advice_info_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_payment_payment_advice_info_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "payment_advice_id")
    private Long paId;
    @Column(name = "pan")
    private String pan;
    @Column(name = "drn")
    private String drn;
}
