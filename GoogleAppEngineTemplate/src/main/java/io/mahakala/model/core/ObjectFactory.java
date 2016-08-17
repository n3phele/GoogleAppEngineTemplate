/**
 * (C) Copyright 2010-2016. Nigel Cook. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * Licensed under the terms described in LICENSE file that accompanied this code, (the "License"); you may not use this file
 * except in compliance with the License. 
 * 
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on 
 *  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 *  specific language governing permissions and limitations under the License.
 */
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
