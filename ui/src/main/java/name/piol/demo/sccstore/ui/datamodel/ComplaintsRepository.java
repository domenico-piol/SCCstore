package name.piol.demo.sccstore.ui.datamodel;

import java.util.List;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintsRepository { //extends CrudRepository<Complaint, Long> {
    
    List<Complaint> findByComplaint(String complaint);

    Complaint findById(long id);
}
