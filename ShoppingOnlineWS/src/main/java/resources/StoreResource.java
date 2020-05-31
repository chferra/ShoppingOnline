/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import authentication.Authenticated;
import authentication.JwtAuthenticationService;
import authentication.SimplePrincipal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.PreparedStatement;
import utils.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.*;
import javax.ws.rs.*;
import org.apache.commons.codec.digest.DigestUtils;
import utils.ExtendableBean;

/**
 * REST Web Service
 *
 * @author Chris
 */
@Path("store")
public class StoreResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of StoreResource
     */
    public StoreResource() {
    }

    @Authenticated
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerStore(String jsonBody, @Context SecurityContext principal) {
        //((SimplePrincipal)principal.getUserPrincipal()).getNegoziante();
        //((SimplePrincipal)principal.getUserPrincipal()).getId();
        
        
        if (!DatabaseConnector.getIstance().isConnected()) {
            System.out.println("DB connection error !"); //NON RIMUOVERE
            throw new WebApplicationException("failed to connect to db", 500);
        }
            
            
        try {

            ExtendableBean bean = new ObjectMapper()
                    .readerFor(ExtendableBean.class)
                    .readValue(jsonBody);
            
            String nome = bean.getProperties().get("nome");
            Integer idIndirizzo = Integer.parseInt(bean.getProperties().get("IdIndirizzo"));
            Integer idUtente = Integer.parseInt(bean.getProperties().get("IdUtente"));
            
            if (nome == null || nome.isEmpty() || idIndirizzo == null || idUtente == null)
                throw new WebApplicationException(Response.Status.BAD_REQUEST);

            String sql = "INSERT INTO negozi (nome, IdUtente, IdIndirizzo) "
                    + "VALUES ('" + nome + "', '" + idUtente + "', '" + idIndirizzo + "')";
            
            PreparedStatement stmt = DatabaseConnector.getIstance().getConnection(true).prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            
            int newStoreId = 0;
            if (rs.next()) 
                newStoreId = rs.getInt(1);

            Map<String, String> response = new HashMap();
            response.put("IdNegozio", String.valueOf(newStoreId));
            
            return Response
                .status(Response.Status.OK)
                .entity(new ObjectMapper().writeValueAsString(response))
                .build();
            
            
        } catch (SQLException ex) {
            System.out.println("SQL error !"); //NON RIMUOVERE
            throw new WebApplicationException(Response.Status.BAD_REQUEST);  
        } catch (JsonProcessingException ex) {
            System.out.println("JSON syntax error !"); //NON RIMUOVERE
            throw new WebApplicationException(Response.Status.BAD_REQUEST); 
        }

    }
    
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    //@Path("/delete")
    public Response deleteStore(@QueryParam("id")String id) throws JsonProcessingException {
        try {
            if (id == null || id.isEmpty())
                throw new WebApplicationException(Response.Status.BAD_REQUEST);  
            
            if (!DatabaseConnector.getIstance().isConnected())
                throw new WebApplicationException("failed to connect to db", 500);
                
            String sql = "DELETE FROM shops WHERE ID = " + id;
            
            PreparedStatement stmt = DatabaseConnector.getIstance().getConnection(true).prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            
            int newStoreId = 0;
            if (rs.next()) 
                newStoreId = rs.getInt(1);

            Map<String, String> response = new HashMap();
            response.put("IdNegozio", id);
            
            return Response
                .status(Response.Status.OK)
                .entity(new ObjectMapper().writeValueAsString(response))
                .build();
            
       } catch (SQLException ex) {
           throw new WebApplicationException(Response.Status.BAD_REQUEST); 
        } 
    }
    
    
}
