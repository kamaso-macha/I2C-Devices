/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DR.java
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

public enum DR implements ConfigParameter {

	  SPS_8		(0b000, 	  8)
	, SPS_16	(0b001, 	 16)
	, SPS_32	(0b010, 	 32)
	, SPS_64	(0b011, 	 64)
	, SPS_128	(0b100, 	128)
	, SPS_250	(0b101, 	250)
	, SPS_475	(0b110, 	475)
	, SPS_860	(0b111, 	860)
	;
	
	
	public static final int SIZE			= 3;
	public static final int LSB_POSITION	= 5;
	
	public final int value;
	public final int conversionTime;		// milliseconds 
	
	
	private DR(final int aValue, final int aSamplingRate) { 
	
		value = aValue; 
		conversionTime = 1_000 / aSamplingRate;
		
	} // DR()
	
	@Override
	public int getSetMask() { return (int) (value << LSB_POSITION) & 0x00FFFF; }
	

	@Override
	public int getClearMask() { 
		
		int clearMask = 0;
		
		for(int n = 0; n < SIZE; n++) {
			
			clearMask = clearMask << 1;
			clearMask += 1;
			
		}
		
		return (int) ((clearMask << LSB_POSITION) ^ 0x0FFFF);
		
	} // getClearMask()
	
	
	@SuppressWarnings("unchecked")
	public static <T> T value2type(final int aValue) {
		
	    for (DR e : values()) {
	    	
	        if (java.util.Objects.equals(e.value, aValue)) return (T) e;

	    }
	    
	    return null;
	    
	} // valueOf()
	
	
} // mune
