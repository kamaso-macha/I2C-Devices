/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : LM75RegisterBase.java
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

import i2cDevice.I2CErrorException;
import i2cDevice.register.AddressableRegister;
import i2cDevice.register.DataType;
import tempSensor.utility.Convert;


public abstract class Lm75TempRegisterBase extends AddressableRegister {

    private final Logger logger = LoggerFactory.getLogger(Lm75TempRegisterBase.class.getName());


	public Lm75TempRegisterBase(I2C aI2cDevice, int aRegisterAddress, DataType aDataType) {
		super(aI2cDevice, aRegisterAddress, aDataType);
		
		logger.trace("LM75RegisterBase(): aI2cDevice = {}, aRegisterAddress = {}, aDataType = {}", aI2cDevice, aRegisterAddress, aDataType);

	} // LM75RegisterBase()

	
	public float read() throws I2CErrorException {
		logger.trace("read()");
		
		readRaw();
		return Convert.raw9Bit0p5ToFloat(raw);
		
	} // read()
	
	
	public void write(final float aValue) throws I2CErrorException {
		logger.trace("write()");

		Convert.float0p5ToRaw9Bit(aValue, raw);
		writeRaw();
		
	} // write()


} // ssalc
