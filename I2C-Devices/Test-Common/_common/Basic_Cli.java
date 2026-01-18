/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : xxx.java
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

package _common;

import org.kohsuke.args4j.Option;


public class Basic_Cli {					// NOSONAR
	
	/*
	 * 	Global defaults
	 */
	public static final int NOT_SET = -1;
	public static final String UNDEF = "UNDEF";
	
	/*
	 * All the little nice cli options :)
	 */

	@Option(name="-run", usage="0 | 1 -> 0 = 1 shot, 1 = persistent")
	public static int run = 0;						// NOSONAR
	
	@Option(name="-adr", usage="I2C device address [dec | hex]")
	private static String addrString = UNDEF;
	
	@Option(name="-bus", usage="I2C bus number")
	public static int bus = NOT_SET;		// NOSONAR
	
	
	public static int getAddress() {
		System.out.println("getAddress()");		// NOSONAR
		
		if(bus == NOT_SET
		|| addrString == UNDEF) {
			System.err.println("please provide the required parameter -bus and -adr!");		// NOSONAR
			System.exit(1);
		}

		int radix = addrString.matches("^0[xX].*") ? 16 : 10;
		System.out.println("addrString = " + addrString + " -> " + addrString.matches("^0[xX].*") + ", radix = " + radix);		// NOSONAR
		
		return Integer.parseUnsignedInt(addrString.replaceAll("0[xX]", ""), radix);

	} // getAddress()
	
} // end class CliParameter
