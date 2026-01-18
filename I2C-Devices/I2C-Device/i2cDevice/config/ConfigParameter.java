/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : ConfigParameter.java
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

/**
 * Description:<br>
 * Used by the enums which define the configuration items of a device.
 * 
 * <p>
 * @author Stefan
 *
 */

public interface ConfigParameter {

	/**
	 * Method to retrieve a bit mask used to set a specific configuration.
	 * 
	 * @return the bit mask needed to set a configuration item.
	 */
	int getSetMask();
	
	
	/**
	 * Method to retrieve a bit mask used to clear a specific configuration.
	 * 
	 * @return the bit mask needed to clear a configuration item.
	 */
	int getClearMask();
	
} // ssalc
