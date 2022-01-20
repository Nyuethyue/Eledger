package bhutan.eledger.adapter.out.ref.persistence.holidaydate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "holiday_date", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class HolidayDateEntity {
    @Id
    @SequenceGenerator(name = "holiday_date_id_seq", schema = "ref", sequenceName = "holiday_date_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "holiday_date_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "year")
    private String year;

    @Column(name = "holiday_start_date")
    private LocalDate holidayStartDate;

    @Column(name = "holiday_end_date")
    private LocalDate holidayEndDate;

    @Column(name = "is_valid_for_one_year")
    private Boolean isValidForOneYear;

    @OneToMany(
            mappedBy = "holidayDate",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<HolidayDateDescriptionEntity> descriptions;

    public HolidayDateEntity(Long id, String year, LocalDate holidayStartDate, LocalDate holidayEndDate, Boolean isValidForOneYear) {
        this.id = id;
        this.year = year;
        this.holidayStartDate = holidayStartDate;
        this.holidayEndDate = holidayEndDate;
        this.isValidForOneYear = isValidForOneYear;
    }

    public void addToDescriptions(HolidayDateDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setHolidayDate(this);
        descriptions.add(description);
    }
}
