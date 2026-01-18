/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS1621RegisterBaseTest.java
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
import static org.mockito.Mockito.*;

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


class DS1621RegisterBaseTest {

	private final Logger logger = LoggerFactory.getLogger(DS1621RegisterBaseTest.class.getName());

	private class DS1621RegisterBaseImpl extends DS1621RegisterBase {

		public DS1621RegisterBaseImpl(I2C aI2cDevice, COMMAND_SET aCommand, DataType aDataType) {
			super(aI2cDevice, aCommand, aDataType);
		}
		
		public void setRaw(byte[] aRaw) { raw = aRaw; }
		public byte[] getRaw() { return raw; }
		
	} // ssalc

	COMMAND_SET commandSet = COMMAND_SET.READ_TEMP;
	int command = commandSet.value;
	DataType dataType = DataType.WORD;
	
	MockI2C i2cDevice;
	DS1621RegisterBaseImpl cut;
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
		
		cut = new DS1621RegisterBaseImpl(i2cDevice, commandSet, dataType);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621RegisterBase#DS1621RegisterBase(com.pi4j.io.i2c.I2C, tempSensor.ds1621.configParameter.COMMAND_SET, i2cDevice.register.DataType)}.
	 */
	@Test
	void testDS1621RegisterBase() {
		logger.info("testDS1621RegisterBase()");
		
		// error handling
		// UNDEF is checked in the base class

		IllegalArgumentException thrown;
		
		thrown = assertThrows(IllegalArgumentException.class, () 
			-> new DS1621RegisterBaseImpl(i2cDevice, COMMAND_SET.ACCESS_CONFIG, DataType.BYTE));
		assertEquals("aDataType must be DataType.WORD.", thrown.getMessage());
		
		assertDoesNotThrow( () 
			-> new DS1621RegisterBaseImpl(i2cDevice, COMMAND_SET.ACCESS_CONFIG, DataType.WORD));
		
	} // testDS1621RegisterBase()
	

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621RegisterBase#read()}.
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
			
			// 1,0°
			rawBytes = new byte[] { (byte) 0x01, (byte) 0x00 };
			reference = 1.0f;
			verifyRead(reference, rawBytes);
			
			// 0,5°
			rawBytes = new byte[] { (byte) 0x00, (byte) 0x80 };
			reference = 0.5f;
			verifyRead(reference, rawBytes);
			
			// 0,0°
			rawBytes = new byte[] { (byte) 0x00, (byte) 0x00 };
			reference = 0.0f;
			verifyRead(reference, rawBytes);
			
			// -0,5°
			rawBytes = new byte[] { (byte) 0xFF, (byte) 0x80 };
			reference = -0.5f;
			verifyRead(reference, rawBytes);
			
			// -1,0°
			rawBytes = new byte[] { (byte) 0xFF, (byte) 0x00 };
			reference = -1.0f;
			verifyRead(reference, rawBytes);
			
			// -55,0°
			rawBytes = new byte[] { (byte) 0xC9, (byte) 0x00 };
			reference = -55.0f;
			verifyRead(reference, rawBytes);
			
		} catch (I2CErrorException e) {

			e.printStackTrace();
			fail("Unexpeted exception occured.");
			
		} // yrt
		
	} // testRead()
	
	
	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621RegisterBase#write(float)}.
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
			
			// 1,0°
			rawBytes = new byte[] { (byte) 0x01, (byte) 0x00 };
			value = 1.0f;
			verifyWrite(value);
			
			// 0,5°
			rawBytes = new byte[] { (byte) 0x00, (byte) 0x80 };
			value = 0.5f;
			verifyWrite(value);
			
			// 0,0°
			rawBytes = new byte[] { (byte) 0x00, (byte) 0x00 };
			value = 0.0f;
			verifyWrite(value);
			
			// -0,5°
			rawBytes = new byte[] { (byte) 0xFF, (byte) 0x80 };
			value = -0.5f;
			verifyWrite(value);
			
			// -1,0°
			rawBytes = new byte[] { (byte) 0xFF, (byte) 0x00 };
			value = -1.0f;
			verifyWrite(value);
			
			// -55,0°
			rawBytes = new byte[] { (byte) 0xC9, (byte) 0x00 };
			value = -55.0f;
			verifyWrite(value);

			I2CErrorException thrown = assertThrows(I2CErrorException.class, () 
				-> verifyWrite(42.0f));
			
			assertEquals("Unable to write register.", thrown.getMessage());

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
