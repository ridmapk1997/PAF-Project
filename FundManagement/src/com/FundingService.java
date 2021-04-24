package com;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.Funding;

@Path("/Funding")
public class FundingService {
	Funding FundingObj = new Funding();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readFunding() {
		return FundingObj.readFunding();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertFunding(@FormParam("fid") 
	String fid, @FormParam("ftype") String ftype,

			@FormParam("fsource") String fsource,
			@FormParam("famount") String famount,
			@FormParam("fdate") String fdate) {
		String output = FundingObj.insertFunding(fid, ftype, fsource, famount, fdate);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateFunding(String fundingData) {
		// Convert the input string to a JSON object
		JsonObject fundingObject = new JsonParser().parse(fundingData).getAsJsonObject();
		// Read the values from the JSON object
		String fid = fundingObject.get("fid").getAsString();
		String ftype = fundingObject.get("ftype").getAsString();
		String fsource = fundingObject.get("fsource").getAsString();
		String famount = fundingObject.get("famount").getAsString();
		String fdate = fundingObject.get("fdate").getAsString();

		String output = FundingObj.updateFunding(fid, ftype, fsource, famount, fdate);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteFunding(String fundingData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(fundingData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String fid = doc.select("fid").text();
		String output = FundingObj.deleteFunding(fid);
		return output;
	}
}
