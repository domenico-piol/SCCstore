package name.piol.demo.sccstore.ui;

//import org.jboss.logging.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import name.piol.demo.sccstore.common.RestSupport;
import name.piol.demo.sccstore.common.SCCUser;
import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    // @Autowired
    // private UserAccountService userAccountService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        Authentication result = null;

        logger.info("starting auth for: " + name);

        // User user = userAccountService.getUser(name);

        RestTemplate restTemplate;
        try {
            restTemplate = RestSupport.getRestTemplate(); // getRestTemplate();
            String fooResourceUrl = "https://localhost:444/authenticate?userId=" + name;
            ResponseEntity<SCCUser> response = restTemplate.getForEntity(fooResourceUrl, SCCUser.class);
            
            logger.info("STATUS: " + response.getStatusCode());

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info(response.toString());
                result = new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
            } else {
                logger.error("user not authenticated!");
                throw new AuthenticationServiceException("Exception in Authentication Service");
            }

            /*if (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR || 
                response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
                logger.error("user not authenticated!");
                throw new AuthenticationServiceException("Exception in Authentication Service");
            } else {
                // OK case
                logger.info(response.toString());
                result = new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
            }*/
        } catch (Exception e) {
            logger.error("an authentication error occurred", e);
            throw new AuthenticationServiceException("Authentication Service not available");
        }

        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
