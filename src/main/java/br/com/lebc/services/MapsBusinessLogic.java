package br.com.lebc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lebc.beans.LogisticMesh;
import br.com.lebc.exception.BusinessException;
import br.com.lebc.exception.DataException;
import br.com.lebc.persistence.RegisterData;

/**
 * Implementation of Business Logic requirements.
 * 
 * @author Luiz Couto(bittencourt.vr@gmail.com)
 */
@Component
public class MapsBusinessLogic implements MapsBusinessLogicIf{

	@Autowired
	RegisterData persistence;
	
	static private List<LogisticMesh> maps=null;
	
	public List<LogisticMesh> getAll() throws DataException,BusinessException{
		if(maps==null){
			maps=persistence.readPersistedData();
		}
		return maps;
	}
	
	public LogisticMesh getOne(String name)throws DataException,BusinessException{
		List<LogisticMesh> maps=this.getAll();
		if(maps!=null && maps.size()>0){
			for(LogisticMesh m:maps){
				if(m.getName().equalsIgnoreCase(name)){
					return m;
				}
			}
		}
		throw new DataException("Map not found");
	}
	
	public List<LogisticMesh> insertNew(LogisticMesh mesh)throws DataException,BusinessException{
		
		maps=persistence.insertMesh(mesh);
		return maps;
	}
	
	public List<LogisticMesh> deleteOne(String name)throws DataException,BusinessException{
		maps=persistence.deleteMesh(name);
		return maps;
	}
		
	

}
