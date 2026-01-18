/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : COMMAND_SET.java
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


package tempSensor.ds1621.configParameter;


public enum COMMAND_SET {
	
	  READ_TEMP			(0xAA)
	, ACCESS_TH			(0xA1)
	, ACCESS_TL			(0xA2)
	, ACCESS_CONFIG		(0xAC)
	, READ_COUNTER		(0xA8)
	, READ_SLOPE		(0xA9)
	, START_CONVERT		(0xEE)
	, STOP_CONVERT		(0x22)
	;

	public final int value;
	
	private COMMAND_SET(final int aValue) { value = aValue; }

	
} // ssalc
