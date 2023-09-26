package com.luminousonion;

import com.luminousonion.dto.ScanRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.io.IOException;

@Path("/request")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScanController {
    private static final Logger logger = LoggerFactory.getLogger(ScanController.class);

    @ConfigProperty(name = "quarkus.application.version")
    String version;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getVersion() {
        return version;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String runScanner(ScanRequest scanRequest) throws IOException, InterruptedException {

        String os = System.getProperty("os.name");
        logger.info(os);

        logger.info(scanRequest.image);

        ProcessBuilder pb = new ProcessBuilder("notepad.exe");
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);

        //Process scanJob = pb.start();
        //int code = scanJob.waitFor();
        //logger.info("Return code: %d", code);

        return "Test";
    }
}
