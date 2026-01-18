/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : AddressableRegister.java
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

import com.pi4j.io.i2c.I2C;

import i2cDevice.I2CErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Responsibilities:<br>
 * Abstract base class for all registers which must be addressed on the device.
 * 
 * <p>
 * Collaborators:<br>
 * 
 * 
 * <p>
 * Description:<br>
 * Held the register address and provides specialized methods for read/write operations
 * on the device.
 * 
 * <p>
 * @author Stefan
 *
 */

public abstract class AddressableRegister extends RegisterBase {

    private final Logger logger = LoggerFactory.getLogger(AddressableRegister.class.getName());
	
	protected int registerAddress;
	
	
	/**
	 * Default parameterized constructor.
	 * 
	 * @param aI2cDevice The I2C device to talk with. It's handed over to the base class.
	 * @param aRegisterAddress The address of the register to talk to.
	 * @param aDataType the used data type {BYTE, WORD, ...}. It's handed over to the base class.
	 */
	public AddressableRegister(I2C aI2cDevice, int aRegisterAddress, DataType aDataType) {
		super(aI2cDevice, aDataType);
		
		logger.trace("RegisterBase(): aI2cDevice = {}, aRegisterAddress = {}, aDataType = {}", 
			aI2cDevice, String.format("0x%02X", aRegisterAddress), aDataType);
		
		registerAddress = (aRegisterAddress & 0x00FF);
		
	} // ConfigRegister()


	/**
	 * Reads the I2C device.<br>
	 * The data read are stored in the raw buffer in the order used by the I2C device.
	 * 
	 * @throws I2CErrorException If number of bytes read is unequal the size of the data type. 
	 */
	protected void readRaw() throws I2CErrorException {
		logger.trace(String.format("readRaw(): addr: = 0x%04X, bytes = %d", registerAddress, DATA_TYPE.getSize()));
		
		int read = i2cDevice.readRegister(registerAddress, raw, 0, DATA_TYPE.getSize());
		logger.trace("read = {}", read);
		
		if(read != DATA_TYPE.getSize())
			throw new I2CErrorException("Unable to read register.");
		
	} // read()
	
	
	/**
	 * Writes data to the I2C device.<br>
	 * The data read must be provided in the raw buffer in the order used by the I2C device.
	 * 
	 * @throws I2CErrorException If number of bytes read is unequal the size of the data type. 
	 */
	protected void writeRaw() throws I2CErrorException {
		logger.trace(String.format("writeRaw(): addr: = 0x%04X, bytes = %d", registerAddress, DATA_TYPE.getSize()));

		int written = i2cDevice.writeRegister(registerAddress, raw, 0, DATA_TYPE.getSize());
		logger.trace("written = {}", written);
		
		if(written != DATA_TYPE.getSize() + 1)
			throw new I2CErrorException("Unable to write register.");
		
	} // write()

} // ConfigRegister()