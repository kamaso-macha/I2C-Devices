/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : Ads1115LoThresholdRegister.java
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.i2c.I2C;

import adc.ads1115.configParameter.Ads1115RegisterMap;
import i2cDevice.register.DataType;


public class Ads1115LoThresholdRegister extends Ads1115DataRegisterBase {

	private final Logger logger = LoggerFactory.getLogger(Ads1115LoThresholdRegister.class.getName());


	public Ads1115LoThresholdRegister(I2C aI2cDevice) {
		super(aI2cDevice, Ads1115RegisterMap.LO_THRESH, DataType.WORD);
		
		logger.trace("LoThresholdRegister(): aI2cDevice = {}", aI2cDevice);

	} // ConversionRegister()
	

} // ssalc
