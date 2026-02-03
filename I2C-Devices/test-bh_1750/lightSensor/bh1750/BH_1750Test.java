/**
 *
 * **********************************************************************
 * PROJECT       : I2C-Devices
 * FILENAME      : BH_1750Test.java
 *
 * More information about this project can be found on Github
 * http://github.com/kamaso-macha/I2C-Devices
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


package lightSensor.bh1750;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import lightSensor.bh1750.configParameter.InstructionSet;
import lightSensor.bh1750.configParameter.MEASUREMENT_MODE;
import lightSensor.bh1750.configParameter.POWER_DOWNN_MODE;
import lightSensor.bh1750.configParameter.RESOLUTION_MODE;


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

// DOC
// Created at 2026-01-30 11:25:16

class BH_1750Test {

	private final Logger logger = LoggerFactory.getLogger(BH_1750Test.class.getName());
	
	private static final int I2C_BUS	= 2;
	private static final int I2C_ADDR	= 0x23;
	
	private class TestBH_1750 extends BH_1750 {

		public TestBH_1750(int aI2cBusNbr, int aI2cAddress) throws I2CErrorException {
			super(aI2cBusNbr, aI2cAddress);
		}
		
		public byte[] getrawBuffer() { return raw; }
		public void setI2cDevice(final I2C aI2cDevice) { i2cDevice = aI2cDevice; }
		
	} // ssalc

//	private MockI2C i2cMockDevice;

	
	private TestBH_1750 cut;
	private MockI2C i2cMock;

	private byte[] rawBytes;
	
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
		
		cut = new TestBH_1750(I2C_BUS, I2C_ADDR);
		
		i2cMock = mock(MockI2C.class);		
		cut.setI2cDevice(i2cMock);
		
		rawBytes = cut.getrawBuffer();
		
	} // setUp()
	

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link lightSensor.bh1750.BH_1750#BH_1750(int, int)}.
	 */
	@Test
	void testBH_1750() {
		logger.info("testBH_1750()");

		// error handling
		
		IllegalArgumentException thrown;
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new BH_1750(I2C_BUS, 0x22));
		assertEquals("aI2cAddress must be 0x23 or 0x5C.", thrown.getMessage());
				
		assertDoesNotThrow(() -> new BH_1750(I2C_BUS, 0x23));

		thrown = assertThrows(IllegalArgumentException.class, () -> new BH_1750(I2C_BUS, 0x24));
		assertEquals("aI2cAddress must be 0x23 or 0x5C.", thrown.getMessage());
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new BH_1750(I2C_BUS, 0x5B));
		assertEquals("aI2cAddress must be 0x23 or 0x5C.", thrown.getMessage());
		
		assertDoesNotThrow(() -> new BH_1750(I2C_BUS, 0x5C));
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new BH_1750(I2C_BUS, 0x5D));
		assertEquals("aI2cAddress must be 0x23 or 0x5C.", thrown.getMessage());

		
		final POWER_DOWNN_MODE pdMode = POWER_DOWNN_MODE.POWER_DOWN;
		final MEASUREMENT_MODE mmMode	= MEASUREMENT_MODE.ONE_SHOT;
		final RESOLUTION_MODE  rsMode = RESOLUTION_MODE.HIGH;
		
		final int sensit = 69;
		
		try {
			
			cut = new TestBH_1750(I2C_BUS, 0x23);
			
			assertEquals(pdMode, cut.powerDownMode);
			assertEquals(mmMode, cut.measurementMode);
			assertEquals(rsMode, cut.resolutionMode);
			assertEquals(sensit, cut.mtReg);

		}
		catch (I2CErrorException e) {
		
			fail("Ooops ... An exception flew by ... " + e.getClass());
		
		} // yrt

	} // testBH_1750()
	

	/**
	 * Test method for {@link lightSensor.bh1750.BH_1750#withMeasurementMode(lightSensor.bh1750.configParameter.MEASUREMENT_MODE)}.
	 */
	@Test
	void testWithMeasurementMode() {
		logger.info("testWithMeasurementMode()");

		IllegalArgumentException thrown;
		
		thrown = assertThrows(IllegalArgumentException.class, () ->cut.withMeasurementMode(null));
		assertEquals("aMeasurementMode can't be null.", thrown.getMessage());

		TestBH_1750 instance;

		for(MEASUREMENT_MODE mode : MEASUREMENT_MODE.values()) {
			
			mode = MEASUREMENT_MODE.ONE_SHOT;
			instance = (TestBH_1750) cut.withMeasurementMode(mode);
			
			assertEquals(cut, instance);
			assertEquals(mode, cut.measurementMode);
			
		} // rof
		
	} // testWithMeasurementMode()
	

	/**
	 * Test method for {@link lightSensor.bh1750.BH_1750#withPowerDownMode(lightSensor.bh1750.configParameter.POWER_DOWNN_MODE)}.
	 */
	@Test
	void testWithPowerDownMode() {
		logger.info("testWithPowerDownMode()");

		IllegalArgumentException thrown;
		
		thrown = assertThrows(IllegalArgumentException.class, () ->cut.withPowerDownMode(null));
		assertEquals("aPowerDownMode can't be null.", thrown.getMessage());

		TestBH_1750 instance;
		
		try {

			when(i2cMock.write(anyInt())).thenReturn(1);
		
			// power down
			instance = (TestBH_1750) cut.withPowerDownMode(POWER_DOWNN_MODE.POWER_DOWN);
			
			assertTrue(instance != null);
			assertEquals(cut,  instance);
			
			verify(i2cMock, times(1)).write(0x0_0000);
	
	
			// permanent on
			instance = (TestBH_1750) cut.withPowerDownMode(POWER_DOWNN_MODE.PERMANENT_ON);
	
			verify(i2cMock, times(1)).write(0x0_0001);

		}
		catch (I2CErrorException e) {
		
		fail("Ooops ... An exception flew by ... " + e.getClass());
		
		} // yrt
		
	} // testWithPowerDownMode()
	

	/**
	 * Test method for {@link lightSensor.bh1750.BH_1750#withResolutionMode(lightSensor.bh1750.configParameter.RESOLUTION_MODE)}.
	 */
	@Test
	void testWithResolutionMode() {
		logger.info("testWithResolutionMode()");

		IllegalArgumentException thrown;
		
		thrown = assertThrows(IllegalArgumentException.class, () ->cut.withResolutionMode(null));
		assertEquals("aResolutionMode can't be null.", thrown.getMessage());

		TestBH_1750 instance;
		
		for(RESOLUTION_MODE mode : RESOLUTION_MODE.values()) {
			
			instance = (TestBH_1750) cut.withResolutionMode(mode);
			
			assertEquals(cut, instance);
			assertEquals(mode, cut.resolutionMode);
		
		} // rof
		
	} // testWithResolutionMode()
	

	/**
	 * Test method for {@link lightSensor.bh1750.BH_1750#withSensitivity(float)}.
	 */
	@Test
	void testWithSensitivity() {
		logger.info("testWithSensitivity()");
		
		float[] sensitivity = new float[] {
			0.45f,
			1.0f,
			3.68f
		};
		
		int[] mtRegRef = new int[] {
			31,
			69,
			254
		};
		
		int[][] cmdref = new int[][] {
			{ 0x040, 0x07F },
			{ 0x042, 0x065 },
			{ 0x047, 0x07E }
		};
		
		TestBH_1750 instance;
		
		when(i2cMock.write(anyInt())).thenReturn(1);

		for(int n = 0; n < sensitivity.length; n++) {
		
			try {
				
				instance = (TestBH_1750) cut.withSensitivity(sensitivity[n]);
				
				assertEquals(cut, instance);
				assertEquals(mtRegRef[n], cut.mtReg, "value = " + sensitivity[n]);

				verify(i2cMock, times(1)).write(cmdref[n][0]);
				verify(i2cMock, times(1)).write(cmdref[n][1]);
				
			}
			catch (I2CErrorException e) {
			
			fail("Ooops ... An exception flew by ... " + e.getClass());
			
			} // yrt
			
		} // rof

	} // testWithSensitivity()
	

	/**
	 * Test method for {@link lightSensor.bh1750.BH_1750#read()}.
	 */
	@Test
	void testRead() {
		logger.info("testRead()");
		
		RESOLUTION_MODE[] resMode = new RESOLUTION_MODE[] {
			RESOLUTION_MODE.LOW,
			RESOLUTION_MODE.HIGH,
			RESOLUTION_MODE.HIGH_2
		};
		
		float[] sensitivity = new float[] {
				0.45f,		//  31T, factor = 1,8855
				1.0f,		//  69T, factor = 0,833
				3.68f		// 254T, factor = 0,226
			};
		
		final float SENS_0p45	= 1.85483871f;
		final float SENS_1p00	= 0.83333333f;
		final float SENS_3p68	= 0.22637795f;
		
		int[] measurement = new int[] {
			0x00000,	//      0
			0x00001,	//      1
			0x07FFF,	// 32'767
			0x08000,	// 32'768
			0x0FFFE,	// 65'534
			0x0FFFF		// 65'535
		};
			
		//   [res] [sens] [meas]
		float[][][] result = new float[][][] {
			{  
				{(float) 0x00000 * SENS_1p00, (float) 0x00001 * SENS_1p00, (float) 0x07FFF * SENS_1p00, (float) 0x08000 * SENS_1p00, (float) 0x0FFFE * SENS_1p00, -1.0f },	// sens 0.45
				{(float) 0x00000 * SENS_1p00, (float) 0x00001 * SENS_1p00, (float) 0x07FFF * SENS_1p00, (float) 0x08000 * SENS_1p00, (float) 0x0FFFE * SENS_1p00, -1.0f },	// sens 1.0
				{(float) 0x00000 * SENS_1p00, (float) 0x00001 * SENS_1p00, (float) 0x07FFF * SENS_1p00, (float) 0x08000 * SENS_1p00, (float) 0x0FFFE * SENS_1p00, -1.0f },	// sens 3.68
			},		// res LOW
			
			{  
				{(float) 0x00000 * SENS_0p45, (float) 0x00001 * SENS_0p45, (float) 0x07FFF * SENS_0p45, (float) 0x08000 * SENS_0p45, (float) 0x0FFFE * SENS_0p45, -1.0f },	// sens 0.45
				{(float) 0x00000 * SENS_1p00, (float) 0x00001 * SENS_1p00, (float) 0x07FFF * SENS_1p00, (float) 0x08000 * SENS_1p00, (float) 0x0FFFE * SENS_1p00, -1.0f },	// sens 1.0
				{(float) 0x00000 * SENS_3p68, (float) 0x00001 * SENS_3p68, (float) 0x07FFF * SENS_3p68, (float) 0x08000 * SENS_3p68, (float) 0x0FFFE * SENS_3p68, -1.0f },	// sens 3.68
			},		// res HIGH
			
			{  
				{(float) 0x00000 * SENS_0p45 / 2, (float) 0x00001 * SENS_0p45 / 2, (float) 0x07FFF * SENS_0p45 / 2, (float) 0x08000 * SENS_0p45 / 2, (float) 0x0FFFE * SENS_0p45 / 2, -1.0f },	// sens 0.45
				{(float) 0x00000 * SENS_1p00 / 2, (float) 0x00001 * SENS_1p00 / 2, (float) 0x07FFF * SENS_1p00 / 2, (float) 0x08000 * SENS_1p00 / 2, (float) 0x0FFFE * SENS_1p00 / 2, -1.0f },	// sens 1.0
				{(float) 0x00000 * SENS_3p68 / 2, (float) 0x00001 * SENS_3p68 / 2, (float) 0x07FFF * SENS_3p68 / 2, (float) 0x08000 * SENS_3p68 / 2, (float) 0x0FFFE * SENS_3p68 / 2, -1.0f },	// sens 3.68
			},	// res HIGH_2
		};

		float read;
		
		try {

			for(int res = 0; res < resMode.length; res++) {
				
				for(int sens = 0; sens < sensitivity.length; sens ++) {
					
					for(int meas = 0; meas < measurement.length; meas++) {
						
						int currentMeas = measurement[meas];
			
						when(i2cMock.write(anyInt())).thenReturn(1);
						
						when(i2cMock.read(rawBytes)).thenAnswer(
							invocation -> {
								
								rawBytes[0] = (byte) ((currentMeas >> 8) & 0x000FF);
								rawBytes[1] = (byte) ( currentMeas & 0x000FF);
								
								return 2;
							}
						);
						
						cut.withResolutionMode(RESOLUTION_MODE.values()[res]);
						cut.withSensitivity(sensitivity[sens]);
	
						read = cut.read();

						logger.info("{}, read = {}", 
							String.format("res = %s,  sens = %f, meas = 0x%04X, rawBytes[0] = 0x%02X, rawBytes[1] = 0x%02X", 
								RESOLUTION_MODE.values()[res], 
								sensitivity[sens],
								meas, 
								rawBytes[0], rawBytes[1]
							),
							read
						);
						
						float ref = result[res][sens][meas];
						float rounded = ((int) ((ref + (ref >= 0 ? 1 : -1) * 0.005f) * 100)) / 100f;

						assertEquals(rounded, read, String.format("res = %s, sens = %f, meas = %d", resMode[res], sensitivity[sens], measurement[meas]));
						
					} // rof meas
					
				} // rof sens
				
			} // rof res
			
		}
		catch (I2CErrorException e) {
		
		fail("Ooops ... An exception flew by ... " + e.getClass());
		
		} // yrt
		
	} // testRead()
	

	/**
	 * Test method for {@link lightSensor.bh1750.BH_1750#reset()}.
	 */
	@Test
	void testReset() {
		logger.info("testReset()");

		when(i2cMock.write(anyInt())).thenReturn(1);
		
		cut.reset();
		
		verify(i2cMock, times(1)).write((byte) 0x0_07);
		
	} // testReset()
	

	/**
	 * Test method for {@link lightSensor.bh1750.BH_1750#setSensitivity(float)}.
	 */
	@Test
	void testSetSensitivity() {
		logger.info("testSetSensitivity()");

		when(i2cMock.write(anyInt())).thenReturn(1);
		
		// Error handling
		IllegalArgumentException thrown;
		
		thrown = assertThrows(IllegalArgumentException.class, () ->cut.withSensitivity(-1.0f));
		assertEquals("aSensitivity is out of range 0,450000 .. 3,680000.", thrown.getMessage());

		thrown = assertThrows(IllegalArgumentException.class, () ->cut.withSensitivity(0.449999f));
		assertEquals("aSensitivity is out of range 0,450000 .. 3,680000.", thrown.getMessage());

		assertDoesNotThrow(() -> cut.withSensitivity(0.45f));

		assertDoesNotThrow(() -> cut.withSensitivity(3.68f));

		thrown = assertThrows(IllegalArgumentException.class, () ->cut.withSensitivity(3.681f));
		assertEquals("aSensitivity is out of range 0,450000 .. 3,680000.", thrown.getMessage());

		// test of correct value setting is tested by withSensitivita() method.
		
	} // testSetSensitivity()
	

	/**
	 * Test method for {@link lightSensor.bh1750.BH_1750#triggerMeasurement()}.
	 */
	@Test
	void testTriggerMeasurementCommandSelection() {
		logger.info("testTriggerMeasurementCommandSelection()");

		InOrder sequence = inOrder(i2cMock);
		
		// meas - res
		int[][] command = new int[][] {
			{
				0x0_23,		// OT L
				0x0_20,		// OT H
				0x0_21		// OT H2
			},
			{
				0x0_13,		// C L
				0x0_10,		// C H
				0x0_11		// C H2
			}
		};
		
		try {
			
			// command selection and execution
			
			for(int meas = 0; meas < MEASUREMENT_MODE.values().length; meas++) {

				for(int res = 0; res < RESOLUTION_MODE.values().length; res++ ) {
									
					reset(i2cMock);
					when(i2cMock.write(anyInt())).thenReturn(1);
					when(i2cMock.read(rawBytes)).thenReturn(2);
					
					cut.withResolutionMode(RESOLUTION_MODE.values()[res]);
					cut.withMeasurementMode(MEASUREMENT_MODE.values()[meas]);
					
					cut.triggerMeasurement();
					
					sequence.verify(i2cMock, times(1)).write(command[meas][res]);
					sequence.verify(i2cMock, times(1)).read(rawBytes);
					
				} // rof res
				
			} // rof meas
						
		}
		catch (I2CErrorException e) {
		
		fail("Ooops ... An exception flew by ... " + e.getClass());
		
		} // yrt

	} // testTriggerMeasurementCommandSelection()
	

	/**
	 * Test method for {@link lightSensor.bh1750.BH_1750#triggerMeasurement()}.
	 */
	@Test
	void testTriggerMeasurementPowerDown() {
		logger.info("testTriggerMeasurementPowerDown()");

		final int PD_CMD = 0x0_00;
		InOrder sequence = inOrder(i2cMock);
				
		try {
			
			// don't power down after measurement

			when(i2cMock.write(anyInt())).thenReturn(1);
			when(i2cMock.read(rawBytes)).thenReturn(2);
			
			
			cut.withPowerDownMode(POWER_DOWNN_MODE.POWER_DOWN);
			cut.withMeasurementMode(MEASUREMENT_MODE.ONE_SHOT);
			
			reset(i2cMock);
			when(i2cMock.write(anyInt())).thenReturn(1);
			when(i2cMock.read(rawBytes)).thenReturn(2);

			cut.triggerMeasurement();
			verify(i2cMock, never()).write(PD_CMD);

			
			cut.withPowerDownMode(POWER_DOWNN_MODE.PERMANENT_ON);
			cut.withMeasurementMode(MEASUREMENT_MODE.ONE_SHOT);
			
			cut.triggerMeasurement();
			verify(i2cMock, never()).write(PD_CMD);
			
			
			cut.withPowerDownMode(POWER_DOWNN_MODE.PERMANENT_ON);
			cut.withMeasurementMode(MEASUREMENT_MODE.CONTINUOSLY);
			
			cut.triggerMeasurement();
			verify(i2cMock, never()).write(PD_CMD);
			
			
			// power down after measurement
			
			cut.withResolutionMode(RESOLUTION_MODE.HIGH);

			cut.withPowerDownMode(POWER_DOWNN_MODE.POWER_DOWN);
			cut.withMeasurementMode(MEASUREMENT_MODE.CONTINUOSLY);
			
			reset(i2cMock);
			when(i2cMock.write(anyInt())).thenReturn(1);
			when(i2cMock.read(rawBytes)).thenReturn(2);

			cut.triggerMeasurement();
			sequence.verify(i2cMock, times(1)).write(InstructionSet.CONT_H_RES.value);
			sequence.verify(i2cMock, times(1)).read(rawBytes);
			sequence.verify(i2cMock, times(1)).write((byte) PD_CMD);
			
		}
		catch (I2CErrorException e) {
		
		fail("Ooops ... An exception flew by ... " + e.getClass());
		
		} // yrt

	} // testTriggerMeasurementPowerDown()
	

	/**
	 * Test method for {@link lightSensor.bh1750.BH_1750#readDeviceRaw()}.
	 */
	@Test
	void testReadDeviceRaw() {
		logger.info("testReadDeviceRaw()");

		I2CErrorException thrown;

		when(i2cMock.read(rawBytes)).thenReturn(
			1,
			3
		);
		
		thrown = assertThrows(I2CErrorException.class, () -> cut.readDeviceRaw());
		assertEquals("Cant read I2C device.", thrown.getMessage());
		
		thrown = assertThrows(I2CErrorException.class, () -> cut.readDeviceRaw());
		assertEquals("Cant read I2C device.", thrown.getMessage());
		
		reset(i2cMock);
		
		try {
			
			when(i2cMock.read(rawBytes)).thenReturn(2);
			
			cut.readDeviceRaw();
			
			verify(i2cMock, times(1)).read(rawBytes);
			
		}
		catch (I2CErrorException e) {
		
		fail("Ooops ... An exception flew by ... " + e.getClass());
		
		} // yrt
		
	} // testReadDeviceRaw()
	

	/**
	 * Test method for {@link lightSensor.bh1750.BH_1750#writeCommand(byte)}.
	 */
	@Test
	void testWriteCommand() {
		logger.info("testWriteCommand()");

		I2CErrorException thrown;

		when(i2cMock.write(anyInt())).thenReturn(
			0,
			2
		);
		

		thrown = assertThrows(I2CErrorException.class, () -> cut.writeCommand((byte) 0x42));
		assertEquals("Cant write to I2C device.", thrown.getMessage());
		
		thrown = assertThrows(I2CErrorException.class, () -> cut.writeCommand((byte) 0x42));
		assertEquals("Cant write to I2C device.", thrown.getMessage());
		
		
		byte testCmd = 0x0_12;
		
		try {
			
			when(i2cMock.write(anyInt())).thenReturn(1);

			cut.writeCommand(testCmd);
			
			verify(i2cMock, times(1)).write((int)testCmd);
			
		}
		catch (I2CErrorException e) {
		
		fail("Ooops ... An exception flew by ... " + e.getClass());
		
		} // yrt
		
	} // testWriteCommand()
	

} // ssalc
