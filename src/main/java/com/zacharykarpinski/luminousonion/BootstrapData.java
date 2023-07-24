package com.zacharykarpinski.luminousonion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello");

    }
}

