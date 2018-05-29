package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.ReplyServiceResult;

public class ReplyService extends ServiceBase {
	public ReplyService(String apiKey) {
		super(apiKey);
	}

	public ReplyServiceResult getReport(Date startDate, Date endDate, String sender) {
		Client client = ClientBuilder.newClient();

		DateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Response response = client.target("https://sms.comtele.com.br/api/v2").path("replyreporting")
				.queryParam("startDate", apiDateFormat.format(startDate))
				.queryParam("endDate", apiDateFormat.format(endDate)).queryParam("sender", sender)
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).get();

		return response.readEntity(ReplyServiceResult.class);
	}
}
