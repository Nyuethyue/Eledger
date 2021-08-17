package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.common.persistence.multilingual.entity.MultilingualEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "balance_account")
@AllArgsConstructor
@NoArgsConstructor
class BalanceAccountEntity {
    @Id
    @SequenceGenerator(name = "balance_account_id_seq", sequenceName = "balance_account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_account_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "balance_account_last_part_id")
    private Long balanceAccountLastPartId;

    @Column(name = "description")
    @Type(type = "MultilingualType")
    private MultilingualEntity description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getBalanceAccountLastPartId() {
        return balanceAccountLastPartId;
    }

    public void setBalanceAccountLastPartId(Long balanceAccountLastPartId) {
        this.balanceAccountLastPartId = balanceAccountLastPartId;
    }

    public MultilingualEntity getDescription() {
        return description;
    }

    public void setDescription(MultilingualEntity description) {
        this.description = description;
    }
}
