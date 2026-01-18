/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : I2CDeviceBaseTest.java
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


package i2cDevice;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsibilities:<br>
 * 
 * 
 * <p>
 * Collaborators:<br>
 * 
 * 
 * <p>
 * Description:<br>
 * 
 * 
 * <p>
 * @author Stefan
 *
 */

class I2CDeviceBaseTest {

	private final Logger logger = LoggerFactory.getLogger(I2CDeviceBaseTest.class.getName());
	
	private class I2CDeviceBaseImpl extends I2CDeviceBase {

		/**
		 * @param aI2cBusNbr
		 * @param aI2cAddress
		 */
		public I2CDeviceBaseImpl(int aI2cBusNbr, int aI2cAddress) {
			super(aI2cBusNbr, aI2cAddress);

			logger.trace("I2CDeviceBase(): aI2cBusNbr = {}, aI2cAddress = {}", aI2cBusNbr, aI2cAddress);

		} // I2CDeviceBaseImpl()
		
	} // ssalc

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
	 * Test method for {@link i2cDevice.I2CDeviceBase#I2CDeviceBase(int, int)}.
	 */
	@Test
	void testI2CDeviceBase() {
		logger.info("testI2CDeviceBase()");
	
		IllegalArgumentException thrown;
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new I2CDeviceBaseImpl(-1, 0x00));
		assertEquals("aI2cBusNbr must be in the range of 0 .. n.", thrown.getMessage());
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new I2CDeviceBaseImpl(1, 0x00));
		assertEquals("aI2cAddress must be in the range of 0x08 .. 0x7F.", thrown.getMessage());
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new I2CDeviceBaseImpl(1, 0x07));
		assertEquals("aI2cAddress must be in the range of 0x08 .. 0x7F.", thrown.getMessage());
		
		assertDoesNotThrow(() -> new I2CDeviceBaseImpl(1, 0x08));
		assertDoesNotThrow(() -> new I2CDeviceBaseImpl(1, 0x7F));
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new I2CDeviceBaseImpl(1, 0x80));
		assertEquals("aI2cAddress must be in the range of 0x08 .. 0x7F.", thrown.getMessage());
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new I2CDeviceBaseImpl(1, 0xFF));
		assertEquals("aI2cAddress must be in the range of 0x08 .. 0x7F.", thrown.getMessage());
		
	
	} // testI2CDeviceBase()

	
	/**
	 * Test method for {@link i2cDevice.I2CDeviceBase#I2CDeviceBase(int, int)}.
	 */
	@Test
	void testI2CDeviceCreation() {
		logger.info("testI2CDeviceCreation()");
	
			I2CDeviceBaseImpl cut = new I2CDeviceBaseImpl(1, 0x08);
			
			assertEquals("I2C_1_08", cut.i2cDevice.getId(), "device id");
			assertEquals(1, cut.i2cDevice.bus(), "bus id");
			assertEquals(0x08, cut.i2cDevice.getDevice(), "device address");
			assertTrue(cut.i2cDevice.isOpen(), "device status");

	} // testI2CDeviceCreation()
	
	
} // ssalc
