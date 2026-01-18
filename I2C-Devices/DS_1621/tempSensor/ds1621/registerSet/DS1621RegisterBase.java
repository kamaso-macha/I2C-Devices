/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS1621RegisterBase.java
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
import i2cDevice.register.AddressableRegister;
import i2cDevice.register.DataType;
import tempSensor.ds1621.configParameter.COMMAND_SET;
import tempSensor.utility.Convert;

public abstract class DS1621RegisterBase extends AddressableRegister {

    private final Logger logger = LoggerFactory.getLogger(DS1621RegisterBase.class.getName());
	

	public DS1621RegisterBase(final I2C aI2cDevice, final COMMAND_SET aCommand, final DataType aDataType) {
		super(aI2cDevice, aCommand.value, aDataType);
		
		logger.trace("DataRegisterBase(): aI2cDevice = {}, aCommand = {}, aDataType = {}", aI2cDevice, aCommand, aDataType);

		if(aDataType != DataType.WORD)
			throw new IllegalArgumentException("aDataType must be DataType.WORD.");
		
	} //DS1621RegisterBase()

	
	public float read() throws I2CErrorException {
		logger.trace("read()");
		
		readRaw();
		return Convert.raw9Bit0p5ToFloat(raw);
		
	} // read()
	
	
	public void write(final float aValue) throws I2CErrorException {
		logger.trace("write(): aValue = {}", aValue);
		
		Convert.float0p5ToRaw9Bit(aValue, raw);
		writeRaw();
		
	} // write()
	
	
} // ssalc
