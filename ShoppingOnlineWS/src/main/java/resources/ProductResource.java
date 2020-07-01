/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.digest.DigestUtils;
import utils.DatabaseConnector;

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
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts(@QueryParam("keyword")String keyword) {
        try {
            Connection conn = DatabaseConnector.getIstance().getConnection();
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM users WHERE name LIKE '%" + keyword + "%' AND password='";
            
            ResultSet rs = st.executeQuery(sql);

            if (!rs.next()) 
                throw new WebApplicationException(Response.Status.NOT_FOUND);      
            else {
                do {
                    int id = rs.getInt("ID");
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    int idShop = rs.getInt("IdShop");
                } while (rs.next());
            }
            
        } catch (SQLException ex) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR); 
        }
        return null;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerProduct(String jsonBody) {
//        if (!DatabaseConnector.getIstance().isConnected())
//            throw new WebApplicationException("failed to connect to db", 500);
//            
//        try {
//
//            ExtendableBean bean = new ObjectMapper()
//                    .readerFor(ExtendableBean.class)
//                    .readValue(jsonBody);
//            
//            String nome = bean.getProperties().get("nome");
//            String descrizione = bean.getProperties().get("descrizione");
//            Float prezzo = Float.parseFloat(bean.getProperties().get("prezzo"));
//            Integer idNegozio = Integer.parseInt(bean.getProperties().get("IdNegozio"));
//            
//            if (nome == null || nome.isEmpty() || descrizione == null || descrizione.isEmpty()
//                    || prezzo == null || idNegozio == null)
//                throw new WebApplicationException(Response.Status.BAD_REQUEST);
//
//            String sql = "INSERT INTO prodotti (nome, descrizione, prezzo, IdNegozio) "
//                    + "VALUES ('" + nome + "', '" + descrizione + "', '" + prezzo + "', '" + idNegozio + "')";
//            
//            
//            PreparedStatement stmt = DatabaseConnector.getIstance().getConnection(true).prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            stmt.execute();
//            ResultSet rs = stmt.getGeneratedKeys();
//            
//            int newProductId = 0;
//            if (rs.next()) 
//                newProductId = rs.getInt(1);
//
//            Map<String, String> response = new HashMap();
//            response.put("IdProdotto", String.valueOf(newProductId));
//            
//            return Response
//                .status(Response.Status.OK)
//                .entity(new ObjectMapper().writeValueAsString(response))
//                .build();
//            
//            
//        } catch (SQLException ex) {
//            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR); 
//        } catch (JsonProcessingException ex) {
//            throw new WebApplicationException(Response.Status.BAD_REQUEST); 
//        }
        return null;

    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    //@Path("/delete")
    public Response deleteProdottoCarrello(@QueryParam("id")String id) {
//        try {
//            if (id == null || id.isEmpty())
//                throw new WebApplicationException(Response.Status.BAD_REQUEST);  
//            
//            if (!DatabaseConnector.getIstance().isConnected())
//                throw new WebApplicationException("failed to connect to db", 500);
//                
//            String sql = "DELETE FROM carts_composition WHERE ID = " + id;
//            
//            PreparedStatement stmt = DatabaseConnector.getIstance().getConnection(false).prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            stmt.execute();
//            ResultSet rs = stmt.getGeneratedKeys();
//            
//            int newStoreId = 0;
//            if (rs.next()) 
//                newStoreId = rs.getInt(1);
//
//            Map<String, String> response = new HashMap();
//            response.put("IdComprende", id);
//            
//            return Response
//                .status(Response.Status.OK)
//                .entity(new ObjectMapper().writeValueAsString(response))
//                .build();
//            
//       } catch (SQLException ex) {
//           throw new WebApplicationException(Response.Status.BAD_REQUEST); 
//        } 
        return null;
    }
    
}
