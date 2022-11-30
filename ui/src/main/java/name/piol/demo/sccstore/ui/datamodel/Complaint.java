package name.piol.demo.sccstore.ui.datamodel;

/*import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
*/

//@Entity
//@Table(name = "complaints")
public class Complaint {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long complId;

    private String complaint;


    public Complaint() {
    }

    public Complaint(String complaint) {
        this.complaint = complaint;
    }


    public Long getComplId() {
        return complId;
    }


    public void setComplId(Long complId) {
        this.complId = complId;
    }


    public String getComplaint() {
        return complaint;
    }


    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    
}
