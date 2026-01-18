/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS1621ConfigRegister.java
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


package tempSensor.ds1621.registerSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.i2c.I2C;

import i2cDevice.I2CErrorException;
import i2cDevice.config.AddressableConfigRegister;
import i2cDevice.register.DataType;
import tempSensor.ds1621.configParameter.COMMAND_SET;
import tempSensor.ds1621.configParameter.DONE;
import tempSensor.ds1621.configParameter.NVB;
import tempSensor.ds1621.configParameter.ONE_SHOT;
import tempSensor.ds1621.configParameter.POL;
import tempSensor.ds1621.configParameter.THF;
import tempSensor.ds1621.configParameter.TLF;

/**
 * Description:<br>
 * Default modes:
 * 	OsMode.COMPARATOR
 * 	OsPolarity.ACTIVE_LOW
 * 	TOs			= 80°C
 * 	THyst		= 75°C
 * 	Pointer		= RegisterMap.TEMPERATURE
 * 	FaultQueue	= FaultQueue.LEN_1
 * <p>
 * @author Stefan
 *
 */

public class DS1621ConfigRegister extends AddressableConfigRegister implements DS1621StatusFlags {

    private final Logger logger = LoggerFactory.getLogger(DS1621ConfigRegister.class.getName());
		
	
	public DS1621ConfigRegister(final I2C aI2cDevice) {
		super(aI2cDevice, COMMAND_SET.ACCESS_CONFIG.value, DataType.BYTE);
		
		logger.trace("DS1621ConfigRegister()");
			
	} // DS1621ConfigRegister()	
	

	public DONE getDONE() throws I2CErrorException {
		logger.trace("getDONE()");
		
		return DONE.value2type(getParameter(DONE.COMPLETE.getClearMask(), DONE.LSB_POSITION));

	} // getDONE()
	
	
	public THF getTHF() throws I2CErrorException {
		logger.trace("getTHF()");
		
		return THF.value2type(getParameter(THF.BELOW.getClearMask(), THF.LSB_POSITION));

	} // getTHF()

	
	public TLF getTLF() throws I2CErrorException {
		logger.trace("getTLF()");
		
		return TLF.value2type(getParameter(TLF.BELOW.getClearMask(), TLF.LSB_POSITION));

	} // getTLF()

	
	public NVB getNVB() throws I2CErrorException {
		logger.trace("getNVB()");

		return NVB.value2type(getParameter(NVB.COMPLETE.getClearMask(), NVB.LSB_POSITION));

	} // getNVB()

	
	public ONE_SHOT get1SHOT() throws I2CErrorException {
		logger.trace("get1SHOT()");

		return ONE_SHOT.value2type(getParameter(ONE_SHOT.CONTINUOUSLY.getClearMask(), ONE_SHOT.LSB_POSITION));

	} // get1SHOT()

	
	public void setPOL(final POL aOutputPolarity) throws I2CErrorException {
		logger.trace("setPol()");
		
		setConfig(aOutputPolarity);
		
	} // setPol()
	
	
	public void set1SHOT(final ONE_SHOT aOneShotMode) throws I2CErrorException {
		logger.trace("setOneShot()");
		
		setConfig(aOneShotMode);

		if(aOneShotMode == ONE_SHOT.CONTINUOUSLY) {
			i2cDevice.write(COMMAND_SET.START_CONVERT.value);
		}
		else {
			i2cDevice.write(COMMAND_SET.STOP_CONVERT.value);
		}

	} // setOneShot()
	
	
	protected int getParameter(final int aClearMask, final int lsbPosition) throws I2CErrorException {
		logger.trace("getParameter(): aClearMask = {}, lsbPosition = {}", String.format("%02X", aClearMask), lsbPosition);
		
		getConfig();

		int parameter = config & (aClearMask ^ 0xFF);
		parameter = parameter >>> lsbPosition;
		logger.trace(String.format("config = 0x%02X, parameter = 0x%02X", config & 0x00FF, parameter));	
		
		return parameter;

	} // getParameter()

	
} // DS1621ConfigRegister
