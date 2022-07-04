package name.piol.demo.sccstore.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import name.piol.demo.sccstore.ui.datamodel.Complaint;
import name.piol.demo.sccstore.ui.datamodel.ComplaintsRepository;

@Controller
public class SCCstoreComplaintsController {

    //@Autowired
    //private SCCstoreCompliancesController mySCCstoreCompliancesController;

    @Autowired
    protected ComplaintsRepository complaintRepository;
    

    @RequestMapping(value = "/sccstore/complaints", method = RequestMethod.GET)
    public String compliants(Model model) {
        List<Complaint> complaints = (List<Complaint>) complaintRepository.findAll();

        model.addAttribute("complaints", complaints);

        return "complaints";
    }
}