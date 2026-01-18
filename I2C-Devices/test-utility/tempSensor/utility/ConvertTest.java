/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : ConvertTest.java
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


package tempSensor.utility;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tempSensor.utility.Convert;

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
// Created at 2026-01-04 11:26:15

class ConvertTest {

	private final Logger logger = LoggerFactory.getLogger(ConvertTest.class.getName());

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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link tempSensor.utility.Convert#raw9Bit0p5ToFloat(byte[])}.
	 */
	@Test
	void testRaw9Bit0p5ToFloat() {
		logger.info("testRaw9Bit0p5ToFloat()");
		
		// 125.0°
		rawBytes = new byte[] { (byte) 0x7D, (byte) 0x00 };		
		assertEquals(125.0f, Convert.raw9Bit0p5ToFloat(rawBytes), "125.0°");
		
		
		// 124.5°
		rawBytes = new byte[] { (byte) 0x7C, (byte) 0x80 };
		assertEquals(124.5f, Convert.raw9Bit0p5ToFloat(rawBytes), "124.5°");
		
		
		// 124.0°
		rawBytes = new byte[] { (byte) 0x7C, (byte) 0x00 };
		assertEquals(124.0f, Convert.raw9Bit0p5ToFloat(rawBytes), "124.0°");
		
		
		// 1.0°
		rawBytes = new byte[] { (byte) 0x01, (byte) 0x00 };
		assertEquals(1.0f, Convert.raw9Bit0p5ToFloat(rawBytes), "1.0°");
		
		// 0.5°
		rawBytes = new byte[] { (byte) 0x00, (byte) 0x80 };
		assertEquals(0.5f, Convert.raw9Bit0p5ToFloat(rawBytes), "0.5°");
		
		
		// 0.0°
		rawBytes = new byte[] { (byte) 0x00, (byte) 0x00 };
		assertEquals(0.0f, Convert.raw9Bit0p5ToFloat(rawBytes), "0.0°");
		
		
		// -0.5°
		rawBytes = new byte[] { (byte) 0xFF, (byte) 0x80 };
		assertEquals(-0.5f, Convert.raw9Bit0p5ToFloat(rawBytes), "-0.5°");
		
		
		// -1.0°
		rawBytes = new byte[] { (byte) 0xFF, (byte) 0x00 };
		assertEquals(-1.0f, Convert.raw9Bit0p5ToFloat(rawBytes), "-1.0°");
		
		
		// -1.5°
		rawBytes = new byte[] { (byte) 0xFE, (byte) 0x80 };
		assertEquals(-1.5f, Convert.raw9Bit0p5ToFloat(rawBytes), "-1.5°");
		
		
		// -54.0°
		rawBytes = new byte[] { (byte) 0xCA, (byte) 0x00 };
		assertEquals(-54.0f, Convert.raw9Bit0p5ToFloat(rawBytes), "-54.0°");
		
		
		// -54.5°
		rawBytes = new byte[] { (byte) 0xC9, (byte) 0x80 };
		assertEquals(-54.5f, Convert.raw9Bit0p5ToFloat(rawBytes), "-54.5°");
		
		
		// -55.0°
		rawBytes = new byte[] { (byte) 0xC9, (byte) 0x00 };
		assertEquals(-55.0f, Convert.raw9Bit0p5ToFloat(rawBytes), "-55.0°");
		
		
	} // testRaw9Bit0p5ToFloat()

	/**
	 * Test method for {@link tempSensor.utility.Convert#float0p5ToRaw9Bit(float, byte[])}.
	 */
	@Test
	void testFloat0p5ToRaw9Bit() {
		logger.info("testFloat0p5ToRaw9Bit()");

		rawBytes = new byte[2];
		
		Convert.float0p5ToRaw9Bit(125.0f, rawBytes);
		assertEquals((byte) 0x7D,  rawBytes[0]);
		assertEquals((byte) 0x00,  rawBytes[1]);
		
		
		Convert.float0p5ToRaw9Bit(1.0f, rawBytes);
		assertEquals((byte) 0x01,  rawBytes[0]);
		assertEquals((byte) 0x00,  rawBytes[1]);
		
		
		Convert.float0p5ToRaw9Bit(0.5f, rawBytes);
		assertEquals((byte) 0x00,  rawBytes[0]);
		assertEquals((byte) 0x80,  rawBytes[1]);
		
		
		Convert.float0p5ToRaw9Bit(0.0f, rawBytes);
		assertEquals((byte) 0x00,  rawBytes[0]);
		assertEquals((byte) 0x00,  rawBytes[1]);
		
		
		Convert.float0p5ToRaw9Bit(-0.5f, rawBytes);
		assertEquals((byte) 0xFF,  rawBytes[0]);
		assertEquals((byte) 0x80,  rawBytes[1]);
		
		
		Convert.float0p5ToRaw9Bit(-1.0f, rawBytes);
		assertEquals((byte) 0xFF,  rawBytes[0]);
		assertEquals((byte) 0x00,  rawBytes[1]);
		
		
		Convert.float0p5ToRaw9Bit(-55.0f, rawBytes);
		assertEquals((byte) 0xC9,  rawBytes[0]);
		assertEquals((byte) 0x00,  rawBytes[1]);

	} // testFloat0p5ToRaw9Bit()

	
} // ssalc
