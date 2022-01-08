package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reconciliation_upload_record", schema = "epayment")
@Getter
@Setter
class ReconciliationUploadRecordEntity {

    @Id
    @SequenceGenerator(name = "reconciliation_upload_record_id_seq", sequenceName = "reconciliation_upload_record_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reconciliation_upload_record_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "deposit_number")
    private String depositNumber;

    @Column(name = "bank_transaction_number")
    private String bankTransactionNumber;

    @Column(name = "bank_branch_code")
    private String bankBranchCode;

    @Column(name = "bank_processing_date")
    private LocalDate bankProcessingDate;

    @Column(name = "bank_amount")
    private BigDecimal bankAmount;

    @Column(name = "deposit_date_time")
    private LocalDateTime depositDateTime;

    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;

    @Column(name = "deposit_status")
    private String depositStatus;

    @Column(name = "record_status")
    private String recordStatus;

    @Column(name = "creation_date_time", nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @ManyToOne
    @JoinColumn(name = "reconciliation_upload_file_id", nullable = false)
    private ReconciliationUploadFileEntity reconciliationUploadFile;

    public ReconciliationUploadRecordEntity(Long id, String depositNumber, String bankTransactionNumber,
                                            String bankBranchCode, LocalDate bankProcessingDate, BigDecimal bankAmount,
                                            LocalDateTime depositDateTime, BigDecimal depositAmount, String depositStatus,
                                            LocalDateTime creationDateTime, String recordStatus) {
        this.id = id;
        this.depositNumber = depositNumber;

        this.bankTransactionNumber = bankTransactionNumber;
        this.bankBranchCode = bankBranchCode;
        this.bankProcessingDate = bankProcessingDate;
        this.bankAmount = bankAmount;

        this.depositDateTime = depositDateTime;
        this.depositAmount = depositAmount;
        this.depositStatus = depositStatus;

        this.recordStatus = recordStatus;
        this.creationDateTime = creationDateTime;
    }
}
