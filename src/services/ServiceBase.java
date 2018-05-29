package services;

public abstract class ServiceBase {
	public ServiceBase(String apiKey) {
		this.ApiKey = apiKey;
	}
	
	protected String ApiKey;
}
