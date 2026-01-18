/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : ONE_SHOTTest.java
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


class ONE_SHOTTest {

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
	 * Test method for {@link tempSensor.ds1621.configParameter.ONE_SHOT}.
	 */
	@Test
	void testONE_SHOT() {
		LOGGER.info("testONE_SHOT()");

		assertEquals(2, ONE_SHOT.values().length);
		
	} // testONE_SHOT()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.ONE_SHOT#getSetMask()}.
	 */
	@Test
	void testGetSetMask() {
		LOGGER.info("testGetSetMask()");

		LOGGER.info("ONE_SHOT.CONTINUOUSLY.getSetMask() = {}", String.format("%02X", ONE_SHOT.CONTINUOUSLY.getSetMask()));
		assertTrue(ONE_SHOT.CONTINUOUSLY.getSetMask() == 0b0000_0000, "CONTINUOUSLY");		
		
		LOGGER.info("ONE_SHOT.ONE_SHOT.getSetMask() = {}", String.format("%02X", ONE_SHOT.ONE_SHOT.getSetMask()));
		assertTrue(ONE_SHOT.ONE_SHOT.getSetMask() == 0b0000_0001, "ONE_SHOT");		
		
	} // testGetSetMask()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.ONE_SHOT#getClearMask()}.
	 */
	@Test
	void testGetClearMask() {
		LOGGER.info("testGetClearMask()");

		LOGGER.info("ONE_SHOT.getclearMask() = {}", String.format("%02X", ONE_SHOT.CONTINUOUSLY.getClearMask()));
		assertTrue(ONE_SHOT.CONTINUOUSLY.getClearMask() == 0b1111_1110, "clear mask");		
		
	} // testGetClearMask()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.ONE_SHOT#getValue()}.
	 */
	@Test
	void testGetValue() {
		LOGGER.info("testGetValue()");

		LOGGER.info("ONE_SHOT.CONTINUOUSLY.value = {}", String.format("%02X", ONE_SHOT.CONTINUOUSLY.value));
		assertTrue(ONE_SHOT.CONTINUOUSLY.value == 0b0, "CONTINUOUSLY");
		
		LOGGER.info("ONE_SHOT.ONE_SHOT.value = {}", String.format("%02X", ONE_SHOT.ONE_SHOT.value));
		assertTrue(ONE_SHOT.ONE_SHOT.value == 0b1, "ONE_SHOT");
		
	} // testGetValue()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.ONE_SHOT#getType(int)}.
	 */
	@Test
	void testValue2type() {
		LOGGER.info("testValue2type()");

		// config register: B000 0000
		
		assertTrue(ONE_SHOT.value2type(0) == ONE_SHOT.CONTINUOUSLY, "CONTINUOUSLY");		
		assertTrue(ONE_SHOT.value2type(1) == ONE_SHOT.ONE_SHOT, "ONE_SHOT");		
		assertTrue(ONE_SHOT.value2type(2) == null, "null");		
		
	} // testValue2type()

	
} // ssalc
