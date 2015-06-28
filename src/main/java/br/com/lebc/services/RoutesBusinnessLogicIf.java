package br.com.lebc.services;

import java.math.BigDecimal;

import br.com.lebc.beans.Route;
import br.com.lebc.exception.BusinessException;
import br.com.lebc.exception.DataException;

/**
 * Host of the business rules about manipulating Routes.
 * 
 * @author Luiz Couto(bittencourt.vr@gmail.com)
 */
public interface RoutesBusinnessLogicIf {
	/** 
	* Find the best route from all maps
	* @param source
	* @param destination
	* @param fuelcost
	* @param autonomy
	* @return A Route 
	* @throws DataException, BusinessException
	*/ 
	public Route getBestFromAll(String source,String destination, BigDecimal fuelCost, BigDecimal autonomy) throws DataException, BusinessException;
	/** 
	* Find the best route from a selected map
	* @param mapName
	* @param source
	* @param destination
	* @param fuelcost
	* @param autonomy
	* @return A Route 
	* @throws DataException, BusinessException
	*/	
	public Route getBestFromOne(String mapName,String source,String destination, BigDecimal fuelCost, BigDecimal autonomy) throws DataException, BusinessException;
}
