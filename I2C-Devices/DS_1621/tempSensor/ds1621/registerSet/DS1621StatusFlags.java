/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS1621StatusFlags.java
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

import i2cDevice.I2CErrorException;
import tempSensor.ds1621.configParameter.NVB;
import tempSensor.ds1621.configParameter.THF;
import tempSensor.ds1621.configParameter.TLF;

public interface DS1621StatusFlags extends DS1621TemperatureStatusFlags {

	public NVB getNVB() throws I2CErrorException;
	public TLF getTLF() throws I2CErrorException;
	public THF getTHF() throws I2CErrorException;
	
}
