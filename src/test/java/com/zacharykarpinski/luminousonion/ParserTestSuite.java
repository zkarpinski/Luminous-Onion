package com.zacharykarpinski.luminousonion;

import com.zacharykarpinski.luminousonion.parser.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("Parsers Suite")
@Suite
@SelectClasses({
    GrypeTest.class,
    SarifTest.class,
    TrivyTest.class
})
public class ParserTestSuite {

}
