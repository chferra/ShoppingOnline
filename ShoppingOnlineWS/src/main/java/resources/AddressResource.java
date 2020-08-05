/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import authentication.Authenticated;
import authentication.SimplePrincipal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;
import resources.entities.Address;
import utils.DatabaseConnector;

/**
 * REST Web Service
 *
 * @author Chris
 */
@Path("address")
public class AddressResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AddressResource
     */
    public AddressResource() {
    }

    /**
     * Retrieves representation of an instance of resources.AddressResource
     *
     * @return an instance of java.lang.String
     */
    @Authenticated
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    public Response getAddressesOfUser(@Context SecurityContext principal) {
        if (!DatabaseConnector.getIstance().isConnected()) {
            throw new WebApplicationException("failed to connect to db", 500);
        }

        Connection conn = DatabaseConnector.getIstance().getConnection();
        try {
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();

            Integer userID = ((SimplePrincipal) principal.getUserPrincipal()).getId();

            String sql = "SELECT * FROM addresses WHERE IdUser='" + userID + "'";

            ResultSet rs = st.executeQuery(sql);
            List<Address> addresses = new ArrayList<>();
            Address primaryAddress = null;

            if (!rs.next()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                do {
                    if (rs.getBoolean("primaryAddress")) {
                        primaryAddress = new Address(rs.getInt("ID"), rs.getString("addressee"), rs.getString("phone"), rs.getString("country"), rs.getString("province"),
                                rs.getString("city"), rs.getString("street"), rs.getString("number"), rs.getString("zipCode"), rs.getBoolean("primaryAddress"), rs.getInt("IdUser"));
                    } else {
                        addresses.add(new Address(rs.getInt("ID"), rs.getString("addressee"), rs.getString("phone"), rs.getString("country"), rs.getString("province"),
                                rs.getString("city"), rs.getString("street"), rs.getString("number"), rs.getString("zipCode"), rs.getBoolean("primaryAddress"), rs.getInt("IdUser")));
                    }

                } while (rs.next());
                addresses.add(0, primaryAddress);
            }

            return Response.status(Response.Status.OK).entity(addresses).build();
        } catch (SQLException | JSONException ex) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @Authenticated
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateDefaultAddress(@Context SecurityContext principal, String body) {
        if (!DatabaseConnector.getIstance().isConnected()) {
            throw new WebApplicationException("failed to connect to db", 500);
        }

        Connection conn = DatabaseConnector.getIstance().getConnection();
        try {
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();

            Integer addressID = Integer.parseInt(body);
            Integer userID = ((SimplePrincipal) principal.getUserPrincipal()).getId();

            String sql =  "UPDATE addresses "
                        + "SET "
                        +   "primaryAddress = CASE WHEN ID = " + addressID + " THEN '1' ELSE '0' END "
                        + "WHERE "
                        +    "IdUser = " + userID;
                                  
            st.execute(sql, Statement.RETURN_GENERATED_KEYS);

            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (SQLException | JSONException ex) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

}
