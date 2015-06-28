package br.com.lebc.persistence;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.lebc.beans.LogisticMesh;
import br.com.lebc.exception.DataException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementation of Interface RegisterData that implements persistence to file system.
 * 
 * @author Luiz Couto(bittencourt.vr@gmail.com)
 */
@Component
public class RegisterDataFile implements RegisterData {

	@Override
	public List<LogisticMesh> readPersistedData() throws DataException {
		return convertFromFile();
	}

	@Override
	public List<LogisticMesh> insertMesh(LogisticMesh newMesh)
			throws DataException {
		List<LogisticMesh> maps=readPersistedData();
		maps.add(newMesh);
		persistMeshList(maps);
		return maps;
	}

	@Override
	public List<LogisticMesh> deleteMesh(String meshName) throws DataException {
		List<LogisticMesh> maps=readPersistedData();
		List<LogisticMesh> newMaps=new ArrayList<LogisticMesh>();
		
		for(LogisticMesh m:maps){
			if(!m.getName().equalsIgnoreCase(meshName)){
				newMaps.add(m);
			}
		}
		
		persistMeshList(newMaps);
		return newMaps;
	}

	@Override
	public void persistMeshList(List<LogisticMesh> listMesh)
			throws DataException {
		try {
			convertToFile(listMesh);
		} catch (IOException e) {
			throw new DataException("Nao foi possivel ler o arquivo corretamente");
		}
		
	}
	private String readFile() throws IOException {
		try{
			Path path = Paths.get("c:/dados/mapas.json");
			List<String> lines = Files.readAllLines(path, Charset.defaultCharset()); 
			
			StringBuilder sb=new StringBuilder();
			
			for(String l:lines){
				sb.append(l);
			}
			
			return sb.toString();
		}catch(NoSuchFileException e){
			return "";
		}
	}
	
	private void writeFile(String json) throws IOException {
		Path path = Paths.get("c:/dados/mapas.json");
		List<String> linhas=new ArrayList<String>();
		linhas.add(json);
		Files.write(path, linhas, Charset.defaultCharset());
	}
	
	private List<LogisticMesh> convertFromFile(){
		String content=null;
		try {
			content=readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(content==null || content.length()==0){
			return new ArrayList<LogisticMesh>();
		}
		
		Gson gson = new Gson();
		
		List<LogisticMesh>maps=gson.fromJson(content, new TypeToken<ArrayList<LogisticMesh>>(){}.getType());
		return maps;
	}
	
	private void convertToFile(List<LogisticMesh> maps) throws IOException{
		
		Gson gson = new Gson();
		
		String json=gson.toJson(maps, new TypeToken<ArrayList<LogisticMesh>>(){}.getType());
		
		writeFile(json);
		
	}
}
