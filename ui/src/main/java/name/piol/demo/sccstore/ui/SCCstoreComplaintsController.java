package name.piol.demo.sccstore.ui;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;

import name.piol.demo.sccstore.ui.datamodel.Complaint;
//import name.piol.demo.sccstore.ui.datamodel.ComplaintsRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Controller
public class SCCstoreComplaintsController {
    Logger logger = LoggerFactory.getLogger(SCCstoreComplaintsController.class);

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
            /*
            ConnectionProvider provider = ConnectionProvider.builder("fixed")
                .maxConnections(500)
                .maxIdleTime(Duration.ofSeconds(20))
                .maxLifeTime(Duration.ofSeconds(60))
                .pendingAcquireTimeout(Duration.ofSeconds(60))
                .evictInBackground(Duration.ofSeconds(120)).build();
            
            
            WebClient webClient = WebClient.builder().baseUrl(complaintsBackend).clientConnector(new ReactorClientHttpConnector(HttpClient.create(provider))).build();
            //WebClient webClient = WebClient.builder().baseUrl(complaintsBackend).build();

            Flux<Complaint> complaintsFlux = webClient.get().uri("/complaints").retrieve().bodyToFlux(Complaint.class).log();  
            List<Complaint> complaints = complaintsFlux.collect(Collectors.toList()).share().block(); 

            //Mono<Complaint[]> cList = webClient.get().uri("/complaints").accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Complaint[].class).log();
            //Complaint[] complaints = cList.block();
            */

            logger.info("Reading REST backend for Complaints...");
            URL url = new URL(complaintsBackend + "/complaints");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();

            List<Complaint> complaints = new ArrayList<Complaint>();
            
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
                String inline = "";
                Scanner scanner = new Scanner(url.openStream());
            
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                JSONParser parse = new JSONParser();
                JSONArray arr = (JSONArray) parse.parse(inline);

                Gson gson = new Gson();

                logger.info(arr.size() + " Complaints obtained");
                
                Iterator<JSONObject> iterator = arr.iterator();
                while(iterator.hasNext()) {
                    Complaint compl = gson.fromJson(iterator.next().toJSONString(), Complaint.class);
                    complaints.add(compl);
                }
                
                scanner.close();
            }


            model.addAttribute("complaints", complaints);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "complaints";
    }
}