package bhutan.eledger.adapter.out.epayment.persistence.deposit;

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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ep_deposit", schema = "epayment")
@Getter
@Setter
class DepositEntity {
    @Id
    @SequenceGenerator(name = "ep_deposit_id_seq", sequenceName = "ep_deposit_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_deposit_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "deposit_number")
    private String depositNumber;

    @Column(name = "payment_mode_id")
    private Long paymentModeId;
    @Column(name = "bank_deposit_date")
    private LocalDate bankDepositDate;
    @Column(name = "last_printed_date")
    private LocalDate lastPrintedDate;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "status")
    private String status;
    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @OneToMany(
            mappedBy = "deposit",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<DepositReceiptEntity> depositReceipts;

    @OneToMany(
            mappedBy = "deposit",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<DepositDenominationCountsEntity> depositDenominations;

    public DepositEntity(Long id, String depositNumber, Long paymentModeId, LocalDate bankDepositDate, BigDecimal amount,
                         String status, LocalDateTime creationDateTime) {
        this.id = id;
        this.depositNumber = depositNumber;
        this.paymentModeId = paymentModeId;
        this.bankDepositDate = bankDepositDate;
        this.status = status;
        this.amount = amount;
        this.status = status;
        this.creationDateTime = creationDateTime;
    }
}
