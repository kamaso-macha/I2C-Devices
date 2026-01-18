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

package lm75;

import org.kohsuke.args4j.Option;

import _common.Basic_Cli;

public final class LM_75_Cli extends Basic_Cli {					// NOSONAR
	
	/*
	 * 	Global defaults
	 */
	public static final int TH = 125;
	public static final int TL = -55;
	
	/*
	 * All the little nice cli options :)
	 */

	@Option(name="-shutdown_mode", usage="0 = RUN_PERSISTENT | 1 = SUTDOWN")
	public static int shutdown_mode = NOT_SET;		// NOSONAR
	
	@Option(name="-cmp_int", usage="0 = COMPARATOR | 1 = INTERRUPT")
	public static int cmp_int = NOT_SET;			// NOSONAR
	
	@Option(name="-os_polarity", usage="0 = ACTIVE_LOW | 1 = ACTIVE_HIGH")
	public static int os_polarity = NOT_SET;		// NOSONAR
	
	@Option(name="-fault_queue", usage="0 = LEN_1 | 1 = LEN_2 | 2 = LEN_4 | 3 = LEN_6")
	public static int fault_queue = NOT_SET;		// NOSONAR
		
	@Option(name="-tOs", usage="int value to be set")
	public static int tOs = TH;						// NOSONAR
	
	@Option(name="-tHyst", usage="int value to be set")
	public static int tHyst = TL;					// NOSONAR
	
} // end class CliParameter
