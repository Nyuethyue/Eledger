package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reconciliation_upload_file", schema = "epayment")
@Getter
@Setter
class ReconciliationUploadFileEntity {

    @Id
    @SequenceGenerator(name = "reconciliation_upload_file_id_seq", sequenceName = "reconciliation_upload_file_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reconciliation_upload_file_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "bank_id")
    private String bankId;

    @Column(name = "status")
    private String status;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "creation_date_time", nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @OneToMany(
            mappedBy = "reconciliationUploadFile",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Collection<ReconciliationUploadRecordEntity> records;

    public ReconciliationUploadFileEntity(String filePath, String bankId,
                                          String status, String userName,
                                          LocalDateTime creationDateTime) {
        this.filePath = filePath;
        this.bankId = bankId;
        this.status = status;
        this.userName = userName;
        this.creationDateTime = creationDateTime;
    }
}
