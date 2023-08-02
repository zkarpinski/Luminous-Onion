package com.zacharykarpinski.luminousonion.service;

import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.model.SourceTool;
import com.zacharykarpinski.luminousonion.parsers.*;
import com.zacharykarpinski.luminousonion.repository.FindingRepository;
import com.zacharykarpinski.luminousonion.repository.SourceRepository;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class UploadSourceService {

    @Autowired
    private FindingRepository findingRepository;
    @Autowired
    private SourceRepository sourceRepository;

    public Source uploadFileAndParse(MultipartFile mpf, SourceTool tool) throws IOException {

        Pair<Source, List<Finding>> parsed = switch (tool) {
            case ANCORE_GRYPE -> Grype.parse(mpf);
            case AQUA_TRIVY -> Trivy.parse(mpf);
            default -> null;
        };

        // Save source to the repo
        Source source = sourceRepository.save(parsed.getValue0());

        // Save the findings
        findingRepository.saveAll(parsed.getValue1());

        return source;
    }

}
