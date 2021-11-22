package bhutan.eledger.adapter.out.persistence.audit;

import bhutan.eledger.common.history.persistence.envers.UsernameAwareRevisionEntity;
import bhutan.eledger.common.history.persistence.envers.UsernameAwareRevisionListener;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "revision", schema = "public")
@RevisionEntity(UsernameAwareRevisionListener.class)
class AuditRevisionEntity extends UsernameAwareRevisionEntity {
}
