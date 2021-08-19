package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.common.persistence.multilingual.entity.MultilingualEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "balance_account_part")
@AllArgsConstructor
@NoArgsConstructor
class BalanceAccountPartEntity {
    @Id
    @SequenceGenerator(name = "balance_account_part_id_seq", sequenceName = "balance_account_part_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_account_part_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column("parent_id")
    private Long parentId;

    @Column(name = "balance_account_part_type_id")
    private Integer balanceAccountPartTypeId;

    @Column(name = "status")
    private String status;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "description")
    @Type(type = "MultilingualType")
    private MultilingualEntity description;

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

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public MultilingualEntity getDescription() {
        return description;
    }

    public void setDescription(MultilingualEntity description) {
        this.description = description;
    }
}
