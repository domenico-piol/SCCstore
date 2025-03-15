package name.piol.demo.sccstore.qcomplaints;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/complaints")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QServerlessResource {

    @Inject
    ComplaintsRepository complaintsRepository;

    // Retrieve all tasks
    @GET
    public List<Complaint> list() {
        //System.out.println("ALL: " + complaintsRepository.listAll());
        return complaintsRepository.listAll();
    }
}


/*

package name.piol.demo.sccstore.qcomplaints;

import java.util.List;
import java.util.logging.Logger;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import javax.inject.Inject;
import io.agroal.api.AgroalDataSource;
import jakarta.inject.Inject;

@Path("/")
public class QServerlessResource {
    private static final Logger LOG = Logger.getLogger(QServerlessResource.class.getName());

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
    public Response all() {
        List<Complaint> ml = new ArrayList<Complaint>();

        try {
            con = ds.getConnection();
            stmt = con.createStatement();
			rs = stmt.executeQuery("select compl_id, complaint from complaints");
            while(rs.next()) {
                ml.add(new Complaint(rs.getInt("compl_id"), rs.getString("complaint")));
			}

            LOG.info(ml.size() + " Complaints retrieved");
        } catch (SQLException e) {
			e.printStackTrace();
            return Response.status(500).entity("SQL error while reading data").build();
		} finally{
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return Response.status(500).entity("an error occurred").build();
            }
        }

        return Response.status(200).entity(ml).build();
    }

    @Path("/complaint")
    @POST
    public Response add(@FormParam("compl") String compl) {
        System.out.println("........... "+"INSERT INTO complaints (complaint) VALUES ('" + compl + "');");
        try {
            con = ds.getConnection();
            con.setAutoCommit(true);
            stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO complaints (complaint) VALUES ('" + compl + "');");
        } catch (SQLException e) {
			e.printStackTrace();
            return Response.status(500).entity("SQL error while adding record").build();
		} finally{
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return Response.status(500).entity("an error occurred").build();
            }
        }

        return Response.status(201).entity("complaint added").build();
    }

    @Path("/complaint")
    @DELETE
    public Response delete(@FormParam("id") String id) {
        System.out.println("........... "+"DELETE FROM complaints where compl_id=" + id + ";");

        try {
            con = ds.getConnection();
            con.setAutoCommit(true);
            stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM complaints where compl_id=" + id + ";");
        } catch (SQLException e) {
			e.printStackTrace();
            return Response.status(500).entity("SQL error while deleting record").build();
		} finally{
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return Response.status(500).entity("an error occurred").build();
            }
        }

        return Response.status(200).entity("complaint " + id + " deleted").build();
    }

}
*/