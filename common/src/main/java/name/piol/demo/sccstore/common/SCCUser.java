package name.piol.demo.sccstore.common;

public class SCCUser {
    
    private final String userId;

    public SCCUser(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }


    @Override
    public String toString() {
        return "SCCUser [userId=" + userId + "]";
    }
}
