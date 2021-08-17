package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.common.persistence.multilingual.entity.MultilingualEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "balance_account_part")
@AllArgsConstructor
@NoArgsConstructor
class BalanceAccountPartEntity {
    @Id
    @SequenceGenerator(name = "balance_account_part_id_seq", sequenceName = "balance_account_part_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_account_part_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "balance_account_part_type_id")
    private Integer balanceAccountPartTypeId;

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

    public Integer getBalanceAccountPartTypeId() {
        return balanceAccountPartTypeId;
    }

    public void setBalanceAccountPartTypeId(Integer balanceAccountPartLevelId) {
        this.balanceAccountPartTypeId = balanceAccountPartLevelId;
    }

    public MultilingualEntity getDescription() {
        return description;
    }

    public void setDescription(MultilingualEntity description) {
        this.description = description;
    }
}
