/**
 *
 * **********************************************************************
 * PROJECT       : I2C-Devices
 * FILENAME      : LM75.java
 *
 * PURPOSE       : what is it for?
 *
 * This file is part of the I2C-Devices project. More information about
 * this project can be found here:  http://_project_web_page_
 * **********************************************************************
 *
 * Copyright (C)2025 by Stefan Dickel, _project_email_address_
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
import i2cDevice.config.AddressableConfigRegister;
import i2cDevice.register.DataType;
import tempSensor.lm75.configParameter.CMP_INT;
import tempSensor.lm75.configParameter.FAULT_QUEUE;
import tempSensor.lm75.configParameter.LM75RegisterMap;
import tempSensor.lm75.configParameter.OS_POLARITY;
import tempSensor.lm75.configParameter.SHUTDOWN_MODE;

/**
 * Description:<br>
 * Default modes:
 * 	OsMode.COMPARATOR
 * 	OsPolarity.ACTIVE_LOW
 * 	TOs			= 80°C
 * 	THyst		= 75°C
 * 	Pointer		= RegisterMap.TEMPERATURE
 * 	FaultQueue	= FaultQueue.LEN_1
 * <p>
 * @author Stefan
 *
 */


public class Lm75ConfigRegister extends AddressableConfigRegister {

    private final Logger logger = LoggerFactory.getLogger(Lm75ConfigRegister.class.getName());
	

	public Lm75ConfigRegister(final I2C aI2cDevice) {
		super(aI2cDevice, LM75RegisterMap.CONFIG.registerAddress, DataType.BYTE);
		
		logger.trace("Lm75ConfigRegister(): aI2cDevice = {}", aI2cDevice);
			
	} // Reg_config()

	
	public void setShutdown(final SHUTDOWN_MODE aShutdown) throws I2CErrorException {
		logger.trace("setShutdown(): aShutdown = {}", aShutdown);
		
		setConfig(aShutdown);
		
	} // setShutdown()
	
	
	public void setCmpInt(final CMP_INT aCmoInt) throws I2CErrorException {
		logger.trace("setCmpInt(): aCmoInt = {}", aCmoInt);
		
		setConfig(aCmoInt);
		
	} // setCmpInt()
	
	
	public void setOsPolarity(final OS_POLARITY aOsPolarity) throws I2CErrorException {
		logger.trace("setOsPolarity(): aOsPolarity = {}", aOsPolarity);
		
		setConfig(aOsPolarity);
		
	} // setOsPolarity()
	
	
	public void setFaultQueue(final FAULT_QUEUE aFaultQueue) throws I2CErrorException {
		logger.trace("setFaultQueue(): aFaultQueue = {}", aFaultQueue);
		
		setConfig(aFaultQueue);
		
	} // setFaultQueue()
	
	
} // Reg_config
