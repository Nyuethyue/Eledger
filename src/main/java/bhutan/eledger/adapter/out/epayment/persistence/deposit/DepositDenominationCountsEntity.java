package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "deposit_denomination_counts", schema = "epayment")
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

    public DepositDenominationCountsEntity(Long denominationId, Long count, DepositEntity deposit) {
        this.denominationId = denominationId;
        this.count = count;
        this.deposit = deposit;
    }

    public void setDeposit(DepositEntity deposit) {
        this.deposit = deposit;
    }
}
