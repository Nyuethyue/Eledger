package bhutan.eledger.adapter.persistence.audit;

import bhutan.eledger.common.userdetails.UserDetailsHolder;
import lombok.extern.log4j.Log4j2;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Log4j2
class AuditRevisionListener implements RevisionListener, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void newRevision(Object revisionEntity) {

        if (revisionEntity != null) {
            AuditRevisionEntity auditRevision = (AuditRevisionEntity) revisionEntity;

            var userDetails = applicationContext.getBean(UserDetailsHolder.class).get();

            if (userDetails != null) {
                auditRevision.setUsername(userDetails.getUsername());
            }

        } else {
            log.warn("AUDIT: Additional revision data wasn't set");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
