package bhutan.eledger.adapter.persistence.config.balanceaccount;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "balance_account_part_type", schema = "config")
@NoArgsConstructor
class BalanceAccountPartTypeEntity {

    @Id
    @SequenceGenerator(name = "balance_account_part_type_id_seq", schema = "config", sequenceName = "balance_account_part_type_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_account_part_type_id_seq")
    @Column(name = "id")
    private Integer id;

    @Column(name = "level")
    private Integer level;

    public BalanceAccountPartTypeEntity(Integer id, Integer level) {
        this.id = id;
        this.level = level;
    }

    @OneToMany(
            mappedBy = "balanceAccountPartType",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<BalanceAccountPartTypeDescriptionEntity> descriptions;

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

    public Set<BalanceAccountPartTypeDescriptionEntity> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<BalanceAccountPartTypeDescriptionEntity> descriptions) {
        this.descriptions = descriptions;
    }

    public void addToDescriptions(BalanceAccountPartTypeDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setBalanceAccountPartType(this);
        descriptions.add(description);
    }
}
