/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS1621TempRegisterBaseTest.java
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
import org.mockito.InOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.i2c.I2C;
import com.pi4j.plugin.mock.provider.i2c.MockI2C;

import i2cDevice.I2CErrorException;
import i2cDevice.register.DataType;
import tempSensor.ds1621.configParameter.COMMAND_SET;
import tempSensor.ds1621.configParameter.DONE;
import tempSensor.ds1621.configParameter.ONE_SHOT;

class DS1621TempRegisterBaseTest {

	private final Logger logger = LoggerFactory.getLogger(DS1621TempRegisterBaseTest.class.getName());
	
	private class TestDS1621TempRegisterBase extends DS1621TempRegisterBase {
		public TestDS1621TempRegisterBase(I2C aI2cDevice, DS1621TemperatureStatusFlags aStatusFlags) {
			super(aI2cDevice, aStatusFlags);
		}
		
		byte[] getRawBytes() { return raw; }
		
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
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621TempRegisterBase#DS1621TempRegisterBase(com.pi4j.io.i2c.I2C, tempSensor.ds1621.registerSet.DS1621TemperatureStatusFlags)}.
	 */
	@Test
	void testDS1621TempRegisterBase() {
		logger.trace("testDS1621TempRegisterBase()");
		
		// error detection
		IllegalArgumentException thrown;
		
		MockI2C i2cDevice = mock(MockI2C.class);
		
		thrown = assertThrows(IllegalArgumentException.class, () 
			-> new TestDS1621TempRegisterBase(i2cDevice, null));
		
		assertEquals("aStatusFlags can't be null.", thrown.getMessage());
		
	} // testDS1621TempRegisterBase()
	

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621TempRegisterBase#doReadTemperature()}.
	 */
	@Test
	void testDoReadTemperature1Shot() {
		logger.trace("testDoReadTemperature1Shot()");

		int command = COMMAND_SET.READ_TEMP.value;
		int size = DataType.WORD.getSize();
		int startConvert = COMMAND_SET.START_CONVERT.value;
		
		MockI2C i2cDevice = mock(MockI2C.class);
		DS1621TemperatureStatusFlags statusFlags = mock(DS1621ConfigRegister.class);
		InOrder sequence = inOrder(statusFlags, i2cDevice);

		try {

			TestDS1621TempRegisterBase cut = new TestDS1621TempRegisterBase(i2cDevice, statusFlags);
			byte[] rawBytes = cut.getRawBytes();
			
			when(statusFlags.get1SHOT()).thenReturn(ONE_SHOT.ONE_SHOT);
			
			when(statusFlags.getDONE())
				.thenReturn(DONE.IN_PROGRESS)
				.thenReturn(DONE.COMPLETE)
				;
	
			when(i2cDevice.readRegister(command, rawBytes, 0, size))
			.thenReturn(2)
			;
		
			cut.doReadTemperature();
			
			sequence.verify(statusFlags, times(1)).get1SHOT();
			sequence.verify(i2cDevice,   times(1)).write(startConvert);
			sequence.verify(statusFlags, times(2)).getDONE();
			sequence.verify(i2cDevice,   times(1)).readRegister(command, rawBytes, 0, size);
			
		} catch (I2CErrorException e) {

			fail("Oooooops ... An exception flew by ... " + e.getClass());
			
		}
		
	} // testDoReadTemperature1Shot()
	

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621TempRegisterBase#doReadTemperature()}.
	 */
	@Test
	void testDoReadTemperatureContinuos() {
		logger.trace("testDoReadTemperatureContinuos()");

		try {

			int command = COMMAND_SET.READ_TEMP.value;
			int size = DataType.WORD.getSize();
			int startConvert = COMMAND_SET.START_CONVERT.value;
			
			MockI2C i2cDevice = mock(MockI2C.class);
			DS1621TemperatureStatusFlags statusFlags = mock(DS1621ConfigRegister.class);
			InOrder sequence = inOrder(statusFlags, i2cDevice);
	
			TestDS1621TempRegisterBase cut = new TestDS1621TempRegisterBase(i2cDevice, statusFlags);
			byte[] rawBytes = cut.getRawBytes();
			
			when(statusFlags.get1SHOT()).thenReturn(ONE_SHOT.CONTINUOUSLY);
			when(statusFlags.getDONE()).thenReturn(DONE.IN_PROGRESS);		// should never happen
	
			when(i2cDevice.readRegister(command, rawBytes, 0, size))
			.thenReturn(2)
			;
		
			cut.doReadTemperature();
			
			sequence.verify(statusFlags, times(1)).get1SHOT();
			sequence.verify(i2cDevice,   never()).write(startConvert);
			sequence.verify(statusFlags, never()).getDONE();
			sequence.verify(i2cDevice,   times(1)).readRegister(command, rawBytes, 0, size);
			
		} catch (I2CErrorException e) {

			fail("Oooooops ... An exception flew by ... " + e.getClass());
			
		}
		
	} // testDoReadTemperatureContinuos()
	

} // ssalc
