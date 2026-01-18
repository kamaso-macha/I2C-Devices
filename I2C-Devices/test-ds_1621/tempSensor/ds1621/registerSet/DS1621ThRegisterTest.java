/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS1621ThRegisterTest.java
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


package tempSensor.ds1621.registerSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.i2c.I2C;
import com.pi4j.plugin.mock.provider.i2c.MockI2C;

import i2cDevice.I2CErrorException;
import i2cDevice.register.DataType;
import tempSensor.ds1621.configParameter.COMMAND_SET;

class DS1621ThRegisterTest {

	private final Logger logger = LoggerFactory.getLogger(DS1621ThRegisterTest.class.getName());

	COMMAND_SET commandSet = COMMAND_SET.ACCESS_TH;
	int command = commandSet.value;
	DataType dataType = DataType.WORD;

	private class TestDS1621ThRegister extends DS1621ThRegister {

		public TestDS1621ThRegister(I2C aI2cDevice, DS1621TemperatureStatusFlags aStatusFlags) {
			super(aI2cDevice, aStatusFlags);
		}
		
		public void setRaw(byte[] aRaw) { raw = aRaw; }
		public byte[] getRaw() { return raw; }

	} // sssalc
	
	
	MockI2C i2cDevice;
	DS1621TemperatureStatusFlags statusFlags;

	TestDS1621ThRegister cut;
	byte[] rawBytes;
	
	
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
		
		i2cDevice = mock(MockI2C.class);
		statusFlags = mock(DS1621ConfigRegister.class);
		
		cut = new TestDS1621ThRegister(i2cDevice,statusFlags);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621ThRegister#DS1621ThRegister(com.pi4j.io.i2c.I2C, tempSensor.ds1621.registerSet.DS1621TemperatureStatusFlags)}.
	 */
	@Test
	void testDS1621ThRegister() {
		logger.info("testDS1621ThRegister()");
		
		I2C i2cDevice = mock(MockI2C.class);
		DS1621TemperatureStatusFlags statusFlagsMock = mock(DS1621ConfigRegister.class);
		
		IllegalArgumentException thrown;
		
		thrown = assertThrows(IllegalArgumentException.class, () 
			-> new DS1621ThRegister(i2cDevice, null));
		assertEquals("aStatusFlags can't be null.", thrown.getMessage());
		
		assertDoesNotThrow(() -> new DS1621ThRegister(i2cDevice, statusFlagsMock));
		
	} // testDS1621ThRegister()
	

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621ThRegister#read()}.
	 */
	@Test
	void testRead() {
		logger.info("testRead()");
		
		try {
			
			float reference;
			
			byte[] rawBytes = cut.getRaw();
			
			// 125,0°
			rawBytes = new byte[] { (byte) 0x7D, (byte) 0x00 };
			reference = 125.0f;		
			verifyRead(reference, rawBytes);
			
		} catch (I2CErrorException e) {

			e.printStackTrace();
			fail("Unexpeted exception occured.");
			
		} // yrt
		
	} // testRead()
	

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621ThRegister#write(float)}.
	 */
	@Test
	void testWrite() {
		logger.info("testWrite()");
		
		try {
		
			float value;
		
			// 125,0°
			rawBytes = new byte[] { (byte) 0x7D, (byte) 0x00 };
			value = 125.0f;		
			verifyWrite(value);
			
		} catch (I2CErrorException e) {

			e.printStackTrace();
			fail("Unexpeted exception occured.");
			
		} // yrt
		
	} // testWrite()
	

	/**
	 * @param value
	 * @throws I2CErrorException
	 */
	private void verifyWrite(float value) throws I2CErrorException {
		logger.info("verifyWrite(): value = {}", value);
		
		when(i2cDevice.writeRegister(command, rawBytes, 0, dataType.getSize()))
		.thenReturn(dataType.getSize() + 1)
		;

		cut.write(value);		
		verify(i2cDevice, atMost(7)).writeRegister((byte) command, rawBytes, 0, dataType.getSize());
		assertEquals(rawBytes[0], cut.getRaw()[0]);
		assertEquals(rawBytes[1], cut.getRaw()[1]);
		
	} // verifyWrite()
	

	private void verifyRead(float reference, byte[] rawBytes) throws I2CErrorException {
		logger.info("(): reference = {}, rawBytes[0] = {}, rawBytes[1] = {}", reference, rawBytes[0], rawBytes[1]);
		
		float result;
		cut.setRaw(rawBytes);
		
		when(i2cDevice.readRegister(command, rawBytes, 0, dataType.getSize()))
		.thenReturn(dataType.getSize())
		;

		result = cut.read();
		assertEquals(reference, result);
		
	} // verifyRead()
	

} // sslac
