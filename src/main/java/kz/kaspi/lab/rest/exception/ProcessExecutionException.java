package kz.kaspi.lab.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class ProcessExecutionException extends WebApplicationException {
    public ProcessExecutionException(String message){
        super(Response.status(Response.Status.BAD_REQUEST).entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}
