/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista;


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
 * @author colo
 */
@Path("/")
public class ListaResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public ListaResource() {
    }

    /**
     * Retrieves representation of an instance of war.UserResource
     * @return an instance of java.lang.String
     */
    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    //@Consumes(MediaType.APPLICATION_JSON)
    //@Path("/listaProdotti")
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/listaProdotti")
    public Response listaProdotti(@QueryParam("CostoMin")String CostoMin, @QueryParam("CostoMax")String CostoMax, 
            @QueryParam("Categoria")String Categoria, @QueryParam("idNegozio")String idNegozio) {
        try {
            //if (idNegozio == null)
                //throw new WebApplicationException(Response.Status.BAD_REQUEST);  
            String sql = "SELECT * FROM Prodotto inner join negozio on prodotto.CodNegozio = negozio.ID WHERE ID='" + idNegozio + "'";
            
            
//            if (Categoria != null){
//                sql = "SELECT Prodotto.nome FROM Prodotto inner join Appartiene on Prodotto.ID = Appartiene.CodProdotto inner join Categoria on Appartiene.CodCategoria = Categoria.ID inner join negozio on prodotto.CodNegozio = negozio.ID where negozio.ID='"+ idNegozio +"' AND Categoria.nome = '"+ Categoria +"'";
//            }
//            
//            if (CostoMin != null){
//                sql += " AND prezzo > '" + CostoMin +"'";
//            }
//            
//            if (CostoMax != null){
//                sql += " AND prezzo < '" + CostoMax + "'";
//            }       
            
            
            
            ResultSet rs = DatabaseConnector.getIstance().query(sql);
            String ris ="";
            if (rs.next()) 
            {
                String nome = rs.getString("nome");
                String descrizione = rs.getString("descrizione");
                int prezzo = rs.getInt("prezzo");
                ris+=nome+descrizione+prezzo+"\n";
            }         

            return Response
                .status(Response.Status.OK)
                .entity(ris)
                .build();
            
       } catch (Exception ex) {
           throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR); 
        } 
    }}