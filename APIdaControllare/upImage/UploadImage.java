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
import utils.ExtendableBean;


/**
 * REST Web Service
 *
 * @author Marco
 */
@Path("image")
public class UploadImage {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UploadImage
     */
    public UploadImage() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Response UploadImage(String jsonBody) {
        if (!DatabaseConnector.getIstance().isConnected())
            throw new WebApplicationException("failed to connect to db", 500);
            
        try {

            ExtendableBean bean = new ObjectMapper()
                    .readerFor(ExtendableBean.class)
                    .readValue(jsonBody);
            
            String percorso = bean.getProperties().get("percorso");
            Integer codNegozio = Integer.parseInt(bean.getProperties().get("codNegozio"));
            
            
            if (percorso == null || percorso.isEmpty() || codNegozio == null || codNegozio.isEmpty())
                throw new WebApplicationException(Response.Status.BAD_REQUEST);

            String sql = "INSERT INTO fotografie (percorso, CodNegozio) "
                    + "VALUES ('" + percorso + "', '" + CodNegozio + "')";
            
            PreparedStatement stmt = DatabaseConnector.getIstance().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            
            // int newStoreId = 0;
            // if (rs.next()) 
                // newStoreId = rs.getInt(1);

            // Map<String, String> response = new HashMap();
            // response.put("IdNegozio", String.valueOf(newStoreId));
            
            return Response
                .status(Response.Status.OK)
                .entity(new ObjectMapper().writeValueAsString(response))
                .build();
            
            
        } catch (SQLException ex) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);  
        } catch (JsonProcessingException ex) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST); 
        }

    }
}