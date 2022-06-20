package name.piol.demo.sccstore.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SCCstoreController {

    @Autowired
    private SCCstoreController mySCCstoreController;
    

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "redirect:/sccstore";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/sccstore", method = RequestMethod.GET)
    public String index() {
        return "index";
    }


    @RequestMapping(value = "/sccstore/store", method = RequestMethod.GET)
    public String store() {
        return "store";
    }

    @RequestMapping(value = "/sccstore/shopping-cart", method = RequestMethod.GET)
    public String shoppingCart() {
        return "shopping-cart";
    }
}
