package bhutan.eledger.domain.epayment.glaccount;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ep_gl_account_description", schema = "epayment")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
class EpGLAccountDescription extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "ep_gl_account_description_id_seq", sequenceName = "ep_gl_account_description_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_gl_account_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gl_account_id", nullable = false)
    private EpGLAccount glAccount;

    EpGLAccountDescription(Long id, String languageCode, String value, EpGLAccount glAccount) {
        super(languageCode, value);
        this.id = id;
        this.glAccount = glAccount;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
