package bhutan.eledger.adapter.out.persistence.ref.bank;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bank_description", schema = "ref")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class RefBankDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "bank_description_id_seq", schema = "ref", sequenceName = "bank_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private RefBankEntity bank;

    public RefBankDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }


    @Override
    public boolean idSupported() {
        return true;
    }
}
