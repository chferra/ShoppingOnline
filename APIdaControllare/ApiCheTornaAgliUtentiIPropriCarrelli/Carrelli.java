/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import db.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.*;
import javax.ws.rs.*;
import java.util.ArrayList;
import org.apache.commons.codec.digest.DigestUtils;


/**
 * REST Web Service
 *
 * @author Marco
 */
@Path("carrelli")
public class carrelli {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of carrelli
     */
    public carrelli() {
    }

    @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

    public Response carrelli(@QueryParam("idUtente")String idUtente) {
        try {
			String sql="";
			
            if (idUtente == null || idUtente.isEmpty())
                throw new WebApplicationException(Response.Status.BAD_REQUEST);  
            
            if (!DatabaseConnector.getIstance().isConnected())
                throw new WebApplicationException("failed to connect to db", 500);
            
			sql = "SELECT * FROM carrello inner join comprende on carrello.id=comprende.CodCarrello inner join prodotto on comprende.CodProdotto = prodotto.id WHERE carrello.CodUtente='" + idUtente + "'";//GROUP BY carrello.CodNegozio
						
			ResultSet rs = DatabaseConnector.getIstance().query(sql);
            ArrayList<String> ris = new ArrayList<String>();
            while (rs.next()) 
            {
				String negozio = rs.getString("idNegozio");
                String nome = rs.getString("nome");
                String descrizione = rs.getString("descrizione");
                String prezzo = rs.getString("prezzo");
                ris.add(negozio+nome+descrizione+prezzo+"\n");
            }         

			StringBuilder sb = new StringBuilder();
			for (String s : ris)
			{
			sb.append(s);
			}

            return Response
                .status(Response.Status.OK)
                .entity(sb.toString())
                .build();
            
            
            
        } catch (SQLException ex) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);  
        } catch (JsonProcessingException ex) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST); 
        }

    }
}