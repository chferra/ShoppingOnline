/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.addProdotto;

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
 * @author Chris
 */
@Path("/")
public class addProdotto {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public addProdotto() {
    }

    /**
     * Retrieves representation of an instance of war.UserResource
     * @return an instance of java.lang.String
     */
    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    //@Consumes(MediaType.APPLICATION_JSON)
    //@Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addProdotto")
    public Response addProdotto(@FormParam("codNegozio")Integer codNegozio, @FormParam("nome")String nome, @FormParam("prezzo")Integer prezzo,
            @FormParam("categoria")String categoria, @FormParam("descrizione")String descrizione, @FormParam("pathImmagine")String pathImmagine) {
        try {
            if (codNegozio == null || codNegozio.toString().isEmpty() || nome == null || nome.isEmpty() || prezzo == null || prezzo.toString().isEmpty()
                    || categoria == null || categoria.isEmpty() || descrizione == null || descrizione.isEmpty() || pathImmagine == null || pathImmagine.isEmpty())
                throw new WebApplicationException(Response.Status.BAD_REQUEST);  
                
            ResultSet rs = DatabaseConnector.getIstance().query("INSERT INTO Prodotto (nome, prezzo, categoria, descrizione, immagine, codNegozio)" + 
                                                                "VALUES (" + nome + ", " + prezzo + ", " + categoria + ", " + descrizione + ", " + pathImmagine + ", " +
                                                                codNegozio + ")");
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
