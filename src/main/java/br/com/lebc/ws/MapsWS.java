package br.com.lebc.ws;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lebc.beans.LogisticMesh;
import br.com.lebc.exception.BusinessException;
import br.com.lebc.exception.DataException;
import br.com.lebc.services.MapsBusinessLogicIf;


/**
 * This class exposes the WebService to manipulate LogisticMesh.
 * 
 * @author Luiz Couto(bittencourt.vr@gmail.com)
 */
@Component
@Path("/maps")
public class MapsWS {

	@Autowired
	MapsBusinessLogicIf mapsBusinessLogic;

	/** 
	* Return all maps
	* @return A list containing all LogisticMesh inserted to the system 
	* @throws DataException, BusinessException
	*/ 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<LogisticMesh> getAllMap() throws DataException, BusinessException {
    	return mapsBusinessLogic.getAll();
    }

    
	/** 
	* Return one specific map
	* @param Map name
	* @return The map if it exists 
	* @throws DataException, BusinessException
	*/ 
    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public LogisticMesh getSpecificMap(@PathParam("name") String name) throws DataException, BusinessException {
    	return mapsBusinessLogic.getOne(name);
    }

	/** 
	* Insert a new map
	* @param A complete map
	* @return A list containing all LogisticMesh inserted to the system, after insert the new one 
	* @throws DataException, BusinessException
	*/ 
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<LogisticMesh> insertNewMesh(LogisticMesh map, @Context final HttpServletResponse response) throws DataException, BusinessException {
    	return mapsBusinessLogic.insertNew(map);
    }
 
	/** 
	* Delete map by name
	* @param map name
	* @return A list containing all LogisticMesh inserted to the system, after delete completed 
	* @throws DataException, BusinessException
	*/ 
    @DELETE
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LogisticMesh> deleteMesh(@PathParam("name") String name) throws DataException, BusinessException{
    	return mapsBusinessLogic.deleteOne(name);
    }
    
}

