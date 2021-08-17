package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.common.persistence.multilingual.entity.MultilingualEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "balance_account_part_type")
@AllArgsConstructor
@NoArgsConstructor
class BalanceAccountPartTypeEntity {

    @Id
    @SequenceGenerator(name = "balance_account_part_type_id_seq", sequenceName = "balance_account_part_type_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_account_part_type_id_seq")
    @Column(name = "id")
    private Integer id;

    @Column(name = "level")
    private Integer level;

    @Column(name = "description")
    @Type(type = "MultilingualType")
    private MultilingualEntity description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public MultilingualEntity getDescription() {
        return description;
    }

    public void setDescription(MultilingualEntity description) {
        this.description = description;
    }
}
