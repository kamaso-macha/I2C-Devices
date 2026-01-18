/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : LM75RegisterMap.java
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


package tempSensor.lm75.configParameter;


public enum LM75RegisterMap {
	
	TEMPERATURE (0b00)
	, CONFIG	(0b01)
	, T_OS		(0b10)
	, T_HYST	(0b11)
	;
	
	public final int registerAddress;
	
	private LM75RegisterMap(final int aRegisterAddress) { registerAddress = aRegisterAddress; }

} //mune
