package name.piol.demo.sccstore.ui.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
