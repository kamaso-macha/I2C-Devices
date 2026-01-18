/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : Lm75ConfigRegisterTest.java
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

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
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
import tempSensor.lm75.configParameter.CMP_INT;
import tempSensor.lm75.configParameter.FAULT_QUEUE;
import tempSensor.lm75.configParameter.LM75RegisterMap;
import tempSensor.lm75.configParameter.OS_POLARITY;
import tempSensor.lm75.configParameter.SHUTDOWN_MODE;


class Lm75ConfigRegisterTest {

	private final Logger logger = LoggerFactory.getLogger(Lm75ConfigRegisterTest.class.getName());

	private class TestLm75ConfigRegister extends Lm75ConfigRegister {

		public TestLm75ConfigRegister(I2C aI2cDevice) { super(aI2cDevice); }
		
		@SuppressWarnings("unused")
		public void setRawBytes(byte[] aRaw) { raw = aRaw; }
		public byte[] getRawBytes() { return raw; }

		public void clearRawBytes() {  raw[0] = 0; if(DATA_TYPE == DataType.WORD) raw[1] = 0; }

	} // ssalc
	
	
	int cmd_adr = LM75RegisterMap.CONFIG.registerAddress;
	DataType dataType = DataType.BYTE;

	MockI2C i2cMock;

	TestLm75ConfigRegister cut;
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
		
		cut = new TestLm75ConfigRegister(i2cMock);
		
		rawBytes = cut.getRawBytes();
		
	} // setUp()
	

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link tempSensor.lm75.registerSet.Lm75ConfigRegister#Lm75ConfigRegister(com.pi4j.io.i2c.I2C)}.
	 */
	@Test
	void testLm75ConfigRegister() {
		logger.info("testLm75ConfigRegister()");
		
		/*
		 * 	Nothing to test!
		 */
		
		assertTrue(true);
		
	} // testLm75ConfigRegister()


	/**
	 * Test method for {@link tempSensor.lm75.registerSet.Lm75ConfigRegister#setShutdown(tempSensor.lm75.configParameter.SHUTDOWN_MODE)}.
	 */
	@Test
	void testSetShutdown() {
		logger.info("testSetShutdown()");
				
		try {

			when(i2cMock.readRegister(cmd_adr, rawBytes, 0, dataType.getSize()))
			.thenReturn(dataType.getSize())
			;

			when(i2cMock.writeRegister(cmd_adr, rawBytes, 0, dataType.getSize()))
			.thenReturn(dataType.getSize() + 1)
			;

			cut.clearRawBytes();
			cut.setShutdown(SHUTDOWN_MODE.RUN_PERSISTENT);
			assertEquals(SHUTDOWN_MODE.RUN_PERSISTENT.getSetMask(), rawBytes[0]);
			
			cut.clearRawBytes();
			cut.setShutdown(SHUTDOWN_MODE.SHUTDOWN);
			assertEquals(SHUTDOWN_MODE.SHUTDOWN.getSetMask(), rawBytes[0]);
			
			verify(i2cMock, times(2)).writeRegister(cmd_adr, rawBytes, 0, dataType.getSize());

		}
		catch(I2CErrorException e) {

			fail("Ooooops ... An exception flew by ... " + e.getMessage());

		} // yrt
		
	} // testSetShutdown()


	/**
	 * Test method for {@link tempSensor.lm75.registerSet.Lm75ConfigRegister#setCmpInt(tempSensor.lm75.configParameter.CMP_INT)}.
	 */
	@Test
	void testSetCmpInt() {
		logger.info("testSetCmpInt()");

		try {

			when(i2cMock.readRegister(cmd_adr, rawBytes, 0, dataType.getSize()))
			.thenReturn(dataType.getSize())
			;

			when(i2cMock.writeRegister(cmd_adr, rawBytes, 0, dataType.getSize()))
			.thenReturn(dataType.getSize() + 1)
			;

			cut.clearRawBytes();
			cut.setCmpInt(CMP_INT.COMPARATOR);
			assertEquals(CMP_INT.COMPARATOR.getSetMask(), rawBytes[0]);
			
			cut.clearRawBytes();
			cut.setCmpInt(CMP_INT.INTERRUPT);
			assertEquals(CMP_INT.INTERRUPT.getSetMask(), rawBytes[0]);

			verify(i2cMock, times(2)).writeRegister(cmd_adr, rawBytes, 0, dataType.getSize());

		}
		catch(I2CErrorException e) {

			fail("Ooooops ... An exception flew by ... " + e.getMessage());

		} // yrt

	} // testSetCmpInt()


	/**
	 * Test method for {@link tempSensor.lm75.registerSet.Lm75ConfigRegister#setOsPolarity(tempSensor.lm75.configParameter.OS_POLARITY)}.
	 */
	@Test
	void testSetOsPolarity() {
		logger.info("testSetOsPolarity()");


		try {

			when(i2cMock.readRegister(cmd_adr, rawBytes, 0, dataType.getSize()))
			.thenReturn(dataType.getSize())
			;

			when(i2cMock.writeRegister(cmd_adr, rawBytes, 0, dataType.getSize()))
			.thenReturn(dataType.getSize() + 1)
			;

			cut.clearRawBytes();
			cut.setOsPolarity(OS_POLARITY.ACTIVE_LOW);
			assertEquals(OS_POLARITY.ACTIVE_LOW.getSetMask(), rawBytes[0]);
			
			cut.clearRawBytes();
			cut.setOsPolarity(OS_POLARITY.ACTIVE_HIGH);
			assertEquals(OS_POLARITY.ACTIVE_HIGH.getSetMask(), rawBytes[0]);

			verify(i2cMock, times(2)).writeRegister(cmd_adr, rawBytes, 0, dataType.getSize());

		}
		catch(I2CErrorException e) {

			fail("Ooooops ... An exception flew by ... " + e.getMessage());

		} // yrt

	} // testSetOsPolarity()


	/**
	 * Test method for {@link tempSensor.lm75.registerSet.Lm75ConfigRegister#setFaultQueue(tempSensor.lm75.configParameter.FAULT_QUEUE)}.
	 */
	@Test
	void testSetFaultQueue() {
		logger.info("testSetFaultQueue()");


		try {

			when(i2cMock.readRegister(cmd_adr, rawBytes, 0, dataType.getSize()))
			.thenReturn(dataType.getSize())
			;

			when(i2cMock.writeRegister(cmd_adr, rawBytes, 0, dataType.getSize()))
			.thenReturn(dataType.getSize() + 1)
			;

			cut.clearRawBytes();
			cut.setFaultQueue(FAULT_QUEUE.LEN_1);
			assertEquals(FAULT_QUEUE.LEN_1.getSetMask(), rawBytes[0]);
			
			cut.clearRawBytes();
			cut.setFaultQueue(FAULT_QUEUE.LEN_2);
			assertEquals(FAULT_QUEUE.LEN_2.getSetMask(), rawBytes[0]);

			cut.clearRawBytes();
			cut.setFaultQueue(FAULT_QUEUE.LEN_4);
			assertEquals(FAULT_QUEUE.LEN_4.getSetMask(), rawBytes[0]);
			
			cut.clearRawBytes();
			cut.setFaultQueue(FAULT_QUEUE.LEN_6);
			assertEquals(FAULT_QUEUE.LEN_6.getSetMask(), rawBytes[0]);


			verify(i2cMock, times(4)).writeRegister(cmd_adr, rawBytes, 0, dataType.getSize());
			
		}
		catch(I2CErrorException e) {

			fail("Ooooops ... An exception flew by ... " + e.getMessage());

		} // yrt

	} // testSetFaultQueue()

	
} // ssalc
