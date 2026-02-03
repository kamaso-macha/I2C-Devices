/**
 *
 * **********************************************************************
 * PROJECT       : I2C-Devices
 * FILENAME      : InstructionSet.java
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


package lightSensor.bh1750.configParameter;

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
// Created at 2026-01-29 13:56:53

public enum InstructionSet {
	
	  POWER_DOWN		(0b0000_0000)
	, POWER_ON			(0b0000_0001)
	, RESET				(0b0000_0111)
	, CONT_H_RES		(0b0001_0000)
	, CONT_H_RES2		(0b0001_0001)
	, CONT_L_RES		(0b0001_0011)
	, ONE_TIME_H_RES	(0b0010_0000)
	, ONE_TIME_H_RES2	(0b0010_0001)
	, ONE_TIME_L_RES	(0b0010_0011)
	
	, CHG_MEAS_TIME_H	(0b0100_0000)
	, CHG_MEAS_TIME_L	(0b0110_0000)
	;
	
	public final int value;
	
	private InstructionSet(final int aValue) { this.value = aValue; }

}
