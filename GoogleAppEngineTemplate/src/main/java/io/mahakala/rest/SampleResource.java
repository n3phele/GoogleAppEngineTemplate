package io.mahakala.rest;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.googlecode.objectify.NotFoundException;

import io.mahakala.model.SampleEntity;
import io.mahakala.model.core.ObjectFactory;

@Path("/sample")
public class SampleResource {
	private static Logger log = Logger.getLogger(SampleResource.class.getName()); 
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public SampleEntity get(@PathParam("id") Long id) throws NotFoundException {
		log.info("Getting "+id+" for "+dao());
		SampleEntity item = dao().get(id);
		log.info("Got "+item);
		return item;
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public SampleEntity delete(@PathParam("id") Long id) throws NotFoundException {
		SampleEntity item = dao().get(id);
		dao().delete(item);
		return item;
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/")
	public Response create(String text) {
		
		SampleEntity item = dao().instanceOf(new SampleEntity(text));
		
		log.warning("Created "+item.getUri());
		return Response.created(item.getUri()).build();
	}
	
	private ObjectFactory<SampleEntity> dao() {
		if(dao == null) {
			dao = new ObjectFactory<SampleEntity>(SampleEntity.class, uriInfo.getBaseUri());
		}
		return dao;
	}
	@Context 
	private UriInfo uriInfo;
	private ObjectFactory<SampleEntity> dao;
}
