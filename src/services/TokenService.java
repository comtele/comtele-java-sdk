package services;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.DefaultServiceResult;
import models.SendTokenResource;
import models.ValidateTokenResource;

public class TokenService extends ServiceBase {
	public TokenService(String apiKey) {
		super(apiKey);
	}
	
	public DefaultServiceResult sendToken(String phoneNumber, String prefix) {
		Client client = ClientBuilder.newClient();
		
		SendTokenResource sendTokenResource = new SendTokenResource();
		sendTokenResource.PhoneNumber = phoneNumber;
		sendTokenResource.Prefix = prefix;
		
		Response response = client.target("https://sms.comtele.com.br/api/v2").path("tokenmanager")
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).post(Entity.json(sendTokenResource));
		
		return response.readEntity(DefaultServiceResult.class);
	}
	
	public DefaultServiceResult sendToken(String phoneNumber) {
		return sendToken(phoneNumber, "");
	}
	
	public DefaultServiceResult validateToken(String tokenCode)	{
		Client client = ClientBuilder.newClient();
		
		ValidateTokenResource validateTokenResource = new ValidateTokenResource();
		validateTokenResource.TokenCode = tokenCode;		
		
		Response response = client.target("https://sms.comtele.com.br/api/v2").path("tokenmanager")
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).put(Entity.json(validateTokenResource));
		
		return response.readEntity(DefaultServiceResult.class);
	}
}
