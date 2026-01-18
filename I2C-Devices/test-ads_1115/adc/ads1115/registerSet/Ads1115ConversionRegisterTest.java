/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : Ads1115ConversionRegisterTest.java
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


package adc.ads1115.registerSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.plugin.mock.provider.i2c.MockI2C;

import adc.ads1115.configParameter.PGA;
import i2cDevice.register.DataType;


class Ads1115ConversionRegisterTest {

	private final Logger logger = LoggerFactory.getLogger(Ads1115ConversionRegisterTest.class.getName());

	final int regAdr = 0x0042;
	final DataType dataType = DataType.WORD;
	final int dataSize = dataType.getSize();
	
	MockI2C i2cMock;

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

		i2cMock = mock(MockI2C.class);
		assertTrue(i2cMock != null);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115ConversionRegister#write(float, adc.ads1115.configParameter.PGA)}.
	 */
	@Test
	void testWrite() {
		logger.info("testWrite()");

		Ads1115ConversionRegister cut = new Ads1115ConversionRegister(i2cMock);
		
		IllegalAccessError thrown = 
			assertThrows(IllegalAccessError.class, () -> cut.write(69.69f, PGA.FSR_0_256V));
		
		assertEquals("r/- register doesn't provide a write() method).", thrown.getMessage());
		
	} // testWrite()

} // ssalc
