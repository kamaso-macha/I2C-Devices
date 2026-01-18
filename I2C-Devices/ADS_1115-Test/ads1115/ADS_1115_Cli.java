/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : ADS_1115_Cli.java
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

package ads1115;

import org.kohsuke.args4j.Option;

import _common.Basic_Cli;

public final class ADS_1115_Cli extends Basic_Cli {					// NOSONAR
	
	/*
	 * 	Global defaults
	 */
	public static final int TH = 10;
	public static final int TL = -10;
	
	
	/*
	 * All the little nice cli options :)
	 */

	@Option(name="-pga", usage="0 = 6.144 | 1 = 4.096 | 2 = 2.048 | 3 = 1.024 | 4 = 0.512 | 5 = 0.256")
	public static int pga = NOT_SET;			// NOSONAR
	
	@Option(name="-mode", usage="0 = CONTINUOS | 1 = SINGLE_SHOT")
	public static int mode = NOT_SET;		// NOSONAR
	
	@Option(name="-dr", usage="0 = 8 | 1 = 16 | 2 = 32 | 3 = 64 | 4 = 128 | 5 = 250 | 6 = 475 | 7 = 860")
	public static int dr = NOT_SET;		// NOSONAR
		
	@Option(name="-comp_mode", usage="0 = transition | 1 = window_comp")
	public static int comp_mode = NOT_SET;						// NOSONAR
	
	@Option(name="-comp_pol", usage="0 = act_low | act_high")
	public static int comp_pol = NOT_SET;						// NOSONAR
	
	@Option(name="-comp_lat", usage="0 = not_latching | 1 = latching")
	public static int comp_lat = NOT_SET;						// NOSONAR
	
	@Option(name="-comp_que", usage="0 = len_0 | 1 = len_2 | 2 = len_4 | 3 = disable comp")
	public static int comp_que = NOT_SET;						// NOSONAR
	
	@Option(name="-th")
	public static int th = TH;					// NOSONAR
	
	@Option(name="-tl")
	public static int tl = TL;					// NOSONAR
	
} // end class CliParameter
