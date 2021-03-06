package eu.elderspaces.activities.services;

import it.cybion.commons.web.responses.ResponseStatus;
import it.cybion.commons.web.responses.StringResponse;
import it.cybion.commons.web.services.base.JsonService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import eu.elderspaces.activities.ActivitiesEndpoint;

/**
 * This service is used to check that every dependency is satisfied and up and
 * running
 * 
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
@Path(ActivitiesEndpoint.REST_RADIX + ActivitiesEndpoint.STATUS)
@Produces(MediaType.APPLICATION_JSON)
public class StatusService extends JsonService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusService.class);
    
    @Inject
    public StatusService() {
    
    }
    
    @GET
    @Path(ActivitiesEndpoint.NOW)
    public Response getStatus() {
    
        final DateTime now = new DateTime();
        LOGGER.info("checking services status at " + now.toString());
        
        final Response.ResponseBuilder rb = Response.ok();
        rb.entity(new StringResponse(ResponseStatus.OK, "services up and running as of '"
                + now.toString() + "'"));
        return rb.build();
    }
}
