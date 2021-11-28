package name.piol.demo.sccstore.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SCCstoreReadinessConroller {
    Logger logger = LoggerFactory.getLogger(SCCstoreReadinessConroller.class);

    @Autowired
    private  ApplicationEventPublisher eventPublisher;

    public SCCstoreReadinessConroller(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

}
