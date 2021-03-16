package nom.aob.quarkusbench.controller.rest;

import io.smallrye.mutiny.Uni;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import nom.aob.quarkusbench.model.SimpleResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;


@Slf4j
@Path(SimpleRestController.PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimpleRestController {

    public static final String PATH_SIMPLE = "/simple";
    public static final String PATH_PROXY = "/proxy";
    public static final String PATH = "/rest";
    public static final String SCHEME_HTTP = "http://";

    @ConfigProperty(name = "nom.aob.quarkusbench.logstring")
    private Optional<String> logString;

    public SimpleRestController() {
        log.info("Constructed instance of {}. logString={}.",
                SimpleRestController.class.getSimpleName(),
                logString);
    }

    @GET
    @Path(PATH_SIMPLE)
    public @NonNull Uni<SimpleResponse> simpleResponse() {

        logString.ifPresent(s -> log.info("in simpleResponse. logString = {}.",s));

        SimpleResponse simpleResponse = SimpleResponse.newSimpleResponse(PATH_SIMPLE);
        Uni<SimpleResponse> responseUni = Uni.createFrom().item(simpleResponse)
                .onItem().transform(n -> n);
        return responseUni;
    }

    @GET
    @Path(PATH_PROXY+"/{address}/{port}")
    public @NonNull Uni<SimpleResponse> simpleProxy(
//            @RequestHeader(value = "x-b3-traceid", required = false) String traceId,
            String address,
            Integer port) {

        logString.ifPresent(s -> log.info("in simpleProxy. logString = {}.",s));

        StringBuilder sb = new StringBuilder(SCHEME_HTTP);
        sb.append(address).append(":").append(port).append(PATH).append(PATH_SIMPLE);
        String resourceUrl = sb.toString();

        //@TODO: implement vertex client call::
/*
        ResponseEntity<SimpleResponse> response
                = restTemplate.getForEntity(resourceUrl, SimpleResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            SimpleResponse simpleResponse = response.getBody();
            simpleResponse.setPathString(PATH_PROXY + "/" + address + "/" + port);
            return new ResponseEntity<>(simpleResponse, response.getStatusCode());
        }
*/
        SimpleResponse simpleResponse = SimpleResponse.newSimpleResponse(PATH_PROXY + "/" + address + "/" + port);
        Uni<SimpleResponse> responseUni = Uni.createFrom().item(simpleResponse)
                .onItem().transform(n -> n);
        return responseUni;
    }

}
