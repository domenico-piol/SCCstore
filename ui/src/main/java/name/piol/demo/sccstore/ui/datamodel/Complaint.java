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

    private String complaints;


    public Complaint() {
    }

    public Complaint(String complaints) {
        this.complaints = complaints;
    }


    public Long getComplId() {
        return complId;
    }


    public void setComplId(Long complId) {
        this.complId = complId;
    }


    public String getComplaint() {
        return complaints;
    }


    public void setComplaint(String complaints) {
        this.complaints = complaints;
    }

    
}
