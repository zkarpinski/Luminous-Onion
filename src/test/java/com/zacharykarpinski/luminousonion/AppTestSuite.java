package com.zacharykarpinski.luminousonion;

import com.zacharykarpinski.luminousonion.model.FindingTest;
import com.zacharykarpinski.luminousonion.model.SourceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("App Test Suite")
@Suite
@SelectClasses({
        FindingTest.class,
        SourceTest.class
})
public class AppTestSuite {

}
