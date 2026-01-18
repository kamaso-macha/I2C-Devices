/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : COMP_MODETest.java
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


class COMP_MODETest {

	private final Logger logger = LoggerFactory.getLogger(COMP_MODETest.class.getName());

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
	void testCOMP_MODE() {
		logger.info("testCOMP_MODE()");

		assertEquals(2, COMP_MODE.values().length);
		
	} // testCOMP_MODE()

	
	/**
	 * Test method for {@link adc.ads1115.configParameter.COMP_MODE#getSetMask()}.
	 */
	@Test
	void testGetSetMask() {
		logger.info("testGetSetMask()");

		assertEquals(0b0_0000_0000__0000_0000, COMP_MODE.TRADITIONAL.getSetMask());
		assertEquals(0b0_0000_0000__0001_0000, COMP_MODE.WINDOW_COMP.getSetMask());
		
	} // testGetSetMask()
	

	/**
	 * Test method for {@link adc.ads1115.configParameter.COMP_MODE#getClearMask()}.
	 */
	@Test
	void testGetClearMask() {
		logger.info("testGetClearMask()");

		assertEquals(0b0_1111_1111__1110_1111, COMP_MODE.TRADITIONAL.getClearMask());
		assertEquals(0b0_1111_1111__1110_1111, COMP_MODE.WINDOW_COMP.getClearMask());
		
	} // testGetClearMask()
	

	/**
	 * Test method for {@link adc.ads1115.configParameter.COMP_MODE#value2type(int)}.
	 */
	@Test
	void testValue2type() {
		logger.info("testValue2type()");

		assertTrue(COMP_MODE.value2type(0) == COMP_MODE.TRADITIONAL);		
		assertTrue(COMP_MODE.value2type(1) == COMP_MODE.WINDOW_COMP);		
		assertTrue(COMP_MODE.value2type(2) == null, "null");		
		
	} // testValue2type()
	

} // ssalc
