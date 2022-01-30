package bhutan.eledger.adapter.out.ref.persistence.agency;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "agency", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefAgencyEntity {
    @Id
    @SequenceGenerator(name = "agency_id_seq", schema = "ref", sequenceName = "agency_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agency_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "start_of_validity")
    private LocalDate startOfValidity;

    @Column(name = "end_of_validity")
    private LocalDate endOfValidity;

    @OneToMany(
            mappedBy = "agency",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefAgencyDescriptionEntity> descriptions;

    public RefAgencyEntity(Long id, String code, LocalDate startOfValidity, LocalDate endOfValidity) {
        this.id = id;
        this.code = code;
        this.startOfValidity = startOfValidity;
        this.endOfValidity = endOfValidity;
    }

    public void addToDescriptions(RefAgencyDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setAgency(this);
        descriptions.add(description);
    }
}
