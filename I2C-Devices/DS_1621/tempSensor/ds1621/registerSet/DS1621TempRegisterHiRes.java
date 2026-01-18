/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS1621TempRegisterHiRes.java
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


package tempSensor.ds1621.registerSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.i2c.I2C;

import i2cDevice.I2CErrorException;
import tempSensor.ds1621.configParameter.COMMAND_SET;

public class DS1621TempRegisterHiRes extends DS1621TempRegisterBase implements DS1621TempRegister {

	private final Logger logger = LoggerFactory.getLogger(DS1621TempRegisterHiRes.class.getName());

	public DS1621TempRegisterHiRes(I2C aI2cDevice, DS1621TemperatureStatusFlags aStatusFlags) {
		super(aI2cDevice, aStatusFlags);
		
		logger.trace("DataRegisterBase(): aI2cDevice = {}, aStatusFlags = {}", aI2cDevice, aStatusFlags);

	}


	public float readTemperature() throws I2CErrorException {
		logger.trace("readTemperature()");

		doReadTemperature();

		temperature = raw[0];

		float counterRemain	= i2cDevice.readRegister(COMMAND_SET.READ_COUNTER.value);
		float countPerC		= i2cDevice.readRegister(COMMAND_SET.READ_SLOPE.value);
				
		float temp = temperature - 0.25f + ((countPerC - counterRemain) / countPerC);
		
		logger.debug(String.format("ctrRem = %f, cpc = %f, tmpRead = %f, temp = %f", 
			counterRemain,
			countPerC,
			temperature,
			temp
		));

		return temp;
		
	} // readTemperature()
	
	
} // ssalc
