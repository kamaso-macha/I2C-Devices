/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : ConfigRegister.java
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


package adc.ads1115.registerSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.i2c.I2C;

import adc.ads1115.configParameter.Ads1115RegisterMap;
import adc.ads1115.configParameter.COMP_LAT;
import adc.ads1115.configParameter.COMP_MODE;
import adc.ads1115.configParameter.COMP_POL;
import adc.ads1115.configParameter.COMP_QUE;
import adc.ads1115.configParameter.DR;
import adc.ads1115.configParameter.MODE;
import adc.ads1115.configParameter.MUX;
import adc.ads1115.configParameter.OS_Ro;
import adc.ads1115.configParameter.OS_Wo;
import adc.ads1115.configParameter.PGA;
import i2cDevice.I2CErrorException;
import i2cDevice.config.AddressableConfigRegister;
import i2cDevice.register.DataType;


public class Ads1115ConfigRegister extends AddressableConfigRegister {

	private final Logger logger = LoggerFactory.getLogger(Ads1115ConfigRegister.class.getName());

	/**
	 * @param aI2cDevice
	 * @throws I2CErrorException 
	 */
	public Ads1115ConfigRegister(final I2C aI2cDevice) {
		super(aI2cDevice, Ads1115RegisterMap.CONFIG.value, DataType.WORD);
		
		logger.trace("Ads1115ConfigRegister(): aI2cDevice = {}",  aI2cDevice);

	} // Ads1115ConfigRegister()

	
	public DR getDr() throws I2CErrorException {
		logger.trace("getDr()");
		
		int mask = DR.SPS_860.getClearMask() ^ 0x0FFFF; 
		int value = (config & mask) >> DR.LSB_POSITION;

		logger.debug("getDr(): value = {}", value);

		return DR.value2type(value);
		
	} // getDr()
	
	
	/**
	 * @return
	 * @throws I2CErrorException 
	 */
	public MODE getMode() throws I2CErrorException {
		logger.trace("getMode()");
				
		int mask = MODE.SINGLE_SHOT.getClearMask() ^ 0x0FFFF; 
		int value = (config & mask) >> MODE.LSB_POSITION;
		
		logger.debug(String.format("config = 0x%04X, mask = 0x%04X, value = 0x%04X, MODE = %s", config, mask, value, MODE.value2type(value)));

		return MODE.value2type(value);
		
	} // getMode()
	
	
	public OS_Ro getOs() throws I2CErrorException {
		logger.trace("getOs()");
		
		getConfig();
		
		int mask = OS_Ro.CONVERSION_RUNNING.getClearMask() ^ 0x0FFFF; 
		int value = (config & mask) >> OS_Ro.LSB_POSITION;

		logger.debug("getOs(): value = {}", value);

		return OS_Ro.value2type(value);
		
	} // getOs()
	
	
	public void setOs(final OS_Wo aOs) throws I2CErrorException {
		logger.trace("setOs(): aOs = {}", aOs);
		
		setConfig(aOs);
		
	} // setOs()
	
	
	public void setMux(final MUX aMux) throws I2CErrorException {
		logger.trace("setMux()");
		
		setConfig(aMux);
		
	} // setMux()
	
	
	public void setPga(final PGA aPga) throws I2CErrorException {
		logger.trace("setPga()");
		
		setConfig(aPga);
		
	} // setPga()
	
	
	public void setMode(final MODE aMode) throws I2CErrorException {
		logger.trace("setMode()");
		
		setConfig(aMode);

		// delay for first conversion.
		try { Thread.sleep(getDr().conversionTime); } 
		catch (InterruptedException e) { e.printStackTrace(); }

		
	} // setMode()
	
	
	public void setDr(final DR aDr) throws I2CErrorException {
		logger.trace("setDr()");
		
		setConfig(aDr);
		
	} // setDr()
	
	
	public void setCompMode(final COMP_MODE aCompMode) throws I2CErrorException {
		logger.trace("setCompMode()");
		
		setConfig(aCompMode);
		
	} // setCompMode()
	
	
	public void setCompPol(final COMP_POL aCompPol) throws I2CErrorException {
		logger.trace("setCompPol()");
		
		setConfig(aCompPol);
		
	} // setCompPol()
	
	
	public void setCompLat(final COMP_LAT aCompLat) throws I2CErrorException {
		logger.trace("setCompLat()");
		
		setConfig(aCompLat);
		
	} // setCompLat()
	
	
	public void setCompQue(final COMP_QUE aCompQue) throws I2CErrorException {
		logger.trace("setCompQue()");
		
		setConfig(aCompQue);
		
	} // setCompQue()


} // ssalc
