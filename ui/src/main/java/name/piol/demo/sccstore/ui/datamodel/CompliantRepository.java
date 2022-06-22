package name.piol.demo.sccstore.ui.datamodel;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompliantRepository extends CrudRepository<Compliant, Long> {
    
    List<Compliant> findByCompliant(String compliant);

    Compliant findById(long id);
}
