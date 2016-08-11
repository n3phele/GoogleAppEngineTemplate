package io.mahakala.service.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.googlecode.objectify.NotFoundException;

@Provider
public class EntityNotFoundMapper implements ExceptionMapper<NotFoundException> {
	@Override
	 public Response toResponse(NotFoundException ex) {
		    return Response.status(404).
		      entity(ex.getMessage()).
		      type("text/plain").
		      build();
	}

}
