package name.piol.demo.sccstore.iam;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    //@Autowired
    //private UserAccountService userAccountService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        //User user = userAccountService.getUser(name);

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