/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DRTest.java
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


public class DRTest {

	private final Logger logger = LoggerFactory.getLogger(Test.class.getName());

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
	void test() {
		logger.info("test()");

		assertEquals(8, DR.values().length);
		
	} // test()

	
	/**
	 * Test method for {@link adc.ads1115.configParameter.#getSetMask()}.
	 */
	@Test
	void testGetSetMask() {
		logger.info("testGetSetMask()");

		logger.info(String.format("setMask = 0x%04X", DR.SPS_860.getSetMask()));

		assertEquals(0b0000_0000_0000_0000, DR.SPS_8.getSetMask());
		assertEquals(0b0000_0000_0010_0000, DR.SPS_16.getSetMask());
		assertEquals(0b0000_0000_0100_0000, DR.SPS_32.getSetMask());
		assertEquals(0b0000_0000_0110_0000, DR.SPS_64.getSetMask());
		assertEquals(0b0000_0000_1000_0000, DR.SPS_128.getSetMask());
		assertEquals(0b0000_0000_1010_0000, DR.SPS_250.getSetMask());
		assertEquals(0b0000_0000_1100_0000, DR.SPS_475.getSetMask());
		assertEquals(0b0000_0000_1110_0000, DR.SPS_860.getSetMask());
		
	} // testGetSetMask()
	

	/**
	 * Test method for {@link adc.ads1115.configParameter.#getClearMask()}.
	 */
	@Test
	void testGetClearMask() {
		logger.info("testGetClearMask()");

		logger.info(String.format("clearMask = 0x%04X", DR.SPS_860.getClearMask()));

		assertEquals(0b0_1111_1111_0001_1111, DR.SPS_8.getClearMask());
		assertEquals(0b0_1111_1111_0001_1111, DR.SPS_16.getClearMask());
		assertEquals(0b0_1111_1111_0001_1111, DR.SPS_32.getClearMask());
		assertEquals(0b0_1111_1111_0001_1111, DR.SPS_64.getClearMask());
		assertEquals(0b0_1111_1111_0001_1111, DR.SPS_128.getClearMask());
		assertEquals(0b0_1111_1111_0001_1111, DR.SPS_250.getClearMask());
		assertEquals(0b0_1111_1111_0001_1111, DR.SPS_475.getClearMask());
		assertEquals(0b0_1111_1111_0001_1111, DR.SPS_860.getClearMask());
		
	} // testGetClearMask()
	

	/**
	 * Test method for {@link adc.ads1115.configParameter.#getType(int)}.
	 */
	@Test
	void testConversionTime() {
		logger.info("testConversionTime()");

		final int millis = 1_000;
		
		assertEquals(millis /   8, DR.SPS_8.conversionTime);
		assertEquals(millis /  16, DR.SPS_16.conversionTime);
		assertEquals(millis /  32, DR.SPS_32.conversionTime);
		assertEquals(millis /  64, DR.SPS_64.conversionTime);
		assertEquals(millis / 128, DR.SPS_128.conversionTime);
		assertEquals(millis / 250, DR.SPS_250.conversionTime);
		assertEquals(millis / 475, DR.SPS_475.conversionTime);
		assertEquals(millis / 860, DR.SPS_860.conversionTime);
		
	} // testGetType()
	

	/**
	 * Test method for {@link adc.ads1115.configParameter.#value2type(int)}.
	 */
	@Test
	void testValue2type() {
		logger.info("testValue2type()");

		assertTrue(DR.value2type(0) == DR.SPS_8);		
		assertTrue(DR.value2type(1) == DR.SPS_16);		
		assertTrue(DR.value2type(2) == DR.SPS_32);		
		assertTrue(DR.value2type(3) == DR.SPS_64);		
		assertTrue(DR.value2type(4) == DR.SPS_128);		
		assertTrue(DR.value2type(5) == DR.SPS_250);		
		assertTrue(DR.value2type(6) == DR.SPS_475);		
		assertTrue(DR.value2type(7) == DR.SPS_860);		
		assertTrue(DR.value2type(8) == null, "null");		
		
	} // testValue2type()
	

} // ssalc
