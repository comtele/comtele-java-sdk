package services;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.AccountServiceResult;

public class AccountService extends ServiceBase {

	public AccountService(String apiKey) {
		super(apiKey);
	}

	public AccountServiceResult getAccountByUnsername(String username) {
		Client client = ClientBuilder.newClient();

		Response response = client.target("https://sms.comtele.com.br/api/v2").path("accounts")
				.queryParam("id", username).request(MediaType.APPLICATION_JSON)
				.header("Content-Type", "application/json").header("auth-key", ApiKey).get();

		return response.readEntity(AccountServiceResult.class);
	}

	public AccountServiceResult getAllAccounts() {
		Client client = ClientBuilder.newClient();

		Response response = client.target("https://sms.comtele.com.br/api/v2").path("accounts")
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).get();

		return response.readEntity(AccountServiceResult.class);
	}
}
