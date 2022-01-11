package bhutan.eledger.adapter.out.ref.persistence.rrcocahcounter;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rrco_cash_counter_description", schema = "ref")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class RefRRCOCashCounterDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "rrco_cash_counter_description_id_seq", schema = "ref", sequenceName = "rrco_cash_counter_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rrco_cash_counter_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rrco_cash_counter_id", nullable = false)
    private RefRRCOCashCounterEntity rrcoCashCounters;

    public RefRRCOCashCounterDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
