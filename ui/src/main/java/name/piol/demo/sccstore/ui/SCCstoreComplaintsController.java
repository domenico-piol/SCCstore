package name.piol.demo.sccstore.ui;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;

import name.piol.demo.sccstore.ui.datamodel.Complaint;
//import name.piol.demo.sccstore.ui.datamodel.ComplaintsRepository;
import reactor.core.publisher.Flux;

@Controller
public class SCCstoreComplaintsController {

    //@Autowired
    //private SCCstoreCompliancesController mySCCstoreCompliancesController;

    //@Autowired
    //protected ComplaintsRepository complaintRepository;

    @Value("${sccstore.complaintsBackend}")
    private String complaintsBackend;
    

    @RequestMapping(value = "/sccstore/complaints", method = RequestMethod.GET)
    public String complaints(Model model) {
        //List<Complaint> complaints = (List<Complaint>) complaintRepository.findAll();

        WebClient webClient = WebClient.builder().baseUrl(complaintsBackend).build();

        Flux<Complaint> complaintsFlux = webClient.get().uri("/complaints").retrieve().bodyToFlux(Complaint.class).timeout(Duration.ofMillis(10000));   
        List<Complaint> complaints = complaintsFlux.collect(Collectors.toList()).share().block(); 

        model.addAttribute("complaints", complaints);
        return "complaints";
    }
}