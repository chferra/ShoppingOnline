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
 * @author Luca
 */
@Path("carrello")
public class addProdottoACarrello {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of addProdottoACarrello
     */
    public addProdottoACarrello() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Response addProdottoACarrello(String jsonBody) {
        if (!DatabaseConnector.getIstance().isConnected())
            throw new WebApplicationException("failed to connect to db", 500);
            
        try {

            ExtendableBean bean = new ObjectMapper()
                    .readerFor(ExtendableBean.class)
                    .readValue(jsonBody);
            
			
			
            Integer codNegozio = Integer.parseInt(bean.getProperties().get("codNegozio"));
            String nome = bean.getProperties().get("nome");
			String descrizione = bean.getProperties().get("descrizione");
			Integer prezzo = Integer.parseInt(bean.getProperties().get("prezzo"));
			Integer codUtente = Integer.parseInt(bean.getProperties().get("codUtente"));
			Integer codNegozioProdotto = Integer.parseInt(bean.getProperties().get("codNegozioProdotto"));
            
			if(codNegozio == codNegozioProdotto){
				if (nome == null || nome.isEmpty() || codNegozioProdotto == null || codNegozioProdotto.toString().isEmpty() || prezzo == null || prezzo.toString().isEmpty() || 
					descrizione == null || descrizione.isEmpty() || codUtente == null || codUtente.toString().isEmpty())
						throw new WebApplicationException(Response.Status.BAD_REQUEST);

				String sql = "INSERT INTO carrello (codUtente, codNegozioProdotto, nome, descrizione, prezzo) "
							+ "VALUES ('" + codUtente + "', '" + codNegozioProdotto + "', '" + nome + "', '" + descrizione + "', '" + prezzo + "')";
				
				PreparedStatement stmt = DatabaseConnector.getIstance().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.execute();
				ResultSet rs = stmt.getGeneratedKeys();
			}
			
            
            
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