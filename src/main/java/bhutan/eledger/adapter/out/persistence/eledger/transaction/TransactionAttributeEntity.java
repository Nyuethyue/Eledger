package bhutan.eledger.adapter.out.persistence.eledger.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "el_transaction_attribute", schema = "eledger")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class TransactionAttributeEntity {
    @Id
    @SequenceGenerator(name = "el_transaction_attribute_id_seq", schema = "eledger", sequenceName = "el_transaction_attribute_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "el_transaction_attribute_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_type_attribute_id")
    private Long transactionTypeAttributeId;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private TransactionEntity transaction;
}
