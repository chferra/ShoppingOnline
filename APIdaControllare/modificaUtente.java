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
 * @author colo
 */
@Path("modificaUtente/")
public class modificaUtente {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of modificaUtente
     */
    public modificaUtente() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Response modificaUtente(String jsonBody) {
        if (!DatabaseConnector.getIstance().isConnected())
            throw new WebApplicationException("failed to connect to db", 500);
            
        try {

            ExtendableBean bean = new ObjectMapper()
                    .readerFor(ExtendableBean.class)
                    .readValue(jsonBody);
            
            String percorsoFoto = bean.getProperties().get("percorsoFoto");
            Integer id = Integer.parseInt(bean.getProperties().get("id"));
			String password = bean.getProperties().get("password");
			String email = bean.getProperties().get("email");
			String dataNascita = bean.getProperties().get("dataNascita");
            
            String sql = "";
            if (id == null || is.isEmpty())
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
			
			sql = "SELECT CodFotografia FROM `utenti` WHERE ID="+id;
			
			
			
			
			if (percorsoFoto == null || is.isEmpty())
			{
            ResultSet rs = DatabaseConnector.getIstance().query(sql);
            String ris ="";
            if (rs.next()) 
            {
                ris = rs.getString("CodFotografia");
                
            }
		            
				sql = "UPDATE Fotografia SET percorso='" + percorsoFoto + "' WHERE id ='" + ris + "'";
				DatabaseConnector.getIstance().query(sql);
			}
			
			if (password == null || is.isEmpty())
			{
				sql="UPDATE `utenti` SET password='"+ password + "'] WHERE ID='"+id+"'";
			}
			
			if (email == null || is.isEmpty())
			{
				sql="UPDATE `utenti` SET email='"+ email + "'] WHERE ID='"+id+"'";
			}
			
			if (dataNascita == null || is.isEmpty())
			{
				sql="UPDATE `utenti` SET dataNascita='"+ dataNascita + "'] WHERE ID='"+id+"'";
			}
			
			
            return Response
                .status(Response.Status.OK)
                .entity(id)
                .build();
            
            
        } catch (SQLException ex) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);  
        } catch (JsonProcessingException ex) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST); 
        }

    }
}