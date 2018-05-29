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

import models.ConsolidatedReportResult;
import models.DefaultServiceResult;
import models.DeliveryStatus;
import models.DetailedReportResult;
import models.ReportGroupType;
import models.ScheduleTextMessageResource;
import models.SendTextMessageResource;

public class TextMessageService extends ServiceBase {
	public TextMessageService(String apiKey) {
		super(apiKey);
	}

	public DefaultServiceResult send(String sender, String content, List<String> receivers) {
		Client client = ClientBuilder.newClient();

		SendTextMessageResource sendTextMessageResource = new SendTextMessageResource();
		sendTextMessageResource.Sender = sender;
		sendTextMessageResource.Content = content;
		sendTextMessageResource.Receivers = String.join(",", receivers);

		Response response = client.target("https://sms.comtele.com.br/api/v2").path("send")
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).post(Entity.json(sendTextMessageResource));

		return response.readEntity(DefaultServiceResult.class);
	}

	public DefaultServiceResult schedule(String sender, String content, Date scheduleDate, List<String> receivers) {
		Client client = ClientBuilder.newClient();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		ScheduleTextMessageResource scheduleTextMessageResource = new ScheduleTextMessageResource();
		scheduleTextMessageResource.Sender = sender;
		scheduleTextMessageResource.Content = content;
		scheduleTextMessageResource.Receivers = String.join(",", receivers);
		scheduleTextMessageResource.ScheduleDate = dateFormat.format(scheduleDate);

		Response response = client.target("https://sms.comtele.com.br/api/v2").path("schedule")
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).post(Entity.json(scheduleTextMessageResource));

		return response.readEntity(DefaultServiceResult.class);
	}

	public DetailedReportResult getDetailedReport(Date startDate, Date endDate, DeliveryStatus deliveryStatus) {
		Client client = ClientBuilder.newClient();

		DateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String deliveryStatusAsString = deliveryStatusToString(deliveryStatus);

		Response response = client.target("https://sms.comtele.com.br/api/v2").path("detailedreporting")
				.queryParam("startDate", apiDateFormat.format(startDate))
				.queryParam("endDate", apiDateFormat.format(endDate)).queryParam("delivered", deliveryStatusAsString)
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).get();

		return response.readEntity(DetailedReportResult.class);
	}

	public ConsolidatedReportResult getConsolidatedReport(Date startDate, Date endDate, ReportGroupType groupType) {
		Client client = ClientBuilder.newClient();

		DateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String groupTypeAsString = reportGroupTypeToString(groupType);

		Response response = client.target("https://sms.comtele.com.br/api/v2").path("consolidatedreporting")
				.queryParam("startDate", apiDateFormat.format(startDate))
				.queryParam("endDate", apiDateFormat.format(endDate)).queryParam("group", groupTypeAsString)
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).get();

		return response.readEntity(ConsolidatedReportResult.class);
	}

	private String reportGroupTypeToString(ReportGroupType groupType) {
		switch (groupType) {
		case Monthly:
			return "true";
		case Daily:
			return "false";
		}

		return "true";
	}

	private String deliveryStatusToString(DeliveryStatus deliveryStatus) {
		switch (deliveryStatus) {
		case All:
			return "all";
		case Delivered:
			return "true";
		case Undelivered:
			return "false";
		}

		return "all";
	}
}
