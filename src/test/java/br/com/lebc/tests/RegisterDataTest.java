package br.com.lebc.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.lebc.beans.LogisticMesh;
import br.com.lebc.beans.LogisticMeshItem;
import br.com.lebc.exception.DataException;
import br.com.lebc.persistence.RegisterData;

public class RegisterDataTest extends TestConfig{

	private static final String B = "B";
	private static final String A = "A";
	private static final long _10L = 10L;
	private static final String TEST = "Test";
	private static final String TEST_2 = "Test 2";
	
	@Autowired
	RegisterData persistence;
	
	@Test
    public void testWired() {
    	Assert.assertNotNull(persistence);
    }
	
	@Test
	public void writeReadData() throws DataException{
		List<LogisticMesh> listMesh=new ArrayList<LogisticMesh>();
		LogisticMesh map=new LogisticMesh();
		
		List<LogisticMeshItem> items=new ArrayList<LogisticMeshItem>();
		LogisticMeshItem item=new LogisticMeshItem();
		
		item.setDestination(B);
		item.setSource(A);
		item.setDistance(_10L);
		
		items.add(item);
		
		map.setRoads(items);
		map.setName(TEST);
		
		listMesh.add(map);
		
		persistence.persistMeshList(listMesh);
		
		List<LogisticMesh> returnedData=persistence.readPersistedData();
		
		Assert.assertNotNull(returnedData);
		
		Assert.assertEquals(1, returnedData.size());
		
		LogisticMesh mapNew=new LogisticMesh();
		mapNew.setName(TEST_2);
		
		persistence.insertMesh(mapNew);
		
		returnedData=persistence.readPersistedData();
		
		Assert.assertNotNull(returnedData);
		
		Assert.assertEquals(2, returnedData.size());
		
		persistence.deleteMesh(TEST);
		
		returnedData=persistence.readPersistedData();
		
		Assert.assertNotNull(returnedData);
		
		Assert.assertEquals(1, returnedData.size());
		
		Assert.assertEquals(TEST_2,returnedData.get(0).getName());
		
	}
	
}
