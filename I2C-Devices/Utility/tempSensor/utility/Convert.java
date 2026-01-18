/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : Convert.java
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


package tempSensor.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsibilities:<br>
 * Convert 9-bit temperature values used by Ti LM_75 and Dallas DS_1621 
 * to the corresponding float value.
 * 
 * <p>
 * Collaborators:<br>
 * 
 * 
 * <p>
 * Description:<br>
 * Temperature values of the noted devices have a 0.5° granularity. 
 * The value is handled by the device in two bytes: MSB represents the full degree
 *  while the highest bit of LSB contains the 0.5 degree.
 * 
 * <p>
 * @author Stefan
 *
 */

public class Convert {

	private static Logger logger = LoggerFactory.getLogger(Convert.class.getName());

	private Convert() { /* hidden */ }
	
	public static float raw9Bit0p5ToFloat(final byte[] aRaw) {
		logger.trace("raw2float()");
		
		float result = aRaw[0];

		if(aRaw[1] == (byte) 0x80)
			result += 0.5f;
		
		logger.debug(String.format("raw[0] = 0x%02X, raw[1] = 0x%02X, result = %f", aRaw[0], aRaw[1], result));
		
		return result;
		
	} // raw2float()
	
	
	public static void float0p5ToRaw9Bit(final float aValue, byte[] aRaw) {
	
		int intVal = (int) aValue;
		float fract  = aValue - (int) aValue;

		if(aValue < 0.0f && fract != 0)
			intVal--;

		aRaw[0] = (byte) (intVal & 0xFF);

		if(fract != 0)
			aRaw[1] = (byte) 0x80;
		else 
			aRaw[1] = (byte) 0x00;
		
		logger.debug(String.format("aValue = %3.1f, intVal = 0x%08X, fract = %f, raw[0] = 0x%02X, raw[1] = 0x%02X", 
				aValue,
				intVal,
				fract,
				aRaw[0],
				aRaw[1]
			));
		
	} // float2raw()

	
} // ssalc
