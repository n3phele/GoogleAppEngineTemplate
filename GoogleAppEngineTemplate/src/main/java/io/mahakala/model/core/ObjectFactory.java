package io.mahakala.model.core;

import io.mahakala.model.SampleEntity;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.lang.reflect.ParameterizedType;
import java.net.URI;

import com.googlecode.objectify.NotFoundException;


public class ObjectFactory<T extends ResourceEntity> {
	final protected Class<T> myClass; 
	final protected String resourceType;
	final protected String base;
	        
	public ObjectFactory (Class<T> clazz, String resourceType, URI base) {
		 this.myClass = clazz;
		 this.resourceType = resourceType.toLowerCase();
		 String baseURL = base.toString();
		 this.base = baseURL+(baseURL.endsWith("/")?this.resourceType : "/"+this.resourceType)+"/";
	 }
	
	public ObjectFactory (Class<T> clazz, URI base) {
		 this(clazz, clazz.getSimpleName().replaceFirst("Entity$", ""), base);
	 }
	
	@SuppressWarnings("unchecked")
	public ObjectFactory (URI base) {
		this.myClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		 this.resourceType = this.myClass.getSimpleName().replaceFirst("Entity$", "").toLowerCase();
		 String baseURL = base.toString();
		 this.base = baseURL+(baseURL.endsWith("/")?this.resourceType : "/"+this.resourceType)+"/";
	 }
	
	public T get(Long id) throws NotFoundException {
		return ofy().load().type(myClass).id(id).safe();
	}
	
	public T put(T e) {
		ofy().save().entity(e).now();
		return e;
		
	}
	
	public T instanceOf() throws InstantiationException, IllegalAccessException {
		T e = this.myClass.newInstance();
		ofy().save().entity(e).now();
		e.uri = this.base+e.id;
		ofy().save().entity(e);
		return e;
		
	}
	
	public T instanceOf(T e)  {
		ofy().save().entity(e).now();
		e.uri = this.base+e.id;
		ofy().save().entity(e);
		return e;
	}

	public void delete(SampleEntity item) {
		ofy().delete().entity(item).now();
	}
	
	public String toString() {
		return "ObjectFactory "+myClass.getCanonicalName()+" "+resourceType+" "+base;
	}

}
