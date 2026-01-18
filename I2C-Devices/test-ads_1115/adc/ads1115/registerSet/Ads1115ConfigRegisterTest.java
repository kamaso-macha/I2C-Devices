/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : Ads1115ConfigRegisterTest.java
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


package adc.ads1115.registerSet;

import static org.junit.Assert.fail;
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

import adc.ads1115.configParameter.Ads1115RegisterMap;
import adc.ads1115.configParameter.COMP_LAT;
import adc.ads1115.configParameter.COMP_MODE;
import adc.ads1115.configParameter.COMP_POL;
import adc.ads1115.configParameter.COMP_QUE;
import adc.ads1115.configParameter.DR;
import adc.ads1115.configParameter.MODE;
import adc.ads1115.configParameter.MUX;
import adc.ads1115.configParameter.OS_Wo;
import adc.ads1115.configParameter.PGA;
import i2cDevice.I2CErrorException;
import i2cDevice.register.DataType;


class Ads1115ConfigRegisterTest {

	private final Logger logger = LoggerFactory.getLogger(Ads1115ConfigRegisterTest.class.getName());

	private class TestAds1115ConfigRegister extends Ads1115ConfigRegister {

		public TestAds1115ConfigRegister(I2C aI2cDevice) throws I2CErrorException {
			super(aI2cDevice);
		}

		public byte[] getRawBytes() { return raw; }
		public void clearConfig4Test() { config = 0; }
		public void setConfig4Test(int aConfig) { config = aConfig; }
		
	} // ssalc
	
	
	private TestAds1115ConfigRegister cut;
	
	private MockI2C i2cMock;
	
	final int regAdr = Ads1115RegisterMap.CONFIG.value;	// 0b01
	final DataType dataType = DataType.WORD;
	final int dataSize = dataType.getSize();

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
		assertTrue(i2cMock != null);

		inOrder(i2cMock);
		
		cut = new TestAds1115ConfigRegister(i2cMock);

		rawBytes = cut.getRawBytes();
		
	} // setUp()

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115ConfigRegister#Ads1115ConfigRegister(com.pi4j.io.i2c.I2C)}.
	 */
	@Test
	void testAds1115ConfigRegister() {
		logger.info("testAds1115ConfigRegister()");

		
		/*
		 * 	Nothing to test!
		 */
		
		assertTrue(true);
		
	} // testAds1115ConfigRegister()
	

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115ConfigRegister#getDr()}.
	 */
	@Test
	void testGetDr() {
		logger.info("testGetDr()");
		
		try {

			int[] testValues = new int[] {
				
					0x0000,
					0x0020,
					0x0040,
					0x0060,
					0x0080,
					0x00A0,
					0x00C0,
					0x00E0,
				
			};
			
			DR result;
			
			when(i2cMock.readRegister(regAdr, rawBytes, 0, dataSize))
				.thenReturn(dataSize)
				;
			
			
			for(int n = 0; n < DR.values().length; n++) {
				
				cut.setConfig4Test(testValues[n]);
				
				result = cut.getDr();
				
				assertEquals(DR.value2type(n), result);

			} // rof
			
		}
		catch (I2CErrorException e) {
			
			fail("Ooops ... An exception flew by ... " + e.getClass());

		} // yrt
			
	} // testGetDr()
	

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115ConfigRegister#getMode()}.
	 */
	@Test
	void testGetMode() {
		logger.info("testGetMode()");
		
		try {

			int[] testValues = new int[] {
					
					0x0000,
					0x0100,
				
			};

			MODE result;
			
			when(i2cMock.readRegister(regAdr, rawBytes, 0, dataSize))
				.thenReturn(dataSize)
				;
			
			
			for(int n = 0; n < MODE.values().length; n++) {
				
				cut.setConfig4Test(testValues[n]);
				
				result = cut.getMode();
				
				assertEquals(MODE.value2type(n), result);

			} // rof
			
		}
		catch (I2CErrorException e) {
			
			fail("Ooops ... An exception flew by ... " + e.getClass());

		} // yrt
			
	} // testGetMode()
	

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115ConfigRegister#getOs()}.
	 */
	@Test
	void testGetOs() {
		logger.info("testGetOs()");
		
		try {
			
			when(i2cMock.readRegister(regAdr, rawBytes, 0, dataSize))
				.thenReturn(dataSize)
				;
			
			cut.getOs();
			
			verify(i2cMock, times(1)).readRegister(regAdr, rawBytes, 0, dataSize);
			
		}
		catch (I2CErrorException e) {
			
			fail("Ooops ... An exception flew by ... " + e.getClass());

		} // yrt
			
	} // testGetOs()
	

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115ConfigRegister#setOs(adc.ads1115.configParameter.OS_Wo)}.
	 */
	@Test
	void testSetOs() {
		logger.info("testSetOs()");
		
		when(i2cMock.writeRegister(regAdr, rawBytes, 0, dataSize))
			.thenReturn(dataSize + 1)
			;
	
		when(i2cMock.readRegister(regAdr, rawBytes, 0, dataSize))
			.thenReturn(dataSize)
			;

		try {

			cut.clearConfig4Test();
			
			cut.setOs(OS_Wo.START_SINGLE_CONVERSION);
			
			config = cut.getConfig();
			
			logger.info(String.format("config = 0x%04X, setMask = 0x%04X", 
					config, OS_Wo.START_SINGLE_CONVERSION.getSetMask()));

			assertEquals(OS_Wo.START_SINGLE_CONVERSION.getSetMask(), config);
			
			verify(i2cMock, timeout(1)).writeRegister(regAdr, rawBytes, 0, dataSize);
			
		}
		catch(I2CErrorException e) {
			
			fail("Ooooops ... An exception flew by ... " + e.getMessage());
			
		} // yrt

	} // testSetOs()
	

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115ConfigRegister#setMux(adc.ads1115.configParameter.MUX)}.
	 */
	@Test
	void testSetMux() {
		logger.info("testSetMux()");
		
		MUX[] muxValues = new MUX[] {
			MUX.DIFF_0_1,
			MUX.DIFF_0_3,
			MUX.DIFF_1_3,
			MUX.DIFF_2_3,
			MUX.SINGLE_0,
			MUX.SINGLE_1,
			MUX.SINGLE_2,
			MUX.SINGLE_3
		};

		when(i2cMock.writeRegister(regAdr, rawBytes, 0, dataSize))
		.thenReturn(dataSize + 1)
		;

		when(i2cMock.readRegister(regAdr, rawBytes, 0, dataSize))
		.thenReturn(dataSize)
		;

		try {
			
			for(int n = 0; n < muxValues.length; n++) {
			
				cut.clearConfig4Test();			
				cut.setMux(muxValues[n]);
				
				config = cut.getConfig();
				
				assertEquals(muxValues[n].getSetMask(), config);
				
			} // rof

			verify(i2cMock, times(8)).writeRegister(regAdr, rawBytes, 0, dataSize);
			
		} 
		catch(I2CErrorException e) {
			
			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");
			
		} // yrt

	} // testSetMux()
	

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115ConfigRegister#setPga(adc.ads1115.configParameter.PGA)}.
	 */
	@Test
	void testSetPga() {
		logger.info("testSetPga()");

		PGA[] pgaValues = new PGA[] {
				PGA.FSR_6_144V,
				PGA.FSR_4_096V,
				PGA.FSR_2_048V,
				PGA.FSR_1_024V,
				PGA.FSR_0_512V,
				PGA.FSR_0_256V,
		};

		when(i2cMock.writeRegister(regAdr, rawBytes, 0, dataSize))
		.thenReturn(dataSize + 1)
		;

		when(i2cMock.readRegister(regAdr, rawBytes, 0, dataSize))
		.thenReturn(dataSize)
		;

		try {

			for(int n = 0; n < pgaValues.length; n++) {
				logger.info("n = {}, pgaValue = {}", n, pgaValues[n]);

				cut.clearConfig4Test();			
				cut.setPga(pgaValues[n]);

				config = cut.getConfig();

				logger.info(String.format("n = %d, pgaValue = %s, config = 0x%04X, setMask = 0x%04X", 
						n, pgaValues[n], config, pgaValues[n].getSetMask()));

				assertEquals(pgaValues[n].getSetMask(), config);

			} // rof

			verify(i2cMock, times(6)).writeRegister(regAdr, rawBytes, 0, dataSize);

		} 
		catch(I2CErrorException e) {

			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");

		} // yrt

	} // testSetPga()
	

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115ConfigRegister#setMode(adc.ads1115.configParameter.MODE)}.
	 */
	@Test
	void testSetMode() {
		logger.info("testSetMode()");

		MODE[] modeValues = new MODE[] {
				MODE.CONTINUOUS,
				MODE.SINGLE_SHOT
		};

		when(i2cMock.writeRegister(regAdr, rawBytes, 0, dataSize))
		.thenReturn(dataSize + 1)
		;

		when(i2cMock.readRegister(regAdr, rawBytes, 0, dataSize))
		.thenReturn(dataSize)
		;

		try {

			for(int n = 0; n < modeValues.length; n++) {

				cut.clearConfig4Test();			
				cut.setMode(modeValues[n]);

				config = cut.getConfig();

				logger.info(String.format("n = %d, modeValues = %s, config = 0x%04X, setMask = 0x%04X", 
						n, modeValues[n], config, modeValues[n].getSetMask()));

				assertEquals(modeValues[n].getSetMask(), config);

			} // rof

			verify(i2cMock, times(2)).writeRegister(regAdr, rawBytes, 0, dataSize);

		}
		catch(I2CErrorException e) {

			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");

		} // yrt

	} // testSetMode()
	

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115ConfigRegister#setDr(adc.ads1115.configParameter.DR)}.
	 */
	@Test
	void testSetDr() {
		logger.info("testSetDr()");
		
		DR[] values = new DR[] {
				DR.SPS_8,
				DR.SPS_16,
				DR.SPS_32,
				DR.SPS_64,
				DR.SPS_128,
				DR.SPS_250,
				DR.SPS_475,
				DR.SPS_860,
		};

		when(i2cMock.writeRegister(regAdr, rawBytes, 0, dataSize))
			.thenReturn(dataSize + 1)
			;

		when(i2cMock.readRegister(regAdr, rawBytes, 0, dataSize))
			.thenReturn(dataSize)
			;

		try {

			for(int n = 0; n < values.length; n++) {
				
				logger.info("n = {}, dr = {}  -------------------------------------------------------------------------", 
					n, values[n]);

				cut.clearConfig4Test();			
				cut.setDr(values[n]);

				config = cut.getConfig();

				logger.info(String.format("n = %d, values = %s, config = 0x%04X, setMask = 0x%04X", 
						n, values[n], config, values[n].getSetMask()));

				assertEquals(values[n].getSetMask(), config);

			} // rof

			verify(i2cMock, times(8)).writeRegister(regAdr, rawBytes, 0, dataSize);


		}
		catch(I2CErrorException e) {

			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");

		} // yrt
		
	} // testSetDr()
	

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115ConfigRegister#setCompMode(adc.ads1115.configParameter.COMP_MODE)}.
	 */
	@Test
	void testSetCompMode() {
		logger.info("testSetCompMode()");

		COMP_MODE[] values = new COMP_MODE[] {
			COMP_MODE.TRADITIONAL,
			COMP_MODE.WINDOW_COMP
		};

		when(i2cMock.writeRegister(regAdr, rawBytes, 0, dataSize))
		.thenReturn(dataSize + 1)
		;
		
		when(i2cMock.readRegister(regAdr, rawBytes, 0, dataSize))
		.thenReturn(dataSize)
		;

		try {

			for(int n = 0; n < values.length; n++) {
				
				cut.clearConfig4Test();			
				cut.setCompMode(values[n]);
				
				config = cut.getConfig();
				
				logger.info(String.format("n = %d, values = %s, config = 0x%04X, setMask = 0x%04X", 
						n, values[n], config, values[n].getSetMask()));
					
				assertEquals(values[n].getSetMask(), config);
				
			} // rof

			verify(i2cMock, times(2)).writeRegister(regAdr, rawBytes, 0, dataSize);

		}
		catch(I2CErrorException e) {
			
			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");
			
		} // yrt

	} // testSetCompMode()
	

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115ConfigRegister#setCompPol(adc.ads1115.configParameter.COMP_POL)}.
	 */
	@Test
	void testSetCompPol() {
		logger.info("testSetCompPol()");

		COMP_POL[] values = new COMP_POL[] {
			COMP_POL.ACTIVE_LOW,
			COMP_POL.ACTIVE_HIGH
		};

		when(i2cMock.writeRegister(regAdr, rawBytes, 0, dataSize))
		.thenReturn(dataSize + 1)
		;
		
		when(i2cMock.readRegister(regAdr, rawBytes, 0, dataSize))
		.thenReturn(dataSize)
		;

		try {

			for(int n = 0; n < values.length; n++) {
				
				cut.clearConfig4Test();			
				cut.setCompPol(values[n]);
				
				config = cut.getConfig();
				
				logger.info(String.format("n = %d, values = %s, config = 0x%04X, setMask = 0x%04X", 
						n, values[n], config, values[n].getSetMask()));
					
				assertEquals(values[n].getSetMask(), config);
				
			} // rof

			verify(i2cMock, times(2)).writeRegister(regAdr, rawBytes, 0, dataSize);

		}
		catch(I2CErrorException e) {
			
			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");
			
		} // yrt

	} // testSetCompPol()
	

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115ConfigRegister#setCompLat(adc.ads1115.configParameter.COMP_LAT)}.
	 */
	@Test
	void testSetCompLat() {
		logger.info("testSetCompLat()");

		COMP_LAT[] values = new COMP_LAT[] {
			COMP_LAT.NON_LATCHING,
			COMP_LAT.LATCHNG
		};

		when(i2cMock.writeRegister(regAdr, rawBytes, 0, dataSize))
		.thenReturn(dataSize + 1)
		;
		
		when(i2cMock.readRegister(regAdr, rawBytes, 0, dataSize))
		.thenReturn(dataSize)
		;

		try {

			for(int n = 0; n < values.length; n++) {
				
				cut.clearConfig4Test();			
				cut.setCompLat(values[n]);
				
				config = cut.getConfig();
				
				logger.info(String.format("n = %d, values = %s, config = 0x%04X, setMask = 0x%04X", 
						n, values[n], config, values[n].getSetMask()));
					
				assertEquals(values[n].getSetMask(), config);
				
			} // rof

			verify(i2cMock, times(2)).writeRegister(regAdr, rawBytes, 0, dataSize);

		}
		catch(I2CErrorException e) {
			
			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");
			
		} // yrt

	} // testSetCompLat()
	

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115ConfigRegister#setCompQue(adc.ads1115.configParameter.COMP_QUE)}.
	 */
	@Test
	void testSetCompQue() {
		logger.info("testSetCompQue()");

		COMP_QUE[] values = new COMP_QUE[] {
			COMP_QUE.ASSERT_AFTER_ONE,
			COMP_QUE.ASSERT_AFTER_TWO,
			COMP_QUE.ASSERT_AFTER_FOUR,
			COMP_QUE.DISABLE_COMPARATOR
		};

		when(i2cMock.writeRegister(regAdr, rawBytes, 0, dataSize))
		.thenReturn(dataSize + 1)
		;

		when(i2cMock.readRegister(regAdr, rawBytes, 0, dataSize))
		.thenReturn(dataSize)
		;

		try {

			for(int n = 0; n < values.length; n++) {

				cut.clearConfig4Test();			
				cut.setCompQue(values[n]);

				config = cut.getConfig();

				logger.info(String.format("n = %d, values = %s, config = 0x%04X, setMask = 0x%04X", 
						n, values[n], config, values[n].getSetMask()));

				assertEquals(values[n].getSetMask(), config);

			} // rof

			verify(i2cMock, times(4)).writeRegister(regAdr, rawBytes, 0, dataSize);

		}
		catch(I2CErrorException e) {

			logger.error("Ooooops ... An exception flew by ... " + e.getMessage());
			fail("I2CErrorException thrown");

		} // yrt

	} // testSetCompQue()
	

} // ssalc
