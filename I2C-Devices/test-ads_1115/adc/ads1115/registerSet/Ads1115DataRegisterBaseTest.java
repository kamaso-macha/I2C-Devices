/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : Ads1115DataRegisterBaseTest.java
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
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

import adc.ads1115.configParameter.Ads1115RegisterMap;
import adc.ads1115.configParameter.PGA;
import i2cDevice.I2CErrorException;
import i2cDevice.register.DataType;


class Ads1115DataRegisterBaseTest {

	private final Logger logger = LoggerFactory.getLogger(Ads1115DataRegisterBaseTest.class.getName());

	private class TestAds1115DataRegisterBase extends Ads1115DataRegisterBase {

		public TestAds1115DataRegisterBase(I2C aI2cDevice, Ads1115RegisterMap aAddressPointer, DataType aDataType) {
			super(aI2cDevice, aAddressPointer, aDataType);
		}
		
		@SuppressWarnings("unused")
		public void setRawBytes(byte[] aRaw) { raw = aRaw; }
		public byte[] getRawBytes() { return raw; }

	} // ssalc
	
	final Ads1115RegisterMap register = Ads1115RegisterMap.CONVERSION;
	final int regAdr = register.value;
	final DataType dataType = DataType.WORD;
	final int dataSize = dataType.getSize();
	
	MockI2C i2cMock;

	private byte[] rawBytes;
	
	TestAds1115DataRegisterBase cut;
	
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

		cut = new TestAds1115DataRegisterBase(i2cMock, register, dataType);

		rawBytes = cut.getRawBytes();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115DataRegisterBase#Ads1115DataRegisterBase(com.pi4j.io.i2c.I2C, adc.ads1115.configParameter.Ads1115RegisterMap, i2cDevice.register.DataType)}.
	 */
	@Test
	void testAds1115DataRegisterBase() {
		logger.trace("testAds1115DataRegisterBase()");
		
		/*
		 * 		Nothing to thest here!
		 */
		
		assertTrue(true);
		
	} // testAds1115DataRegisterBase()
	

	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115DataRegisterBase#setPga(adc.ads1115.configParameter.PGA)}.
	 */
	@Test
	void testRead() {
		logger.trace("testRead()");
		
		int[] values = new int[] {
			0x7FFF,
			0x0001,
			0x0000,
			0xFFFF,
			0x8000
		};
		
		double reference;
		double result;
		
		try {

			when(i2cMock.writeRegister(regAdr, rawBytes, 0, dataSize))
				.thenReturn(dataSize + 1)
				;

			when(i2cMock.readRegister(regAdr, rawBytes, 0, dataSize))
			.thenReturn(dataSize)
			;

			for(PGA pga : PGA.values()) {
			
				for(int n = 0; n < values.length; n++) {

					rawBytes = cut.getRawBytes();
					rawBytes[0] = (byte) (values[n] >> 8);
					rawBytes[1] = (byte) (values[n] & 0x00FF);
					
					result = cut.read(pga);
					
					if(values[n] < 0x8000) {
						reference = values[n] * pga.lsbSize;
					}
					else {
						reference = -pga.lsbSize * ((~values[n] & 0x0FFFF) + 1);
					}

					logger.info(String.format("values[n] = 0x%04X, rawBytes[0] = 0x%02X, rawBytes[1] = 0x%02X, result = %.7f, reference = %.7f", 
						values[n], rawBytes[0], rawBytes[1], result, reference));

					assertEquals(reference, result);
					
				} // rof n
				
			} // rof PGA

		} catch (I2CErrorException e) {
			
			fail("Ooops ... An exception flew by ... " + e.getClass());
			
		} // yrt
		
	} // testRead()


	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115DataRegisterBase#write(float)}.
	 * 
	 * Tests code transition points described in section 9.5.4, table 5 of the Ti data sheet
	 * 
	 */
	@Test
	void testWrite1() {
		logger.trace("testWrite()");
		
		int[] values = new int[] {
				0x7FFF,
				0x0001,
				0x0000,
				0xFFFF,
				0x8000
		};
		
		double reference;
		double result;
		
		try {

			when(i2cMock.writeRegister(regAdr, rawBytes, 0, dataSize))
				.thenReturn(dataSize + 1)
				;

			when(i2cMock.readRegister(regAdr, rawBytes, 0, dataSize))
			.thenReturn(dataSize)
			;

			for(PGA pga : PGA.values()) {
			
				for(int n = 0; n < values.length; n++) {

					if(values[n] < 0x8000) {
						reference = values[n] * pga.lsbSize;
					}
					else {
						reference = -pga.lsbSize * ((~values[n] & 0x0FFFF) + 1);
					}

					cut.write(reference, pga);
					
					result = cut.read(pga);
					
					logger.info(String.format("values[n] = 0x%04X, rawBytes[0] = 0x%02X, rawBytes[1] = 0x%02X, result = %.7f, reference = %.7f", 
						values[n], rawBytes[0], rawBytes[1], result, reference));

					assertEquals(reference, result);
					
				} // rof n
				
			} // rof PGA

		} catch (I2CErrorException e) {
			
			fail("Ooops ... An exception flew by ... " + e.getClass());
			
		} // yrt
		
	} // testWrite()


	/**
	 * Test method for {@link adc.ads1115.registerSet.Ads1115DataRegisterBase#setPga(final PGA aPga)}.
	 */
	@Test
	void testSetPga() {
		logger.trace("testSetPga()");

		IllegalArgumentException thrown = 
			assertThrows(IllegalArgumentException.class,  () -> cut.write(42.42d,  null));
		assertEquals("aPga can't be null.", thrown.getMessage());
		
	} // testSetPga()


} // ssalc
