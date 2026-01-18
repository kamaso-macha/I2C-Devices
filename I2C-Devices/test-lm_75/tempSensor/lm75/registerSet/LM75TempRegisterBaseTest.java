/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : LM75RegisterBaseTest.java
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
import tempSensor.lm75.configParameter.LM75RegisterMap;


class LM75TempRegisterBaseTest {

	private final Logger logger = LoggerFactory.getLogger(LM75TempRegisterBaseTest.class.getName());
	
	private class TestLM75TempRegisterBase extends Lm75TempRegisterBase {

		public TestLM75TempRegisterBase(I2C aI2cDevice, int aRegisterAddress, DataType aDataType) {
			super(aI2cDevice, aRegisterAddress, aDataType);
		}
		
		@SuppressWarnings("unused")
		public void setRawBytes(byte[] aRaw) { raw = aRaw; }
		public byte[] getRawBytes() { return raw; }

	} // ssalc

	
	int cmd_adr = LM75RegisterMap.TEMPERATURE.registerAddress;
	DataType dataType = DataType.WORD;

	MockI2C i2cMock;

	TestLM75TempRegisterBase cut;
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
		
		cut = new TestLM75TempRegisterBase(i2cMock, cmd_adr, dataType);
		
		rawBytes = cut.getRawBytes();
		
	} // setUp()
	

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {		
	} // tearDown()
	

	/**
	 * Test method for {@link tempSensor.lm75.registerSet.Lm75TempRegisterBase#LM75RegisterBase(com.pi4j.io.i2c.I2C, byte, i2cDevice.register.DataType)}.
	 */
	@Test
	void testLM75RegisterBase() {
		logger.trace("testLM75RegisterBase()");
		
		/*
		 * 		Nothing to thest here!
		 */
		
		assertTrue(true);
		
	} // testLM75RegisterBase()
	

	/**
	 * Test method for {@link tempSensor.lm75.registerSet.Lm75TempRegisterBase#read()}.
	 */
	@Test
	void testRead() {
		logger.trace("testRead()");

		try {

			rawBytes[0] = (byte) 0x42;
			rawBytes[1] = (byte) 0x80;
		
			when(i2cMock.readRegister(cmd_adr, rawBytes, 0, dataType.getSize()))
			.thenReturn(dataType.getSize())
			;

			float result = cut.read();
			
			assertEquals(66,5f,result );

		} catch (I2CErrorException e) {
			
			fail("Ooops ... An exception flew by ... " + e.getClass());
			
		} // yrt
		
	} // testRead()
	

	/**
	 * Test method for {@link tempSensor.lm75.registerSet.Lm75TempRegisterBase#write(int)}.
	 */
	@Test
	void testWrite() {
		logger.trace("testWrite()");
		
		try {
			
			when(i2cMock.writeRegister(cmd_adr, rawBytes, 0, dataType.getSize()))
			.thenReturn(dataType.getSize() + 1)
			;

			when(i2cMock.readRegister(cmd_adr, rawBytes, 0, dataType.getSize()))
			.thenReturn(dataType.getSize())
			;

			cut.write(66.5f);
			
			assertEquals((byte) 0x42, rawBytes[0]);
			assertEquals((byte) 0x80, rawBytes[1]);


		} catch (I2CErrorException e) {
			
			fail("Ooops ... An exception flew by ... " + e.getClass());
			
		} // yrt
		
	} // testWrite()
	

} // ssalc
