package com;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.Customer;

@Path("/Customer")
public class CustomerService {
	Customer customerObj = new Customer();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readCustomer() {
		return customerObj.readCustomer();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertCustomer(@FormParam("cName") String cName,
			
	 @FormParam("cAddress") String cAddress,
	 @FormParam("pName") String pName,
	 @FormParam("cEmail") String cEmail,
	 @FormParam("cDate") String cDate,
	 @FormParam("cGender") String cGender,
	 @FormParam("cPno") String cPno)
	{
	 String output = customerObj.insertCustomer(cName, cAddress, pName, cEmail, cDate, cGender, cPno);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(String customerData)
	{
	//Convert the input string to a JSON object
	 JsonObject customerObject = new JsonParser().parse(customerData).getAsJsonObject();
	//Read the values from the JSON object
	 String cID = customerObject.get("cID").getAsString();
	 String cName = customerObject.get("cName").getAsString();
	 String cAddress = customerObject.get("cAddress").getAsString();
	 String pName = customerObject.get("pName").getAsString();
	 String cEmail = customerObject.get("cEmail").getAsString();
	 String pDate = customerObject.get("cDate").getAsString();
	 String cGender = customerObject.get("cGender").getAsString();
	 String cPno = customerObject.get("cPno").getAsString();
	 String output = customerObj.updateCustomer(cID, cName, cAddress, pName, cEmail, pDate, cGender, cPno);
	return output;
	} 
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCustomer(String customerData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(customerData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String cID = doc.select("cID").text();
	 String output = customerObj.deleteCustomer(cID);
	return output;
	}
}
