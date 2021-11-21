package bhutan.eledger.adapter.persistence.eledger.config.glaccount;

import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Audited
@Table(name = "el_gl_account_part", schema = "eledger_config")
@NoArgsConstructor
class GLAccountPartEntity {
    @Id
    @SequenceGenerator(name = "el_gl_account_part_id_seq", schema = "eledger_config", sequenceName = "el_gl_account_part_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "el_gl_account_part_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "full_code")
    private String fullCode;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "gl_account_part_type_id")
    private Integer glAccountPartTypeId;

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
            mappedBy = "glAccountPart",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<GLAccountPartDescriptionEntity> descriptions;

    public GLAccountPartEntity(Long id, String code, String fullCode, Long parentId, Integer glAccountPartTypeId, String status, LocalDateTime creationDateTime, LocalDateTime lastModificationDateTime, LocalDateTime startOfValidity, LocalDateTime endOfValidity) {
        this.id = id;
        this.code = code;
        this.fullCode = fullCode;
        this.parentId = parentId;
        this.glAccountPartTypeId = glAccountPartTypeId;
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

    public String getFullCode() {
        return fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getGlAccountPartTypeId() {
        return glAccountPartTypeId;
    }

    public void setGlAccountPartTypeId(Integer glAccountPartLevelId) {
        this.glAccountPartTypeId = glAccountPartLevelId;
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

    public Set<GLAccountPartDescriptionEntity> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<GLAccountPartDescriptionEntity> descriptions) {
        this.descriptions = descriptions;
    }

    public void addToDescriptions(GLAccountPartDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setGlAccountPart(this);
        descriptions.add(description);
    }
}
