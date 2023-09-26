package com.zacharykarpinski.luminousonion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(BootstrapData.class);
    @Override
    public void run(String... args) throws Exception {
        logger.info("Hello!");

    }
}

