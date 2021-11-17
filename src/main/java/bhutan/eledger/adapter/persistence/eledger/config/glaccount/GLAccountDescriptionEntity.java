package bhutan.eledger.adapter.persistence.eledger.config.glaccount;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
@AuditOverride(forClass = TranslationEntity.class)
@Table(name = "el_gl_account_description", schema = "eledger_config")
@NoArgsConstructor
class GLAccountDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "el_gl_account_description_id_seq", schema = "eledger_config", sequenceName = "el_gl_account_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "el_gl_account_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gl_account_id", nullable = false)
    private GLAccountEntity glAccount;

    public GLAccountDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }

    public GLAccountDescriptionEntity(Long id, String languageCode, String value, GLAccountEntity glAccount) {
        super(languageCode, value);
        this.id = id;
        this.glAccount = glAccount;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GLAccountEntity getGlAccount() {
        return glAccount;
    }

    public void setGlAccount(GLAccountEntity glAccount) {
        this.glAccount = glAccount;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
