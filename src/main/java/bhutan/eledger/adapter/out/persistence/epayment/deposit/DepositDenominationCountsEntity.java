package bhutan.eledger.adapter.out.persistence.epayment.deposit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "deposit_denomination", schema = "epayment")
@Getter
@Setter
class DepositDenominationCountsEntity {
    @Id
    @SequenceGenerator(name = "deposit_denomination_counts_id_seq", sequenceName = "deposit_denomination_counts_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deposit_denomination_counts_id_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "denomination_id")
    private Long denominationId;
    @Column(name = "count")
    private Long count;

    @ManyToOne
    @JoinColumn(name = "deposit_id", nullable = false)
    private DepositEntity deposit;

    public DepositDenominationCountsEntity(Long denominationId, Long count) {
        this.denominationId = denominationId;
        this.count = count;
    }

    public void setDeposit(DepositEntity deposit) {
        this.deposit = deposit;
    }
}
