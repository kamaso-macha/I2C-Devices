/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : RegisterBaseTest.java
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

class RegisterBaseTest {

	private final Logger logger = LoggerFactory.getLogger(RegisterBaseTest.class.getName());

	private class RegisterBaseImpl extends RegisterBase {
		public RegisterBaseImpl(I2C aI2cDevice, DataType aDataType) {
			super(aI2cDevice, aDataType);
		}
		
	} // ssalc
	
	
	private RegisterBaseImpl cut;
	private static I2C i2cDeviceMock;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		i2cDeviceMock = mock(MockI2C.class);
		
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
	 * Test method for {@link i2cDevice.register.RegisterBase#RegisterBase(com.pi4j.io.i2c.I2C, i2cDevice.register.DataType)}.
	 */
	@Test
	void testRegisterBase() {
		logger.info("testRegisterBase()");
		
		IllegalArgumentException thrown;
		
		// error detection
		thrown = assertThrows(IllegalArgumentException.class, () -> new RegisterBaseImpl(null, null));
		assertEquals("aI2cDevice can't be null.", thrown.getMessage());
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new RegisterBaseImpl(i2cDeviceMock, null));
		assertEquals("aDataType can't be null.", thrown.getMessage());
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new RegisterBaseImpl(i2cDeviceMock, DataType.UNDEF));
		assertEquals("aDataType can't be UNDEF.", thrown.getMessage());
		
		assertDoesNotThrow(() -> new RegisterBaseImpl(i2cDeviceMock, DataType.BYTE));
		
		
		// instantiation
		cut = new RegisterBaseImpl(i2cDeviceMock, DataType.BYTE);

		assertEquals(i2cDeviceMock, cut.i2cDevice);
		assertEquals(DataType.BYTE, cut.DATA_TYPE);

		assertEquals(1, cut.raw.length);
		
		cut = new RegisterBaseImpl(i2cDeviceMock, DataType.WORD);
		assertEquals(2, cut.raw.length);
		
	} // testRegisterBase()
	

} // ssalc
