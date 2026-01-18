/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : CMP_INTTest.java
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



class CMP_INTTest {

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
	 * Test method for {@link tempSensor.ds1621.configParameter.CMP_INT#getSetMask()}.
	 */
	@Test
	void testGetSetMask() {
		LOGGER.info("testGetSetMask()");

		LOGGER.info("CMP_INT.COMPARATOR.getSetMask() = {}", String.format("%02X", CMP_INT.COMPARATOR.getSetMask()));
		assertTrue(CMP_INT.COMPARATOR.getSetMask() == 0b0000_0000, "COMPARATOR");		
		
		LOGGER.info("CMP_INT.INTERRUPT.getSetMask() = {}", String.format("%02X", CMP_INT.INTERRUPT.getSetMask()));
		assertTrue(CMP_INT.INTERRUPT.getSetMask() == 0b0000_0010, "INTERRUPT");		
		
	} // testGetSetMask()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.CMP_INT#getClearMask()}.
	 */
	@Test
	void testGetClearMask() {
		LOGGER.info("testGetClearMask()");

		LOGGER.info("CMP_INT.getclearMask() = {}", String.format("%02X", CMP_INT.COMPARATOR.getClearMask()));
		assertTrue(CMP_INT.COMPARATOR.getClearMask() == 0b1111_1101, "clear mask");		
		
	} // testGetClearMask()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.CMP_INT#getValue()}.
	 */
	@Test
	void testGetValue() {
		LOGGER.info("testGetValue()");

		LOGGER.info("CMP_INT.COMPARATOR.value = {}", String.format("%02X", CMP_INT.COMPARATOR.value));
		assertTrue(CMP_INT.COMPARATOR.value == 0b0, "COMPARATOR");
		
		LOGGER.info("CMP_INT.INTERRUPT.value = {}", String.format("%02X", CMP_INT.INTERRUPT.value));
		assertTrue(CMP_INT.INTERRUPT.value == 0b1, "INTERRUPT");
		
	} // testGetValue()

	
	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.CMP_INT#getType(int)}.
	 */
	@Test
	void testValue2type() {
		LOGGER.info("testValue2type()");

		// config register: B000 0000
		
		assertTrue(CMP_INT.value2type(0) == CMP_INT.COMPARATOR, "COMPARATOR");		
		assertTrue(CMP_INT.value2type(1) == CMP_INT.INTERRUPT, "INTERRUPT");		
		assertTrue(CMP_INT.value2type(2) == null, "null");		
		
	} // testValue2type()

	
} // ssalc
