package services;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.CreditHistoryResult;
import models.CreditServiceResult;

public class CreditService extends ServiceBase {

	public CreditService(String apiKey) {
		super(apiKey);
	}

	public void addCredits(String username, Integer amount) {
		Client client = ClientBuilder.newClient();

		client.target("https://sms.comtele.com.br/api/v2").path("credits").queryParam("id", username)
				.queryParam("amount", amount).request(MediaType.APPLICATION_JSON)
				.header("Content-Type", "application/json").header("auth-key", ApiKey).put(Entity.json(""));
	}

	public Integer getCredits(String username) {
		Client client = ClientBuilder.newClient();

		Response response = client.target("https://sms.comtele.com.br/api/v2").path("credits")
				.queryParam("id", username).request(MediaType.APPLICATION_JSON)
				.header("Content-Type", "application/json").header("auth-key", ApiKey).get();

		CreditServiceResult data = response.readEntity(CreditServiceResult.class);
		return data.Object;
	}

	public Integer getMyCredits() {
		Client client = ClientBuilder.newClient();

		Response response = client.target("https://sms.comtele.com.br/api/v2").path("credits")
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).get();

		CreditServiceResult data = response.readEntity(CreditServiceResult.class);
		return data.Object;
	}

	public CreditHistoryResult getHistory(String username) {
		Client client = ClientBuilder.newClient();

		Response response = client.target("https://sms.comtele.com.br/api/v2").path("balancehistory")
				.queryParam("id", username).request(MediaType.APPLICATION_JSON)
				.header("Content-Type", "application/json").header("auth-key", ApiKey).get();

		return response.readEntity(CreditHistoryResult.class);
	}

}
