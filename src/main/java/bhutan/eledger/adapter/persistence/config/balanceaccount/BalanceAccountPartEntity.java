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

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @OneToMany(
            mappedBy = "balanceAccountPart",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<BalanceAccountPartDescriptionEntity> descriptions;

    public BalanceAccountPartEntity(Long id, String code, Long parentId, Integer balanceAccountPartTypeId, String status, LocalDateTime creationDateTime, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.code = code;
        this.parentId = parentId;
        this.balanceAccountPartTypeId = balanceAccountPartTypeId;
        this.status = status;
        this.creationDateTime = creationDateTime;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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
