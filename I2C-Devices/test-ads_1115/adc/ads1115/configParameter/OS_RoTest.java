/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : OS_RoTest.java
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


package adc.ads1115.configParameter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class OS_RoTest {

	private final Logger logger = LoggerFactory.getLogger(OS_RoTest.class.getName());

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
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
	void testOS_Ro() {
		logger.info("testOS_Ro()");

		assertEquals(2, OS_Ro.values().length);
		
	} // testOS_Ro()

	
	/**
	 * Test method for {@link adc.ads1115.configParameter.OS_Ro#getSetMask()}.
	 */
	@Test
	void testGetSetMask() {
		logger.info("testGetSetMask()");

		assertEquals(0b0_0000_0000__0000_0000, OS_Ro.CONVERSION_RUNNING.getSetMask());
		assertEquals(0b0_1000_0000__0000_0000, OS_Ro.CONVERSION_IDLE.getSetMask());
		
	} // testGetSetMask()
	

	/**
	 * Test method for {@link adc.ads1115.configParameter.OS_Ro#getClearMask()}.
	 */
	@Test
	void testGetClearMask() {
		logger.info("testGetClearMask()");

		logger.info(String.format("clearMask = 0x%04X", OS_Ro.CONVERSION_IDLE.getClearMask()));
		
		assertEquals(0b0_0111_1111__1111_1111, OS_Ro.CONVERSION_RUNNING.getClearMask());
		assertEquals(0b0_0111_1111__1111_1111, OS_Ro.CONVERSION_IDLE.getClearMask());
		
	} // testGetClearMask()
	

	/**
	 * Test method for {@link adc.ads1115.configParameter.OS_Ro#value2type(int)}.
	 */
	@Test
	void testValue2type() {
		logger.info("testValue2type()");

		assertTrue(OS_Ro.value2type(3) == null, "null");	
		
		assertEquals(OS_Ro.value2type(0), OS_Ro.CONVERSION_RUNNING);		
		assertEquals(OS_Ro.value2type(1), OS_Ro.CONVERSION_IDLE);		
		
	} // testValue2type()
	

} // ssalc
