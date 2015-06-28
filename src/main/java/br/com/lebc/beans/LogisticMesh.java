package br.com.lebc.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Logistic Mesh data.
 * 
 * @author Luiz Couto (bittencourt.vr@gmail.com)
 * 
 */
public class LogisticMesh implements Serializable{

	private static final long serialVersionUID = -4935921792377143899L;
	
	private String name;
	private List<LogisticMeshItem> roads;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<LogisticMeshItem> getRoads() {
		return roads;
	}
	public void setRoads(List<LogisticMeshItem> roads) {
		this.roads = roads;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roads == null) ? 0 : roads.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogisticMesh other = (LogisticMesh) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roads == null) {
			if (other.roads != null)
				return false;
		} else if (!roads.equals(other.roads))
			return false;
		return true;
	}
	
	
}
