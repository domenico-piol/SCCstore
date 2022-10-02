package name.piol.demo.sccstore.iam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import name.piol.demo.sccstore.common.SCCUser;

@RestController
public class SCCstoreIAManager {
    Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @GetMapping("/authenticate")
    public SCCUser authenticateUser(@RequestParam(value = "userId") String userId) {
        SCCUser sccUser = new SCCUser(userId);

        logger.info("authentication initiated for: " + userId);

        return sccUser;
    }

    // BEISPIEL: https://spring.io/guides/gs/rest-service/
    
}
