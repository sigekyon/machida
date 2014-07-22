package junit;

import junit.framework.TestSuite;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ OutLogTest.class, RegInfCheckTest.class, RegInfDAOTest.class})
public class AllTests {
	
	/**public static Test suite(){
		TestSuite suite = new TestSuite("Test for default package");
		
		suite.addTest(new TestSuite(OutLogTest.class));
		suite.addTest(new TestSuite(RegInfCheckTest.class));
		suite.addTest(new TestSuite(RegInfDAOTest.class));
		
		return suite;
	}*/
}
