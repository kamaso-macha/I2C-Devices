/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS1621TempRegisterHiResTest.java
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

class DS1621TempRegisterHiResTest {

	private final Logger logger = LoggerFactory.getLogger(DS1621TempRegisterHiResTest.class.getName());

	private class TestDS1621TempRegisterHiRes extends DS1621TempRegisterHiRes {

		public TestDS1621TempRegisterHiRes(I2C aI2cDevice, DS1621TemperatureStatusFlags aStatusFlags) {
			super(aI2cDevice, aStatusFlags);
		}
		
		public void setRaw(byte[] aRaw) { raw = aRaw; }
		public byte[] getRaw() { return raw; }
				
	} // ssalc
	
	COMMAND_SET command = COMMAND_SET.READ_TEMP;
	DataType dataType = DataType.WORD;

	I2C i2cDevice;
	DS1621StatusFlags statusFlags;
	
	TestDS1621TempRegisterHiRes cut;

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
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621TempRegisterHiRes#DS1621TempRegisterHiRes(com.pi4j.io.i2c.I2C, tempSensor.ds1621.registerSet.DS1621TemperatureStatusFlags)}.
	 */
	@Test
	void testDS1621TempRegisterHiRes() {
		logger.info("testDS1621TempRegisterHiRes()");
		
		/*
		 * Error handling is tested on the super classes.
		 */
		
		assertTrue(true);
		
	} // testDS1621TempRegisterHiRes()
	

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621TempRegisterHiRes#readTemperature()}.
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
					-54.0f,
					-54.5f,
					-55.0f
			};
			
			float[][] count = new float[][] {
				
				{ 0x78,  0.25f },
				{ 0x3C,  0.50f },
				{ 0x00,  0.75f },
				{ 0xF0, -0.25f },
				
			};
			
			cut = new TestDS1621TempRegisterHiRes(i2cDevice, statusFlags);

			/*
			 * F0 / 78 	== +0.25°
			 *    / 3C  == +0.50°
			 *    / 00  == +0.75°
			 *    / F0  == -0.25°
			 */
			count_per_c  = 0x00F0;

			for(int c = 0; c < count.length; c++) {
				
				count_remain = (int) count[c][0];
				
				
				for(int n = 0; n < values.length; n++)
					verifyTemperature(values[n], count[c][1], n);
			
			} // rof (c)

		} catch (I2CErrorException e) {

			logger.error("Oops ... An exception flew by ... {}", e.getClass().getName());
			fail("Unexpeted exception occured.");
			
		} // yrt
		
	} // testReadTemperature()

	/**
	 * @param temperature
	 * @throws I2CErrorException
	 */
	private void verifyTemperature(float temperature, float ref, int step) throws I2CErrorException {
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
		
		when(i2cDevice.readRegister(COMMAND_SET.READ_COUNTER.value)).thenReturn(count_remain);
		when(i2cDevice.readRegister(COMMAND_SET.READ_SLOPE.value)).thenReturn(count_per_c);
		
		float result = cut.readTemperature();
		byte[] raw = cut.getRaw();
		logger.info("temp = {}, res = {}, raw[0] = {}, raw[1] = {}", temperature, result, raw[0], raw[1]);
		
	} // verifyTemperature()
	

} // ssalc
