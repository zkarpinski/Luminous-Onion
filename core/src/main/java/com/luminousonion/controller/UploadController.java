package com.luminousonion.controller;

import com.luminousonion.model.Source;
import com.luminousonion.model.shared.SourceTool;
import com.luminousonion.response.ResponseHandler;
import com.luminousonion.service.UploadSourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {
    private final UploadSourceService uploadSourceService;

    public UploadController(UploadSourceService uploadSourceService) {
            this.uploadSourceService = uploadSourceService;
    }

    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(
            @RequestParam("label") String label,
            @RequestParam("file")MultipartFile mpf,
            @RequestParam("productId") Long productId,
            @RequestParam("sourceTool") SourceTool sourceTool,
            @RequestParam(value = "archivePrevious", defaultValue = "false") boolean archivePrevious) {
        try {
            Source newSource = uploadSourceService.uploadFileAndParse(mpf,label, sourceTool, productId, archivePrevious);
            if (newSource != null)
                return ResponseHandler.created("source created",newSource.getId());
            return ResponseHandler.resp("Unknown error.", HttpStatus.INTERNAL_SERVER_ERROR, null);
        } catch (Exception e) {
            return ResponseHandler.resp(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
