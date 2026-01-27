/**
 *
 * **********************************************************************
 * PROJECT       : I2C-Devices
 * FILENAME      : PCF_8574Test.java
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


package gpio.pcf8574;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.BIT_STATE;
import gpio.pcf8574.impl.BIT_NUMBER;
import gpio.pcf8574.impl.PCF_8574_Impl;
import i2cDevice.I2CErrorException;

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
// Created at 2026-01-26 09:43:56

class PCF_8574_ImplTest {

	private final Logger logger = LoggerFactory.getLogger(PCF_8574_ImplTest.class.getName());

	
	private static final int I2C_BUS	= 2;
	private static final int I2C_ADDR	= 0x42;
	
	
	private class TestPCF_8574_Impl extends PCF_8574_Impl {

		public TestPCF_8574_Impl(int aI2cBusNbr, int aI2cAddress) {
			super(aI2cBusNbr, aI2cAddress);
		}
		
		
		public int computeMask(final BIT_NUMBER aBitNumber) {
			return super.computeMask(aBitNumber);
		}
		
	} // ssalc
		
	private TestPCF_8574_Impl cut;
	
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
	} // setUp()
	

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

//	/**
//	 * Test method for {@link gpio.pcf8574.impl.PCF_8574_Impl#PCF_8574(int, int)}.
//	 */
//	@Test
//	void testPCF_8574() {
//		logger.info("testPCF_8574()");
//		
//		// error handling
//		
//		IllegalArgumentException thrown;
//		
//		thrown = assertThrows(IllegalArgumentException.class, () -> new PCF_8574_Impl(I2C_BUS, 0x1F));
//		assertEquals("aI2cAddress is out of range 0x20 .. 0x27.", thrown.getMessage());
//		
//		assertDoesNotThrow(() -> new PCF_8574_Impl(I2C_BUS, 0x20));
//		assertDoesNotThrow(() -> new PCF_8574_Impl(I2C_BUS, 0x27));
//		
//		thrown = assertThrows(IllegalArgumentException.class, () -> new PCF_8574_Impl(I2C_BUS, 0x28));
//		assertEquals("aI2cAddress is out of range 0x20 .. 0x27.", thrown.getMessage());
//		
//	} // testPCF_8574()
	

	/**
	 * Test method for {@link gpio.pcf8574.impl.PCF_8574_Impl#readBit(gpio.pcf8574.impl.BIT_NUMBER)}.
	 */
	@Test
	void testReadBit() {
		logger.info("testReadBit()");
		
		/*
		 * 		Nothing to do.
		 * 
		 * 		Test is implicitly done by testWriteBit()
		 */
		
		assertTrue(true);
		
	} // testReadBit()
	

	/**
	 * Test method for {@link gpio.pcf8574.impl.PCF_8574_Impl#readByte()}.
	 */
	@Test
	void testReadByte() {
		logger.info("testReadByte()");
		
		/*
		 * 		Nothing to do.
		 * 
		 * 		Test is implicitly done by testWriteByte()
		 */
		
		assertTrue(true);
		
	} // testReadByte()
	

	/**
	 * Test method for {@link gpio.pcf8574.impl.PCF_8574_Impl#writeBit(gpio.pcf8574.impl.BIT_NUMBER, common.BIT_STATE)}.
	 */
	@Test
	void testWriteBit() {
		logger.info("testWriteBit()");
		
		try {
				
			cut = new TestPCF_8574_Impl(I2C_BUS, I2C_ADDR);
			
			BIT_STATE result;
					
			// Using the MockI2C implementation of Pi4J project.
			
			
			for(BIT_NUMBER bitNumber : BIT_NUMBER.values()) {
				logger.info("bitNumber: {} ------------------------------------", bitNumber);
				
			
				// writeBit reads the current state of the PCF_8574 before manipulating the bit
				// so we want to set a defined state for the test
				cut.writeByte((byte) 0x00);
				
				// test 'HIGH'
				cut.writeBit(bitNumber, BIT_STATE.HIGH);
				
				result = cut.readBit(bitNumber);
				assertEquals(BIT_STATE.HIGH, result, "HIGH, bit " + bitNumber);
			
				
				// writeBit reads the current state of the PCF_8574 before manipulating the bit
				// so we want to set a defined state for the test
				cut.writeByte((byte) 0x00FF);
				
				// test 'HIGH'
				cut.writeBit(bitNumber, BIT_STATE.LOW);
				
				result = cut.readBit(bitNumber);
				assertEquals(BIT_STATE.LOW, result, "LOW bit " + bitNumber);
			
			} // rof
			
		} catch (I2CErrorException e) {

			fail("Ooooops ... An exception flew by ... " + e.getMessage());

		} // yrt
		
	} // testWriteBit()
	

	/**
	 * Test method for {@link gpio.pcf8574.impl.PCF_8574_Impl#writeByte(byte)}.
	 */
	@Test
	void testWriteByte() {
		logger.info("testWriteByte()");
		
		final byte DATA = (byte) 0x42;
		
		try {
				
			cut = new TestPCF_8574_Impl(I2C_BUS, I2C_ADDR);
					
			// Using the MockI2C implementation of Pi4J project.
			
			cut.writeByte(DATA);
			
			byte result = cut.readByte();
			
			assertEquals(result, DATA);
			
		} catch (I2CErrorException e) {

			fail("Ooooops ... An exception flew by ... " + e.getMessage());

		} // yrt
		
	} // testWriteByte()
	

	/**
	 * Test method for {@link gpio.pcf8574.impl.PCF_8574_Impl#computeMask(gpio.pcf8574.impl.BIT_NUMBER)}.
	 */
	@Test
	void testComputeMask() {
		logger.info("testComputeMask()");
				
		cut = new TestPCF_8574_Impl(I2C_BUS, I2C_ADDR);
				
		assertEquals(0b0000_0001, cut.computeMask(BIT_NUMBER.B0));
		assertEquals(0b0000_0010, cut.computeMask(BIT_NUMBER.B1));
		assertEquals(0b0000_0100, cut.computeMask(BIT_NUMBER.B2));
		assertEquals(0b0000_1000, cut.computeMask(BIT_NUMBER.B3));
		assertEquals(0b0001_0000, cut.computeMask(BIT_NUMBER.B4));
		assertEquals(0b0010_0000, cut.computeMask(BIT_NUMBER.B5));
		assertEquals(0b0100_0000, cut.computeMask(BIT_NUMBER.B6));
		assertEquals(0b1000_0000, cut.computeMask(BIT_NUMBER.B7));
		
	} // testComputeMask()
	

} // ssalc
