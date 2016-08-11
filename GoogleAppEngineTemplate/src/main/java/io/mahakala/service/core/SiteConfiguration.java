package io.mahakala.service.core;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.net.URI;

import com.google.apphosting.api.ApiProxy;

import io.mahakala.model.core.Credential;

public class SiteConfiguration {
	private static ApiProxy.Environment environment;
	private static String hostname;
	private static String appId;
	private static String serviceName;
	private static URI serviceURL;
	
	public static void initalize() {
		String rawappId = ApiProxy.getCurrentEnvironment().getAppId();
		appId = (rawappId.startsWith("s~")) ? rawappId.substring("s~".length()) : rawappId;
		environment = ApiProxy.getCurrentEnvironment();
		serviceName = environment.getModuleId();
		hostname = (String) environment.getAttributes().get("com.google.appengine.runtime.default_version_hostname");
		if(serviceName == null || serviceName.length()==0 || serviceName.equals("default")) {
			serviceURL = URI.create("https://"+hostname);
		} else {
			serviceURL = URI.create("https://"+serviceName+"."+hostname);
		}
	}

	/**
	 * @return the environment
	 */
	public static ApiProxy.Environment getEnvironment() {
		return environment;
	}

	/**
	 * @return the hostname
	 */
	public static String getHostname() {
		return hostname;
	}

	/**
	 * @return the appId
	 */
	public static String getAppId() {
		return appId;
	}

	/**
	 * @return the serviceName
	 */
	public static String getServiceName() {
		return serviceName;
	}

	/**
	 * @return the serviceURL
	 */
	public static URI getServiceURL() {
		return serviceURL;
	}

	public static String getCredential(String name) {
		Credential credential = ofy().load().type(Credential.class).id(name).now();
		if(credential == null) {
			credential = new Credential(name, "not found");
			ofy().save().entity(credential).now();
		}
		return credential.value();
	}

	
}
