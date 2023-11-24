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
        String scannerTool = scanRequest.tool;
        logger.info(os);
        logger.info(scanRequest.image);
        logger.info(scannerTool);

        // Check if the OS is Linux else return error
        if (os.contains("Linux")) {
            // Check if trivy is installed else return error
            ProcessBuilder checkToolExists = new ProcessBuilder("which", scannerTool);
            Process checkToolJob = checkToolExists.start();
            int checkToolCode = checkToolJob.waitFor();
            if (checkToolCode != 0) {
                return scannerTool + " not installed";
            }
            ProcessBuilder pb;
            if (scannerTool.equals("trivy")) {
                pb = new ProcessBuilder("trivy", "--cache-dir", "/tmp/.cache", "--format", "json", "--scanners", "vuln", "image", scanRequest.image);
            } else if (scannerTool.equals("grype")) {
                pb = new ProcessBuilder("grype", scanRequest.image);
            } else {
                System.out.println(scannerTool + " scanner not supported");
                return scannerTool + " scanner not supported";

            }

            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            pb.redirectInput(ProcessBuilder.Redirect.INHERIT);

            Process scanJob = pb.start();
            //int code = scanJob.waitFor();
            return "Success";
        } else {
            return "OS not supported";
        }
    }
}
