/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : FAULT_QUEUETest.java
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


package tempSensor.lm75.configParameter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class FAULT_QUEUETest {

	private static Logger LOGGER = null;
	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	    System.setProperty("log4j.configurationFile","./test-cfg/log4j2.xml");
		LOGGER = LogManager.getLogger();
		
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
	 * Test method for {@link tempSensor.lm75.configParameter.FAULT_QUEUE#getSetMask()}.
	 */
	@Test
	void testGetSetMask() {
		LOGGER.info("testGetSetMask()");

		LOGGER.info("LEN_1.getSetMask() = {}", String.format("%02X", FAULT_QUEUE.LEN_1.getSetMask()));
		assertTrue(FAULT_QUEUE.LEN_1.getSetMask() == 0b0000_0000, "LEN_1");
		
		LOGGER.info("LEN_2.getSetMask() = {}", String.format("%02X", FAULT_QUEUE.LEN_2.getSetMask()));
		assertTrue(FAULT_QUEUE.LEN_2.getSetMask() == 0b0000_1000, "LEN_2");
		
		LOGGER.info("LEN_4.getSetMask() = {}", String.format("%02X", FAULT_QUEUE.LEN_4.getSetMask()));
		assertTrue(FAULT_QUEUE.LEN_4.getSetMask() == 0b0001_0000, "LEN_4");
		
		LOGGER.info("LEN_6.getSetMask() = {}", String.format("%02X", FAULT_QUEUE.LEN_6.getSetMask()));
		assertTrue(FAULT_QUEUE.LEN_6.getSetMask() == 0b0001_1000, "LEN_6");
		
		
	} // testGetSetMask()
	

	/**
	 * Test method for {@link tempSensor.lm75.configParameter.FAULT_QUEUE#getClearMask()}.
	 */
	@Test
	void testGetClearMask() {
		LOGGER.info("testGetClearMask()");

		LOGGER.info("FAULT_QUEUE.getClearMask() = {}", String.format("%02X", FAULT_QUEUE.LEN_1.getClearMask()));
		assertTrue(FAULT_QUEUE.LEN_1.getClearMask() == 0b1110_0111, "clear mask");
		
	} // testGetClearMask()
	

	/**
	 * Test method for {@link tempSensor.lm75.configParameter.FAULT_QUEUE#getValue()}.
	 */
	@Test
	void testGetValue() {
		LOGGER.info("testGetValue()");

		LOGGER.info("LEN_1.value = {}", String.format("%02X", FAULT_QUEUE.LEN_1.value));
		assertTrue(FAULT_QUEUE.LEN_1.value == 0b00, "LEN_1");
		
		LOGGER.info("LEN_1.value = {}", String.format("%02X", FAULT_QUEUE.LEN_2.value));
		assertTrue(FAULT_QUEUE.LEN_2.value == 0b01, "LEN_2");
		
		LOGGER.info("LEN_1.value = {}", String.format("%02X", FAULT_QUEUE.LEN_4.value));
		assertTrue(FAULT_QUEUE.LEN_4.value == 0b10, "LEN_5");
		
		LOGGER.info("LEN_1.value = {}", String.format("%02X", FAULT_QUEUE.LEN_6.value));
		assertTrue(FAULT_QUEUE.LEN_6.value == 0b11, "LEN_6");
		
	} // testGetValue()
	

	/**
	 * Test method for {@link tempSensor.ds1621.configParameter.FAULT_QUEUE#getType(int)}.
	 */
	@Test
	void testValue2type() {
		LOGGER.info("testValue2type()");

		assertTrue(FAULT_QUEUE.value2type(0) == FAULT_QUEUE.LEN_1, "LEN_1");		
		assertTrue(FAULT_QUEUE.value2type(1) == FAULT_QUEUE.LEN_2, "LEN_2");		
		assertTrue(FAULT_QUEUE.value2type(2) == FAULT_QUEUE.LEN_4, "LEN_4");		
		assertTrue(FAULT_QUEUE.value2type(3) == FAULT_QUEUE.LEN_6, "LEN_6");		
		assertTrue(FAULT_QUEUE.value2type(4) == null, "null");		
		
	} // testGetType()


} // ssalc
