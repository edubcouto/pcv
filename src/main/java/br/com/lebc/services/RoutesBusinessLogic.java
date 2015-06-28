package br.com.lebc.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lebc.beans.LogisticMesh;
import br.com.lebc.beans.Route;
import br.com.lebc.exception.BusinessException;
import br.com.lebc.exception.DataException;

/**
 * Implementation of Routes business logic.
 * 
 * @author Luiz Couto(bittencourt.vr@gmail.com)
 */
@Component
public class RoutesBusinessLogic implements RoutesBusinnessLogicIf {
	
	@Autowired 
	MapsBusinessLogic mapsService;
	
	@Autowired
	SalesmanSolution salesmanSolution;

	public Route getBestFromAll(String source,String destination, BigDecimal fuelCost, BigDecimal autonomy) throws DataException, BusinessException{
		List<LogisticMesh> maps=mapsService.getAll();
		
		Route bestRoute=null;
				
		for(LogisticMesh m:maps){
			try{
				Route currentRoute=salesmanSolution.solveIt(m, source, destination, autonomy, fuelCost);
				currentRoute.setMap(m.getName());
				
				if(bestRoute==null){
					bestRoute=currentRoute;
				}else{
					if(bestRoute.getTotalCost().compareTo(currentRoute.getTotalCost())>0){
						bestRoute=currentRoute;
					}
				}
			}catch(BusinessException e){
				
			}
		}

		if(bestRoute==null){
			throw new BusinessException("No Way To Route");
		}
		
		return bestRoute;
	}
	
	public Route getBestFromOne(String mapName,String source,String destination, BigDecimal fuelCost, BigDecimal autonomy) throws DataException, BusinessException{
				
		LogisticMesh selectedMap=mapsService.getOne(mapName);
		if(selectedMap!=null){
			Route bestRoute=salesmanSolution.solveIt(selectedMap,source,destination,autonomy,fuelCost);
			bestRoute.setMap(selectedMap.getName());
			return bestRoute;
		}
		
		return null;
	}
	
}
