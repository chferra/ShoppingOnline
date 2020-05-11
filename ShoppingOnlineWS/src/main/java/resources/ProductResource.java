/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.DatabaseConnector;
import utils.ExtendableBean;

/**
 * REST Web Service
 *
 * @author Chris
 */
@Path("product")
public class ProductResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProductResource
     */
    public ProductResource() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerProduct(String jsonBody) {
        if (!DatabaseConnector.getIstance().isConnected())
            throw new WebApplicationException("failed to connect to db", 500);
            
        try {

            ExtendableBean bean = new ObjectMapper()
                    .readerFor(ExtendableBean.class)
                    .readValue(jsonBody);
            
            String nome = bean.getProperties().get("nome");
            String descrizione = bean.getProperties().get("descrizione");
            Float prezzo = Float.parseFloat(bean.getProperties().get("prezzo"));
            Integer idNegozio = Integer.parseInt(bean.getProperties().get("IdNegozio"));
            
            if (nome == null || nome.isEmpty() || descrizione == null || descrizione.isEmpty()
                    || prezzo == null || idNegozio == null)
                throw new WebApplicationException(Response.Status.BAD_REQUEST);

            String sql = "INSERT INTO prodotti (nome, descrizione, prezzo, IdNegozio) "
                    + "VALUES ('" + nome + "', '" + descrizione + "', '" + prezzo + "', '" + idNegozio + "')";
            
            
            PreparedStatement stmt = DatabaseConnector.getIstance().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            
            int newProductId = 0;
            if (rs.next()) 
                newProductId = rs.getInt(1);

            Map<String, String> response = new HashMap();
            response.put("IdProdotto", String.valueOf(newProductId));
            
            return Response
                .status(Response.Status.OK)
                .entity(new ObjectMapper().writeValueAsString(response))
                .build();
            
            
        } catch (SQLException ex) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR); 
        } catch (JsonProcessingException ex) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST); 
        }

    }
    
}
