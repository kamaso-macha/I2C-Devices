/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : AddressableRegisterTest.java
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


package i2cDevice.register;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

class AddressableRegisterTest {

	private final Logger logger = LoggerFactory.getLogger(AddressableRegisterTest.class.getName());

	
	private class AddressableRegisterImpl extends AddressableRegister {

		public AddressableRegisterImpl(I2C aI2cDevice, byte aRegisterAddress, DataType aDataType) {
			super(aI2cDevice, aRegisterAddress, aDataType);
		}
		
	} // ssalc

	
	private AddressableRegisterImpl cut;
	private static I2C i2cDeviceMock;
	
	
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
		
		i2cDeviceMock = mock(MockI2C.class);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link i2cDevice.register.AddressableRegister#AddressableRegister(com.pi4j.io.i2c.I2C, byte, i2cDevice.register.DataType)}.
	 */
	@Test
	void testAddressableRegister() {
		logger.info("testAddressableRegister()");
		
		cut = new AddressableRegisterImpl(i2cDeviceMock, (byte) 0x42, DataType.WORD);
		
		assertEquals((byte) 0x42, cut.registerAddress);

	} // testAddressableRegister()
	

	/**
	 * Test method for {@link i2cDevice.register.AddressableRegister#readRaw()}.
	 */
	@Test
	void testReadRaw() {
		logger.info("testReadRaw()");
		
		byte address = (byte) 0x42;
		DataType type = DataType.WORD;
		int bytes = type.getSize();
		
		cut = new AddressableRegisterImpl(i2cDeviceMock, address, DataType.WORD);

		when(i2cDeviceMock.readRegister(address, cut.raw, 0, bytes))
			.thenReturn(1)
			.thenReturn(2)
			;

		I2CErrorException thrown;
		
		thrown = assertThrows(I2CErrorException.class, () -> cut.readRaw());
		assertEquals("Unable to read register.", thrown.getMessage());
		
		assertDoesNotThrow(() -> cut.readRaw());
		
		verify(i2cDeviceMock, times(2)).readRegister(address, cut.raw, 0, bytes);

	} // testReadRaw()
	

	/**
	 * Test method for {@link i2cDevice.register.AddressableRegister#writeRaw()}.
	 */
	@Test
	void testWriteRawByte() {
		logger.info("testWriteRawByte()");

		byte address = (byte) 0x42;
		DataType type = DataType.BYTE;
		int bytes = type.getSize();
		
		cut = new AddressableRegisterImpl(i2cDeviceMock, address, type);

		when(i2cDeviceMock.writeRegister(address, cut.raw, 0, bytes))
			.thenReturn(1)
			.thenReturn(2)
			;

		I2CErrorException thrown;
		
		thrown = assertThrows(I2CErrorException.class, () -> cut.writeRaw());
		assertEquals("Unable to write register.", thrown.getMessage());
		
		assertDoesNotThrow(() -> cut.writeRaw());
		
		verify(i2cDeviceMock, times(2)).writeRegister(address, cut.raw, 0, bytes);

	} // testWriteRawByte()
	

	/**
	 * Test method for {@link i2cDevice.register.AddressableRegister#writeRaw()}.
	 */
	@Test
	void testWriteRawWord() {
		logger.info("testWriteRawWord()");

		byte address = (byte) 0x42;
		DataType type = DataType.WORD;
		int bytes = type.getSize();
		
		cut = new AddressableRegisterImpl(i2cDeviceMock, address, type);

		when(i2cDeviceMock.writeRegister(address, cut.raw, 0, bytes))
			.thenReturn(1)
			.thenReturn(2)
			.thenReturn(3)
			;

		I2CErrorException thrown;
		
		thrown = assertThrows(I2CErrorException.class, () -> cut.writeRaw());
		assertEquals("Unable to write register.", thrown.getMessage());
		
		thrown = assertThrows(I2CErrorException.class, () -> cut.writeRaw());
		assertEquals("Unable to write register.", thrown.getMessage());
		
		assertDoesNotThrow(() -> cut.writeRaw());
		
		verify(i2cDeviceMock, times(3)).writeRegister(address, cut.raw, 0, bytes);

	} // testWriteRawWord()
	

} // ssalc
