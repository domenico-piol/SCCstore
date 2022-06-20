package name.piol.demo.sccstore.common;

public class SCCUser {
    
    private String userId;

    public SCCUser() {
        super();
    }

    public SCCUser(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SCCUser [userId=" + userId + "]";
    }
}
