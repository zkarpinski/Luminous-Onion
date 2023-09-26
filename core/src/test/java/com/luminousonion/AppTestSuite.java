package com.luminousonion;

import com.luminousonion.model.FindingTest;
import com.luminousonion.model.SourceTest;
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
