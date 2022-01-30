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

    @Column(name = "year")
    private String year;

    @Column(name = "start_day_of_holiday")
    private int startDayOfHoliday;

    @Column(name = "start_month_of_holiday")
    private int startMonthOfHoliday;

    @Column(name = "end_day_of_holiday")
    private int endDayOfHoliday;

    @Column(name = "end_month_of_holiday")
    private int endMonthOfHoliday;

    @Column(name = "start_of_validity")
    private LocalDate startOfValidity;

    @Column(name = "end_of_validity")
    private LocalDate endOfValidity;

    @OneToMany(
            mappedBy = "workingDays",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefNonWorkingDaysDescriptionEntity> descriptions;

    public RefNonWorkingDaysEntity(Long id, String year, int startDayOfHoliday,int startMonthOfHoliday, int endDayOfHoliday,  int endMonthOfHoliday, LocalDate startOfValidity, LocalDate endOfValidity) {
        this.id = id;
        this.year = year;
        this.startDayOfHoliday = startDayOfHoliday;
        this.startMonthOfHoliday = startMonthOfHoliday;
        this.endDayOfHoliday = endDayOfHoliday;
        this.endMonthOfHoliday = endMonthOfHoliday;
        this.startOfValidity = startOfValidity;
        this.endOfValidity = endOfValidity;
    }

    public void addToDescriptions(RefNonWorkingDaysDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setWorkingDays(this);
        descriptions.add(description);
    }
}
