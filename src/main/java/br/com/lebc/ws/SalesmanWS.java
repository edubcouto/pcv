package br.com.lebc.ws;

import java.math.BigDecimal;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lebc.beans.Route;
import br.com.lebc.exception.BusinessException;
import br.com.lebc.exception.DataException;
import br.com.lebc.services.RoutesBusinnessLogicIf;

/**
 * This class exposes the WebService to find the best route.
 * 
 * @author Luiz Couto(bittencourt.vr@gmail.com)
 */
@Component
@Path("/routes")
public class SalesmanWS {

	@Autowired
	RoutesBusinnessLogicIf routesBusinnesLogic;

	/** 
	* Generate routes to one map and return it
	* @param mapname
	* @param source
	* @param destination
	* @param autonomy
	* @param fuelcost
	* @return The best route found  
	* @throws DataException, BusinessException
	*/ 
    @GET
    @Path("/simple/mapname/{mapname}/source/{source}/destination/{destination}/autonomy/{autonomy}/fuelcost/{fuelcost}")
    @Produces(MediaType.APPLICATION_JSON)
    public Route getRoute(@PathParam("mapname") String mapname,@PathParam("source") String source,@PathParam("destination") String destination,@PathParam("autonomy") Double autonomy,@PathParam("fuelcost") Double fuelcost) throws DataException, BusinessException {
    	return routesBusinnesLogic.getBestFromOne(mapname,source, destination, new BigDecimal(fuelcost), new BigDecimal(autonomy));
    }

	/** 
	* Generate routes from all maps and return the best one
	* @param source
	* @param destination
	* @param autonomy
	* @param fuelcost
	* @return The best route found  
	* @throws DataException, BusinessException
	*/ 
    @GET
    @Path("/multiple/source/{source}/destination/{destination}/autonomy/{autonomy}/fuelcost/{fuelcost}")
    @Produces(MediaType.APPLICATION_JSON)
    public Route getRouteMultiple(@PathParam("source") String source,@PathParam("destination") String destination,@PathParam("autonomy") Double autonomy,@PathParam("fuelcost") Double fuelcost) throws DataException, BusinessException {
    	return routesBusinnesLogic.getBestFromAll(source, destination, new BigDecimal(fuelcost), new BigDecimal(autonomy));
    }
    
}
