/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS1621ConfigRegisterTest.java
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

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import tempSensor.ds1621.configParameter.COMMAND_SET;
import tempSensor.ds1621.configParameter.DONE;
import tempSensor.ds1621.configParameter.NVB;
import tempSensor.ds1621.configParameter.ONE_SHOT;
import tempSensor.ds1621.configParameter.POL;
import tempSensor.ds1621.configParameter.THF;
import tempSensor.ds1621.configParameter.TLF;


class DS1621ConfigRegisterTest {

	private Logger logger = LoggerFactory.getLogger(DS1621ConfigRegisterTest.class);
	
	private class TestDS1621ConfigRegister extends DS1621ConfigRegister {

		public TestDS1621ConfigRegister(I2C aI2cDevice) {
			super(aI2cDevice);
		}
		
		public byte[] getRawBytes() { return raw; }
		
	} // ssalc
	

	private TestDS1621ConfigRegister cut;
	
	private MockI2C i2cMock;
	
	final int ptrReg = COMMAND_SET.ACCESS_CONFIG.value;
	final DataType DATA_TYPE = DataType.BYTE;

	private byte[] rawBytes;

	int config;
	
	
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
		
		cut = new TestDS1621ConfigRegister(i2cMock);
		
	} // setUp()
	

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621ConfigRegister#DS1621ConfigRegister(com.pi4j.io.i2c.I2C)}.
	 */
	@Test
	void testDS1621ConfigRegister() {
		logger.info("testDS1621ConfigRegister()");
		
		IllegalArgumentException thrown;
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new DS1621ConfigRegister(null));
		assertEquals("aI2cDevice can't be null.", thrown.getMessage());
	
		assertDoesNotThrow(() -> new DS1621ConfigRegister(i2cMock));
		
	} // testDS1621ConfigRegister()
	

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621ConfigRegister#getDONE()}.
	 */
	@Test
	void testGetDONE() {
		logger.info("testGetDONE()");
		
		try { 

			rawBytes = cut.getRawBytes();
			
			when(i2cMock.readRegister(ptrReg, rawBytes, 0, DATA_TYPE.getSize()))
				.thenReturn(1);
			
			for(DONE candidate : DONE.values()) {
			
				rawBytes[0] = (byte) candidate.getSetMask();
				DONE result = cut.getDONE();
		
				logger.info(String.format("rawBytes[0] = 0x%02X, result = %s", rawBytes[0], result));
				
				// then
				assertTrue(result != null, "not null");
				assertEquals(result, candidate);
			
			} // rof
			
		}
		catch (I2CErrorException e) {
		
			fail("Ooops ... An exception flew by ... " + e.getClass());
		
		} // yrt
		
	} // testGetDONE()
	

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621ConfigRegister#getTHF()}.
	 */
	@Test
	void testGetTHF() {
		logger.info("testGetTHF()");
		
		try { 

			rawBytes = cut.getRawBytes();
			
			when(i2cMock.readRegister(ptrReg, rawBytes, 0, DATA_TYPE.getSize()))
				.thenReturn(1);
			
			for(THF candidate : THF.values()) {
			
				rawBytes[0] = (byte) candidate.getSetMask();
				THF result = cut.getTHF();
		
				logger.info(String.format("rawBytes[0] = 0x%02X, result = %s", rawBytes[0], result));
				
				// then
				assertTrue(result != null, "not null");
				assertEquals(result, candidate);
			
			} // rof
			
		}
		catch (I2CErrorException e) {
		
			fail("Ooops ... An exception flew by ... " + e.getClass());
		
		} // yrt
		
	} // testGetTHF()
	

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621ConfigRegister#getTLF()}.
	 */
	@Test
	void testGetTLF() {
		logger.info("testGetTLF()");
		
		try { 

			rawBytes = cut.getRawBytes();
			
			when(i2cMock.readRegister(ptrReg, rawBytes, 0, DATA_TYPE.getSize()))
				.thenReturn(1);
			
			for(TLF candidate : TLF.values()) {
			
				rawBytes[0] = (byte) candidate.getSetMask();
				TLF result = cut.getTLF();
		
				logger.info(String.format("rawBytes[0] = 0x%02X, result = %s", rawBytes[0], result));
				
				// then
				assertTrue(result != null, "not null");
				assertEquals(result, candidate);
			
			} // rof
			
		}
		catch (I2CErrorException e) {
		
			fail("Ooops ... An exception flew by ... " + e.getClass());
		
		}
		
	} // testGetTLF()
	

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621ConfigRegister#getNVB()}.
	 */
	@Test
	void testGetNVB() {
		logger.info("testGetNVB()");
		
		try { 

			rawBytes = cut.getRawBytes();
			
			when(i2cMock.readRegister(ptrReg, rawBytes, 0, DATA_TYPE.getSize()))
				.thenReturn(1);
			
			for(NVB candidate : NVB.values()) {
			
				rawBytes[0] = (byte) candidate.getSetMask();
				NVB result = cut.getNVB();
		
				logger.info(String.format("rawBytes[0] = 0x%02X, result = %s", rawBytes[0], result));
				
				// then
				assertTrue(result != null, "not null");
				assertEquals(result, candidate);
			
			} // rof
			
		}
		catch (I2CErrorException e) {
		
			fail("Ooops ... An exception flew by ... " + e.getClass());
		
		}
		
	} // testGetNVB()
	

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621ConfigRegister#get1SHOT()}.
	 */
	@Test
	void testGet1SHOT() {
		logger.info("testGet1SHOT()");
		
		try { 

			rawBytes = cut.getRawBytes();
			
			when(i2cMock.readRegister(ptrReg, rawBytes, 0, DATA_TYPE.getSize()))
				.thenReturn(DATA_TYPE.getSize());
			
			for(ONE_SHOT candidate : ONE_SHOT.values()) {
			
				rawBytes[0] = (byte) candidate.getSetMask();
				ONE_SHOT result = cut.get1SHOT();
		
				logger.info(String.format("rawBytes[0] = 0x%02X, result = %s", rawBytes[0], result));
				
				// then
				assertTrue(result != null, "not null");
				assertEquals(result, candidate);
			
			} // rof
			
		}
		catch (I2CErrorException e) {
		
			fail("Ooops ... An exception flew by ... " + e.getClass());
		
		}
		
	} // testGet1SHOT()
	

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621ConfigRegister#setPOL(tempSensor.ds1621.configParameter.POL)}.
	 */
	@Test
	void testSetPOL() {
		logger.info("testSetPOL()");
		
		try { 

			rawBytes = cut.getRawBytes();
			byte result;
			
			when(i2cMock.readRegister(ptrReg, rawBytes, 0, DATA_TYPE.getSize()))
			.thenReturn(DATA_TYPE.getSize());
		
			when(i2cMock.writeRegister(ptrReg, rawBytes, 0, DATA_TYPE.getSize()))
				.thenReturn(DATA_TYPE.getSize() + 1);
			
			for(POL candidate : POL.values()) {
			
				cut.setPOL(candidate);
				
				result = rawBytes[0];
				
				logger.info(String.format("rawBytes[0] = 0x%02X, result = %s", rawBytes[0], result));
				
				// then
				assertEquals(result, candidate.getSetMask());
			
			} // rof
			
		}
		catch (I2CErrorException e) {
		
			fail("Ooops ... An exception flew by ... " + e.getClass());
		
		} // yrt
		
	} // testSetPOL()
	

	/**
	 * Test method for {@link tempSensor.ds1621.registerSet.DS1621ConfigRegister#set1SHOT(tempSensor.ds1621.configParameter.ONE_SHOT)}.
	 */
	@Test
	void testSet1SHOT() {
		logger.info("testSet1SHOT()");
		
		try { 

			rawBytes = cut.getRawBytes();
			byte result;
			
			when(i2cMock.readRegister(ptrReg, rawBytes, 0, DATA_TYPE.getSize()))
			.thenReturn(DATA_TYPE.getSize());
		
			when(i2cMock.writeRegister(ptrReg, rawBytes, 0, DATA_TYPE.getSize()))
				.thenReturn(DATA_TYPE.getSize() + 1);
			
			for(ONE_SHOT candidate : ONE_SHOT.values()) {
			
				cut.set1SHOT(candidate);
				
				result = rawBytes[0];
				
				logger.info(String.format("rawBytes[0] = 0x%02X, result = %s", rawBytes[0], result));
				
				// then
				assertEquals(result, candidate.getSetMask());
				
			} // rof
			
		}
		catch (I2CErrorException e) {
		
			fail("Ooops ... An exception flew by ... " + e.getClass());
		
		} // yrt
		
	} // testSet1SHOT()
	

} // ssalc
