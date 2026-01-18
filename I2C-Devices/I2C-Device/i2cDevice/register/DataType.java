/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DataType.java
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


package i2cDevice.register;

/**
 * Description:<br>
 * Currently defined data types and their size in bytes. 
 * 
 * <p>
 * @author Stefan
 *
 */

public enum DataType {
	
	  UNDEF	( 0)
	, BYTE	( 1)
	, WORD	( 2)
	;

	private int size;
	
	private DataType(final int aSize) { size = aSize; }
	
	public int getSize() { return size; }
	
} // mune
