/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : AddressableConfigRegisterTest.java
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


package i2cDevice.config;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import i2cDevice.I2CErrorException;
import i2cDevice.register.DataType;

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

class AddressableConfigRegisterTest {

	private final Logger logger = LoggerFactory.getLogger(AddressableConfigRegisterTest.class.getName());

	
	private class TestConfig implements ConfigParameter {
		
		byte value;
		private int lsbPos;
		private int nbrOfBits;

		@Override public int getSetMask()		{ return _getSetMask();	}
		@Override public int getClearMask()		{ return _getClearMask();	}
			
		public void putParameter(int aSize, int aLsbPos, int aValue) {	
			nbrOfBits	= aSize;
			lsbPos	= aLsbPos;
			value	= (byte) aValue;
		} // putParameter()
		
		
		public byte getRef0x00(int aStep) { return (byte) (value << aStep); }		
		public byte getRef0xFF(int aStep) { return (byte) (_getClearMask() | getRef0x00(aStep)); }
		public byte _getSetMask() { return (byte) (value << lsbPos); }

		public byte _getClearMask() { 
			int clearMask = 0;
			for(int n = 0; n < nbrOfBits; n++) {
				clearMask = clearMask << 1;
				clearMask += 1;
				
			} // rof
			return (byte) ((clearMask << lsbPos) ^ 0xFF);
		} // getClearMask()
		
	} // ssalc
	
	
	private class AddressableConfigRegisterImpl extends AddressableConfigRegister { 

		public AddressableConfigRegisterImpl(final I2C aI2cDevice, final int aRegisterAddress, final DataType aDataType) {
			super(aI2cDevice, aRegisterAddress, aDataType);
			
		}
		
		// helper method for testing
		public int getRegisterAddress() { return registerAddress; }
		public byte[] getRawBytes() { return raw; }
		public void clearConfig4Test() { config = 0; }
		public void setConfig4Test(int aConfig) { config = (byte) aConfig; }
			
	} // ssalc
		
	
	private int registerAddress;
	private DataType dataType;
	private TestConfig testConfig;
	private AddressableConfigRegisterImpl cut;
	
	private static MockI2C i2cDevice;
	
	
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
		
		testConfig = new TestConfig();

		i2cDevice = mock(MockI2C.class);
		registerAddress = 0x0042;
		
	} // setUp()()

	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link i2cDevice.config.AddressableConfigRegister#AddressableConfigRegister(com.pi4j.io.i2c.I2C, byte, i2cDevice.register.DataType)}.
	 */
	@Test
	void testAddressableConfigRegister() {
		logger.info("testAddressableConfigRegister()");
		
		cut = new AddressableConfigRegisterImpl(i2cDevice, (byte) 0, DataType.BYTE);
		assertEquals(0x00, cut.getRegisterAddress());
		
		cut = new AddressableConfigRegisterImpl(i2cDevice, (byte) 0xFF, DataType.BYTE);
		assertEquals(0xFF, cut.getRegisterAddress());

	} // testAddressableConfigRegister()
	

	/**
	 * Test method for {@link i2cDevice.config.AddressableConfigRegister#getConfig()}.
	 */
	@Test
	void testGetConfig() {
		logger.info("testGetConfig()");
		
		/*
		 * 		Nothing to do!
		 * 
		 * 		It's implicitly tested by testSetConfig
		 */
		
		assertTrue(true);
		
	} // testGetConfig()
	

	/**
	 * Test method for {@link i2cDevice.config.AddressableConfigRegister#setConfig(i2cDevice.config.ConfigParameter)}.
	 */
	@Test
	void testSetConfig() {
		logger.info("testSetConfig()");
		
		dataType = DataType.BYTE;		
		cut = new AddressableConfigRegisterImpl(i2cDevice, registerAddress, dataType);
		runTest();
		
		dataType = DataType.WORD;		
		cut = new AddressableConfigRegisterImpl(i2cDevice, registerAddress, dataType);
		runTest();
		
	} // testSetConfig()

	/**
	 * 
	 */
	protected void runTest() {
		
		int NO_OF_BITS = dataType.getSize();
		
		byte[] rawBytes = cut.getRawBytes();
		
		int maxSize = NO_OF_BITS;
		int maxVal;
		int maxPos;
		
		int config;
		byte ref;
		int mask;
		
		when(i2cDevice.writeRegister(registerAddress, rawBytes, 0, dataType.getSize()))
			.thenReturn(NO_OF_BITS + 1);
		
		when(i2cDevice.readRegister(registerAddress, rawBytes, 0, dataType.getSize()))
		.thenReturn(NO_OF_BITS);
	
		try {
		
			for(int size = 2; size <= maxSize; size++) {
				logger.info(String.format("size: %d, max: %d", size, maxSize));
			
				// position
				
				maxPos = NO_OF_BITS - size;
				
				for(int pos = 0; pos <= maxPos; pos++) {
					logger.info(String.format("pos: %d, max: %d", pos, maxPos));
					
					// value
					
					maxVal = (int)Math.pow(2, size);
					
					for(int val = 0; val < maxVal; val++) {
						logger.info(String.format("val: %d, max: %d", val, maxVal));
	
						testConfig.putParameter(size, pos, val);
						mask   = testConfig.getClearMask();
			
						// test with all other bits cleared
						cut.clearConfig4Test();
						cut.setConfig(testConfig);
	
						config = cut.getConfig();
						ref    = testConfig.getRef0x00(pos);
	
						logger.info(String.format("val = 0x%02X, ref = 0x%02X, config = 0x%02X, clMask = 0x%02X", 
								((byte) (val & 0x00FF)), ref, config, mask));
	
						assertEquals(ref, config, 
							String.format("size: %d, pos: %d, val: %d - got: 0x%02X, ref: 0x%02X", 
								size,
								pos,
								val,
								config,
								ref
							));
	
						
						// test with all other bits set
						cut.setConfig4Test(0x00FF);
						cut.setConfig(testConfig);
	
						config = cut.getConfig();
						ref = testConfig.getRef0xFF(pos);
	
						logger.info(String.format("val = 0x%02X, ref = 0x%02X, config = 0x%02X, clMask = 0x%02X", 
								((byte) (val & 0x00FF)), ref, config, mask));
						
						assertEquals((byte)ref, (byte)config, 
							String.format("size: %d, pos: %d, val: %d - got: 0x%02X, ref: 0x%02X", 
								size,
								pos,
								val,
								config,
								ref
							));
								
					} // rof value
					
				} // rof position
			
			} // rof size
			
		}
		catch(I2CErrorException e) {
			
			fail("Ooooops ... An exception flew by ... " + e.getMessage());
			
		} // yrt
	
	} // runTest()
	
	
} // ssalc
