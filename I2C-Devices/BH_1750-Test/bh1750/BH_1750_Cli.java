/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : LM_75_Cli.java
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

package bh1750;

import org.kohsuke.args4j.Option;

import _common.Basic_Cli;

public final class BH_1750_Cli extends Basic_Cli {					// NOSONAR
	
	/*
	 * 	Global defaults
	 */
	public static final int TH = 125;
	public static final int TL = -55;
	
	/*
	 * All the little nice cli options :)
	 */

	@Option(name="-power_down_mode", usage="0 = POWER_DOWN | 1 = PERMANENT_ON")
	public static int power_down_mode = NOT_SET;		// NOSONAR
	
	@Option(name="-resolution_mode", usage="0 = LOW | 1 = HIGH | 2 = HIGH_2")
	public static int resolution_mode = NOT_SET;			// NOSONAR
	
	@Option(name="-measurement_mode", usage="0 = ONE_SHOT | 1 = CONTINUOSLY")
	public static int measurement_mode = NOT_SET;		// NOSONAR
	
	@Option(name="-sensitivity", usage="0 = ONE_SHOT | 1 = CONTINUOSLY")
	public static float sensitivity = (float)NOT_SET;		// NOSONAR
	
} // end class CliParameter
