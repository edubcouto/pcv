package br.com.lebc.services;

import java.util.List;

import br.com.lebc.beans.LogisticMesh;
import br.com.lebc.exception.BusinessException;
import br.com.lebc.exception.DataException;

/**
 * Host of the business rules about manipulating Maps.
 * 
 * @author Luiz Couto(bittencourt.vr@gmail.com)
 */
public interface MapsBusinessLogicIf {

	/** 
	* Get all maps from storage
	* @return List of LogisticMesh
	* @throws DataException, BusinessException
	*/ 
	public List<LogisticMesh> getAll() throws DataException,BusinessException;
	
	/** 
	* Get a map from storage
	* @param map name
	* @return A LogisticMesh
	* @throws DataException, BusinessException
	*/ 	
	public LogisticMesh getOne(String name)throws DataException,BusinessException;

	/** 
	* Insert a new map and return all data
	* @param a new Logistic Mesh
	* @return List of LogisticMesh
	* @throws DataException, BusinessException
	*/ 
	public List<LogisticMesh> insertNew(LogisticMesh mesh)throws DataException,BusinessException;
	
	/** 
	* Delete one map and return all data
	* @param map name
	* @return List of LogisticMesh
	* @throws DataException, BusinessException
	*/ 
	public List<LogisticMesh> deleteOne(String name)throws DataException,BusinessException;
	
}
