package br.com.lebc.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.com.lebc.beans.LogisticMesh;
import br.com.lebc.beans.LogisticMeshItem;
import br.com.lebc.beans.Route;
import br.com.lebc.exception.BusinessException;

/**
 * Heuristic to solve Logistics problem(Salesman problem).
 * 
 * @author Luiz Couto(bittencourt.vr@gmail.com)
 */
@Component
public class SalesmanSolution {

	/** 
	* Calculates the best route for a map
	* @param LogisticMesh
	* @param source
	* @param destination
	* @param autonomy
	* @param fuelcost
	* @return The best route at this map, throws exception if there is no route to host
	* @throws BusinessException
	*/ 
	public Route solveIt(LogisticMesh map,String source,String destination,BigDecimal autonomy,BigDecimal fuelCost) throws BusinessException{

		Route directRoute=calculateRoute(map, source, destination, autonomy, fuelCost);
		Route reverseRoute=calculateRoute(map, destination, source, autonomy, fuelCost);
		
		if(directRoute.getTotalCost().compareTo(reverseRoute.getTotalCost())>0){
			List<String> nodes=reverseRoute.getNodes();
			List<String> revertNodes=new ArrayList<String>();
			for(int i=nodes.size()-1;i>=0;i--){
				revertNodes.add(nodes.get(i));
			}
			reverseRoute.setNodes(revertNodes);
			return reverseRoute;
		}
		
		return directRoute;
	}

	private Route calculateRoute(LogisticMesh map, String source,
			String destination, BigDecimal autonomy, BigDecimal fuelCost)
			throws BusinessException {
		RouteAnalisys currentAnalisys=new RouteAnalisys();
		
		currentAnalisys.setSource(source);
		currentAnalisys.setDestination(destination);
		
		LogisticMeshItem lastPath=null;
		
		// Iterates it one Route is found or when is determined that host can't be reached
		do{
			// 1 - Get Neighbors
			Set<LogisticMeshItem> neighbors=currentAnalisys.getNeighbors(lastPath!=null?lastPath.getDestination():source, map);
			
			// 2 - Select best way
			lastPath=currentAnalisys.selectNext(neighbors);
			
			// 3 - No Way Route, get back try other
			if(lastPath==null){
				LogisticMeshItem beforePath=currentAnalisys.getLast();
				currentAnalisys.addIgnoreList(beforePath);
				currentAnalisys.removeLast();				
				lastPath=currentAnalisys.getLast();
				
				//No more routes exists, then throw 'No Way to Route'
				if(lastPath==null){
					throw new BusinessException("No way to route");
				}
				
			}else{

				// Valid Path, add to the route
				currentAnalisys.addArc(lastPath);
				
				// 4 - Destination Found
				if(lastPath!=null && lastPath.getDestination().equalsIgnoreCase(destination)){
					break;
				}			
			}
		}while(true);
		
		// Optimize route
		List<LogisticMeshItem> arcs=currentAnalisys.getArcs();
		for(int i=0;i<3;i++){
			int pos=0;
			
			do{
				arcs=currentAnalisys.optimizeChain(arcs.get(arcs.size()-pos-1), arcs);
				pos++;
			}while(pos<arcs.size());
		}
		currentAnalisys.setArcs(arcs);
		
		// Do calculations about the route
		return currentAnalisys.generateRoute(autonomy, fuelCost);
	}
	
	public class RouteAnalisys{
		private List<LogisticMeshItem> arcs=null;
		private List<LogisticMeshItem> ignoreList=new ArrayList<LogisticMeshItem>();
		private List<String> alreadyVisited=null;
		private Map<String,Set<LogisticMeshItem>> neighborhood=null;
		
		private String source;
		private String destination;
		/** 
		* Calculate route cost and generate Route Object
		* @return Route object
		* 
		*/ 
		public Route generateRoute(BigDecimal autonomy,BigDecimal fuelCost){
			Route returnRoute=new Route();
			
			BigDecimal distance=BigDecimal.ZERO;
			List<String> nodes=new ArrayList<String>();
			
			nodes.add(source);
			
			for(LogisticMeshItem a:arcs){
				nodes.add(a.getDestination());
				distance=distance.add(new BigDecimal(a.getDistance()));
			}
			
			returnRoute.setNodes(nodes);
			
			BigDecimal fuelAmount=distance.divide(autonomy,2,BigDecimal.ROUND_HALF_UP);
			BigDecimal travelCost=fuelAmount.multiply(fuelCost);
			
			returnRoute.setTotalCost(travelCost);
			
			return returnRoute;
		}

		public List<LogisticMeshItem> optimizeChain(LogisticMeshItem current,List<LogisticMeshItem> oldChain){
			List<LogisticMeshItem> optimizedChain=new ArrayList<LogisticMeshItem>();
			
			boolean found =false;
			LogisticMeshItem shortcut=null;
			Long auxDistance=0L;
			
			for(LogisticMeshItem i:oldChain){
				if(found){
					auxDistance+=i.getDistance();
					LogisticMeshItem otherWay=searchArc(current.getSource(),i.getDestination());
					if(otherWay!=null){
						if(auxDistance>=otherWay.getDistance()){
							shortcut=otherWay;
						}
					}
				}else if(i.getDestination().equalsIgnoreCase(current.getDestination())&&i.getSource().equalsIgnoreCase(current.getSource())){
					auxDistance+=i.getDistance();
					found=true;
				}
			}

			if(shortcut==null){
				return oldChain;
			}
			
			boolean foundSource =false;
			boolean foundDestination =false;
			for(LogisticMeshItem i:oldChain){
				if(!foundSource){
					if(i.getSource().equalsIgnoreCase(shortcut.getSource())){
						foundSource=true;
						optimizedChain.add(shortcut);
					}else{
						optimizedChain.add(i);
					}
				}else{
					if(!foundDestination){
						if(i.getDestination().equalsIgnoreCase(shortcut.getDestination())){
							foundDestination=true;
						}
					}else{
						optimizedChain.add(i);
					}
				}
			}
		
			return optimizedChain;
		}
		
		public LogisticMeshItem searchArc(String source,String destination){
			Set<LogisticMeshItem> neighbors=neighborhood.get(source);
			
			for(LogisticMeshItem i:neighbors){
				if(i.getDestination().equalsIgnoreCase(destination)&&i.getSource().equalsIgnoreCase(source)){
					return i;
				}
			}
			return null;
		}
		
		/** 
		* Add a new Arc to the Route
		* @param Arc to be added
		* 
		*/ 
		public void addArc(LogisticMeshItem newArc){
			
			if(newArc==null){
				return;
			}
			
			if(arcs==null){
				arcs=new ArrayList<LogisticMeshItem>();
				alreadyVisited=new ArrayList<String>();
				alreadyVisited.add(newArc.getSource());
			}
			
			arcs.add(newArc);
			alreadyVisited.add(newArc.getDestination());			
		}
		
		/** 
		* Add a arc to ignore list
		* @param Arc to be ignored
		* 
		*/ 
		public void addIgnoreList(LogisticMeshItem item){
			ignoreList.add(item);
			
			// Remove Mesh from Map "neighborhood"
			if(neighborhood!=null){
				Set<LogisticMeshItem> neighbors=neighborhood.get(item.getSource());
								
				if(neighbors!=null){
					neighbors.remove(item);
					neighborhood.put(item.getSource(), neighbors);
				}
			}
			
			alreadyVisited.remove(item.getDestination());
		}
		
		/** 
		* Check if one node was already visited
		* @param node label
		* @return boolean
		* 
		*/ 
		public boolean alreadyVisited(String node){
			if(alreadyVisited==null){
				alreadyVisited=new ArrayList<String>();
			}
			for(String s:alreadyVisited){
				if(s.equalsIgnoreCase(node)){
					return true;
				}
			}
			return false;
		}
		
		/** 
		* Get all neighbors from one Node
		* @return List of LogisticMeshItem
		*/ 
		public Set<LogisticMeshItem> getNeighbors(String node,LogisticMesh map){
			if(neighborhood==null){
				if(map!=null && map.getRoads()!=null && map.getRoads().size()>0){
					
					neighborhood=new HashMap<String,Set<LogisticMeshItem>>();
					
					for(LogisticMeshItem i : map.getRoads()){					
						if(!this.isMeshIgnored(i)){						
							Set<LogisticMeshItem> currentNeigbors=neighborhood.get(i.getSource());
							Set<LogisticMeshItem> currentNeigborsReverse=neighborhood.get(i.getDestination());
							
							if(currentNeigbors==null){
								currentNeigbors=new HashSet<LogisticMeshItem>();
							}
							if(currentNeigborsReverse==null){
								currentNeigborsReverse=new HashSet<LogisticMeshItem>();
							}
							
							LogisticMeshItem reverse=new LogisticMeshItem();
							reverse.setDestination(i.getSource());
							reverse.setSource(i.getDestination());
							reverse.setDistance(i.getDistance());
							
							currentNeigbors.add(i);
							currentNeigborsReverse.add(reverse);
							
							neighborhood.put(i.getSource(), currentNeigbors);
							neighborhood.put(i.getDestination(),currentNeigborsReverse);
						}						
					}				
				}			
			}
			
			return neighborhood.get(node);
		}

		/** 
		* Get the best path from all possibilities
		* @param List of LogisticMeshItem
		* @return The best way, which one that has the lowest cost
		* 
		*/ 
		public LogisticMeshItem selectNext(Set<LogisticMeshItem> neighbors){
			 LogisticMeshItem selectedMesh=null;
 
			 if(neighbors!=null && neighbors.size()>0){
				 for(LogisticMeshItem i:neighbors){
					 
					 if(this.alreadyVisited(i.getDestination())){
						 continue;
					 }
					 
					 if(selectedMesh==null){
						 selectedMesh=i;
					 }else{
						 if(selectedMesh.getDistance()>i.getDistance()){
							 selectedMesh=i;
						 }
					 }
				 }
			 }
			 return selectedMesh;
		}
	
		/** 
		* Check if one arc is marked to be ignored
		* @param LogisticMeshItem
		* @return boolean
		*/ 
		public boolean isMeshIgnored(LogisticMeshItem item){
			if(ignoreList!=null && ignoreList.size()>0){
				for(LogisticMeshItem i:ignoreList){
					if(i==item){
						return true;
					}
				}
			}
			return false;
		}
		
		/** 
		* Retrieve the last arc from list
		* @return LogisticMeshItem
		*/ 
		public LogisticMeshItem getLast(){
			if(arcs!=null && arcs.size()>0){
				return arcs.get(arcs.size()-1);
			}
			return null;
		}
		
		/** 
		* Remove last Arc from list
		* 
		*/ 
		public void removeLast(){
			if(arcs!=null && arcs.size()>0){
				arcs.remove(arcs.size()-1);
			}
		}
		
		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getDestination() {
			return destination;
		}

		public void setDestination(String destination) {
			this.destination = destination;
		}

		public List<LogisticMeshItem> getArcs() {
			return arcs;
		}

		public void setArcs(List<LogisticMeshItem> arcs) {
			this.arcs = arcs;
		}
	
	}
	
}
