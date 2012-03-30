package org.noranj.formak.server.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	//@ Test
	public void testDeserializeHttpServletRequest() {
		fail("Not yet implemented");
	}

	//@ Test
	public void testDeserializeByteArray() {
		fail("Not yet implemented");
	}

	//@ Test
	public void testStackTraceToString() {
		fail("Not yet implemented");
	}

	//@ Test
	public void testReadAll() {
		fail("Not yet implemented");
	}

	//@ Test
	public void testBuildMap() {
		fail("Not yet implemented");
	}

	@Test
	public void testExtractNamesFromEmail() {
		/* the method is no longer used. instead using InternetAddress.parse.
		String[] names = Utils.extractNamesFromEmail("Babak Noranj<babak@noranj.com>");
		
		if (!names[0].equals("Babak") || !names[1].equals("Noranj") ||!names[2].equals("babak@noranj.com")) {
			fail("input[Babak Noranj<babak@noranj.com>] => One of the names does not match. names{"+names[0]+", "+names[1]+", "+names[2]+"}");
		} 

		names = null;
		
		names = Utils.extractNamesFromEmail("babak@noranj.com");
		
		if (names[0]!=null || names[1]!=null ||!names[2].equals("babak@noranj.com")) {
			fail("input[babak@noranj.com] => One of the names does not match. names{"+names[0]+", "+names[1]+", "+names[2]+"}");
		} 

		names = null;
		names = Utils.extractNamesFromEmail("<babak@noranj.com>");
		
		if (names[0]!=null || names[1]!=null ||!names[2].equals("babak@noranj.com")) {
			fail("One of the names does not match. names{"+names[0]+", "+names[1]+", "+names[2]+"}");
		}
		else {
			System.out.println("input[<babak@noranj.com>] => names{"+names[0]+", "+names[1]+", "+names[2]+"}");
		}
		
		names = null;
		names = Utils.extractNamesFromEmail("babak@noranj.com>");
		
		if (names[0]!=null || names[1]!=null ||!names[2].equals("babak@noranj.com")) {
			fail("One of the names does not match. names{"+names[0]+", "+names[1]+", "+names[2]+"}");
		}
		else {
			System.out.println("input[babak@noranj.com>] => names{"+names[0]+", "+names[1]+", "+names[2]+"}");
		}
		*/
	}

}
