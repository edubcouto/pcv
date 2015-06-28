package br.com.lebc.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.lebc.beans.LogisticMesh;
import br.com.lebc.exception.BusinessException;
import br.com.lebc.exception.DataException;
import br.com.lebc.persistence.RegisterData;
import br.com.lebc.services.MapsBusinessLogicIf;

public class MapsServiceTest extends TestConfig{

	private static final long _2L = 2L;

	private static final long _3L = 3L;

	private static final String TEST3 = "test3";

	private static final String TEST2 = "test2";

	private static final long _1L = 1L;

	private static final String TEST1 = "test1";

	private static final String NOT_FOUND_NAME = "wayToTheMoon";

	@Autowired
	MapsBusinessLogicIf mapsBusinnesLogic;
	
	@Autowired
	RegisterData persistence;
	
	@Test
    public void testWired() {
    	Assert.assertNotNull(mapsBusinnesLogic);
    }
	
	@Test
	public void basicManipulationTest() throws DataException, BusinessException{
		persistence.persistMeshList(new ArrayList<LogisticMesh>());
		
		LogisticMesh map=new LogisticMesh();
		
		map.setName(TEST1);
		
		List<LogisticMesh> returnedData= mapsBusinnesLogic.insertNew(map);
		
		Assert.assertNotNull(returnedData);
		Assert.assertEquals(_1L,returnedData.size());
		
		LogisticMesh map2=new LogisticMesh();
		
		map2.setName(TEST2);
		
		returnedData= mapsBusinnesLogic.insertNew(map2);
		
		LogisticMesh map3=new LogisticMesh();
		
		map3.setName(TEST3);
		
		returnedData= mapsBusinnesLogic.insertNew(map3);
		
		Assert.assertNotNull(returnedData);
		Assert.assertEquals(_3L,returnedData.size());
		
		returnedData=mapsBusinnesLogic.getAll();
		
		Assert.assertNotNull(returnedData);
		Assert.assertEquals(_3L,returnedData.size());
		
		LogisticMesh getOneTest=mapsBusinnesLogic.getOne(TEST2);
		Assert.assertNotNull(getOneTest);
		
		mapsBusinnesLogic.deleteOne(TEST2);

		returnedData=mapsBusinnesLogic.getAll();
		
		Assert.assertNotNull(returnedData);
		Assert.assertEquals(_2L,returnedData.size());
	}
	
	@Test(expected=DataException.class)
	public void notFoundTest() throws DataException, BusinessException{
		mapsBusinnesLogic.getOne(NOT_FOUND_NAME);
	}
}
