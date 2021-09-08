package bhutan.eledger.adapter.persistence.config.balanceaccount;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "balance_account_part", schema = "config")
@NoArgsConstructor
class BalanceAccountPartEntity {
    @Id
    @SequenceGenerator(name = "balance_account_part_id_seq", schema = "config", sequenceName = "balance_account_part_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_account_part_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "balance_account_part_type_id")
    private Integer balanceAccountPartTypeId;

    @Column(name = "status")
    private String status;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "last_modification_date_time")
    private LocalDateTime lastModificationDateTime;

    @Column(name = "start_of_validity")
    private LocalDateTime startOfValidity;

    @Column(name = "end_of_validity")
    private LocalDateTime endOfValidity;

    @OneToMany(
            mappedBy = "balanceAccountPart",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<BalanceAccountPartDescriptionEntity> descriptions;

    public BalanceAccountPartEntity(Long id, String code, Long parentId, Integer balanceAccountPartTypeId, String status, LocalDateTime creationDateTime, LocalDateTime lastModificationDateTime, LocalDateTime startOfValidity, LocalDateTime endOfValidity) {
        this.id = id;
        this.code = code;
        this.parentId = parentId;
        this.balanceAccountPartTypeId = balanceAccountPartTypeId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getBalanceAccountPartTypeId() {
        return balanceAccountPartTypeId;
    }

    public void setBalanceAccountPartTypeId(Integer balanceAccountPartLevelId) {
        this.balanceAccountPartTypeId = balanceAccountPartLevelId;
    }

    public String getStatus() {
        return status;
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

    public void setStatus(String status) {
        this.status = status;
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

    public Set<BalanceAccountPartDescriptionEntity> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<BalanceAccountPartDescriptionEntity> descriptions) {
        this.descriptions = descriptions;
    }

    public void addToDescriptions(BalanceAccountPartDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setBalanceAccountPart(this);
        descriptions.add(description);
    }
}
