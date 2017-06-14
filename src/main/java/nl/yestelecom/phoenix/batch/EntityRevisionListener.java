package nl.yestelecom.phoenix.batch;

import org.hibernate.envers.RevisionListener;

import nl.yestelecom.phoenix.security.UserAwareUserDetails;

public class EntityRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        final RevisionInfo exampleRevEntity = (RevisionInfo) revisionEntity;
        exampleRevEntity.setUsername(((UserAwareUserDetails) LoginUtil.authentication.getPrincipal()).getUser().getUsername());
    }
}
