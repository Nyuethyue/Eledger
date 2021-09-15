package bhutan.eledger.adapter.persistence.audit;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;

@Entity
@Table(name = "revision")
@RevisionEntity(AuditRevisionListener.class)
public class AuditRevisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revision_id_seq")
    @SequenceGenerator(name = "revision_id_seq", sequenceName = "revision_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @RevisionNumber
    private Long id;

    @RevisionTimestamp
    @Column(name = "timestamp", nullable = false)
    private long timestamp;

    @Column(name = "username")
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
