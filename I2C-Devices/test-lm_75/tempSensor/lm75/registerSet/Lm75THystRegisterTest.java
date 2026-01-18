/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : Lm75THystRegisterTest.java
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


package tempSensor.lm75.registerSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
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
import tempSensor.lm75.configParameter.LM75RegisterMap;


class Lm75THystRegisterTest {

	private final Logger logger = LoggerFactory.getLogger(Lm75THystRegisterTest.class.getName());

	private class TestLm75THystRegister extends Lm75THystRegister {

		public TestLm75THystRegister(I2C aI2cDevice) { super(aI2cDevice); }
		
		@SuppressWarnings("unused")
		public void setRawBytes(byte[] aRaw) { raw = aRaw; }
		public byte[] getRawBytes() { return raw; }

	} // ssalc
	
	
	int cmd_adr = LM75RegisterMap.T_HYST.registerAddress;
	DataType dataType = DataType.WORD;

	MockI2C i2cMock;

	TestLm75THystRegister cut;
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

		
		i2cMock = mock(MockI2C.class);
		
		cut = new TestLm75THystRegister(i2cMock);
		
		rawBytes = cut.getRawBytes();
		
		
	} // setUp()

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link tempSensor.lm75.registerSet.Lm75THystRegister#Lm75THystRegister(com.pi4j.io.i2c.I2C)}.
	 */
	@Test
	void testLm75THystRegister() {
		logger.trace("testLm75THystRegister()");
		
		/*
		 * 		Nothing to test!
		 */
		
		assertTrue(true);
		
	} // testLm75THystRegister() 


	/**
	 * Test method for {@link tempSensor.lm75.registerSet.LM75THystRegisterBase#read()}.
	 */
	@Test
	void testRead() {
		logger.trace("testRead()");
		
		float value = 42.5f;
		
		try {
			
			when(i2cMock.writeRegister(cmd_adr, rawBytes, 0, dataType.getSize()))
			.thenReturn(dataType.getSize() + 1)
			;

			when(i2cMock.readRegister(cmd_adr, rawBytes, 0, dataType.getSize()))
			.thenReturn(dataType.getSize())
			;

			cut.write(value);
			float result = cut.read();
			assertEquals(value, result);
			
		} catch (I2CErrorException e) {
			
			fail("Ooops ... An exception flew by ... " + e.getClass());
			
		} // yrt
		
	} // testRead() 


	/**
	 * Test method for {@link tempSensor.lm75.registerSet.LM75THystRegisterBase#write(float)}.
	 */
	@Test
	void testWrite() {
		logger.trace("testWrite()");
		
		/*
		 * 		Nothing to test!
		 * 
		 * 		It's done by testRead()
		 */
		
		assertTrue(true);
		
	} // testWrite() 

	
} // ssalc
