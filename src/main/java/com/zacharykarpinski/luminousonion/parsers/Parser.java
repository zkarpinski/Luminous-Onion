package com.zacharykarpinski.luminousonion.parsers;

import com.zacharykarpinski.luminousonion.model.Source;
import org.springframework.web.multipart.MultipartFile;


public interface Parser {

    static Source parse(MultipartFile mpf) {
        return null;
    }

}
