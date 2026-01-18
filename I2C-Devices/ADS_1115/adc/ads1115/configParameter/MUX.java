/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : MUX.java
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

package adc.ads1115.configParameter;

import i2cDevice.config.ConfigParameter;

public enum MUX implements ConfigParameter {

	  DIFF_0_1		(0b000)
	, DIFF_0_3		(0b001)
	, DIFF_1_3		(0b010)
	, DIFF_2_3		(0b011)
	, SINGLE_0		(0b100)
	, SINGLE_1		(0b101)
	, SINGLE_2		(0b110)
	, SINGLE_3		(0b111)
	;

	public static final int SIZE			= 3;
	public static final int LSB_POSITION	= 12;
	
	public final int value;
	
	private MUX(final int aValue) { value = aValue; }
	
	@Override
	public int getSetMask() { return (value << LSB_POSITION); }
	

	@Override
	public int getClearMask() { 
		
		int clearMask = 0;
		
		for(int n = 0; n < SIZE; n++) {
			
			clearMask = clearMask << 1;
			clearMask += 1;
			
		}
		
		return ((clearMask << LSB_POSITION) ^ 0x0FFFF);
		
	} // getClearMask()
	
	
	@SuppressWarnings("unchecked")
	public static <T> T value2type(final int aValue) {
		
	    for (MUX e : values()) {
	    	
	        if (java.util.Objects.equals(e.value, aValue)) return (T) e;

	    }
	    
	    return null;
	    
	} // valueOf()
	
	
} // mune
