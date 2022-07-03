package name.piol.demo.sccstore.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import name.piol.demo.sccstore.ui.datamodel.Compliant;
import name.piol.demo.sccstore.ui.datamodel.CompliantRepository;

@Controller
public class SCCstoreCompliancesController {

    //@Autowired
    //private SCCstoreCompliancesController mySCCstoreCompliancesController;

    @Autowired
    protected CompliantRepository compliantRepository;
    

    @RequestMapping(value = "/sccstore/compliants", method = RequestMethod.GET)
    public String compliants(Model model) {
        List<Compliant> compliants = (List<Compliant>) compliantRepository.findAll();

        model.addAttribute("compliants", compliants);

        return "compliants";
    }
}