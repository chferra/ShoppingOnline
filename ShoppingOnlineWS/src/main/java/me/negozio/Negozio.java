/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.negozio;

import me.login.*;
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
 * @author Samuele Peduzz
 */
@Path("/")
public class Negozio {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public Negozio() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/negozio/me/inserisciNegozio/")
    public Response insertNegozio(@FormParam("nome")String nome, @FormParam("codIndirizzo")String codIndirizzo) {
        try {
            if (nome == null || nome.isEmpty() || codIndirizzo == null || codIndirizzo.isEmpty())
                throw new WebApplicationException(Response.Status.BAD_REQUEST);  
                
            ResultSet rs = DatabaseConnector.getIstance().query("INSERT INTO Negozio (*CodIndirizzo, nome) VALUES (" + codIndirizzo + "," + nome + ")");
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
    
    public Response deleteNegozio(@QueryParam("ID")String ID) {
        try {
            if (ID == null || ID.isEmpty())
                throw new WebApplicationException(Response.Status.BAD_REQUEST);  
                
            ResultSet rs = DatabaseConnector.getIstance().query("DELETE FROM Negozio WHERE ID = " + ID);
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
