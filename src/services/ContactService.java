package services;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.ActionContactGroupResource;
import models.ContactGroupResult;
import models.CreateContactGroupResource;
import models.DefaultServiceResult;

public class ContactService extends ServiceBase {

	public ContactService(String apiKey) {
		super(apiKey);
	}
	
	public DefaultServiceResult addContactToGroup(String groupName, String contactPhone, String contactName) {
		Client client = ClientBuilder.newClient();
		
		ActionContactGroupResource actionContactGroupResource = new ActionContactGroupResource();
		actionContactGroupResource.GroupName = groupName;
		actionContactGroupResource.ContactName = contactName;
		actionContactGroupResource.ContactPhone = contactPhone;
		actionContactGroupResource.Action = "add_number";
		
		Response response = client.target("https://sms.comtele.com.br/api/v2").path("contactgroup")
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).put(Entity.json(actionContactGroupResource));
		
		return response.readEntity(DefaultServiceResult.class);
	}
	
	public DefaultServiceResult removeContactFromGroup(String groupName, String contactPhone) {
		Client client = ClientBuilder.newClient();
		
		ActionContactGroupResource actionContactGroupResource = new ActionContactGroupResource();
		actionContactGroupResource.GroupName = groupName;		
		actionContactGroupResource.ContactPhone = contactPhone;
		actionContactGroupResource.Action = "remove_number";
		
		Response response = client.target("https://sms.comtele.com.br/api/v2").path("contactgroup")
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).put(Entity.json(actionContactGroupResource));
		
		return response.readEntity(DefaultServiceResult.class);
	}
	
	public DefaultServiceResult createGroup(String groupName, String groupDescription) {
		Client client = ClientBuilder.newClient();
		
		CreateContactGroupResource createContactGroupResource = new CreateContactGroupResource();
		createContactGroupResource.Name = groupName;
		createContactGroupResource.Description = groupDescription;
		
		Response response = client.target("https://sms.comtele.com.br/api/v2").path("contactgroup")
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).post(Entity.json(createContactGroupResource));
		
		return response.readEntity(DefaultServiceResult.class);
	}
	
	public DefaultServiceResult createGroup(String groupName) {
		Client client = ClientBuilder.newClient();
				
		Response response = client.target("https://sms.comtele.com.br/api/v2").path("contactgroup")
				.queryParam("id", groupName)
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).delete();
		
		return response.readEntity(DefaultServiceResult.class);
	}

	public ContactGroupResult getGroupByName(String groupName) {
		Client client = ClientBuilder.newClient();
		
		Response response = client.target("https://sms.comtele.com.br/api/v2").path("contactgroup")
				.queryParam("id", groupName)
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).get();
		
		return response.readEntity(ContactGroupResult.class);
	}
	
	public ContactGroupResult getAllGroups(String groupName) {
		Client client = ClientBuilder.newClient();
		
		Response response = client.target("https://sms.comtele.com.br/api/v2").path("contactgroup")				
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).get();
		
		return response.readEntity(ContactGroupResult.class);
	}
}
