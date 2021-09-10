package bhutan.eledger.adapter.persistence.audit;

import lombok.extern.log4j.Log4j2;
import org.hibernate.envers.RevisionListener;

@Log4j2
class AuditRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {

        if (revisionEntity != null) {
            AuditRevisionEntity auditRevision = (AuditRevisionEntity) revisionEntity;

            auditRevision.setUsername("TEST");

        } else {
            log.warn("AUDIT: Additional revision data wasn't set");
        }
    }
}
