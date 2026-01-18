/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DONETest.java
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DONETest {

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
	 * Test method for {@link tempSensor.ds1621.configParameter.DONE}.
	 */
	@Test
	void testDONE() {
		LOGGER.info("testDONE()");

		assertEquals(2, DONE.values().length);
		
	} // testDONE()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.DONE#getSetMask()}.
	 */
	@Test
	void testGetSetMask() {
		LOGGER.info("testGetSetMask()");

		LOGGER.info("DONE.IN_PROGRESS.getSetMask() = {}", String.format("%02X", DONE.IN_PROGRESS.getSetMask()));
		assertTrue(DONE.IN_PROGRESS.getSetMask() == 0b0000_0000, "IN_PROGRESS");		
		
		LOGGER.info("DONE.COMPLETE.getSetMask() = {}", String.format("%02X", DONE.COMPLETE.getSetMask()));
		assertTrue(DONE.COMPLETE.getSetMask() == 0b1000_0000, "COMPLETE");		
		
	} // testGetSetMask()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.DONE#getClearMask()}.
	 */
	@Test
	void testGetClearMask() {
		LOGGER.info("testGetClearMask()");

		LOGGER.info("DONE.getclearMask() = {}", String.format("%02X", DONE.COMPLETE.getClearMask()));
		assertTrue(DONE.COMPLETE.getClearMask() == 0b0111_1111, "clear mask");		
		
	} // testGetClearMask()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.DONE#getValue()}.
	 */
	@Test
	void testGetValue() {
		LOGGER.info("testGetValue()");

		LOGGER.info("DONE.IN_PROGRESS.value = {}", String.format("%02X", DONE.IN_PROGRESS.value));
		assertTrue(DONE.IN_PROGRESS.value == 0b0, "IN_PROGRESS");
		
		LOGGER.info("DONE.COMPLETE.value = {}", String.format("%02X", DONE.COMPLETE.value));
		assertTrue(DONE.COMPLETE.value == 0b1, "COMPLETE");
		
	} // testGetValue()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.DONE#getType(int)}.
	 */
	@Test
	void testValue2type() {
		LOGGER.info("testValue2type()");

		// config register: B000 0000
		
		assertTrue(DONE.value2type(0) == DONE.IN_PROGRESS, "IN_PROGRESS");		
		assertTrue(DONE.value2type(1) == DONE.COMPLETE, "COMPLETE");		
		assertTrue(DONE.value2type(2) == null, "null");		
		
	} // testValue2type()

	
} // ssalc
