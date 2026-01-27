/**
 *
 * **********************************************************************
 * PROJECT       : I2C-Devices
 * FILENAME      : PCF_8574.java
 *
 * More information about this project can be found on Github
 * http://github.com/kamaso-macha/I2C-Devices
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


package gpio.pcf8574a;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gpio.pcf8574.impl.PCF_8574_Impl;

/**
 * Responsibilities:<br>
 * 
 * 
 * <p>
 * Collaborators:<br>
 * 
 * 
 * <p>
 * Description:<br>
 * 
 * 
 * <p>
 * @author Stefan
 *
 */

// DOC
// Created at 2026-01-27 09:56:48

public class PCF_8574A extends PCF_8574_Impl { // NOSONAR

	private final Logger logger = LoggerFactory.getLogger(PCF_8574A.class.getName());

	protected static final byte ADR_LOW	= 0x38;
	protected static final byte ADR_HIG	= 0x3F;
	
	
	/**
	 * @param aI2cBusNbr
	 * @param aI2cAddress
	 */
	public PCF_8574A(int aI2cBusNbr, int aI2cAddress) {
		super(aI2cBusNbr, aI2cAddress);
	
		if(aI2cAddress < ADR_LOW || aI2cAddress > ADR_HIG)
			throw new IllegalArgumentException("aI2cAddress is out of range 0x38 .. 0x3F.");
		
		logger.trace(String.format("PCF_8574(): aI2cBus = %d, aI2cAddress = 0x%02X", aI2cBusNbr, aI2cAddress));

	}
	

} // ssalc
