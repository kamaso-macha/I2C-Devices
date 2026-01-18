/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : Ads1115AInput.java
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


package adc.ads1115;

import adc.ads1115.configParameter.MUX;
import adc.ads1115.configParameter.PGA;


public class Ads1115AInput {

	public final MUX mux;
	public final PGA pga;
	

	public Ads1115AInput(final MUX aMux, final PGA aPga) {
		
		if(aMux == null) throw new IllegalArgumentException("aMux can't be null.");
		if(aPga == null) throw new IllegalArgumentException("aPga can't be null.");
		
		mux = aMux;
		pga = aPga;
		
	}

	@Override
	public String toString() {
		return "Ads1115AInput [mux=" + mux + ", pga=" + pga + "]";
	}
	

} // ssalc
