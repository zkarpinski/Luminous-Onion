package com.zacharykarpinski.luminousonion.cucumber.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = {"src/test/resources/Features"}, glue = { "com.zacharykarpinski.luminousonion.cucumber.definitions"})

public class CucumberRunnerTests {
}
