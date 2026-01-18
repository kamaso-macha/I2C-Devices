/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : MUXTest.java
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


class MUXTest {

	private final Logger logger = LoggerFactory.getLogger(MUXTest.class.getName());

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
	void testMUX() {
		logger.info("testMUX()");

		assertEquals(8, MUX.values().length);
		
	} // testMUX()

	
	/**
	 * Test method for {@link adc.ads1115.configParameter.MUX#getSetMask()}.
	 */
	@Test
	void testGetSetMask() {
		logger.info("testGetSetMask()");

		logger.info(String.format("clearMask = 0x%04X", MUX.SINGLE_3.getSetMask()));

		assertEquals(0b0_0000_0000__0000_0000, MUX.DIFF_0_1.getSetMask());
		assertEquals(0b0_0001_0000__0000_0000, MUX.DIFF_0_3.getSetMask());
		assertEquals(0b0_0010_0000__0000_0000, MUX.DIFF_1_3.getSetMask());
		assertEquals(0b0_0011_0000__0000_0000, MUX.DIFF_2_3.getSetMask());
		assertEquals(0b0_0100_0000__0000_0000, MUX.SINGLE_0.getSetMask());
		assertEquals(0b0_0101_0000__0000_0000, MUX.SINGLE_1.getSetMask());
		assertEquals(0b0_0110_0000__0000_0000, MUX.SINGLE_2.getSetMask());
		assertEquals(0b0_0111_0000__0000_0000, MUX.SINGLE_3.getSetMask());
		
	} // testGetSetMask()
	

	/**
	 * Test method for {@link adc.ads1115.configParameter.MUX#getClearMask()}.
	 */
	@Test
	void testGetClearMask() {
		logger.info("testGetClearMask()");

		logger.info(String.format("clearMask = 0x%04X", MUX.SINGLE_3.getClearMask()));
		
		assertEquals(0b0_1000_1111__1111_1111, MUX.DIFF_0_1.getClearMask());
		assertEquals(0b0_1000_1111__1111_1111, MUX.DIFF_0_3.getClearMask());
		assertEquals(0b0_1000_1111__1111_1111, MUX.DIFF_1_3.getClearMask());
		assertEquals(0b0_1000_1111__1111_1111, MUX.DIFF_2_3.getClearMask());
		assertEquals(0b0_1000_1111__1111_1111, MUX.SINGLE_0.getClearMask());
		assertEquals(0b0_1000_1111__1111_1111, MUX.SINGLE_1.getClearMask());
		assertEquals(0b0_1000_1111__1111_1111, MUX.SINGLE_2.getClearMask());
		assertEquals(0b0_1000_1111__1111_1111, MUX.SINGLE_3.getClearMask());
		
	} // testGetClearMask()
	

	/**
	 * Test method for {@link adc.ads1115.configParameter.MUX#value2type(int)}.
	 */
	@Test
	void testValue2type() {
		logger.info("testValue2type()");

		assertTrue(MUX.value2type(8) == null, "null");	
		
		assertEquals(MUX.value2type(0), MUX.DIFF_0_1);		
		assertEquals(MUX.value2type(1), MUX.DIFF_0_3);		
		assertEquals(MUX.value2type(2), MUX.DIFF_1_3);		
		assertEquals(MUX.value2type(3), MUX.DIFF_2_3);		
		assertEquals(MUX.value2type(4), MUX.SINGLE_0);		
		assertEquals(MUX.value2type(5), MUX.SINGLE_1);		
		assertEquals(MUX.value2type(6), MUX.SINGLE_2);		
		assertEquals(MUX.value2type(7), MUX.SINGLE_3);		
		
	} // testValue2type()
	

} // ssalc
