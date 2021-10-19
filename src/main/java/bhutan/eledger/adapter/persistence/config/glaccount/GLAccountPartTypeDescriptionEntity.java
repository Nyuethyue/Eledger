package bhutan.eledger.adapter.persistence.config.glaccount;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "gl_account_part_type_description", schema = "config")
@AllArgsConstructor
@NoArgsConstructor
class GLAccountPartTypeDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "gl_account_part_type_description_id_seq", schema = "config", sequenceName = "gl_account_part_type_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gl_account_part_type_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gl_account_part_type_id", nullable = false)
    private GLAccountPartTypeEntity glAccountPartType;

    public GLAccountPartTypeDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GLAccountPartTypeEntity getGlAccountPartType() {
        return glAccountPartType;
    }

    public void setGlAccountPartType(GLAccountPartTypeEntity glAccountPartType) {
        this.glAccountPartType = glAccountPartType;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
