/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS1621TempRegister1p0Test.java
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


class DS1621TempRegister1p0Test {

	private final Logger logger = LoggerFactory.getLogger(DS1621TempRegister1p0Test.class.getName());

	private class TestDS1621TempRegister1p0 extends DS1621TempRegister1p0 {

		public TestDS1621TempRegister1p0(I2C aI2cDevice, DS1621TemperatureStatusFlags aStatusFlags) {
			super(aI2cDevice, aStatusFlags);
		}
		
		public void setRaw(byte[] aRaw) { raw = aRaw; }
		public byte[] getRaw() { return raw; }
				
	} // ssalc
	
	COMMAND_SET command = COMMAND_SET.READ_TEMP;
	DataType dataType = DataType.WORD;

	I2C i2cDevice;
	DS1621StatusFlags statusFlags;
	
	TestDS1621TempRegister1p0 cut;

	byte[] rawBytes;
	int count_per_c;
	int count_remain;

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
		
		rawBytes = new byte[dataType.getSize()];

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621TempRegister1p0#DS1621TempRegister1p0(com.pi4j.io.i2c.I2C, tempSensor.ds1621.registerSet.DS1621TemperatureStatusFlags)}.
	 */
	@Test
	void testDS1621TempRegister1p0() {
		logger.info("testDS1621TempRegister1p0()");
		
		/*
		 * Error handling is tested on the super classes.
		 */
		
		assertTrue(true);
		
	} // testDS1621TempRegister1p0()
	

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621TempRegister1p0#readTemperature()}.
	 */
	@Test
	void testReadTemperature() {
		logger.info("testReadTemperature()");
		
		try {
			
			float[] values = new float[] {
				
					125.0f,
					124.5f,
					124.0f,
					  1.0f,
					  0.5f,
					  0.0f,
					 -0.5f,
					 -1.0f,
					 -1.5f,
					 -2.0f,
					-54.0f,
					-54.5f,
					-55.0f
			};
			
			cut = new TestDS1621TempRegister1p0(i2cDevice, statusFlags);
			
			for(int n = 0; n < values.length; n++)
				verifyTemperature(values[n]);
			
		} catch (I2CErrorException e) {

			logger.error("Oops ... An exception flew by ... {}", e.getClass().getName());
			fail("Unexpeted exception occured.");
			
		} // yrt
		
	} // testReadTemperature()

	/**
	 * @param temperature
	 * @throws I2CErrorException
	 */
	private void verifyTemperature(float temperature) throws I2CErrorException {
		logger.info("verifyTemperature(): temperature = {}", temperature);
		
		int intVal = (int) temperature;
		float fract  = temperature - (int) temperature;

		if(temperature < 0.0f && fract != 0)
			intVal--;

		byte raw1 = (byte) 0x00;
		
		if(fract != 0)
			raw1 = (byte) 0x80;

		when(i2cDevice.readRegister(command.value, rawBytes, 0, dataType.getSize()))
		.thenAnswer(invocation -> {
			cut.setRaw(rawBytes);
			return 2;
		});
		
		rawBytes = new byte[] { (byte) intVal, raw1 };
		
		float result = cut.readTemperature();
		byte[] raw = cut.getRaw();
		logger.info("temperature = {}, res = {}, raw[0] = {}, raw[1] = {}", temperature, result, raw[0], raw[1]);
		
		assertEquals(intVal, result, temperature + "°");
		
	} // verifyTemperature()
	

} // ssalc
