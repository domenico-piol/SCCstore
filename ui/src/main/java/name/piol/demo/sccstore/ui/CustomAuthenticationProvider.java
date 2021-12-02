package name.piol.demo.sccstore.ui;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import name.piol.demo.sccstore.common.RestSupport;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    // @Autowired
    // private UserAccountService userAccountService;


    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        logger.info("starting auth for: " + name);


        // User user = userAccountService.getUser(name);

        RestTemplate restTemplate;
        try {
            restTemplate = RestSupport.getRestTemplate(); // getRestTemplate();
            String fooResourceUrl = "https://localhost:444/authenticate?userId=" + name;
            ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
            logger.info(response.toString());
        } catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        if (name != null && name.equals("domenico@piol.name")) {
            logger.info("User " + name + " logged in");
            return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
        } else {
            logger.error("Authentication Error");
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
