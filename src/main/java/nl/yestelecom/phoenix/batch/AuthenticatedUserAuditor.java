package nl.yestelecom.phoenix.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import nl.yestelecom.phoenix.security.UserAwareUserDetails;
import nl.yestelecom.phoenix.usermanagment.model.User;

@Component
public class AuthenticatedUserAuditor implements AuditorAware<Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticatedUserAuditor.class);

    @Override
    public Long getCurrentAuditor() {
        Long auditorId;
        try {
            auditorId = ((UserAwareUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        } catch (final Exception ex) {
            auditorId = 9999L;
            LOGGER.error("Error while extracting logged in user information, hence setting default auditorId {}", auditorId, ex);
        }
        return auditorId;
    }

    public User getCurrentAuditorRecord() {
        User auditor;
        try {
            auditor = ((UserAwareUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        } catch (final Exception ex) {
            auditor = null;
            LOGGER.error("Error while extracting logged in user information, hence setting default auditorId {}", auditor, ex);
        }
        return auditor;
    }
}