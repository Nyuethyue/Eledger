package bhutan.eledger.adapter.out.persistence.epayment.payment;

import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ep_receipt", schema = "epayment")
@Getter
@Setter
class ReceiptEntity {
    @Id
    @SequenceGenerator(name = "ep_receipt_id_seq", sequenceName = "ep_receipt_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_receipt_id_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "drn")
    private String drn;
    @Column(name = "payment_mode")
    private String paymentMode;
    @Column(name = "status")
    private String status;
    @Column(name = "ref_currency_id")
    private Long refCurrencyId;
    @Column(name = "ref_bank_branch_id")
    private Long refBankBranchId;
    @Column(name = "receipt_number")
    private String receiptNumber;
    @Column(name = "security_number")
    private String securityNumber;
    @Column(name = "instrument_number")
    private String instrumentNumber;
    @Column(name = "instrument_date")
    private LocalDate instrumentDate;
    @Column(name = "other_reference_number")
    private String otherReferenceNumber;
    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @OneToOne
    @JoinColumn(name = "taxpayer_id", nullable = false)
    private EpTaxpayer taxpayer;

    @OneToMany(
            mappedBy = "receipt",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<PaymentEntity> payments;

    public ReceiptEntity(Long id, String drn, String paymentMode, String status, Long refCurrencyId, String receiptNumber, String securityNumber, LocalDateTime creationDateTime, EpTaxpayer taxpayer) {
        this.id = id;
        this.drn = drn;
        this.paymentMode = paymentMode;
        this.status = status;
        this.refCurrencyId = refCurrencyId;
        this.receiptNumber = receiptNumber;
        this.securityNumber = securityNumber;
        this.creationDateTime = creationDateTime;
        this.taxpayer = taxpayer;
    }
}
