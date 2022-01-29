package bhutan.eledger.adapter.out.ref.persistence.holidaydate;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "holiday_date_description", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefHolidayDateDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "holiday_date_description_id_seq", schema = "ref", sequenceName = "holiday_date_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "holiday_date_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "holiday_date_id", nullable = false)
    private RefHolidayDateEntity holidayDate;

    public RefHolidayDateDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
