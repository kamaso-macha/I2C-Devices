/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : LM_75Test.java
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


package tempSensor.lm75;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mockConstruction;
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
import org.mockito.MockedConstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import i2cDevice.I2CErrorException;
import tempSensor.lm75.configParameter.CMP_INT;
import tempSensor.lm75.configParameter.FAULT_QUEUE;
import tempSensor.lm75.configParameter.OS_POLARITY;
import tempSensor.lm75.configParameter.SHUTDOWN_MODE;
import tempSensor.lm75.registerSet.Lm75ConfigRegister;
import tempSensor.lm75.registerSet.Lm75THystRegister;
import tempSensor.lm75.registerSet.Lm75TOsRegister;
import tempSensor.lm75.registerSet.Lm75TempRegister;



class LM_75Test {

	private final Logger logger = LoggerFactory.getLogger(LM_75Test.class.getName());
	
	private static final int I2C_BUS	= 2;
	private static final int I2C_ADDR	= 0x4C;
	
	private class TestLM_75 extends LM_75 {

		public TestLM_75(int aI2cBus, int aI2cAddress) {
			super(aI2cBus, aI2cAddress);
		}
		
		
	} // ssalc
	
	private static TestLM_75 cut;
	
	private InOrder sequence;
	
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
	 * Test method for {@link tempSensor.lm75.LM_75#LM_75(int, int)}.
	 */
	@Test
	void testLM_75() {
		logger.info("testLM_75()");
		
		// error handling
		
		IllegalArgumentException thrown;
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new LM_75(I2C_BUS, 0x47));
		assertEquals("aI2cAddress is out of range 0x48 .. 0x4F.", thrown.getMessage());
		
		assertDoesNotThrow(() -> new LM_75(I2C_BUS, 0x48));
		assertDoesNotThrow(() -> new LM_75(I2C_BUS, 0x4F));
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new LM_75(I2C_BUS, 0x50));
		assertEquals("aI2cAddress is out of range 0x48 .. 0x4F.", thrown.getMessage());
		
	} // testLM_75()
	

	/**
	 * Test method for {@link tempSensor.lm75.LM_75#withOsMode(tempSensor.lm75.configParameter.CMP_INT)}.
	 */
	@Test
	void testWithOsMode() {
		logger.info("testWithOsMode()");
		
		try(
				
			MockedConstruction<Lm75ConfigRegister> constructedConfReg = mockConstruction(Lm75ConfigRegister.class);
			
		) {
			
			cut = new TestLM_75(I2C_BUS, I2C_ADDR);
			
			IllegalArgumentException thrown =
				assertThrows(IllegalArgumentException.class, () -> cut.withOsMode(null));
			assertEquals("aOsMode can't be null.", thrown.getMessage());

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Lm75ConfigRegister configRegisterMock = (Lm75ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			CMP_INT  outputMode = CMP_INT.COMPARATOR;
			
			cut.withOsMode(outputMode);
			
			verify(configRegisterMock, times(1)).setCmpInt(outputMode);
			
		} // yrt
		catch(I2CErrorException e) {

			fail("Ooooops ... An exception flew by ... " + e.getMessage());

		} // yrt
			
	} // testWithOsMode()
	

	/**
	 * Test method for {@link tempSensor.lm75.LM_75#withOsPolarity(tempSensor.lm75.configParameter.OS_POLARITY)}.
	 */
	@Test
	void testWithOsPolarity() {
		logger.info("testWithOsPolarity()");
		
		try(
				
			MockedConstruction<Lm75ConfigRegister> constructedConfReg = mockConstruction(Lm75ConfigRegister.class);
			
		) {
			
			cut = new TestLM_75(I2C_BUS, I2C_ADDR);

			IllegalArgumentException thrown =
					assertThrows(IllegalArgumentException.class, () -> cut.withOsPolarity(null));
				assertEquals("aOsPolarity can't be null.", thrown.getMessage());

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Lm75ConfigRegister configRegisterMock = (Lm75ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			OS_POLARITY outputPolarity = OS_POLARITY.ACTIVE_HIGH;
			
			cut.withOsPolarity(outputPolarity);
			
			verify(configRegisterMock, times(1)).setOsPolarity(outputPolarity);
			
		} // yrt
		catch(I2CErrorException e) {

			fail("Ooooops ... An exception flew by ... " + e.getMessage());

		} // yrt
				
	} // testWithOsPolarity()
	

	/**
	 * Test method for {@link tempSensor.lm75.LM_75#withShutdownMode(tempSensor.lm75.configParameter.SHUTDOWN_MODE)}.
	 */
	@Test
	void testWithShutdownMode() {
		logger.info("testWithShutdownMode()");
		
		try(
				
			MockedConstruction<Lm75ConfigRegister> constructedConfReg = mockConstruction(Lm75ConfigRegister.class);
			
		) {
			
			cut = new TestLM_75(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Lm75ConfigRegister configRegisterMock = (Lm75ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			SHUTDOWN_MODE shutdownMode = SHUTDOWN_MODE.RUN_PERSISTENT;
			
			cut.withShutdownMode(shutdownMode);
			
			verify(configRegisterMock, times(1)).setShutdown(shutdownMode);
			
		} // yrt
		catch(I2CErrorException e) {

			fail("Ooooops ... An exception flew by ... " + e.getMessage());

		} // yrt
						
	} // testWithShutdownMode()
	

	/**
	 * Test method for {@link tempSensor.lm75.LM_75#setShutdown(SHUTDOWN_MODE)}.
	 */
	@Test
	void testSetShutdownMode() {
		logger.info("testWithOsPolarity()");
		
		try(
				
			MockedConstruction<Lm75ConfigRegister> constructedConfReg = mockConstruction(Lm75ConfigRegister.class);
			
		) {
			
			cut = new TestLM_75(I2C_BUS, I2C_ADDR);

			IllegalArgumentException thrown =
					assertThrows(IllegalArgumentException.class, () -> cut.setShutdown(null));
				assertEquals("aShutdownMode can't be null.", thrown.getMessage());

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Lm75ConfigRegister configRegisterMock = (Lm75ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			SHUTDOWN_MODE shutdownMode = SHUTDOWN_MODE.RUN_PERSISTENT;
			
			cut.setShutdown(shutdownMode);
			
			verify(configRegisterMock, times(1)).setShutdown(shutdownMode);
			
		} // yrt
		catch(I2CErrorException e) {

			fail("Ooooops ... An exception flew by ... " + e.getMessage());

		} // yrt
				
	} // testWithOsPolarity()
	

	/**
	 * Test method for {@link tempSensor.lm75.LM_75#withFaultQueue(tempSensor.lm75.configParameter.FAULT_QUEUE)}.
	 */
	@Test
	void testWithFaultQueue() {
		logger.info("testWithFaultQueue()");
		
		try(
				
			MockedConstruction<Lm75ConfigRegister> constructedConfReg = mockConstruction(Lm75ConfigRegister.class);
			
		) {
			
			cut = new TestLM_75(I2C_BUS, I2C_ADDR);

			IllegalArgumentException thrown =
					assertThrows(IllegalArgumentException.class, () -> cut.withFaultQueue(null));
				assertEquals("aFaultQueue can't be null.", thrown.getMessage());

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Lm75ConfigRegister configRegisterMock = (Lm75ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			FAULT_QUEUE faultQueue = FAULT_QUEUE.LEN_1;
			
			cut.withFaultQueue(faultQueue);
			
			verify(configRegisterMock, times(1)).setFaultQueue(faultQueue);
			
		} // yrt
		catch(I2CErrorException e) {

			fail("Ooooops ... An exception flew by ... " + e.getMessage());

		} // yrt
						
	} // testWithFaultQueue()
	

	/**
	 * Test method for {@link tempSensor.lm75.LM_75#setTOs(int)}.
	 */
	@Test
	void testSetTOs() {
		logger.info("testSetTOs()");
		
		IllegalArgumentException thrown;
		
		cut = new TestLM_75(I2C_BUS, I2C_ADDR);
		
		// error handling
		thrown = assertThrows(IllegalArgumentException.class, () ->
				cut.setTOs(126));
		assertEquals("aTos is out of range.", thrown.getMessage());
		
		thrown = assertThrows(IllegalArgumentException.class, () ->
			cut.setTOs(-56));
		assertEquals("aTos is out of range.", thrown.getMessage());

		
		// operation
		try(
				
			MockedConstruction<Lm75TOsRegister> constructedTOsReg = mockConstruction(Lm75TOsRegister.class)
				
		) {
			
			cut = new TestLM_75(I2C_BUS, I2C_ADDR);

			assertTrue(constructedTOsReg != null);
			assertEquals(1, constructedTOsReg.constructed().size());
			
			Lm75TOsRegister tOsRegisterMock = (Lm75TOsRegister) constructedTOsReg.constructed().get(0);
			assertTrue(tOsRegisterMock != null);
			
			int tOs = 42;
			
			try {
				
				cut.setTOs(tOs);
				verify(tOsRegisterMock, times(1)).write(tOs);
				
			} catch (I2CErrorException e) {

				fail("Unexpected exception occured.");
				
			}
			
		} // yrt

	} // testSetTOs()
	

	/**
	 * Test method for {@link tempSensor.lm75.LM_75#setTHyst(int)}.
	 */
	@Test
	void testSetTHyst() {
		logger.info("testSetTHyst()");
		
		IllegalArgumentException thrown;
		
		cut = new TestLM_75(I2C_BUS, I2C_ADDR);
		
		// error handling
		thrown = assertThrows(IllegalArgumentException.class, () ->
				cut.setTHyst(126));
		assertEquals("aTHyst is out of range.", thrown.getMessage());
		
		thrown = assertThrows(IllegalArgumentException.class, () ->
			cut.setTHyst(-56));
		assertEquals("aTHyst is out of range.", thrown.getMessage());

		
		// operation
		try(
				
			MockedConstruction<Lm75THystRegister> constructedTHystReg = mockConstruction(Lm75THystRegister.class)
				
		) {
			
			cut = new TestLM_75(I2C_BUS, I2C_ADDR);

			assertTrue(constructedTHystReg != null);
			assertEquals(1, constructedTHystReg.constructed().size());
			
			Lm75THystRegister tHystRegisterMock = (Lm75THystRegister) constructedTHystReg.constructed().get(0);
			assertTrue(tHystRegisterMock != null);
			
			int tHyst = 42;
			
			try {
				
				cut.setTHyst(tHyst);
				verify(tHystRegisterMock, times(1)).write(tHyst);
				
			} catch (I2CErrorException e) {

				fail("Unexpected exception occured.");
				
			}
			
		} // yrt

	} // testSetTHyst()
	

	/**
	 * Test method for {@link tempSensor.lm75.LM_75#readTemp()}.
	 */
	@Test
	void testReadTemp() {
		logger.info("testReadTemp()");
			
		try(
				
			MockedConstruction<Lm75ConfigRegister> constructedConfReg = mockConstruction(Lm75ConfigRegister.class);
			MockedConstruction<Lm75TempRegister> constructedTempReg = mockConstruction(Lm75TempRegister.class);
				
		) {
			
			LM_75 localCut = new LM_75(I2C_BUS, I2C_ADDR);

			assertTrue(constructedTempReg != null);
			assertEquals(1, constructedTempReg.constructed().size());
			
			Lm75TempRegister tempRegisterMock = (Lm75TempRegister) constructedTempReg.constructed().get(0);
			assertTrue(tempRegisterMock != null);
			
			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Lm75ConfigRegister confRegisterMock = (Lm75ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(confRegisterMock != null);
		
			sequence = inOrder(confRegisterMock, tempRegisterMock);
			
			float temp = 42.5f;
			
			try {
				
				// when in persistent mode
				localCut.setShutdown(SHUTDOWN_MODE.RUN_PERSISTENT);
				reset(confRegisterMock);
				
				when(tempRegisterMock.read())
				.thenReturn(temp)
				;
			
				float result = localCut.readTemp();
				
				sequence.verify(confRegisterMock, never()).setShutdown(SHUTDOWN_MODE.RUN_PERSISTENT);
				sequence.verify(tempRegisterMock, times(1)).read();
				
				assertEquals(temp,  result);
				
				
				// when in shutdown mode
				localCut.setShutdown(SHUTDOWN_MODE.SHUTDOWN);
				reset(confRegisterMock, tempRegisterMock);
				
				when(tempRegisterMock.read())
				.thenReturn(temp)
				;
			
				result = localCut.readTemp();
				
				sequence.verify(confRegisterMock, times(1)).setShutdown(SHUTDOWN_MODE.RUN_PERSISTENT);
				sequence.verify(tempRegisterMock, times(1)).read();
				sequence.verify(confRegisterMock, times(1)).setShutdown(SHUTDOWN_MODE.SHUTDOWN);
				
				assertEquals(temp,  result);
				
			} catch (I2CErrorException e) {

				fail("Unexpected exception occured.");
				
			}
			
		} // yrt

	} // testReadTemp()
	

	/**
	 * Test method for {@link tempSensor.lm75.LM_75#readTOs()}.
	 */
	@Test
	void testReadTOs() {
		logger.info("testReadTOs()");
		
		try(
				
			MockedConstruction<Lm75TOsRegister> constructedTOsReg = mockConstruction(Lm75TOsRegister.class)
				
		) {
			
			LM_75 localCut = new LM_75(I2C_BUS, I2C_ADDR);

			assertTrue(constructedTOsReg != null);
			assertEquals(1, constructedTOsReg.constructed().size());
			
			Lm75TOsRegister tOsRegisterMock = (Lm75TOsRegister) constructedTOsReg.constructed().get(0);
			assertTrue(tOsRegisterMock != null);
			
			float temp = 42.5f;
			
			try {
				
				when(tOsRegisterMock.read())
					.thenReturn(temp)
					;
				
				float result = localCut.readTOs();
				
				verify(tOsRegisterMock, times(1)).read();
				assertEquals(temp,  result);
				
			} catch (I2CErrorException e) {

				fail("Unexpected exception occured: " + e.getClass());
				
			}
			
		} // yrt

	} // testReadTOs()
	

	/**
	 * Test method for {@link tempSensor.lm75.LM_75#readTHyst()}.
	 */
	@Test
	void testReadTHyst() {
		logger.info("testReadTHyst()");
		
		try(
				
			MockedConstruction<Lm75THystRegister> constructedTHystReg = mockConstruction(Lm75THystRegister.class)
				
		) {
			
			LM_75 localCut = new LM_75(I2C_BUS, I2C_ADDR);

			assertTrue(constructedTHystReg != null);
			assertEquals(1, constructedTHystReg.constructed().size());
			
			Lm75THystRegister tHystRegisterMock = (Lm75THystRegister) constructedTHystReg.constructed().get(0);
			assertTrue(tHystRegisterMock != null);
			
			float temp = 42.5f;
			
			try {
				
				when(tHystRegisterMock.read())
					.thenReturn(temp)
					;
				
				float result = localCut.readTHyst();
				
				verify(tHystRegisterMock, times(1)).read();
				assertEquals(temp,  result);
				
			} catch (I2CErrorException e) {

				fail("Unexpected exception occured.");
				
			}
			
		} // yrt

	} // testReadTHyst()
	

} // ssalc
