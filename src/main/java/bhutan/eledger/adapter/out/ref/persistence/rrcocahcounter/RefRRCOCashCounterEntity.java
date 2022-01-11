package bhutan.eledger.adapter.out.ref.persistence.rrcocahcounter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rrco_cash_counter", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefRRCOCashCounterEntity {
    @Id
    @SequenceGenerator(name = "rrco_cash_counter_id_seq", schema = "ref", sequenceName = "rrco_cash_counter_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rrco_cash_counter_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "start_of_validity")
    private LocalDate startOfValidity;

    @Column(name = "end_of_validity")
    private LocalDate endOfValidity;

    @OneToMany(
            mappedBy = "rrcoCashCounters",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefRRCOCashCounterDescriptionEntity> descriptions;

    public RefRRCOCashCounterEntity(Long id, String code, LocalDate startOfValidity, LocalDate endOfValidity) {
        this.id = id;
        this.code = code;
        this.startOfValidity = startOfValidity;
        this.endOfValidity = endOfValidity;
    }

    public void addToDescriptions(RefRRCOCashCounterDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setRrcoCashCounters(this);
        descriptions.add(description);
    }

}
