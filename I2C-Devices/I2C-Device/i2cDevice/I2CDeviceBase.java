/**
 *
 * **********************************************************************
 * PROJECT       : I2C-Devices
 * FILENAME      : I2CDevice.java
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


package i2cDevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;



/**
 * Responsibilities:<br>
 * Encapsulates the functionality of creating a I2C device with pi4j.
 * 
 * <p>
 * Collaborators:<br>
 * 
 * 
 * <p>
 * Description:<br>
 * Creates the I2C device used for communication to the hardware.
 * 
 * <p>
 * @author Stefan
 *
 */

// DOC
// Created at 2025-12-25 17:07:43

public abstract class I2CDeviceBase {

    private final Logger logger = LoggerFactory.getLogger(I2CDeviceBase.class.getName());
	
	protected I2C i2cDevice;
	protected Context context;
	
	
	@SuppressWarnings("unused")
	private I2CDeviceBase() { /* hidden */ }
	
	
	/**
	 * Default parameterized constructor.
	 * 
	 * @param aI2cBusNbr The physical / logical bus number used by the platform.
	 * @param aI2cAddress The physical address of the device.
	 * 
	 */
	public I2CDeviceBase(final int aI2cBusNbr, final int aI2cAddress) {
		logger.info(String.format("I2CDeviceBase(): aI2cBusNbr = %d,  aI2cAddress = 0x%02X",  aI2cBusNbr, aI2cAddress));
		
		if(aI2cBusNbr < 0) 
			throw new IllegalArgumentException("aI2cBusNbr must be in the range of 0 .. n.");
		
		if(aI2cAddress < 0x08 || aI2cAddress > 0x7F) 
			throw new IllegalArgumentException("aI2cAddress must be in the range of 0x08 .. 0x7F.");
		
		StringBuilder sb = new StringBuilder();
		sb.append("I2C_");
		sb.append(aI2cBusNbr);
		sb.append("_");
		sb.append(String.format("%02X", aI2cAddress));

		logger.info("Creating I2C device with id '{}'", sb.toString());
		
        context = Pi4J.newAutoContext();

        I2CConfig i2c2Config = I2C.newConfigBuilder(context)
    		.id(sb.toString())
    		.bus(aI2cBusNbr)
    		.device(aI2cAddress)
    		.build()
    		;

        i2cDevice = context.create(i2c2Config);
		
	} // I2CDeviceBase()
	
	
} // ssalc
