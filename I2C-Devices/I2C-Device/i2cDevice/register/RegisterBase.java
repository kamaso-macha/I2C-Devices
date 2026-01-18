/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : RegisterBase.java
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


package i2cDevice.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.i2c.I2C;

/**
 * Responsibilities:<br>
 * Store a reference to the I2C device (from pi4j project) together with the 
 * data type of the register and a byte buffer for raw read / write operations 
 * on the device.
 * 
 * <p>
 * Collaborators:<br>
 * 
 * 
 * <p>
 * Description:<br>
 * Abstract mother of all registers.<br>
 * Provides the I2C device, the data type {BYTE, WORD, ...} and a raw byte 
 * buffer sized to the data types length.
 * 
 * <p>
 * @author Stefan
 *
 */

public abstract class RegisterBase {

    private final Logger logger = LoggerFactory.getLogger(RegisterBase.class.getName());

	protected I2C i2cDevice;
	protected final DataType DATA_TYPE;		// NOSONAR

	protected byte[] raw;
	
	
	@SuppressWarnings("unused")
	private RegisterBase() { DATA_TYPE = DataType.UNDEF; }
	
	/**
	 * Default parameterized constructor
	 * 
	 * @param aI2cDevice The I2C device to talk to
	 * @param aDataType The data type of the derived register.
	 */
	public RegisterBase(final I2C aI2cDevice, final DataType aDataType) {
		
		logger.trace("RegisterBase(): aI2cDevice = {}, aDataType = {}", aI2cDevice, aDataType);

		if(aI2cDevice == null) 
			throw new IllegalArgumentException("aI2cDevice can't be null.");
		
		if(aDataType == null) 
			throw new IllegalArgumentException("aDataType can't be null.");
		
		if(aDataType == DataType.UNDEF) 
			throw new IllegalArgumentException("aDataType can't be UNDEF.");
		
		i2cDevice = aI2cDevice;

		DATA_TYPE = aDataType;
		raw = new byte[aDataType.getSize()];
		
	} // RegisterBase( .. )
	
	
} // ssalc
