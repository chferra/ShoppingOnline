/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import authentication.Authenticated;
import authentication.JwtAuthenticationService;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
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
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import utils.DatabaseConnector;
import utils.UtilBase64Image;

/**
 * REST Web Service
 *
 * @author Chris
 */
@Path("picture")
public class PictureResource {

    @Context
    private UriInfo context;

    private String[] extensions = {"png", "jpg"};
    
    public PictureResource() {
    }
    
    @Authenticated
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response uploadPicture(String jsonBody, @Context SecurityContext principal) {
        if (!DatabaseConnector.getIstance().isConnected()) 
            throw new WebApplicationException("failed to connect to db", 500);
        
        Connection conn = DatabaseConnector.getIstance().getConnection();   
        
        try {
            Statement st = conn.createStatement();             
            
            JSONObject obj = new JSONObject(jsonBody);
            
            String imageName = obj.getString("imagePath");
            String base64ImageData = obj.getString("imageData");
            
            String imageExtension = FilenameUtils.getExtension(imageName);
            
            if (!Arrays.asList(extensions).contains(imageExtension))
                throw new WebApplicationException("unsupported file extension", 422);          
            
            String imagePath = "ShoppingOnlineStoredImages/" + new java.util.Date().getTime() + "." + imageExtension;
            
            UtilBase64Image.decoder(base64ImageData, imagePath);            
            
            String sql = "INSERT INTO pictures (path) "
                    + "VALUES ('" + imagePath + "')";    
                 
            st.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = st.getGeneratedKeys();
            
            int pictureID = 0;
            if (rs.next()) 
                pictureID = rs.getInt(1);
            
            return Response
                .status(Response.Status.OK)
                .entity(pictureID)
                .build();            
        } catch (SQLException ex) {
           throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR); 
        }
    }

   
}
