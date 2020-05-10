/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.uploadImage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.*;
import javax.ws.rs.*;
import org.apache.commons.codec.digest.DigestUtils;


/**
 * REST Web Service
 *
 * @author Marco
 */
@Path("/")
public class UploadImage {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public UploadImage() {
    }

    /**
     * Retrieves representation of an instance of war.UserResource
     * @return an instance of java.lang.String
     */
    @GET

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/uploadImage")
    public Response upImage(@QueryParam("percorso")String percorso, @QueryParam("codNegozio")String codNegozio) {
        try {
            if (percorso == null || percorso.isEmpty() || codNegozio == null || codNegozio.isEmpty())
                throw new WebApplicationException(Response.Status.BAD_REQUEST);  
                
            ResultSet rs = DatabaseConnector.getIstance().query("INSERT INTO fotografie(percorso,CodNegozio)VALUES('"+ percorso +"','"+ codNegozio+")'");
            if (!rs.next()) 
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);          
            
            String cookie = "sadasdasfw34fa";
            return Response
                .status(Response.Status.OK)
                .entity(new ObjectMapper().writeValueAsString(cookie))
                .build();
            
       } catch (SQLException | JsonProcessingException ex) {
           throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR); 
        } 
    }
}
