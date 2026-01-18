/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : NVBTest.java
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


class NVBTest {

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
	 * Test method for {@link tempSensor.ds1621.configParameter.NVB}.
	 */
	@Test
	void testNVB() {
		LOGGER.info("testNVB()");

		assertEquals(2, NVB.values().length);
		
	} // testNVB()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.NVB#getSetMask()}.
	 */
	@Test
	void testGetSetMask() {
		LOGGER.info("testGetSetMask()");
		LOGGER.info("NVB.COMPLETE.getSetMask() = {}", String.format("%02X", NVB.COMPLETE.getSetMask()));
		assertTrue(NVB.COMPLETE.getSetMask() == 0b0000_0000, "COMPLETE");		
		
		LOGGER.info("NVB.IN_PROGRESS.getSetMask() = {}", String.format("%02X", NVB.IN_PROGRESS.getSetMask()));
		assertTrue(NVB.IN_PROGRESS.getSetMask() == 0b0001_0000, "IN_PROGRESS");		
		
	} // testGetSetMask()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.NVB#getClearMask()}.
	 */
	@Test
	void testGetClearMask() {
		LOGGER.info("testGetClearMask()");

		LOGGER.info("NVB.getclearMask() = {}", String.format("%02X", NVB.IN_PROGRESS.getClearMask()));
		assertTrue(NVB.IN_PROGRESS.getClearMask() == 0b1110_1111, "clear mask");	
		
	} // testGetClearMask()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.NVB#getValue()}.
	 */
	@Test
	void testGetValue() {
		LOGGER.info("testGetValue()");

		LOGGER.info("NVB.COMPLETE.value = {}", String.format("%02X", NVB.COMPLETE.value));
		assertTrue(NVB.COMPLETE.value == 0b0, "COMPLETE");
		
		LOGGER.info("NVB.IN_PROGRESS.value = {}", String.format("%02X", NVB.IN_PROGRESS.value));
		assertTrue(NVB.IN_PROGRESS.value == 0b1, "IN_PROGRESS");
		
	} // testGetValue()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.NVB#getType(int)}.
	 */
	@Test
	void testValue2type() {
		LOGGER.info("testValue2type()");

		assertTrue(NVB.value2type(0) == NVB.COMPLETE, "COMPLETE");		
		assertTrue(NVB.value2type(1) == NVB.IN_PROGRESS, "IN_PROGRESS");		
		assertTrue(NVB.value2type(2) == null, "null");		
		
	} // testValue2type()

	
} // ssalc
