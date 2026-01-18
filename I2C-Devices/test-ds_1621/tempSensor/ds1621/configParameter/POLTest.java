/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : POLTest.java
 *
 * More information about this project can be found on Github
 * http://github.com/kamaso-macha/I2CDevices
 *
 * **********************************************************************
 *
 * Copyright (C)2025 by Stefan Dickel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 *
 */


package tempSensor.ds1621.configParameter;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class POLTest {

	private static Logger LOGGER = null;
	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	    System.setProperty("log4j.configurationFile","./test-cfg/log4j2.xml");
		LOGGER = LogManager.getLogger();
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.POL}.
	 */
	@Test
	void testPOL() {
		LOGGER.info("testPOL()");

		assertEquals(2, POL.values().length);
		
	} // testPOL()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.POL#getSetMask()}.
	 */
	@Test
	void testGetSetMask() {
		LOGGER.info("testGetSetMask()");

		LOGGER.info("POL.ACT_LOW.getSetMask() = {}", String.format("%02X", POL.ACT_LOW.getSetMask()));
		assertTrue(POL.ACT_LOW.getSetMask() == 0b0000_0000, "ACT_LOW");		
		
		LOGGER.info("POL.ACT_HIGH.getSetMask() = {}", String.format("%02X", POL.ACT_HIGH.getSetMask()));
		assertTrue(POL.ACT_HIGH.getSetMask() == 0b0000_0010, "ACT_HIGH");		
		
	} // testGetSetMask()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.POL#getClearMask()}.
	 */
	@Test
	void testGetClearMask() {
		LOGGER.info("testGetClearMask()");

		LOGGER.info("POL.getclearMask() = {}", String.format("%02X", POL.ACT_HIGH.getClearMask()));
		assertTrue(POL.ACT_HIGH.getClearMask() == 0b1111_1101, "clear mask");		
		
	} // testGetClearMask()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.POL#getValue()}.
	 */
	@Test
	void testGetValue() {
		LOGGER.info("testGetValue()");

		LOGGER.info("POL.ACT_LOW.value = {}", String.format("%02X", POL.ACT_LOW.value));
		assertTrue(POL.ACT_LOW.value == 0b0, "ACT_LOW");
		
		LOGGER.info("POL.ACT_HIGH.value = {}", String.format("%02X", POL.ACT_HIGH.value));
		assertTrue(POL.ACT_HIGH.value == 0b1, "ACT_HIGH");
		
	} // testGetValue()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.POL#getType(int)}.
	 */
	@Test
	void testValue2type() {
		LOGGER.info("testValue2type()");

		assertTrue(POL.value2type(0) == POL.ACT_LOW, "ACT_LOW");		
		assertTrue(POL.value2type(1) == POL.ACT_HIGH, "ACT_HIGH");		
		assertTrue(POL.value2type(2) == null, "null");		
		
	} // testValue2type()

	
} // ssalc
