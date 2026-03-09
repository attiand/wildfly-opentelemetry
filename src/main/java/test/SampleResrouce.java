package test;

import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/")
public class SampleResrouce {

    private static final Logger LOG = LoggerFactory.getLogger(SampleResrouce.class);

    @Inject
    SampleService sampleService;

    @GET
    public Response get() {
        return Response.ok(sampleService.sayHello()).build();
    }
}
