/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerStore(String jsonBody) {
        if (!DatabaseConnector.getIstance().isConnected())
            throw new WebApplicationException("failed to connect to db", 500);
            
        try {

            ExtendableBean bean = new ObjectMapper()
                    .readerFor(ExtendableBean.class)
                    .readValue(jsonBody);
            
            String nome = bean.getProperties().get("nome");
            Integer idIndirizzo = Integer.parseInt(bean.getProperties().get("IdIndirizzo"));
            Integer idUtente = Integer.parseInt(bean.getProperties().get("IdUtente"));
            
            if (nome == null || nome.isEmpty() || idIndirizzo == null || idUtente == null) {
                System.out.println("params error");
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
                

            String sql = "INSERT INTO negozio (nome, CodUtente, CodIndirizzo) "
                    + "VALUES ('" + nome + "', '" + idUtente + "', '" + idIndirizzo + "')";
            
            PreparedStatement stmt = DatabaseConnector.getIstance().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
             System.out.println("SQL error");
            throw new WebApplicationException(Response.Status.BAD_REQUEST);  
           
        } catch (JsonProcessingException ex) {
            System.out.println("JSON error");
            throw new WebApplicationException(Response.Status.BAD_REQUEST); 
        }

    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    //@Path("/login")
    public Response login(@QueryParam("idNegozio")String idNegozio) {
        try {
            if (idNegozio == null || idNegozio.isEmpty())
                throw new WebApplicationException(Response.Status.BAD_REQUEST);  
            
            if (!DatabaseConnector.getIstance().isConnected())
                throw new WebApplicationException("failed to connect to db", 500);
                
            PreparedStatement ps = DatabaseConnector.getIstance().getConnection().prepareStatement("DELETE FROM Negozio WHERE ID = " + idNegozio);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);          
            
            Map<String, String> response = new HashMap();
            response.put("IdNegozio", String.valueOf(idNegozio));
            
            return Response
                .status(Response.Status.OK)
                .entity(new ObjectMapper().writeValueAsString(response))
                .build();
            
       } catch (SQLException | JsonProcessingException ex) {
           throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR); 
        } 
    }
}
