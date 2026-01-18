/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : ADS_1115Test.java
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


package adc.ads1115;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mockConstruction;
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

import adc.ads1115.configParameter.COMP_LAT;
import adc.ads1115.configParameter.COMP_MODE;
import adc.ads1115.configParameter.COMP_POL;
import adc.ads1115.configParameter.COMP_QUE;
import adc.ads1115.configParameter.DR;
import adc.ads1115.configParameter.MODE;
import adc.ads1115.configParameter.MUX;
import adc.ads1115.configParameter.OS_Ro;
import adc.ads1115.configParameter.OS_Wo;
import adc.ads1115.configParameter.PGA;
import adc.ads1115.registerSet.Ads1115ConfigRegister;
import adc.ads1115.registerSet.Ads1115ConversionRegister;
import adc.ads1115.registerSet.Ads1115HiThresholdRegister;
import adc.ads1115.registerSet.Ads1115LoThresholdRegister;
import i2cDevice.I2CErrorException;


class ADS_1115Test {

	private final Logger logger = LoggerFactory.getLogger(ADS_1115Test.class.getName());

	private static final int I2C_BUS	= 2;
	private static final int I2C_ADDR	= 0x4C;
	
	private class TestADS_1115 extends ADS_1115 {

		public TestADS_1115(int aI2cBus, int aI2cAddress) {
			super(aI2cBus, aI2cAddress);
		}
		
		
	} // ssalc
	
	private static ADS_1115 cut;

	static final PGA pga = PGA.FSR_1_024V;
	
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
	 * Test method for {@link adc.ads1115.ADS_1115#ADS_1115(int, int)}.
	 */
	@Test
	void testADS_1115() {
		logger.info("testADS_1115()");
		
		// error handling
		
		IllegalArgumentException thrown;
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new ADS_1115(I2C_BUS, 0x47));
		assertEquals("aI2cAddress is out of range 0x48 .. 0x4F.", thrown.getMessage());
		
		assertDoesNotThrow(() -> new ADS_1115(I2C_BUS, 0x48));
		assertDoesNotThrow(() -> new ADS_1115(I2C_BUS, 0x4F));
		
		thrown = assertThrows(IllegalArgumentException.class, () -> new ADS_1115(I2C_BUS, 0x50));
		assertEquals("aI2cAddress is out of range 0x48 .. 0x4F.", thrown.getMessage());
		
	} // testADS_1115()


	/**
	 * Test method for {@link adc.ads1115.ADS_1115#withMode(adc.ads1115.configParameter.MODE)}.
	 */
	@Test
	void testWithMode() {
		logger.info("testWithMode()");
		
		try(
				
			MockedConstruction<Ads1115ConfigRegister> constructedConfReg = mockConstruction(Ads1115ConfigRegister.class);
			
		) {
			
			cut = new TestADS_1115(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Ads1115ConfigRegister configRegisterMock = (Ads1115ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
		
			
			IllegalArgumentException thrown =
					assertThrows(IllegalArgumentException.class, () -> cut.withMode(null));
			assertEquals("aMode can't be null.", thrown.getMessage());

			MODE mode = MODE.SINGLE_SHOT;
			
			cut.withMode(mode);
			
			verify(configRegisterMock, times(1)).setMode(mode);
			
		} // yrt
		catch(I2CErrorException e) {
			
			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");
			
		} // yrt
		
	} // testWithMode()

	/**
	 * Test method for {@link adc.ads1115.ADS_1115#withDr(adc.ads1115.configParameter.DR)}.
	 */
	@Test
	void testWithDr() {
		logger.info("testWithDr()");
		
		try(
				
			MockedConstruction<Ads1115ConfigRegister> constructedConfReg = mockConstruction(Ads1115ConfigRegister.class);
			
		) {
			
			cut = new TestADS_1115(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Ads1115ConfigRegister configRegisterMock = (Ads1115ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			

			IllegalArgumentException thrown =
					assertThrows(IllegalArgumentException.class, () -> cut.withDr(null));
			assertEquals("aDr can't be null.", thrown.getMessage());

			DR dr = DR.SPS_128;
			
			cut.withDr(dr);
			
			verify(configRegisterMock, times(1)).setDr(dr);
			
		} // yrt
		catch(I2CErrorException e) {
			
			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");
			
		} // yrt
		
	} // testWithDr()

	/**
	 * Test method for {@link adc.ads1115.ADS_1115#withCompMode(adc.ads1115.configParameter.COMP_MODE)}.
	 */
	@Test
	void testWithCompMode() {
		logger.info("testWithCompMode()");
				
		try(
			
			MockedConstruction<Ads1115ConfigRegister> constructedConfReg = mockConstruction(Ads1115ConfigRegister.class);
			
		) {
			
			cut = new TestADS_1115(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Ads1115ConfigRegister configRegisterMock = (Ads1115ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
	
			IllegalArgumentException thrown =
					assertThrows(IllegalArgumentException.class, () -> cut.withCompMode(null));
			assertEquals("aCompMode can't be null.", thrown.getMessage());

			COMP_MODE compMode = COMP_MODE.TRADITIONAL;
			
			cut.withCompMode(compMode);
			
			verify(configRegisterMock, times(1)).setCompMode(compMode);
			
		} // yrt
		catch(I2CErrorException e) {
			
			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");
			
		} // yrt
		
	} // testWithCompMode()

	/**
	 * Test method for {@link adc.ads1115.ADS_1115#withCompPol(adc.ads1115.configParameter.COMP_POL)}.
	 */
	@Test
	void testWithCompPol() {
		logger.info("testWithCompPol()");
		
		try(
				
			MockedConstruction<Ads1115ConfigRegister> constructedConfReg = mockConstruction(Ads1115ConfigRegister.class);
			
		) {
			
			cut = new TestADS_1115(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Ads1115ConfigRegister configRegisterMock = (Ads1115ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			IllegalArgumentException thrown =
					assertThrows(IllegalArgumentException.class, () -> cut.withCompPol(null));
			assertEquals("aCompPol can't be null.", thrown.getMessage());

			COMP_POL compPol = COMP_POL.ACTIVE_HIGH;
			
			cut.withCompPol(compPol);
			
			verify(configRegisterMock, times(1)).setCompPol(compPol);
			
		} // yrt
		catch(I2CErrorException e) {
			
			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");
			
		} // yrt
		
	} // testWithCompPol()

	/**
	 * Test method for {@link adc.ads1115.ADS_1115#withCompLat(adc.ads1115.configParameter.COMP_LAT)}.
	 */
	@Test
	void testWithCompLat() {
		logger.info("testWithCompLat()");
		
		try(
				
			MockedConstruction<Ads1115ConfigRegister> constructedConfReg = mockConstruction(Ads1115ConfigRegister.class);
			
		) {
			
			cut = new TestADS_1115(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Ads1115ConfigRegister configRegisterMock = (Ads1115ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			IllegalArgumentException thrown =
					assertThrows(IllegalArgumentException.class, () -> cut.withCompLat(null));
			assertEquals("aCompLat can't be null.", thrown.getMessage());

			COMP_LAT compLat = COMP_LAT.LATCHNG;
			
			cut.withCompLat(compLat);
			
			verify(configRegisterMock, times(1)).setCompLat(compLat);
			
		} // yrt
		catch(I2CErrorException e) {
			
			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");
			
		} // yrt
				
	} // testWithCompLat()


	/**
	 * Test method for {@link adc.ads1115.ADS_1115#withCompQue(adc.ads1115.configParameter.COMP_QUE)}.
	 */
	@Test
	void testWithCompQue() {
		logger.info("testWithCompQue()");
		
		try(
				
			MockedConstruction<Ads1115ConfigRegister> constructedConfReg = mockConstruction(Ads1115ConfigRegister.class);
			
		) {
			
			cut = new TestADS_1115(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Ads1115ConfigRegister configRegisterMock = (Ads1115ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			IllegalArgumentException thrown =
					assertThrows(IllegalArgumentException.class, () -> cut.withCompQue(null));
			assertEquals("aCompQue can't be null.", thrown.getMessage());
			
			COMP_QUE compQue = COMP_QUE.DISABLE_COMPARATOR;
			
			cut.withCompQue(compQue);
			
			verify(configRegisterMock, times(1)).setCompQue(compQue);
			
		} // yrt
		catch(I2CErrorException e) {
			
			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");
			
		} // yrt
				
	} // testWithCompQue()
	

	/**
	 * Test method for {@link adc.ads1115.ADS_1115#getOs(adc.ads1115.configParameter.OS_Wo)}.
	 */
	@Test
	void testGetOs_Ro() {
		logger.info("testGetOs_Ro()");
		
		try(
				
				MockedConstruction<Ads1115ConfigRegister> constructedConfReg = mockConstruction(Ads1115ConfigRegister.class);
				
			) {
				
				cut = new TestADS_1115(I2C_BUS, I2C_ADDR);

				assertTrue(constructedConfReg != null);
				assertEquals(1, constructedConfReg.constructed().size());
				
				Ads1115ConfigRegister configRegisterMock = (Ads1115ConfigRegister) constructedConfReg.constructed().get(0);
				assertTrue(configRegisterMock != null);
				
				when(configRegisterMock.getOs())
					.thenReturn(
						OS_Ro.CONVERSION_RUNNING, 
						OS_Ro.CONVERSION_IDLE
					);
				
				OS_Ro osRo = cut.getOs();			
				assertEquals(OS_Ro.CONVERSION_RUNNING, osRo);
				
				osRo = cut.getOs();
				assertEquals(OS_Ro.CONVERSION_IDLE, osRo);
				
				verify(configRegisterMock, times(2)).getOs();
				
			}
			catch (I2CErrorException e) {
				
				fail("Ooops ... An exception flew by ... " + e.getClass());

			} // yrt
				
	} // testGetOs_Ro()


	/**
	 * Test method for {@link adc.ads1115.ADS_1115#readInput(adc.ads1115.configParameter.MUX)}.
	 */
	@Test
	void testReadInput() {
		logger.info("testReadInput()");
		
		PGA pga = PGA.FSR_1_024V;
		Ads1115AInput input;
				
		double result;

		try(
				
			MockedConstruction<Ads1115ConfigRegister> constructedConfReg = mockConstruction(Ads1115ConfigRegister.class);
			MockedConstruction<Ads1115ConversionRegister> constructedConversionRegister = mockConstruction(Ads1115ConversionRegister.class);
			
		) {
			
			cut = new ADS_1115(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Ads1115ConfigRegister configRegisterMock = (Ads1115ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);

			assertTrue(constructedConversionRegister != null);
			assertEquals(1, constructedConversionRegister.constructed().size());
			
			Ads1115ConversionRegister conversionRegisterMock = (Ads1115ConversionRegister) constructedConversionRegister.constructed().get(0);
			assertTrue(conversionRegisterMock != null);
			
			sequence = inOrder(conversionRegisterMock, configRegisterMock);
			
			for(MUX e : MUX.values()) {
				
				input = new Ads1115AInput(e, pga);

				reset(conversionRegisterMock, configRegisterMock);

				when(configRegisterMock.getMode()).thenReturn(MODE.SINGLE_SHOT);
				when(configRegisterMock.getDr()).thenReturn(DR.SPS_32);
				when(configRegisterMock.getOs()).thenReturn(
					OS_Ro.CONVERSION_RUNNING,
					OS_Ro.CONVERSION_IDLE
				);
				
				
				when(conversionRegisterMock.read(input.pga)).thenReturn(42.42d);

				result = cut.readInput(input);
				
				assertEquals(42.42d,  result);
				
				sequence.verify(configRegisterMock, times(1)).setMux(e);
				sequence.verify(configRegisterMock, times(1)).setPga(pga);
				sequence.verify(configRegisterMock, times(1)).getMode();
				sequence.verify(configRegisterMock, times(1)).setOs(OS_Wo.START_SINGLE_CONVERSION);
				sequence.verify(configRegisterMock, times(2)).getOs();
				
				sequence.verify(conversionRegisterMock, times(1)).read(pga);

			} // rof
							
		}
		catch (I2CErrorException e) {
			
			fail("Ooops ... An exception flew by ... " + e.getClass());

		} // yrt
		
	} // testReadInput()

//	/**
//	 * @param pga
//	 * @param input
//	 * @param conversionRegisterMock
//	 * @throws I2CErrorException
//	 */
//	private void doAndVerifyReadInput(PGA pga, Ads1115AInput input, Ads1115ConversionRegister conversionRegisterMock)
//			throws I2CErrorException {
//
//		reset(conversionRegisterMock);
//
//		when(conversionRegisterMock.read(input.pga)).thenReturn(42.42d);
//
//		result = cut.readInput(input);
//		
//		assertEquals(42.42d,  result);
//		verify(conversionRegisterMock).read(pga);
//		
//	} // doAndVerifyReadInput()


	/**
	 * Test method for {@link adc.ads1115.ADS_1115#readHiThresRegister()}.
	 */
	@Test
	void testReadHiThresRegister() {
		logger.info("testReadHiThresRegister()");
		
		try(
				
				MockedConstruction<Ads1115HiThresholdRegister> constructedHiThrReg = mockConstruction(Ads1115HiThresholdRegister.class);
				
			) {
				
				cut = new ADS_1115(I2C_BUS, I2C_ADDR);

				assertTrue(constructedHiThrReg != null);
				assertEquals(1, constructedHiThrReg.constructed().size());
				
				Ads1115HiThresholdRegister hiThrRegisterMock = (Ads1115HiThresholdRegister) constructedHiThrReg.constructed().get(0);
				assertTrue(hiThrRegisterMock != null);
				
				when(hiThrRegisterMock.read(pga)).thenReturn(42.42d);

				double result = cut.readHiThresRegister(pga);
				
				assertEquals(42.42d,  result);
				verify(hiThrRegisterMock,  times(1)).read(pga);
				
		}
		catch (I2CErrorException e) {
			
			fail("Ooops ... An exception flew by ... " + e.getClass());

		} // yrt
		
	} // testReadHiThresRegister()


	/**
	 * Test method for {@link adc.ads1115.ADS_1115#readLoThresRegister()}.
	 */
	@Test
	void testReadLoThresRegister() {
		logger.info("testReadLoThresRegister()");
		
		try(
				
				MockedConstruction<Ads1115LoThresholdRegister> constructedLoThrReg = mockConstruction(Ads1115LoThresholdRegister.class);
				
			) {
				
				cut = new ADS_1115(I2C_BUS, I2C_ADDR);

				assertTrue(constructedLoThrReg != null);
				assertEquals(1, constructedLoThrReg.constructed().size());
				
				Ads1115LoThresholdRegister loThrRegisterMock = (Ads1115LoThresholdRegister) constructedLoThrReg.constructed().get(0);
				assertTrue(loThrRegisterMock != null);
				
				when(loThrRegisterMock.read(pga)).thenReturn(42.42d);

				double result = cut.readLoThresRegister(pga);
				
				assertEquals(42.42d,  result);
				verify(loThrRegisterMock,  times(1)).read(pga);
				
		}
		catch (I2CErrorException e) {
			
			fail("Ooops ... An exception flew by ... " + e.getClass());

		} // yrt
		
	} // testReadLoThresRegister()


	/**
	 * Test method for {@link adc.ads1115.ADS_1115#writeHiThresRegister(float)}.
	 */
	@Test
	void testWriteHiThresRegister() {
		logger.info("testWriteHiThresRegister()");
		
		try(
				
			MockedConstruction<Ads1115HiThresholdRegister> constructedHiThrReg = mockConstruction(Ads1115HiThresholdRegister.class);
			
		) {
			
			cut = new ADS_1115(I2C_BUS, I2C_ADDR);

			assertTrue(constructedHiThrReg != null);
			assertEquals(1, constructedHiThrReg.constructed().size());
			
			Ads1115HiThresholdRegister hiThrRegisterMock = (Ads1115HiThresholdRegister) constructedHiThrReg.constructed().get(0);
			assertTrue(hiThrRegisterMock != null);

			double value = 42.21d;
			
			cut.writeHiThresRegister(value, pga);
			
			verify(hiThrRegisterMock,  times(1)).write(value, pga);
				
		}
		catch (I2CErrorException e) {
			
			fail("Ooops ... An exception flew by ... " + e.getClass());

		} // yrt
								
	} // testWriteHiThresRegister()


	/**
	 * Test method for {@link adc.ads1115.ADS_1115#writeLoThresRegister(float)}.
	 */
	@Test
	void testWriteLoThresRegister() {
		logger.info("testWriteLoThresRegister()");
		
		try(
				
			MockedConstruction<Ads1115LoThresholdRegister> constructedLoThrReg = mockConstruction(Ads1115LoThresholdRegister.class);
			
		) {
			
			cut = new ADS_1115(I2C_BUS, I2C_ADDR);

			assertTrue(constructedLoThrReg != null);
			assertEquals(1, constructedLoThrReg.constructed().size());
			
			Ads1115LoThresholdRegister loThrRegisterMock = (Ads1115LoThresholdRegister) constructedLoThrReg.constructed().get(0);
			assertTrue(loThrRegisterMock != null);
			
			when(loThrRegisterMock.read(pga)).thenReturn(42.42d);

			double value = 42.21d;
			
			cut.writeLoThresRegister(value, pga);
			
			verify(loThrRegisterMock,  times(1)).write(value, pga);
				
		}
		catch (I2CErrorException e) {
			
			fail("Ooops ... An exception flew by ... " + e.getClass());

		} // yrt

	} // testWriteLoThresRegister()


//	/**
//	 * Test method for {@link adc.ads1115.ADS_1115#setOs(adc.ads1115.configParameter.OS_Wo)}.
//	 */
//	@Test
//	void testSetOs_Wo() {
//		logger.info("testSetOs_Wo()");
//		
//		try(
//				
//			MockedConstruction<Ads1115ConfigRegister> constructedConfReg = mockConstruction(Ads1115ConfigRegister.class);
//			
//		) {
//			
//			cut = new TestADS_1115(I2C_BUS, I2C_ADDR);
//
//			assertTrue(constructedConfReg != null);
//			assertEquals(1, constructedConfReg.constructed().size());
//			
//			Ads1115ConfigRegister configRegisterMock = (Ads1115ConfigRegister) constructedConfReg.constructed().get(0);
//			assertTrue(configRegisterMock != null);
//			
//			IllegalArgumentException thrown =
//					assertThrows(IllegalArgumentException.class, () -> cut.setOs(null));
//			assertEquals("aOs can't be null.", thrown.getMessage());
//			
//			OS_Wo osWo = OS_Wo.START_SINGLE_CONVERSION;
//			
//			cut.setOs(osWo);
//			
//			verify(configRegisterMock, times(1)).setOs(osWo);
//			
//		} // yrt
//		catch(I2CErrorException e) {
//			
//			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
//			fail("I2CErrorException thrown");
//			
//		} // yrt
//			
//	} // testSetOs_Wo()


	/**
	 * Test method for {@link adc.ads1115.ADS_1115#setMode(adc.ads1115.configParameter.MODE)}.
	 */
	@Test
	void testSetMode() {
		logger.info("testSetMode()");
		
		try(
				
			MockedConstruction<Ads1115ConfigRegister> constructedConfReg = mockConstruction(Ads1115ConfigRegister.class);
			
		) {
			
			cut = new TestADS_1115(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Ads1115ConfigRegister configRegisterMock = (Ads1115ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			IllegalArgumentException thrown =
					assertThrows(IllegalArgumentException.class, () -> cut.setMode(null));
			assertEquals("aMode can't be null.", thrown.getMessage());
			
			MODE mode = MODE.SINGLE_SHOT;
			
			cut.setMode(mode);
			
			verify(configRegisterMock, times(1)).setMode(mode);
			
		} // yrt
		catch(I2CErrorException e) {
			
			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");
			
		} // yrt
		
	} // testSetMode()


	/**
	 * Test method for {@link adc.ads1115.ADS_1115#setDr(adc.ads1115.configParameter.DR)}.
	 */
	@Test
	void testSetDr() {
		logger.info("testSetDr()");
		
		try(
				
			MockedConstruction<Ads1115ConfigRegister> constructedConfReg = mockConstruction(Ads1115ConfigRegister.class);
			
		) {
			
			cut = new TestADS_1115(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Ads1115ConfigRegister configRegisterMock = (Ads1115ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			IllegalArgumentException thrown =
					assertThrows(IllegalArgumentException.class, () -> cut.setDr(null));
			assertEquals("aDr can't be null.", thrown.getMessage());
			
			DR dr = DR.SPS_128;
			
			cut.setDr(dr);
			
			verify(configRegisterMock, times(1)).setDr(dr);
			
		} // yrt
		catch(I2CErrorException e) {
			
			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");
			
		} // yrt
		
	} // testSetDr()


	/**
	 * Test method for {@link adc.ads1115.ADS_1115#setCompQue(adc.ads1115.configParameter.COMP_QUE)}.
	 */
	@Test
	void testSetCompQue() {
		logger.info("testSetCompQue()");
		
		try(
				
			MockedConstruction<Ads1115ConfigRegister> constructedConfReg = mockConstruction(Ads1115ConfigRegister.class);
			
		) {
			
			cut = new TestADS_1115(I2C_BUS, I2C_ADDR);

			assertTrue(constructedConfReg != null);
			assertEquals(1, constructedConfReg.constructed().size());
			
			Ads1115ConfigRegister configRegisterMock = (Ads1115ConfigRegister) constructedConfReg.constructed().get(0);
			assertTrue(configRegisterMock != null);
			
			IllegalArgumentException thrown =
					assertThrows(IllegalArgumentException.class, () -> cut.setCompQue(null));
			assertEquals("aCompQue can't be null.", thrown.getMessage());
			
			COMP_QUE compQue = COMP_QUE.DISABLE_COMPARATOR;
			
			cut.setCompQue(compQue);
			
			verify(configRegisterMock, times(1)).setCompQue(compQue);
			
		} // yrt
		catch(I2CErrorException e) {
			
			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");
			
		} // yrt
		
	} // testSetCompQue()

	
} // ssalc
