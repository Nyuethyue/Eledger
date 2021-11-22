package bhutan.eledger.adapter.out.persistence.eledger.config.transaction;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "el_transaction_type", schema = "eledger_config")
@AllArgsConstructor
@NoArgsConstructor
class TransactionTypeEntity {

    @Id
    @SequenceGenerator(name = "el_transaction_type_id_seq", schema = "eledger_config", sequenceName = "el_transaction_type_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "el_transaction_type_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(
            mappedBy = "transactionType",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<TransactionTypeDescriptionEntity> descriptions;

    public TransactionTypeEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TransactionTypeDescriptionEntity> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<TransactionTypeDescriptionEntity> descriptions) {
        this.descriptions = descriptions;
    }
}
