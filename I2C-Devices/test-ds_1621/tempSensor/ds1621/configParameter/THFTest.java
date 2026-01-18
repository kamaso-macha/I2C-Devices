/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : THFTest.java
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


class THFTest {

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
	 * Test method for {@link tempSensor.ds1621.configParameter.THF}.
	 */
	@Test
	void testTHF() {
		LOGGER.info("testTHF()");

		assertEquals(2, THF.values().length);
		
	} // testTHF()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.THF#getSetMask()}.
	 */
	@Test
	void testGetSetMask() {
		LOGGER.info("testGetSetMask()");

		LOGGER.info("THF.BELOW.getSetMask() = {}", String.format("%02X", THF.BELOW.getSetMask()));
		assertTrue(THF.BELOW.getSetMask() == 0b0000_0000, "BELOW");		
		
		LOGGER.info("THF.GT_EQ.getSetMask() = {}", String.format("%02X", THF.GT_EQ.getSetMask()));
		assertTrue(THF.GT_EQ.getSetMask() == 0b0100_0000, "GT_EQ");		
		
	} // testGetSetMask()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.THF#getClearMask()}.
	 */
	@Test
	void testGetClearMask() {
		LOGGER.info("testGetClearMask()");

		LOGGER.info("THF.getclearMask() = {}", String.format("%02X", THF.BELOW.getClearMask()));
		assertTrue(THF.BELOW.getClearMask() == 0b1011_1111, "clear mask");		
		
	} // testGetClearMask()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.THF#getValue()}.
	 */
	@Test
	void testGetValue() {
		LOGGER.info("testGetValue()");

		LOGGER.info("THF.BELOW.value = {}", String.format("%02X", THF.BELOW.value));
		assertTrue(THF.BELOW.value == 0b0, "BELOW");
		
		LOGGER.info("THF.GT_EQ.value = {}", String.format("%02X", THF.GT_EQ.value));
		assertTrue(THF.GT_EQ.value == 0b1, "GT_EQ");
		
	} // testGetValue()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.THF#getType(int)}.
	 */
	@Test
	void testValue2type() {
		LOGGER.info("testValue2type()");

		assertTrue(THF.value2type(0) == THF.BELOW, "BELOW");		
		assertTrue(THF.value2type(1) == THF.GT_EQ, "GT_EQ");		
		assertTrue(THF.value2type(2) == null, "null");		
		
	} // testValue2type()

	
} // ssalc
