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

package pcf8574.impl;

import org.kohsuke.args4j.Option;

import _common.Basic_Cli;

public final class PCF_8574_Cli extends Basic_Cli {					// NOSONAR
	
	/*
	 * 	Global defaults
	 */

	/*
	 * All the little nice cli options :)
	 */

	@Option(name="-rd_bit",
			forbids = {"-set_bit", "-clear_bit", "-rd_byte", "-wr_byte", "-togle_byte", "-run"},
			usage="bit number 0 .. 7")
	public static int rd_bit = NOT_SET;		// NOSONAR
	
	@Option(name="-set_bit", 
			forbids = {"-rd_bit", "-clear_bit", "-rd_byte", "-wr_byte", "-togle_byte", "-run"},
			usage="bit number 0 .. 7")
	public static int set_bit = NOT_SET;			// NOSONAR
	
	@Option(name="-clear_bit", 
			forbids = {"-rd_bit", "-set_bit", "-rd_byte", "-wr_byte", "-togle_byte", "-run"},
			usage="bit number 0 .. 7")
	public static int clear_bit = NOT_SET;			// NOSONAR
	
	@Option(name="-rd_byte", 
			forbids = {"-rd_bit", "-set_bit", "-clear_bit", "-wr_byte", "-togle_byte", "-run"},
			usage="reads current state")
	public static boolean rd_byte = false;		// NOSONAR
	
	@Option(name="-wr_byte", 
			forbids = {"-rd_bit", "-set_bit", "-clear_bit", "-rd_byte", "-togle_byte", "-run"},
			usage="byte to write like 0x42")
	public static String wr_byte = UNDEF;		// NOSONAR
		
	@Option(name="-toggle_byte", 
			forbids = {"-rd_bit", "-set_bit", "-clear_bit", "-rd_byte", "-wr_byte"},
			depends= {"-run"},
			usage="byte to write like 0x42")
	public static String toggle_byte = UNDEF;		// NOSONAR

	
	public static String asString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("PCF_8574_Cli [toString()=");
		
		builder.append(", rd_bit = ");		builder.append(rd_bit);
		builder.append(", set_bit = ");		builder.append(set_bit);
		builder.append(", clear_bit = ");	builder.append(clear_bit);
		builder.append(", rd_byte = ");		builder.append(rd_byte);
		builder.append(", wr_byte = ");		builder.append(wr_byte);
		builder.append(", toggle_byte = ");	builder.append(toggle_byte);

		builder.append("]");
		
		return builder.toString();
	}
		
} // end class CliParameter
