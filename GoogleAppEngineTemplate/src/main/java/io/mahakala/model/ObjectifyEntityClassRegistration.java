package io.mahakala.model;

import com.googlecode.objectify.ObjectifyService;

public class ObjectifyEntityClassRegistration {
	public static void register() {
	    ObjectifyService.register(SampleEntity.class);
	  }

}
