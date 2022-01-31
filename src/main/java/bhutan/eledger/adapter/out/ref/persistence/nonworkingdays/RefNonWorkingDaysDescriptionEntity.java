package bhutan.eledger.adapter.out.ref.persistence.nonworkingdays;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "non_working_days_description", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefNonWorkingDaysDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "non_working_days_description_id_seq", schema = "ref", sequenceName = "non_working_days_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "non_working_days_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "non_working_days_id", nullable = false)
    private RefNonWorkingDaysEntity nonWorkingDays;

    public RefNonWorkingDaysDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
