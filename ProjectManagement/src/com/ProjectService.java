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

@Path("/Project")
public class ProjectService {
	Project ProjectObj = new Project();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readProject() {
		return ProjectObj.readProject();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertProject(@FormParam("pname") String pname, @FormParam("des") String des,
			@FormParam("price") String price) {
		String output = ProjectObj.insertProject(pname, des, price);
		return output;

	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateProject(String ProjectData) {
		// Convert the input string to a JSON object
		JsonObject ProjectObject = new JsonParser().parse(ProjectData).getAsJsonObject();

		// Read the values from the JSON object
		String pid = ProjectObject.get("pid").getAsString();
		String pname = ProjectObject.get("pname").getAsString();
		String des = ProjectObject.get("des").getAsString();
		String price = ProjectObject.get("price").getAsString();

		String output = ProjectObj.updateProject(pid, pname, des, price);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProject(String ProjectData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(ProjectData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String pid = doc.select("pid").text();
		String output = ProjectObj.deleteProject(pid);
		return output;
	}
}
