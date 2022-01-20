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

    @Column(name = "start_of_validity")
    private LocalDate startOfValidity;

    @Column(name = "end_of_validity")
    private LocalDate endOfValidity;

    @Column(name = "is_valid_for_one_year")
    private Boolean isValidForOneYear;

    @OneToMany(
            mappedBy = "holidayDate",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<HolidayDateDescriptionEntity> descriptions;

    public HolidayDateEntity(Long id, String year, LocalDate startOfValidity, LocalDate endOfValidity, Boolean isValidForOneYear) {
        this.id = id;
        this.year = year;
        this.startOfValidity = startOfValidity;
        this.endOfValidity = endOfValidity;
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
