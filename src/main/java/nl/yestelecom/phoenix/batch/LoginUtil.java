package nl.yestelecom.phoenix.batch;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.dao.UserRepository;
import nl.yestelecom.phoenix.security.UserAwareUserDetails;
import nl.yestelecom.phoenix.usermanagment.model.User;

@Service
public class LoginUtil {

    private static final Logger LOG = LoggerFactory.getLogger(LoginUtil.class);

    @Autowired
    private UserRepository userRepo;
    
    public static Authentication authentication;
    
    @PostConstruct
    private void login() {
        // @formatter:off
        final String USER_NAME = "mccs";
        final String PASS_WD = "Phoenix"; 
        final User user = userRepo.findByUsernameIgnoreCase(USER_NAME);
        
        if(user == null) {
            LOG.error("Could not find user with username {}. Please make sure this user exists in the database.", new Object []{USER_NAME});
            System.exit(1);
        }else {
            LOG.info("Logged in as user: " + user.getUsername());
        }

        SecurityContextHolder
               .getContext()
               .setAuthentication(
                       new TestingAuthenticationToken(
                       new UserAwareUserDetails(user, AuthorityUtils.commaSeparatedStringToAuthorityList("a,b")), PASS_WD));
        
        authentication = SecurityContextHolder.getContext().getAuthentication();
        // @formatter:on
    }
}
