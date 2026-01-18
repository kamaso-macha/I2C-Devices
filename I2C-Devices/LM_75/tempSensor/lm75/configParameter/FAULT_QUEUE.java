/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : FAULT_QUEUE.java
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


package tempSensor.lm75.configParameter;

import i2cDevice.config.ConfigParameter;


public enum FAULT_QUEUE implements ConfigParameter {

	  LEN_1		(0b00)
	, LEN_2		(0b01)
	, LEN_4		(0b10)
	, LEN_6		(0b11)
	;
	

	public static final int SIZE			= 2;
	public static final int LSB_POSITION	= 3;
	
	public final int value;
	
	private FAULT_QUEUE(final int aValue) { value = aValue; }
	
	@Override
	public int getSetMask() { return (value << LSB_POSITION); }
	

	@Override
	public int getClearMask() { 
		
		int clearMask = 0;
		
		for(int n = 0; n < SIZE; n++) {
			
			clearMask = clearMask << 1;
			clearMask += 1;
			
		}
		
		return ((clearMask << LSB_POSITION) ^ 0x000FF);
		
	} // getClearMask()
	
	
	@SuppressWarnings("unchecked")
	public static <T> T value2type(final int aValue) {
		
	    for (FAULT_QUEUE e : values()) {
	    	
	        if (java.util.Objects.equals(e.value, aValue)) return (T) e;

	    }
	    
	    return null;
	    
	} // valueOf()
	
	
} // mune
