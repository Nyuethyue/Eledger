package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "balance_account_description", schema = "config")
@NoArgsConstructor
class BalanceAccountDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "balance_account_description_id_seq", schema = "config", sequenceName = "balance_account_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_account_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "balance_account_id", nullable = false)
    private BalanceAccountEntity balanceAccount;

    public BalanceAccountDescriptionEntity(Long id, String languageCode, String value) {
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

    public BalanceAccountEntity getBalanceAccount() {
        return balanceAccount;
    }

    public void setBalanceAccount(BalanceAccountEntity balanceAccount) {
        this.balanceAccount = balanceAccount;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
