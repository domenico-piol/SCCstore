package name.piol.demo.sccstore.qcomplaints;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "complaints")
@Data
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compl_id")
    Long complId;

    String complaint;
}

/*
package name.piol.demo.sccstore.qcomplaints;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Complaint {
    Long complId;
    String complaint;

    public Complaint(long complId, String complaint) {
		this.complId = complId;
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
    */
