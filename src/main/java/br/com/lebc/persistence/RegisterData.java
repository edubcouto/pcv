package br.com.lebc.persistence;

import java.util.List;

import br.com.lebc.beans.LogisticMesh;
import br.com.lebc.exception.DataException;

/**
 * Register data interface, grant that we can change what kind of persistence we use.
 * 
 * @author Luiz Couto(bittencourt.vr@gmail.com)
 */
public interface RegisterData {
	
	/** 
	* Read all persisted Mesh 
	* @return A list containing all LogisticMesh inserted to the system 
	* @throws DataException
	*/ 
	List<LogisticMesh> readPersistedData() throws DataException;
	
	/** 
	* Read all persisted Mesh from file system 
	* @param The new mesh to be inserted at persisted data
	* @return A list containing all LogisticMesh inserted to the system 
	* @throws DataException
	*/ 
	List<LogisticMesh> insertMesh(LogisticMesh newMesh) throws DataException;

	/** 
	* Delete a Mesh and persist them 
	* @param Mesh name to be removed
	* @return A list containing all LogisticMesh inserted to the system 
	* @throws DataException
	*/ 
	List<LogisticMesh> deleteMesh(String meshName)throws DataException;

	/** 
	* Persist entire Mesh list 
	* @param List Mesh to be persisted
	* @return void
	* @throws DataException
	*/ 
	void persistMeshList(List<LogisticMesh> listMesh) throws DataException;
}
