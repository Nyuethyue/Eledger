package bhutan.eledger.adapter.out.epayment.persistence.paymentadvice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ep_pa_bank_info", schema = "epayment")
@Getter
@Setter
class PaymentAdviceBankInfoEntity {
    @Id
    @SequenceGenerator(name = "ep_pa_bank_info_id_seq", sequenceName = "ep_pa_bank_info_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_pa_bank_info_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @OneToMany(
            mappedBy = "bankInfo",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<PaymentAdviceBankInfoDescriptionEntity> descriptions;

    public PaymentAdviceBankInfoEntity(Long id, String bankAccountNumber) {
        this.id = id;
        this.bankAccountNumber = bankAccountNumber;
    }
}
