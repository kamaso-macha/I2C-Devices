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

import i2cDevice.I2CErrorException;
import i2cDevice.register.DataType;

/**
 * Responsibilities:<br>
 * Store and manipulate the current configuration of a config register.
 * 
 * <p>
 * Collaborators:<br>
 * 
 * <p>
 * Description:<br>
 * Provides the functionality needed to set/modify the configuration 
 * of a register. 
 * 
 * <p>
 * @author Stefan
 *
 */

public abstract class ConfigRegisterBase {

    private final Logger logger = LoggerFactory.getLogger(ConfigRegisterBase.class.getName());
	
	protected final DataType dataType;
    
    protected int config = 0;
	protected byte[] raw;
	

	/**
	 * Default parameterized constructor.
	 * 
	 * @param aI2cDevice The I2C device to talk with. It's handed over to the base class.
	 * @param aRegisterAddress The address of the register to talk to.
	 * @param aDataType the used data type {BYTE, WORD, ...}. It's handed over to the base class.
	 */
	public ConfigRegisterBase(final DataType aDataType) {
		
		logger.trace("AddressableConfigRegister()");
		
		if(aDataType == null)
			throw new IllegalArgumentException("aDataType can't be null.");
		
		dataType = aDataType;
		raw = new byte[dataType.size];
				
	} // ConfigRegister()

	
	protected byte[] getRawBytes() { 
		
		switch(dataType) {
		
			case WORD	:	raw[0] = (byte) ((config >> 8 ) & 0x00FF);
							raw[1] = (byte) (config & 0x00FF);
							break;
			
			case BYTE	:	raw[0] = (byte) (config & 0x00FF);
							break;
							
			default		:	logger.error("Undefined DataType");
		
		}
		
		return raw; 
		
	} // getRawBytes()
	

	/**
	 * Set a requested configuration and returns the new configuration.<br>
	 * 
	 * The required bit mask is obtained from the associated enum and accessed 
	 * via the ConfigParameter interface.
	 * 
	 * @param aConfig
	 * @throws I2CErrorException
	 */
	protected int setConfig(final ConfigParameter aConfig) {
		
		int setMask = aConfig.getSetMask();
		logger.debug(String.format("setConfig()  : entry config: 0x%04x,  aConfig: %-10s, setMask  : 0x%04x", config, aConfig, setMask));

		config |= setMask;
		
		logger.debug(String.format("setConfig()  : exit config : 0x%04x", config));
		
		return config;
		
	} // setConfig()
	

	/**
	 * Clears a requested configuration and returns the new configuration.<br>
	 * 
	 * The required bit mask is obtained from the associated enum and accessed 
	 * via the ConfigParameter interface.
	 * 
	 * @param aConfig
	 * @throws I2CErrorException
	 */
	protected int clearConfig(final ConfigParameter aConfig) {
		
		int clearMask = aConfig.getClearMask();

		logger.debug(String.format("clearConfig(): entry config: 0x%04x,  aConfig: %-10s, clearMask: 0x%04x", config, aConfig, clearMask));
		
		config &= clearMask;
		
		logger.debug(String.format("clearConfig(): exit config : 0x%04x", config));
		
		return config;
		
	} // clearConfig()
	

} // ConfigRegister()