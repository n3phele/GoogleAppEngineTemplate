/**
 * (C) Copyright 2010-2016. Nigel Cook. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
