package name.piol.demo.sccstore.ui.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "compliants")
public class Compliant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long complId;

    private String compliant;


    public Compliant() {
    }

    public Compliant(String compliant) {
        this.compliant = compliant;
    }


    public Long getComplId() {
        return complId;
    }


    public void setComplId(Long complId) {
        this.complId = complId;
    }


    public String getCompliant() {
        return compliant;
    }


    public void setCompliant(String compliant) {
        this.compliant = compliant;
    }

    
}
