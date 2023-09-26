package com.luminousonion;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.io.IOException;

@Path("/request")
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
    public String runScanner() throws IOException, InterruptedException {

        String os = System.getProperty("os.name");
        logger.info(os);

        ProcessBuilder pb = new ProcessBuilder("notepad.exe");
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);

        Process scanJob = pb.start();
        int code = scanJob.waitFor();
        logger.info("Return code: %d", code);

        return "Test";
    }
}
