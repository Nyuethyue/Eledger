package bhutan.eledger.common.history.persistence.envers;

import bhutan.eledger.common.userdetails.UserDetailsHolder;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Log4j2
public class UsernameAwareRevisionListener implements RevisionListener, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void newRevision(Object revisionEntity) {

        if (revisionEntity != null) {
            UsernameAwareRevisionEntity auditRevision = (UsernameAwareRevisionEntity) revisionEntity;

            var userDetails = applicationContext.getBean(UserDetailsHolder.class).get();

            if (userDetails != null) {
                auditRevision.setUsername(userDetails.getUsername());
            }

        } else {
            log.warn("AUDIT: Additional revision data wasn't set");
        }
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
