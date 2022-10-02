package name.piol.demo.sccstore.serverless;

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
