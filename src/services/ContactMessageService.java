package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.DefaultServiceResult;
import models.ScheduleContactMessageResource;
import models.SendContactMessageResource;

public class ContactMessageService extends ServiceBase {
	public ContactMessageService(String apiKey) {
		super(apiKey);
	}
	
	public DefaultServiceResult send(String sender, String content, String groupName) {
		Client client = ClientBuilder.newClient();

		SendContactMessageResource sendContactMessageResource = new SendContactMessageResource();
		sendContactMessageResource.Sender = sender;
		sendContactMessageResource.Content = content;
		sendContactMessageResource.GroupName = groupName;

		Response response = client.target("https://sms.comtele.com.br/api/v2").path("sendcontactmessage")
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).post(Entity.json(sendContactMessageResource));

		return response.readEntity(DefaultServiceResult.class);	
	}
	
	public DefaultServiceResult schedule(String sender, String content, String groupName, Date scheduleDate) {
		Client client = ClientBuilder.newClient();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		ScheduleContactMessageResource sendContactMessageResource = new ScheduleContactMessageResource();
		sendContactMessageResource.Sender = sender;
		sendContactMessageResource.Content = content;
		sendContactMessageResource.GroupName = groupName;
		sendContactMessageResource.ScheduleDate = dateFormat.format(scheduleDate);
		
		Response response = client.target("https://sms.comtele.com.br/api/v2").path("schedulecontactmessage")
				.request(MediaType.APPLICATION_JSON).header("Content-Type", "application/json")
				.header("auth-key", ApiKey).post(Entity.json(sendContactMessageResource));

		return response.readEntity(DefaultServiceResult.class);	
	}
}
