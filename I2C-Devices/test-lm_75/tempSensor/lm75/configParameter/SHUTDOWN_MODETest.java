/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : SHUTDOWN_MODETest.java
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


package tempSensor.lm75.configParameter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class SHUTDOWN_MODETest {

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
	 * Test method for {@link tempSensor.ds1621.configParameter.SHUTDOWN_MODE#getSetMask()}.
	 */
	@Test
	void testGetSetMask() {
		LOGGER.info("testGetSetMask()");

		LOGGER.info("SHUTDOWN_MODE.RUN_PERSISTENT.getSetMask() = {}", String.format("%02X", SHUTDOWN_MODE.RUN_PERSISTENT.getSetMask()));
		assertTrue(SHUTDOWN_MODE.RUN_PERSISTENT.getSetMask() == 0b0000_0000, "RUN_PERSISTENT");		
		
		LOGGER.info("SHUTDOWN_MODE.SUTDOWN.getSetMask() = {}", String.format("%02X", SHUTDOWN_MODE.SHUTDOWN.getSetMask()));
		assertTrue(SHUTDOWN_MODE.SHUTDOWN.getSetMask() == 0b0000_0001, "SUTDOWN");		
		
	} // testGetSetMask()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.SHUTDOWN_MODE#getClearMask()}.
	 */
	@Test
	void testGetClearMask() {
		LOGGER.info("testGetClearMask()");

		LOGGER.info("SHUTDOWN_MODE.getclearMask() = {}", String.format("%02X", SHUTDOWN_MODE.RUN_PERSISTENT.getClearMask()));
		assertTrue(SHUTDOWN_MODE.RUN_PERSISTENT.getClearMask() == 0b1111_1110, "clear mask");		
		
	} // testGetClearMask()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.SHUTDOWN_MODE#getValue()}.
	 */
	@Test
	void testGetValue() {
		LOGGER.info("testGetValue()");

		LOGGER.info("SHUTDOWN_MODE.RUN_PERSISTENT.value = {}", String.format("%02X", SHUTDOWN_MODE.RUN_PERSISTENT.value));
		assertTrue(SHUTDOWN_MODE.RUN_PERSISTENT.value == 0b0, "RUN_PERSISTENT");
		
		LOGGER.info("SHUTDOWN_MODE.SUTDOWN.value = {}", String.format("%02X", SHUTDOWN_MODE.SHUTDOWN.value));
		assertTrue(SHUTDOWN_MODE.SHUTDOWN.value == 0b1, "SUTDOWN");
		
	} // testGetValue()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.SHUTDOWN_MODE#getType(int)}.
	 */
	@Test
	void testValue2type() {
		LOGGER.info("testValue2type()");

		assertTrue(SHUTDOWN_MODE.value2type(0) == SHUTDOWN_MODE.RUN_PERSISTENT, "RUN_PERSISTENT");		
		assertTrue(SHUTDOWN_MODE.value2type(1) == SHUTDOWN_MODE.SHUTDOWN, "SUTDOWN");		
		assertTrue(SHUTDOWN_MODE.value2type(2) == null, "null");		
		
	} // testValue2type()

	
} // ssalc
