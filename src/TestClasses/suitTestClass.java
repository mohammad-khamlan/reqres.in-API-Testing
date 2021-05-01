package TestClasses;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({

	TestGetRestAPI.class,
	TestPostRestAPI.class,
	TestPUTRestAPI.class,
//	TestDELETERestAPI.class,
//	TestPOSTRegister.class	
})

public class suitTestClass {}
