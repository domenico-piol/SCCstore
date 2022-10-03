package name.piol.demo.sccstore.ui;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;

import name.piol.demo.sccstore.ui.datamodel.Complaint;
//import name.piol.demo.sccstore.ui.datamodel.ComplaintsRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

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

        try {
            /*ConnectionProvider provider = ConnectionProvider.builder("fixed")
                .maxConnections(500)
                .maxIdleTime(Duration.ofSeconds(20))
                .maxLifeTime(Duration.ofSeconds(60))
                .pendingAcquireTimeout(Duration.ofSeconds(60))
                .evictInBackground(Duration.ofSeconds(120)).build();
            */
            
            //WebClient webClient = WebClient.builder().baseUrl(complaintsBackend).clientConnector(new ReactorClientHttpConnector(HttpClient.create(provider))).build();
            WebClient webClient = WebClient.builder().baseUrl(complaintsBackend).build();

            //Flux<Complaint> complaintsFlux = webClient.get().uri("/complaints").retrieve().bodyToFlux(Complaint.class);  
            //List<Complaint> complaints = complaintsFlux.collect(Collectors.toList()).share().block(); 

            Mono<Complaint[]> cList = webClient.get().uri("/complaints").accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Complaint[].class).log();
            Complaint[] complaints = cList.block();

            model.addAttribute("complaints", complaints);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "complaints";
    }
}