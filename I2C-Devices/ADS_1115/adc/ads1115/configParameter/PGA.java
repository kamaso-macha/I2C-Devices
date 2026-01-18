/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : PGA.java
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

public enum PGA implements ConfigParameter {

	  FSR_6_144V 		(0b000, 187.5000e-6)
	, FSR_4_096V		(0b001, 125.0000e-6)
	, FSR_2_048V		(0b010,  62.5000e-6)
	, FSR_1_024V		(0b011,  31.2500e-6)
	, FSR_0_512V		(0b100,  15.6250e-6)
	, FSR_0_256V		(0b101,   7.8125e-6)
	;

	
	public static final int SIZE			= 3;
	public static final int LSB_POSITION	= 9;
	
	public final int value;
	public final double lsbSize;
	
	private PGA(final int aValue, final double aLsbSize) { 
		value = aValue;
		lsbSize = aLsbSize;
	}

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
		
	    for (PGA e : values()) {
	    	
	        if (java.util.Objects.equals(e.value, aValue)) return (T) e;

	    }
	    
	    return null;
	    
	} // valueOf()
	
	
} // mune
