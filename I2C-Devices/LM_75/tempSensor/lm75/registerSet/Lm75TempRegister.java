/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : Lm75TempRegister.java
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


package tempSensor.lm75.registerSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.i2c.I2C;

import i2cDevice.register.DataType;
import i2cDevice.register.RoRegisterFloat;
import tempSensor.lm75.configParameter.LM75RegisterMap;


public class Lm75TempRegister extends Lm75TempRegisterBase implements RoRegisterFloat {

    private final Logger logger = LoggerFactory.getLogger(Lm75TempRegister.class.getName());

			
	
	public Lm75TempRegister(final I2C aI2cDevice) {
		super(aI2cDevice, LM75RegisterMap.TEMPERATURE.registerAddress, DataType.WORD);
		
		logger.trace("Lm75TOsRegister(): aI2cDevice = {}", aI2cDevice);
		
	} // Lm75TempRegister()


} // ssalc
