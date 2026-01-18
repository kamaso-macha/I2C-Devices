/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : Ads1115AInputTest.java
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


package adc.ads1115;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import adc.ads1115.configParameter.MUX;
import adc.ads1115.configParameter.PGA;


class Ads1115AInputTest {

	private final Logger logger = LoggerFactory.getLogger(Ads1115AInputTest.class.getName());

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
	 * Test method for {@link adc.ads1115.Ads1115AInput#Ads1115AInput(adc.ads1115.configParameter.MUX, adc.ads1115.configParameter.PGA)}.
	 */
	@Test
	void testAds1115AInput() {
		logger.info("testAds1115AInput()");
		
		IllegalArgumentException thrown;
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new Ads1115AInput(null, null));
		assertEquals("aMux can't be null.", thrown.getMessage());
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new Ads1115AInput(MUX.DIFF_0_1, null));
		assertEquals("aPga can't be null.", thrown.getMessage());
		
		assertDoesNotThrow(() -> new Ads1115AInput(MUX.DIFF_0_3, PGA.FSR_0_256V));
		
	} // testAds1115AInput()

	/**
	 * Test method for {@link adc.ads1115.Ads1115AInput#toString()}.
	 */
	@Test
	void testToString() {
		logger.info("testToString()");

		Ads1115AInput cut = new Ads1115AInput(MUX.DIFF_0_3, PGA.FSR_0_256V);
		assertEquals("Ads1115AInput [mux=DIFF_0_3, pga=FSR_0_256V]", cut.toString());
		
	} // testToString()

} // ssalc
