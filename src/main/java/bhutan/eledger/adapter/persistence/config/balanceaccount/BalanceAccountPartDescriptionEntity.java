package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "balance_account_part_description", schema = "config")
@AllArgsConstructor
@NoArgsConstructor
class BalanceAccountPartDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "balance_account_part_description_id_seq", schema = "config", sequenceName = "balance_account_part_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_account_part_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "balance_account_part_id", nullable = false)
    private BalanceAccountPartEntity balanceAccountPart;

    public BalanceAccountPartDescriptionEntity(Long id, String languageCode, String value) {
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

    public BalanceAccountPartEntity getBalanceAccountPart() {
        return balanceAccountPart;
    }

    public void setBalanceAccountPart(BalanceAccountPartEntity balanceAccount) {
        this.balanceAccountPart = balanceAccount;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
