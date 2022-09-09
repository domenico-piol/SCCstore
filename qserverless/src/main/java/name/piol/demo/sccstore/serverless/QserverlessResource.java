package name.piol.demo.sccstore.serverless;

import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;
import io.agroal.api.AgroalDataSource;

@Path("/")
public class QserverlessResource {

    @Inject
    AgroalDataSource ds;

    Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;

    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @Path("/complaints")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<String> all() {
        List<String> ml = new ArrayList<String>();   

        try {
            con = ds.getConnection();
            stmt = con.createStatement();
			rs = stmt.executeQuery("select compl_id, complaint from complaints");
            while(rs.next()) {
                ml.add("COMPL ID="+rs.getInt("compl_id")+", COMPLAINT="+rs.getString("complaint"));
			}
        } catch (SQLException e) {
			e.printStackTrace();
		} finally{
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ml;
    }
}