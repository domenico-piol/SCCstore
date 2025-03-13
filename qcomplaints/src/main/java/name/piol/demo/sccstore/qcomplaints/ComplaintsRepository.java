package name.piol.demo.sccstore.qcomplaints;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ComplaintsRepository implements PanacheRepository<Complaint> {}
