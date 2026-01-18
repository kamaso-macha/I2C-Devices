/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS_1621Test.java
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


package tempSensor.ds1621;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
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
import tempSensor.ds1621.configParameter.PRECISION;
import tempSensor.ds1621.configParameter.THF;
import tempSensor.ds1621.configParameter.TLF;
import tempSensor.ds1621.registerSet.DS1621ConfigRegister;
import tempSensor.ds1621.registerSet.DS1621TempRegister0p5;
import tempSensor.ds1621.registerSet.DS1621TempRegister1p0;
import tempSensor.ds1621.registerSet.DS1621TempRegisterHiRes;
import tempSensor.ds1621.registerSet.DS1621ThRegister;
import tempSensor.ds1621.registerSet.DS1621TlRegister;


class DS_1621Test {

	private final Logger logger = LoggerFactory.getLogger(DS_1621Test.class.getName());

	private static final int I2C_BUS	= 2;
	private static final int I2C_ADDR	= 0x4C;
	
	private class TestDS_1621 extends DS_1621 {

		public TestDS_1621(int aI2cBus, int aI2cAddress) {
			super(aI2cBus, aI2cAddress);
		}
		
		
		public void replaceI2cDevice(final I2C aI2cDevice) { i2cDevice = aI2cDevice; }
		
	} // ssalc
	
	private MockI2C i2cMock;
	
	private final DataType dataType = DataType.WORD;
	private int registerAddress;
	
	private static TestDS_1621 cut;
	
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
		
	} // setUp()

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link tempSensor.ds1621.DS_1621#DS_1621(int, int)}.
	 */
	@Test
	void testDS_1621() {
		logger.info("testDS_1621()");
		
		// error handling
		
		IllegalArgumentException thrown;
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new DS_1621(I2C_BUS, 0x47));
		assertEquals("aI2cAddress is out of range 0x48 .. 0x4F.", thrown.getMessage());
		
		assertDoesNotThrow(() -> new DS_1621(I2C_BUS, 0x48));
		assertDoesNotThrow(() -> new DS_1621(I2C_BUS, 0x4F));
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new DS_1621(I2C_BUS, 0x50));
		assertEquals("aI2cAddress is out of range 0x48 .. 0x4F.", thrown.getMessage());
		
	} // testDS_1621()
	

	/**
	 * Test method for {@link tempSensor.ds1621.DS_1621#withOutputPolarity(tempSensor.ds1621.configParameter.POL)}.
	 */
	@Test
	void testWithOutputPolarity() {
		logger.info("testWithOutputPolarity()");
		
		try(
				
			MockedConstruction<DS1621ConfigRegister> constructedConfReg = mockConstruction(DS1621ConfigRegister.class);
			
		) {
			
			cut = new TestDS_1621(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			DS1621ConfigRegister configRegisterMock = (DS1621ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			POL outputPolarity = POL.ACT_HIGH;
			
			cut.withOutputPolarity(outputPolarity);
			
			verify(configRegisterMock, times(1)).setPOL(outputPolarity);
			
		} // yrt
		catch (I2CErrorException e) {
		
		fail("Ooops ... An exception flew by ... " + e.getClass());
		
		}
		
	} // testWithOutputPolarity()
	

	/**
	 * Test method for {@link tempSensor.ds1621.DS_1621#withConversionMode(tempSensor.ds1621.configParameter.ONE_SHOT)}.
	 */
	@Test
	void testWithConversionMode() {
		logger.info("testWithConversionMode()");
		
		try(
				
			MockedConstruction<DS1621ConfigRegister> constructedConfReg = mockConstruction(DS1621ConfigRegister.class)
				
		) {
			
			
			cut = new TestDS_1621(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			DS1621ConfigRegister configRegisterMock = (DS1621ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			ONE_SHOT conversionMode = ONE_SHOT.ONE_SHOT;
			
			cut.withConversionMode(conversionMode);
			
			verify(configRegisterMock, times(1)).set1SHOT(conversionMode);
			
		} // yrt
		catch (I2CErrorException e) {
			
		fail("Ooops ... An exception flew by ... " + e.getClass());
		
		}

	} // testWithConversionMode()
	

	/**
	 * Test method for {@link tempSensor.ds1621.DS_1621#withPrecicision(tempSensor.ds1621.configParameter.PRECISION)}.
	 */
	@Test
	void testWithPrecicision() {
		logger.info("testWithPrecicision()");
		
		try(
			
			MockedConstruction<DS1621ConfigRegister> constructedConfReg = mockConstruction(DS1621ConfigRegister.class)
				
		) {
			
			cut = new TestDS_1621(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			DS1621ConfigRegister configRegisterMock = (DS1621ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			
			// resolution 0.5 degree
			PRECISION precicision = PRECISION.RES_0_5_DEGREE;
			
			cut.withPrecicision(precicision);		
			assertTrue(cut.tempRegister instanceof DS1621TempRegister0p5);
			
			
			// resolution 1.0 degree
			precicision = PRECISION.RES_1_0_DEGREE;
			
			cut.withPrecicision(precicision);
			assertTrue(cut.tempRegister instanceof DS1621TempRegister1p0);
			

			// high resolution
			precicision = PRECISION.RES_HIGH;
			
			cut.withPrecicision(precicision);
			assertTrue(cut.tempRegister instanceof DS1621TempRegisterHiRes);

			
			verifyNoInteractions(configRegisterMock);

		} // yrt

	} // testWithPrecicision()
	
	
	/**
	 * Test method for {@link tempSensor.ds1621.DS_1621#getDone()}.
	 */
	@Test
	void testGetDONE() {
		logger.info("testGetDONE()");
		
		try(
				
			MockedConstruction<DS1621ConfigRegister> constructedConfReg = mockConstruction(DS1621ConfigRegister.class);
			
		) {
			
			cut = new TestDS_1621(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			DS1621ConfigRegister configRegisterMock = (DS1621ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			DONE done = DONE.COMPLETE;
			
			when(configRegisterMock.getDONE()).thenReturn(done);
			
			DONE result = cut.getDone();
			
			verify(configRegisterMock, times(1)).getDONE();
			assertEquals(done, result);
			
		} // yrt
		catch (I2CErrorException e) {
			
		fail("Ooops ... An exception flew by ... " + e.getClass());
		
		}
		
	} // testGetDONE()
	

	/**
	 * Test method for {@link tempSensor.ds1621.DS_1621#getThf()}.
	 */
	@Test
	void testGetThf() {
		logger.info("testGetThf()");
		
		try(
				
			MockedConstruction<DS1621ConfigRegister> constructedConfReg = mockConstruction(DS1621ConfigRegister.class);
			
		) {
			
			cut = new TestDS_1621(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			DS1621ConfigRegister configRegisterMock = (DS1621ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			THF thf = THF.BELOW;
			
			when(configRegisterMock.getTHF()).thenReturn(thf);
			
			THF result = cut.getThf();
			
			verify(configRegisterMock, times(1)).getTHF();
			assertEquals(thf, result);
			
		} // yrt
		catch (I2CErrorException e) {
			
		fail("Ooops ... An exception flew by ... " + e.getClass());
		
		}
		
	} // testGetThf()
	

	/**
	 * Test method for {@link tempSensor.ds1621.DS_1621#getTlf()}.
	 */
	@Test
	void testGetTlf() {
		logger.info("testGetTlf()");
		
		try(
				
			MockedConstruction<DS1621ConfigRegister> constructedConfReg = mockConstruction(DS1621ConfigRegister.class);
			
		) {
			
			cut = new TestDS_1621(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			DS1621ConfigRegister configRegisterMock = (DS1621ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			TLF tlf = TLF.GT_EQ;
			
			when(configRegisterMock.getTLF()).thenReturn(tlf);
			
			TLF result = cut.getTlf();
			
			verify(configRegisterMock, times(1)).getTLF();
			assertEquals(tlf, result);
			
		} // yrt
		catch (I2CErrorException e) {
			
		fail("Ooops ... An exception flew by ... " + e.getClass());
		
		}
		
	} // testGetTlf()
	

	/**
	 * Test method for {@link tempSensor.ds1621.DS_1621#getset1Shot(ONE_SHOT)}.
	 */
	@Test
	void testSet1Shot() {
		logger.info("testSet1Shot()");
		
		try(
				
			MockedConstruction<DS1621ConfigRegister> constructedConfReg = mockConstruction(DS1621ConfigRegister.class);
			
		) {
			
			cut = new TestDS_1621(I2C_BUS, I2C_ADDR);
			
			IllegalArgumentException thrown = 
				assertThrows(IllegalArgumentException.class, () -> cut.set1Shot(null));
			assertEquals("aOneShot can't be null.", thrown.getMessage());

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			DS1621ConfigRegister configRegisterMock = (DS1621ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);

			cut.set1Shot(ONE_SHOT.ONE_SHOT);
			
			verify(configRegisterMock, times(1)).set1SHOT(ONE_SHOT.ONE_SHOT);
			
		} // yrt
		catch (I2CErrorException e) {
			
		fail("Ooops ... An exception flew by ... " + e.getClass());
		
		}
		
	} // testSet1Shot()
	

	/**
	 * Test method for {@link tempSensor.ds1621.DS_1621#setTh(float)}.
	 */
	@Test
	void testSetTh() {
		logger.info("testSetTh()");
		
		try(
				
			MockedConstruction<DS1621ConfigRegister> constructedConfReg = mockConstruction(DS1621ConfigRegister.class);
			MockedConstruction<DS1621ThRegister> constructedThReg = mockConstruction(DS1621ThRegister.class);
				
		) {
			
			cut = new TestDS_1621(I2C_BUS, I2C_ADDR);
			cut.replaceI2cDevice(i2cMock);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			DS1621ConfigRegister confRegisterMock = (DS1621ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(confRegisterMock != null);

			assertTrue(constructedThReg != null);
			assertEquals(1, constructedThReg.constructed().size());
			
			DS1621ThRegister thRegisterMock = (DS1621ThRegister) constructedThReg.constructed().get(0);
			assertTrue(thRegisterMock != null);

			registerAddress = COMMAND_SET.ACCESS_TH.value;
			
			when(confRegisterMock.getNVB()).thenReturn(
					NVB.IN_PROGRESS,
					NVB.COMPLETE
				);
			
			when(i2cMock.writeRegister(registerAddress, rawBytes, 0, dataType.getSize()))
				.thenReturn(dataType.getSize() + 1)
				;
			
			float th = 42.5f;
			
			cut.setTh(th);
			verify(thRegisterMock, times(1)).write(th);
			
		} catch (I2CErrorException e) {

			fail("Unexpected exception occured.");
		
		} // yrt

	} // testSetTh()
	

	/**
	 * Test method for {@link tempSensor.ds1621.DS_1621#setTl(float)}.
	 */
	@Test
	void testSetTl() {
		logger.info("testSetTl()");
		
		try(
				
				MockedConstruction<DS1621ConfigRegister> constructedConfReg = mockConstruction(DS1621ConfigRegister.class);
				MockedConstruction<DS1621TlRegister> constructedTlReg = mockConstruction(DS1621TlRegister.class);
					
			) {
				
				cut = new TestDS_1621(I2C_BUS, I2C_ADDR);
				cut.replaceI2cDevice(i2cMock);

				assertTrue(constructedConfReg != null);
				assertEquals(1, constructedConfReg.constructed().size());
				
				DS1621ConfigRegister confRegisterMock = (DS1621ConfigRegister) constructedConfReg.constructed().get(0);
				assertTrue(confRegisterMock != null);

				assertTrue(constructedTlReg != null);
				assertEquals(1, constructedTlReg.constructed().size());
				
				DS1621TlRegister tlRegisterMock = (DS1621TlRegister) constructedTlReg.constructed().get(0);
				assertTrue(tlRegisterMock != null);

				registerAddress = COMMAND_SET.ACCESS_TL.value;
				
				when(confRegisterMock.getNVB()).thenReturn(
						NVB.IN_PROGRESS,
						NVB.COMPLETE
					);
				
				when(i2cMock.writeRegister(registerAddress, rawBytes, 0, dataType.getSize()))
					.thenReturn(dataType.getSize() + 1)
					;
				
				float temp = 42.5f;
				
				cut.setTl(temp);
				verify(tlRegisterMock, times(1)).write(temp);
				
			} catch (I2CErrorException e) {

				fail("Unexpected exception occured.");
			
			} // yrt

	} // testSetTl()
	

	/**
	 * Test method for {@link tempSensor.ds1621.DS_1621#readTemperature()}.
	 */
	@Test
	void testReadTemperature() {
		logger.info("testReadTemperature()");
		
		try(
				
			MockedConstruction<DS1621TempRegisterHiRes> constructedTempReg = mockConstruction(DS1621TempRegisterHiRes.class)
				
		) {
			
			DS_1621 localCut = new DS_1621(I2C_BUS, I2C_ADDR)
				.withPrecicision(PRECISION.RES_HIGH)
				;

			assertTrue(constructedTempReg != null);
			assertEquals(1, constructedTempReg.constructed().size());
			
			DS1621TempRegisterHiRes tempRegisterMock = (DS1621TempRegisterHiRes) constructedTempReg.constructed().get(0);
			assertTrue(tempRegisterMock != null);
			
			float temp = 42.5f;
			
			try {
				
				when(tempRegisterMock.readTemperature())
					.thenReturn(temp)
					;
				
				float result = localCut.readTemperature();
				
				verify(tempRegisterMock, times(1)).readTemperature();
				assertEquals(temp,  result);
				
			} catch (I2CErrorException e) {

				fail("Unexpected exception occured.");
				
			}
			
		} // yrt

	} // testReadTemperature()
	

	/**
	 * Test method for {@link tempSensor.ds1621.DS_1621#readTh(float)}.
	 */
	@Test
	void testReadTh() {
		logger.info("testReadTh()");
		
		try(
				
				MockedConstruction<DS1621ConfigRegister> constructedConfReg = mockConstruction(DS1621ConfigRegister.class);
				MockedConstruction<DS1621ThRegister> constructedThReg = mockConstruction(DS1621ThRegister.class);
					
			) {
				
				cut = new TestDS_1621(I2C_BUS, I2C_ADDR);
				cut.replaceI2cDevice(i2cMock);

				assertTrue(constructedConfReg != null);
				assertEquals(1, constructedConfReg.constructed().size());
				
				DS1621ConfigRegister confRegisterMock = (DS1621ConfigRegister) constructedConfReg.constructed().get(0);
				assertTrue(confRegisterMock != null);

				assertTrue(constructedThReg != null);
				assertEquals(1, constructedThReg.constructed().size());
				
				DS1621ThRegister thRegisterMock = (DS1621ThRegister) constructedThReg.constructed().get(0);
				assertTrue(thRegisterMock != null);

				registerAddress = COMMAND_SET.ACCESS_TH.value;
				
				when(confRegisterMock.getNVB()).thenReturn(
						NVB.IN_PROGRESS,
						NVB.COMPLETE
					);
				
				when(i2cMock.writeRegister(registerAddress, rawBytes, 0, dataType.getSize()))
					.thenReturn(dataType.getSize() + 1)
					;
				
				float th = 42.5f;
				
				cut.setTh(th);
				cut.readTh();
				
				verify(thRegisterMock, times(1)).read();
				
			} catch (I2CErrorException e) {

				fail("Unexpected exception occured.");
			
			} // yrt
			
	} // testReadTh()
	

	/**
	 * Test method for {@link tempSensor.ds1621.DS_1621#setTl(float)}.
	 */
	@Test
	void testReadTl() {
		logger.info("testReadTl()");
		
		try(
				
				MockedConstruction<DS1621ConfigRegister> constructedConfReg = mockConstruction(DS1621ConfigRegister.class);
				MockedConstruction<DS1621TlRegister> constructedTlReg = mockConstruction(DS1621TlRegister.class);
					
			) {
				
				cut = new TestDS_1621(I2C_BUS, I2C_ADDR);
				cut.replaceI2cDevice(i2cMock);

				assertTrue(constructedConfReg != null);
				assertEquals(1, constructedConfReg.constructed().size());
				
				DS1621ConfigRegister confRegisterMock = (DS1621ConfigRegister) constructedConfReg.constructed().get(0);
				assertTrue(confRegisterMock != null);

				assertTrue(constructedTlReg != null);
				assertEquals(1, constructedTlReg.constructed().size());
				
				DS1621TlRegister tlRegisterMock = (DS1621TlRegister) constructedTlReg.constructed().get(0);
				assertTrue(tlRegisterMock != null);

				registerAddress = COMMAND_SET.ACCESS_TL.value;
				
				when(confRegisterMock.getNVB()).thenReturn(
						NVB.IN_PROGRESS,
						NVB.COMPLETE
					);
				
				when(i2cMock.writeRegister(registerAddress, rawBytes, 0, dataType.getSize()))
					.thenReturn(dataType.getSize() + 1)
					;
				
				float temp = 42.5f;
				
				cut.setTl(temp);
				cut.readTl();
				verify(tlRegisterMock, times(1)).read();
				
			} catch (I2CErrorException e) {

				fail("Unexpected exception occured.");
			
			} // yrt

	} // testReadTl()
	

	/**
	 * Test method for {@link tempSensor.ds1621.DS_1621#getTlf()}.
	 */
	@Test
	void testWhileNvbBusy() {
		logger.info("testWhileNvbBusy()");
		
		/*
		 * 		Nothing to test!
		 * 
		 * 		Tests are implicitly done by setTh(), setTl(), getTh() and getTl().
		 * 
		 */
		
		assertTrue(true);
		
	} // testWhileNvbBusy()
	

} // ssalc
