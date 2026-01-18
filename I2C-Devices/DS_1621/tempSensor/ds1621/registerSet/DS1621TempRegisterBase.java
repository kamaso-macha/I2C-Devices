/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS1621TempRegisterBase.java
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
import i2cDevice.register.DataType;
import tempSensor.ds1621.configParameter.COMMAND_SET;
import tempSensor.ds1621.configParameter.DONE;
import tempSensor.ds1621.configParameter.ONE_SHOT;

public abstract class DS1621TempRegisterBase extends DS1621RegisterBase {

    private final Logger logger = LoggerFactory.getLogger(DS1621TempRegisterBase.class.getName());

	protected DS1621TemperatureStatusFlags statusFlags;
	protected float temperature;
	
	
	public DS1621TempRegisterBase(final I2C aI2cDevice, final DS1621TemperatureStatusFlags aStatusFlags) {
		super(aI2cDevice, COMMAND_SET.READ_TEMP, DataType.WORD);
		
		logger.trace("DataRegisterBase(): aI2cDevice = {}, aStatusFlags = {}", aI2cDevice, aStatusFlags);

		if(aStatusFlags == null)
			throw new IllegalArgumentException("aStatusFlags can't be null.");
		
		statusFlags = aStatusFlags;
		
	} // Lm75TempRegister()
	
	
	
	protected void doReadTemperature() throws I2CErrorException {
		logger.trace("doReadTemperature()");

		if(statusFlags.get1SHOT() == ONE_SHOT.ONE_SHOT) {
			i2cDevice.write(COMMAND_SET.START_CONVERT.value & 0x00FF);
		
			while(statusFlags.getDONE() == DONE.IN_PROGRESS) {
				
				try { Thread.sleep(500); } 
				catch (InterruptedException e) { e.printStackTrace(); }
				
			} // elihw
			
		} // fi
			
		temperature = read();
				
	} // doReadTemperature()
	
} // ssalc
