package bhutan.eledger.adapter.persistence.eledger.config.glaccount;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "el_gl_account_part_type", schema = "eledger_config")
@NoArgsConstructor
class GLAccountPartTypeEntity {

    @Id
    @SequenceGenerator(name = "el_gl_account_part_type_id_seq", schema = "eledger_config", sequenceName = "el_gl_account_part_type_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "el_gl_account_part_type_id_seq")
    @Column(name = "id")
    private Integer id;

    @Column(name = "level")
    private Integer level;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "last_modification_date_time")
    private LocalDateTime lastModificationDateTime;

    @OneToMany(
            mappedBy = "glAccountPartType",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<GLAccountPartTypeDescriptionEntity> descriptions;

    public GLAccountPartTypeEntity(Integer id, Integer level, LocalDateTime creationDateTime, LocalDateTime lastModificationDateTime) {
        this.id = id;
        this.level = level;
        this.creationDateTime = creationDateTime;
        this.lastModificationDateTime = lastModificationDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public Set<GLAccountPartTypeDescriptionEntity> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<GLAccountPartTypeDescriptionEntity> descriptions) {
        this.descriptions = descriptions;
    }

    public void addToDescriptions(GLAccountPartTypeDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setGlAccountPartType(this);
        descriptions.add(description);
    }
}
