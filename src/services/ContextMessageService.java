package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.ContextReportResult;
import models.DefaultServiceResult;
import models.ScheduleContextMessageResource;
import models.SendContextMessageResource;

public class ContextMessageService extends ServiceBase {
	public ContextMessageService(String apiKey) {
		super(apiKey);
	}

	public DefaultServiceResult send(String sender, String contextRuleName, List<String> receivers) {
		Client client = ClientBuilder.newClient();

		SendContextMessageResource contextMessageResource = new SendContextMessageResource();
		contextMessageResource.Sender = sender;
		contextMessageResource.ContextRuleName = contextRuleName;
		contextMessageResource.Receivers = String.join(",", receivers);

		Response response = client.target("https://sms.comtele.com.br/api/v2").path("sendcontextmessage")
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).post(Entity.json(contextMessageResource));

		return response.readEntity(DefaultServiceResult.class);
	}

	public DefaultServiceResult schedule(String sender, String contextRuleName, Date scheduleDate,
			List<String> receivers) {
		Client client = ClientBuilder.newClient();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		ScheduleContextMessageResource contextMessageResource = new ScheduleContextMessageResource();
		contextMessageResource.Sender = sender;
		contextMessageResource.ContextRuleName = contextRuleName;
		contextMessageResource.Receivers = String.join(",", receivers);
		contextMessageResource.ScheduleDate = dateFormat.format(scheduleDate);

		Response response = client.target("https://sms.comtele.com.br/api/v2").path("schedulecontextmessage")
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).post(Entity.json(contextMessageResource));

		return response.readEntity(DefaultServiceResult.class);
	}

	public ContextReportResult getReport(Date startDate, Date endDate, String sender, String contextRuleName) {
		Client client = ClientBuilder.newClient();

		DateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Response response = client.target("https://sms.comtele.com.br/api/v2").path("contextreporting")
				.queryParam("startDate", apiDateFormat.format(startDate))
				.queryParam("endDate", apiDateFormat.format(endDate)).queryParam("sender", sender)
				.queryParam("contextRuleName", contextRuleName).request(MediaType.APPLICATION_JSON)
				.header("Content-Type", "application/json").header("auth-key", ApiKey).get();

		return response.readEntity(ContextReportResult.class);
	}
}
