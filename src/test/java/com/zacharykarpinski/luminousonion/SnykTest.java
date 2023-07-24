package com.zacharykarpinski.luminousonion;

import com.zacharykarpinski.luminousonion.integration.SnykIntegration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SnykTest {

    @Autowired
    SnykIntegration snykIntegration;

    @Test
    @Disabled
    public void testSnykIntegration() {
        snykIntegration.getRest();
    }
}
