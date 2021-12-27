package bhutan.eledger.adapter.out.epayment.persistence.payment;

import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;
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
@Table(name = "ep_receipt", schema = "epayment")
@Getter
@Setter
class ReceiptEntity {
    @Id
    @SequenceGenerator(name = "ep_receipt_id_seq", sequenceName = "ep_receipt_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_receipt_id_seq")
    @Column(name = "id")
    private Long id;
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
    @Column(name = "total_paid_amount")
    private BigDecimal totalPaidAmount;

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

//    @OneToMany(
//            mappedBy = "receipt",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.EAGER
//    )
//    private Set<ReceiptPaymentAdviceInfoEntity> receiptPaymentAdviceInfos;

    public ReceiptEntity(Long id, String paymentMode, String status, Long refCurrencyId, Long refBankBranchId, String receiptNumber, String securityNumber, String instrumentNumber, LocalDate instrumentDate, String otherReferenceNumber, LocalDateTime creationDateTime, BigDecimal totalPaidAmount, EpTaxpayer taxpayer) {
        this.id = id;
        this.paymentMode = paymentMode;
        this.status = status;
        this.refCurrencyId = refCurrencyId;
        this.refBankBranchId = refBankBranchId;
        this.receiptNumber = receiptNumber;
        this.securityNumber = securityNumber;
        this.instrumentNumber = instrumentNumber;
        this.instrumentDate = instrumentDate;
        this.otherReferenceNumber = otherReferenceNumber;
        this.creationDateTime = creationDateTime;
        this.totalPaidAmount = totalPaidAmount;
        this.taxpayer = taxpayer;
    }
}
