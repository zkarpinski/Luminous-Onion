package com.zacharykarpinski.luminousonion.parsers;

import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.Source;
import org.javatuples.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Parser {

    static Pair<Source, List<Finding>> parse(MultipartFile mpf) {
        return null;
    }

}
