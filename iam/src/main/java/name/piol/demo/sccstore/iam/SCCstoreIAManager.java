package name.piol.demo.sccstore.iam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SCCstoreIAManager {

    @GetMapping("/authenticate")
    public SCCUser authenticateUser(@RequestParam(value = "userId") String userId) {
        SCCUser sccUser = new SCCUser(userId);

        return sccUser;
    }

    // BEISPIEL: https://spring.io/guides/gs/rest-service/
    
}
