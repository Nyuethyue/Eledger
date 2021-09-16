package bhutan.eledger.adapter.persistence.audit;

import bhutan.eledger.common.history.persistence.envers.UsernameAwareRevisionEntity;
import bhutan.eledger.common.history.persistence.envers.UsernameAwareRevisionListener;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "revision")
@RevisionEntity(UsernameAwareRevisionListener.class)
class AuditRevisionEntity extends UsernameAwareRevisionEntity {
}
