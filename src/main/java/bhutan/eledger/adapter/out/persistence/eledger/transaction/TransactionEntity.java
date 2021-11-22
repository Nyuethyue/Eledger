package bhutan.eledger.adapter.out.persistence.eledger.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "el_transaction", schema = "eledger")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class TransactionEntity {

    @Id
    @SequenceGenerator(name = "el_transaction_id_seq", schema = "eledger", sequenceName = "el_transaction_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "el_transaction_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "drn")
    private String drn;

    @Column(name = "taxpayer_id")
    private Long taxpayerId;

    @Column(name = "gl_account_code")
    private String glAccountCode;

    @Column(name = "settlement_date")
    private LocalDate settlementDate;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "transaction_type_id")
    private Long transactionTypeId;

    public TransactionEntity(Long id, String drn, Long taxpayerId, String glAccountCode, LocalDate settlementDate, BigDecimal amount, LocalDateTime creationDateTime, Long transactionTypeId) {
        this.id = id;
        this.drn = drn;
        this.taxpayerId = taxpayerId;
        this.glAccountCode = glAccountCode;
        this.settlementDate = settlementDate;
        this.amount = amount;
        this.creationDateTime = creationDateTime;
        this.transactionTypeId = transactionTypeId;
    }

    @OneToMany(
            mappedBy = "transaction",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<TransactionAttributeEntity> transactionAttributes;
}
