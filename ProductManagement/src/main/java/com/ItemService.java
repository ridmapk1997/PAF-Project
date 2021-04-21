package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Item;

@Path("/Items")
public class ItemService {
   
    Item itemObj = new Item();
    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public String readItems()
     {
        return itemObj.readItems();
    	
     }
    
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertItem(

     @FormParam("Code") String code,
     @FormParam("Name") String name,
     @FormParam("Price") String price,
     @FormParam("Description") String des)
    {
     String output = itemObj.insertProduct(code,name,price,des);
    return output;
    }
    
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateItem(String itemData)
    {
    	
    //Convert the input string to a JSON object
     JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
     
    //Read the values from the JSON object
     String code = itemObject.get("Code").getAsString();
     String name = itemObject.get("Name").getAsString();
     String price = itemObject.get("Price").getAsString();
     String des = itemObject.get("Description").getAsString();
     String output = itemObj.updateProduct(code, name, price, des);
    return output;
    }
    
    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteItem(String itemData)
    {
    //Convert the input string to an XML document
     //Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());
     JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();

    //Read the value from the element <itemID>
     String code = itemObject.get("Code").getAsString();
     String output = itemObj.deleteProduct(code);
    return output;
    }
}
