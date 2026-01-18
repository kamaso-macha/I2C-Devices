/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : AddressableConfigRegister.java
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


package i2cDevice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.i2c.I2C;

import i2cDevice.I2CErrorException;
import i2cDevice.register.AddressableRegister;
import i2cDevice.register.DataType;

/**
 * Responsibilities:<br>
 * Store and manipulate the current configuration of a I2C device register.
 * 
 * <p>
 * Collaborators:<br>
 * 
 * <p>
 * Description:<br>
 * Derived from abstract AddressableRegister, it provides the functionality 
 * needed toset/modify the configuration of a register. 
 * 
 * <p>
 * @author Stefan
 *
 */

public abstract class AddressableConfigRegister extends AddressableRegister {

    private final Logger logger = LoggerFactory.getLogger(AddressableConfigRegister.class.getName());
	
	protected int config = 0;

	private boolean initialized = false;
	
	/**
	 * Default parameterized constructor.
	 * 
	 * @param aI2cDevice The I2C device to talk with. It's handed over to the base class.
	 * @param aRegisterAddress The address of the register to talk to.
	 * @param aDataType the used data type {BYTE, WORD, ...}. It's handed over to the base class.
	 */
	public AddressableConfigRegister(final I2C aI2cDevice, final int aRegisterAddress, final DataType aDataType) {
		super(aI2cDevice, aRegisterAddress, aDataType);
		
		logger.trace("AddressableConfigRegister(): aI2cDevice = {}, aRegisterAddress = {}, aDataType = {}", 
			aI2cDevice, aRegisterAddress, aDataType);
				
	} // ConfigRegister()


	/**
	 * Read and return the current configuration.
	 * 
	 * @return The current configuration as integer value.<br>
	 * This value must be parsed to get the related config items.
	 * 
	 * @throws I2CErrorException
	 */
	public int getConfig() throws I2CErrorException { 
		logger.trace("getConfig(): DATA_TYPE = {}", DATA_TYPE);
		
		readRaw();
		
		config = ((int) raw[0]) & 0x00FF;
		
		if(DATA_TYPE == DataType.BYTE) {
			
			logger.debug(String.format("config = 0x%04X, raw[0] = 0x%02X, ", config, raw[0]));
			
			return config;
			
		}	
		else if(DATA_TYPE == DataType.WORD) {
			
			config = config << 8;
			config += ((int) raw[1]) & 0x00FF;
			
			logger.debug(String.format("config = 0x%04X, raw[0] = 0x%02X, raw[1] = 0x%02X", config, raw[0], raw[1]));

			return config & 0x0FFFF;
			
		}
		else {
			
			throw new IllegalAccessError("Unhandled DataType " + DATA_TYPE);
			
		} // fi
		
	
	} // getConfig()
	

	/**
	 * Set a requested configuration and writes it to the I2C device.<br>
	 * The required bit mask is obtained from the associated enum and accessed 
	 * via the ConfigParameter interface.
	 * 
	 * @param aConfig
	 * @throws I2CErrorException
	 */
	protected void setConfig(final ConfigParameter aConfig) throws I2CErrorException {
		logger.trace("setConfig(): initialized = {}", initialized);
		
		if(!initialized) {
			
			getConfig();
			initialized = true;
			
		} // fi
		
		_clearConfig(aConfig);		
		_setConfig(aConfig);
		
		if(DATA_TYPE == DataType.BYTE) {
			raw[0] = (byte) (config & 0x00FF);

			logger.debug(String.format("config = 0x%04X, raw[0] = 0x%02X, ", config, raw[0]));
		
		}
		else if(DATA_TYPE == DataType.WORD) {
			raw[0] = (byte) ((config & 0x0FF00) >> 8);
			raw[1] = (byte) (config & 0x000FF);

			logger.debug(String.format("config = 0x%04X, raw[0] = 0x%02X, raw[1] = 0x%02X", config, raw[0], raw[1]));
}
		else {
			
			throw new IllegalArgumentException("Undefined DataType " + DATA_TYPE);
			
		}
				
		writeRaw();		
		
	} // setConfig(...)
	

	private void _setConfig(final ConfigParameter aConfig) {
		
		int setMask = aConfig.getSetMask();
		logger.debug(String.format("setConfig()  : entry config: 0x%04x,  aConfig: %-10s, setMask  : 0x%04x", config, aConfig, setMask));

		config |= setMask;
		
		logger.debug(String.format("setConfig()  : exit config : 0x%04x", config));
		
	} // setConfig()
	

	private void _clearConfig(final ConfigParameter aConfig) {
		
		int clearMask = aConfig.getClearMask();

		logger.debug(String.format("clearConfig(): entry config: 0x%04x,  aConfig: %-10s, clearMask: 0x%04x", config, aConfig, clearMask));
		
		config &= clearMask;
		
		logger.debug(String.format("clearConfig(): exit config : 0x%04x", config));
		
	} // clearConfig()
	

} // ConfigRegister()