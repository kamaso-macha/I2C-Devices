/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS_1621_Cli.java
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

package ds1621;

import org.kohsuke.args4j.Option;

import _common.Basic_Cli;

public final class DS_1621_Cli extends Basic_Cli {		// NOSONAR
	
	/*
	 * 	Global defaults
	 */
	public static final int TH = 125;
	public static final int TL = -55;
	
	/*
	 * All the little nice cli options :)
	 */
	
	@Option(name="-1shot")
	public static int one_shot = NOT_SET;		// NOSONAR
	
	@Option(name="-pol")
	public static int pol = NOT_SET;			// NOSONAR
	
	@Option(name="-precicision")
	public static int precicision = NOT_SET;	// NOSONAR
	
	@Option(name="-th")
	public static int th = TH;					// NOSONAR
	
	@Option(name="-tl")
	public static int tl = TL;					// NOSONAR
	
} // end class CliParameter
