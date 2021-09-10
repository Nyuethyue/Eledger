package bhutan.eledger.adapter.persistence.config.balanceaccount;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Audited
@Table(name = "balance_account", schema = "config")
@AllArgsConstructor
@NoArgsConstructor
class BalanceAccountEntity {
    @Id
    @SequenceGenerator(name = "balance_account_id_seq", schema = "config", sequenceName = "balance_account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_account_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "balance_account_last_part_id")
    private Long balanceAccountLastPartId;

    @Column(name = "status")
    private String status;

    @Column(name = "creation_date_time", nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @Column(name = "last_modification_date_time")
    private LocalDateTime lastModificationDateTime;

    @Column(name = "start_of_validity")
    private LocalDateTime startOfValidity;

    @Column(name = "end_of_validity")
    private LocalDateTime endOfValidity;

    @OneToMany(
            mappedBy = "balanceAccount",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<BalanceAccountDescriptionEntity> descriptions;

    public BalanceAccountEntity(Long id, String code, Long balanceAccountLastPartId, String status, LocalDateTime creationDateTime, LocalDateTime lastModificationDateTime, LocalDateTime startOfValidity, LocalDateTime endOfValidity) {
        this.id = id;
        this.code = code;
        this.balanceAccountLastPartId = balanceAccountLastPartId;
        this.status = status;
        this.creationDateTime = creationDateTime;
        this.lastModificationDateTime = lastModificationDateTime;
        this.startOfValidity = startOfValidity;
        this.endOfValidity = endOfValidity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getBalanceAccountLastPartId() {
        return balanceAccountLastPartId;
    }

    public void setBalanceAccountLastPartId(Long balanceAccountLastPartId) {
        this.balanceAccountLastPartId = balanceAccountLastPartId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDateTime getLastModificationDateTime() {
        return lastModificationDateTime;
    }

    public void setLastModificationDateTime(LocalDateTime lastModificationDateTime) {
        this.lastModificationDateTime = lastModificationDateTime;
    }

    public LocalDateTime getStartOfValidity() {
        return startOfValidity;
    }

    public void setStartOfValidity(LocalDateTime startOfValidity) {
        this.startOfValidity = startOfValidity;
    }

    public LocalDateTime getEndOfValidity() {
        return endOfValidity;
    }

    public void setEndOfValidity(LocalDateTime endOfValidity) {
        this.endOfValidity = endOfValidity;
    }

    public Set<BalanceAccountDescriptionEntity> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<BalanceAccountDescriptionEntity> descriptions) {
        this.descriptions = descriptions;
    }

    public void addToDescription(BalanceAccountDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setBalanceAccount(this);
        descriptions.add(description);
    }
}
