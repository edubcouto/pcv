package br.com.lebc.tests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.lebc.beans.LogisticMesh;
import br.com.lebc.beans.LogisticMeshItem;
import br.com.lebc.beans.Route;
import br.com.lebc.exception.BusinessException;
import br.com.lebc.services.SalesmanSolution;

public class SalesmanLogicTest extends TestConfig{

	@Autowired
	SalesmanSolution salesman;
	
    @Test
    public void testWired() {
    	Assert.assertNotNull(salesman);
    }
    
    @Test
    public void basicTest() throws BusinessException{
		List<LogisticMeshItem> arcos=new ArrayList<LogisticMeshItem>();
		
		arcos.add(novoArco("A","B",10L));
		arcos.add(novoArco("B","D",15L));
		arcos.add(novoArco("A","C",20L));
		arcos.add(novoArco("C","D",30L));
		arcos.add(novoArco("B","E",50L));
		arcos.add(novoArco("D","E",30L));
		
		LogisticMesh map=new LogisticMesh();
		
		map.setRoads(arcos);
		map.setName("Teste do email");
		
		Route solucao=salesman.solveIt(map,"A","D",BigDecimal.TEN,new BigDecimal(2.5F));
		Assert.assertNotNull(solucao);
		Assert.assertNotNull(solucao.getNodes());
		Assert.assertEquals(3L, solucao.getNodes().size());
		BigDecimal cost=solucao.getTotalCost();
		cost.setScale(2, BigDecimal.ROUND_HALF_UP);
		Assert.assertTrue(cost.compareTo(new BigDecimal(6.25))==0);

    }

    @Test
    public void reverseBasicTest() throws BusinessException{
		List<LogisticMeshItem> arcos=new ArrayList<LogisticMeshItem>();
		
		arcos.add(novoArco("A","B",10L));
		arcos.add(novoArco("B","D",15L));
		arcos.add(novoArco("A","C",20L));
		arcos.add(novoArco("C","D",30L));
		arcos.add(novoArco("B","E",50L));
		arcos.add(novoArco("D","E",30L));
		
		LogisticMesh map=new LogisticMesh();
		
		map.setRoads(arcos);
		map.setName("Teste do email");
		
		Route solucao=salesman.solveIt(map,"D","A",BigDecimal.TEN,new BigDecimal(2.5F));
		Assert.assertNotNull(solucao);
		Assert.assertNotNull(solucao.getNodes());
		Assert.assertEquals(3L, solucao.getNodes().size());
		BigDecimal cost=solucao.getTotalCost();
		cost.setScale(2, BigDecimal.ROUND_HALF_UP);
		Assert.assertTrue(cost.compareTo(new BigDecimal(6.25))==0);

    }
    
    @Test
    public void oneNoWayRouteTest() throws BusinessException{
		List<LogisticMeshItem> arcos=new ArrayList<LogisticMeshItem>();
		
		arcos.add(novoArco("A","B",10L));
		arcos.add(novoArco("B","D",15L));
		arcos.add(novoArco("A","C",20L));
		arcos.add(novoArco("C","D",30L));
		arcos.add(novoArco("B","E",50L));
		arcos.add(novoArco("D","E",30L));
		arcos.add(novoArco("B","F",3L));
		
		LogisticMesh map=new LogisticMesh();
		
		map.setRoads(arcos);
		map.setName("Teste do email");
		
		Route solucao=salesman.solveIt(map,"A","D",BigDecimal.TEN,new BigDecimal(2.5F));
		Assert.assertNotNull(solucao);
		Assert.assertNotNull(solucao.getNodes());
		Assert.assertEquals(3L, solucao.getNodes().size());
		BigDecimal cost=solucao.getTotalCost();
		cost.setScale(2, BigDecimal.ROUND_HALF_UP);
		Assert.assertTrue(cost.compareTo(new BigDecimal(6.25))==0);

    }

    
    
    @Test
    public void bigNoWayRouteTest() throws BusinessException{
		List<LogisticMeshItem> arcos=new ArrayList<LogisticMeshItem>();
		
		arcos.add(novoArco("A","B",10L));
		arcos.add(novoArco("B","D",15L));
		arcos.add(novoArco("A","C",20L));
		arcos.add(novoArco("C","D",30L));
		arcos.add(novoArco("B","E",50L));
		arcos.add(novoArco("D","E",30L));
		arcos.add(novoArco("B","F",3L));
		arcos.add(novoArco("F","G",3L));
		arcos.add(novoArco("G","H",3L));
		arcos.add(novoArco("G","I",2L));
		arcos.add(novoArco("I","J",3L));
		
		LogisticMesh map=new LogisticMesh();
		
		map.setRoads(arcos);
		map.setName("Teste do email");
		
		Route solucao=salesman.solveIt(map,"A","D",BigDecimal.TEN,new BigDecimal(2.5F));
		Assert.assertNotNull(solucao);
		Assert.assertNotNull(solucao.getNodes());
		Assert.assertEquals(3L, solucao.getNodes().size());
		BigDecimal cost=solucao.getTotalCost();
		cost.setScale(2, BigDecimal.ROUND_HALF_UP);
		Assert.assertTrue(cost.compareTo(new BigDecimal(6.25))==0);

    }
    
    @Test
    public void basicTestAtoC() throws BusinessException{
		List<LogisticMeshItem> arcos=new ArrayList<LogisticMeshItem>();
		
		arcos.add(novoArco("A","B",10L));
		arcos.add(novoArco("B","D",15L));
		arcos.add(novoArco("A","C",20L));
		arcos.add(novoArco("C","D",30L));
		arcos.add(novoArco("B","E",50L));
		arcos.add(novoArco("D","E",30L));
		
		LogisticMesh map=new LogisticMesh();
		
		map.setRoads(arcos);
		map.setName("Teste do email");
		
		Route solucao=salesman.solveIt(map,"A","C",BigDecimal.TEN,new BigDecimal(2.5F));
		Assert.assertNotNull(solucao);
		Assert.assertNotNull(solucao.getNodes());
		Assert.assertEquals(2L, solucao.getNodes().size());
		BigDecimal cost=solucao.getTotalCost();
		cost.setScale(2, BigDecimal.ROUND_HALF_UP);
		Assert.assertTrue(cost.compareTo(new BigDecimal(5.00))==0);
    }
    
    @Test
    public void complexTestWithShortcut() throws BusinessException{
		List<LogisticMeshItem> arcos=new ArrayList<LogisticMeshItem>();
		
		arcos.add(novoArco("A","B",10L));
		arcos.add(novoArco("B","D",20L));
		arcos.add(novoArco("A","C",15L));
		arcos.add(novoArco("D","E",35L));
		arcos.add(novoArco("C","E",25L));
		arcos.add(novoArco("C","F",30L));
		arcos.add(novoArco("E","F",45L));
		arcos.add(novoArco("G","F",40L));
		arcos.add(novoArco("D","H",60L));
		arcos.add(novoArco("G","H",70L));
		arcos.add(novoArco("E","H",50L));
		
		LogisticMesh map=new LogisticMesh();
		
		map.setRoads(arcos);
		map.setName("Teste do email");
		
		Route solucao=salesman.solveIt(map,"A","H",BigDecimal.TEN,new BigDecimal(2.5F));
		Assert.assertNotNull(solucao);
		Assert.assertNotNull(solucao.getNodes());
		Assert.assertEquals(4L, solucao.getNodes().size());
		BigDecimal cost=solucao.getTotalCost();
		cost.setScale(2, BigDecimal.ROUND_HALF_UP);
		Assert.assertTrue(cost.compareTo(new BigDecimal(22.50))==0);
    }

    @Test
    public void reverseTest() throws BusinessException{
		List<LogisticMeshItem> arcos=new ArrayList<LogisticMeshItem>();
		
		arcos.add(novoArco("A","B",10L));
		arcos.add(novoArco("B","D",70L));
		arcos.add(novoArco("A","C",30L));
		arcos.add(novoArco("C","D",20L));
		
		LogisticMesh map=new LogisticMesh();
		
		map.setRoads(arcos);
		map.setName("Teste do email");
		
		Route solucao=salesman.solveIt(map,"A","D",BigDecimal.TEN,new BigDecimal(2.5F));
		Assert.assertNotNull(solucao);
		Assert.assertNotNull(solucao.getNodes());
		Assert.assertEquals(3L, solucao.getNodes().size());
		BigDecimal cost=solucao.getTotalCost();
		cost.setScale(2, BigDecimal.ROUND_HALF_UP);
		Assert.assertTrue(cost.compareTo(new BigDecimal(12.50))==0);
    }
    
	private LogisticMeshItem novoArco(String source,String destination,Long distance){
		LogisticMeshItem arco=new LogisticMeshItem();
		
		arco.setSource(source);
		arco.setDestination(destination);
		arco.setDistance(distance);
		
		return arco;
	}
    
}
