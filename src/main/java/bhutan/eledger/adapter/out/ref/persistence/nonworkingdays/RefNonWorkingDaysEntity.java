package bhutan.eledger.adapter.out.ref.persistence.nonworkingdays;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "non_working_days", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefNonWorkingDaysEntity {
    @Id
    @SequenceGenerator(name = "non_working_days_id_seq", schema = "ref", sequenceName = "non_working_days_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "non_working_days_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "start_day")
    private int startDay;

    @Column(name = "start_month")
    private int startMonth;

    @Column(name = "end_day")
    private int endDay;

    @Column(name = "end_month")
    private int endMonth;

    @Column(name = "start_of_validity")
    private LocalDate startOfValidity;

    @Column(name = "end_of_validity")
    private LocalDate endOfValidity;

    @OneToMany(
            mappedBy = "nonWorkingDays",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefNonWorkingDaysDescriptionEntity> descriptions;

    public RefNonWorkingDaysEntity(Long id, String code, int startDay, int startMonth, int endDay, int endMonth, LocalDate startOfValidity, LocalDate endOfValidity) {
        this.id = id;
        this.code = code;
        this.startDay = startDay;
        this.startMonth = startMonth;
        this.endDay = endDay;
        this.endMonth = endMonth;
        this.startOfValidity = startOfValidity;
        this.endOfValidity = endOfValidity;
    }

    public void addToDescriptions(RefNonWorkingDaysDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setNonWorkingDays(this);
        descriptions.add(description);
    }
}
