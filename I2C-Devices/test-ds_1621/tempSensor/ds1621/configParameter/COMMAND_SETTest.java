/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : COMMAND_SETTest.java
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


package tempSensor.ds1621.configParameter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class COMMAND_SETTest {

	private final Logger logger = LoggerFactory.getLogger(COMMAND_SETTest.class.getName());

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
	 * Test method for {@link tempSensor.ds1621.configParameter.COMMAND_SET#getValue()}.
	 */
	@Test
	void testGetValue() {
		logger.info("testGetValue()");
		
		assertEquals(8, COMMAND_SET.values().length);
		
		assertEquals(0xAA, COMMAND_SET.READ_TEMP.value);
		assertEquals(0xA1, COMMAND_SET.ACCESS_TH.value);
		assertEquals(0xA2, COMMAND_SET.ACCESS_TL.value);
		assertEquals(0xAC, COMMAND_SET.ACCESS_CONFIG.value);
		assertEquals(0xA8, COMMAND_SET.READ_COUNTER.value);
		assertEquals(0xA9, COMMAND_SET.READ_SLOPE.value);
		assertEquals(0xEE, COMMAND_SET.START_CONVERT.value);
		assertEquals(0x22, COMMAND_SET.STOP_CONVERT.value);
		
		
	}// testGetValue()

	
} // ssalc
