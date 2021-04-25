package com;

import model.Project;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

@Path("/Projects")
public class ProjectService {
	
Project projectObj = new Project(); 
	//
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readProjects() 
	{
	  
		return projectObj.readProjects(); 
	 
	}
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertProject(@FormParam("projectCode") String projectCode, 
							 @FormParam("projectName") String projectName, 
							 @FormParam("projectPrice") String projectPrice, 
							 @FormParam("projectDesc") String projectDesc,
							 @FormParam("projectBuy") String projectBuy,
							 @FormParam("projectCtg") String projectCtg) 
	{ 
		String output = projectObj.insertProject(projectCode, projectName, projectPrice, projectDesc, projectBuy, projectCtg); 
		return output; 
	}
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateProject(String projectData) 
	{ 
		//Convert the input string to a JSON object 
		 JsonObject projectObject = new JsonParser().parse(projectData).getAsJsonObject(); 
		 
		//Read the values from the JSON object
		 String projectID = projectObject.get("projectID").getAsString(); 
		 String projectCode = projectObject.get("projectCode").getAsString(); 
		 String projectName = projectObject.get("projectName").getAsString(); 
		 String projectPrice = projectObject.get("projectPrice").getAsString(); 
		 String projectDesc = projectObject.get("projectDesc").getAsString();
		 String projectBuy = projectObject.get("projectBuy").getAsString(); 
		 String projectCtg = projectObject.get("projectCtg").getAsString(); 
		 
		 String output = projectObj.updateProject(projectID, projectCode, projectName, projectPrice, projectDesc, projectBuy, projectCtg); 
		 return output; 
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteProject(String projectData) 
	{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(projectData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <projectID>
		 String projectID = doc.select("projectID").text(); 
		 String output = projectObj.deleteProject(projectID); 
		return output; 
	}

}
