package bhutan.eledger.adapter.persistence.epayment.paymentmode;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "payment_mode", schema = "ref")
@Getter
@Setter
class PaymentModeEntity {
    @Id
    @SequenceGenerator(name = "payment_mode_id_seq", schema = "ref", sequenceName = "payment_mode_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_mode_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @OneToMany(
            mappedBy = "paymentMode",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<PaymentModeDescriptionEntity> descriptions;

    public PaymentModeEntity(Long id, String code) {
        this.id = id;
        this.code = code;
    }
}
