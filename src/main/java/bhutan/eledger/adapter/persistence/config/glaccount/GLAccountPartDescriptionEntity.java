package bhutan.eledger.adapter.persistence.config.glaccount;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
@AuditOverride(forClass = TranslationEntity.class)
@Table(name = "gl_account_part_description", schema = "config")
@AllArgsConstructor
@NoArgsConstructor
class GLAccountPartDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "gl_account_part_description_id_seq", schema = "config", sequenceName = "gl_account_part_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gl_account_part_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gl_account_part_id", nullable = false)
    private GLAccountPartEntity glAccountPart;

    public GLAccountPartDescriptionEntity(Long id, String languageCode, String value) {
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

    public GLAccountPartEntity getGlAccountPart() {
        return glAccountPart;
    }

    public void setGlAccountPart(GLAccountPartEntity glAccount) {
        this.glAccountPart = glAccount;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
