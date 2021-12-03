package name.piol.demo.sccstore.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class SCCErrorController implements ErrorController {

    Logger logger = LoggerFactory.getLogger(SCCErrorController.class);


    @RequestMapping("/error")
    public String handleError() {
        return "/error";
    }

    @RequestMapping("/service503")
    public String handleServiceUnavailableError() {
        logger.error("Service not available");
        return "/service503";
    }
    
}