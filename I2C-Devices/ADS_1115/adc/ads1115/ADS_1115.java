/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : ADS_1115.java
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import adc.ads1115.configParameter.COMP_LAT;
import adc.ads1115.configParameter.COMP_MODE;
import adc.ads1115.configParameter.COMP_POL;
import adc.ads1115.configParameter.COMP_QUE;
import adc.ads1115.configParameter.DR;
import adc.ads1115.configParameter.MODE;
import adc.ads1115.configParameter.OS_Ro;
import adc.ads1115.configParameter.OS_Wo;
import adc.ads1115.configParameter.PGA;
import adc.ads1115.registerSet.Ads1115ConfigRegister;
import adc.ads1115.registerSet.Ads1115ConversionRegister;
import adc.ads1115.registerSet.Ads1115HiThresholdRegister;
import adc.ads1115.registerSet.Ads1115LoThresholdRegister;
import i2cDevice.I2CDeviceBase;
import i2cDevice.I2CErrorException;

/**
 * Implementation of Texas Instruments ADS 1115 analog to digital converter.
 * <p>
 * Please read the related documentation provided by Ti to learn, 
 * what this device can do and how it does it.
 * <p>
 */

// DOC
// Created at 2026-01-05 15:07:46

public class ADS_1115 extends I2CDeviceBase { // NOSONAR

	private final Logger logger = LoggerFactory.getLogger(ADS_1115.class.getName());

	protected static final byte ADR_LOW	= 0x48;
	protected static final byte ADR_HIG	= 0x4F;

	protected Ads1115ConfigRegister configRegister;

	protected Ads1115ConversionRegister conversionRegister;
	protected Ads1115HiThresholdRegister hiThresholdRegister;
	protected Ads1115LoThresholdRegister loThresholdRegister;


	public ADS_1115(final int aI2cBus, final int aI2cAddress) {
		super(aI2cBus, aI2cAddress);
		
		if(aI2cAddress < ADR_LOW || aI2cAddress > ADR_HIG)
			throw new IllegalArgumentException("aI2cAddress is out of range 0x48 .. 0x4F.");
		
		logger.trace(String.format("ADS_1115(): aI2cBus = %d, aI2cAddress = 0x02X", aI2cBus, aI2cAddress));
		
		configRegister		= new Ads1115ConfigRegister(i2cDevice);
		conversionRegister	= new Ads1115ConversionRegister(i2cDevice);
		hiThresholdRegister	= new Ads1115HiThresholdRegister(i2cDevice);
		loThresholdRegister	= new Ads1115LoThresholdRegister(i2cDevice);
		
	} // ADS_1115()
	
	
	public void withMode(final MODE aMode) throws I2CErrorException {
		logger.trace("withMode(): aMode = {}", aMode);
		
		setMode(aMode);
		
	} // withMode()
	
	
	public void withDr(final DR aDr) throws I2CErrorException {
		logger.trace("withDr(): aDr = {}", aDr);
		
		setDr(aDr);
		
	} // withDr()
	
	
	public void withCompMode(final COMP_MODE aCompMode) throws I2CErrorException {
		logger.trace("withCompMode(): aCompMode = {}", aCompMode);
		
		if(aCompMode == null) throw new IllegalArgumentException("aCompMode can't be null.");

		configRegister.setCompMode(aCompMode);
		
	} // withCompMode()
	
	
	public void withCompPol(final COMP_POL aCompPol) throws I2CErrorException {
		logger.trace("withCompPol(): aCompPol = {}", aCompPol);
		
		if(aCompPol == null) throw new IllegalArgumentException("aCompPol can't be null.");

		configRegister.setCompPol(aCompPol);
		
	} // withCompPol()
	
	
	public void withCompLat(final COMP_LAT aCompLat) throws I2CErrorException {
		logger.trace("withCompLat(): aCompLat = {}", aCompLat);
		
		if(aCompLat == null) throw new IllegalArgumentException("aCompLat can't be null.");

		configRegister.setCompLat(aCompLat);
		
	} // withCompLat()
	
	
	public void withCompQue(final COMP_QUE aCompQue) throws I2CErrorException {
		logger.trace("withCompQue(): aCompQue = {}", aCompQue);
		
		setCompQue(aCompQue);
		
	} // withCompQue()
	
	
	public OS_Ro getOs() throws I2CErrorException {
		logger.trace("getOs()" );
		
		return configRegister.getOs();
		
	} // getOs()
	
	
	/**
	 * Reads the voltage an a given input channel.
	 * 
	 * @param aMux the input to select
	 * @return the current voltage at the selected input
	 * @throws I2CErrorException
	 */
	public double readInput(final Ads1115AInput aInput) throws I2CErrorException {
		logger.trace("readInput(): aInput = {}", aInput);

		configRegister.setMux(aInput.mux);
		configRegister.setPga(aInput.pga);
		
		if(configRegister.getMode() == MODE.SINGLE_SHOT) {
			
			configRegister.setOs(OS_Wo.START_SINGLE_CONVERSION);
		
			int conversionTime = configRegister.getDr().conversionTime;
			
			while(configRegister.getOs() == OS_Ro.CONVERSION_RUNNING) {
				
				try { Thread.sleep(conversionTime); } 
				catch (InterruptedException e) { e.printStackTrace(); }
				
			} // elihw
		
		} // fi

		return conversionRegister.read(aInput.pga);
		
	} // readInput()
	
	
	/**
	 * Reads the voltage of high threshold register
	 * 
	 * @return the current voltage set to high threshold register
	 * @throws I2CErrorException
	 */
	public double readHiThresRegister(final PGA aPga) throws I2CErrorException {
		logger.trace("readHiThresRegister(): aPga = {}", aPga);

		return hiThresholdRegister.read(aPga);
		
	} // readHiThresRegister()
	
	
	/**
	 * Reads the voltage of low threshold register
	 * 
	 * @return the current voltage set to low threshold register
	 * @throws I2CErrorException
	 */
	public double readLoThresRegister(final PGA aPga) throws I2CErrorException {
		logger.trace("readLoThresRegister(): aPga = {}", aPga);

		return loThresholdRegister.read(aPga);
		
	} // readLoThresRegister()
	
	
	/**
	 * Set the threshold voltage of the high threshold register.
	 * <p>
	 * The effective content of the register is calculated by the formula
	 * <code>int rawInt = (int) (aValue / LSB_SIZE); </code>
	 * 
	 * @param aValue the threshold voltage to be set.
	 * @throws I2CErrorException
	 */
	public void writeHiThresRegister(final double aValue, final PGA aPga) throws I2CErrorException {
		logger.trace("writeHiThresRegister(): aValue = {},  aPga = {}", aValue, aPga);
		
		hiThresholdRegister.write(aValue, aPga);
		
	} // writeHiThresRegister()
	
	
	/**
	 * Set the threshold voltage of the low threshold register.
	 * <p>
	 * The effective content of the register is calculated by the formula
	 * <code>int rawInt = (int) (aValue / LSB_SIZE); </code>
	 * 
	 * @param aValue the threshold voltage to be set.
	 * @throws I2CErrorException
	 */
	public void writeLoThresRegister(final double aValue, final PGA aPga) throws I2CErrorException {
		logger.trace("writeLoThresRegister(): aValue = {},  aPga = {}", aValue, aPga);
		
		loThresholdRegister.write(aValue, aPga);
		
	} // writeLoThresRegister()
	
	
	public void setMode(final MODE aMode) throws I2CErrorException {
		logger.trace("setMode(): aMode = {}", aMode);
		
		if(aMode == null) throw new IllegalArgumentException("aMode can't be null.");

		configRegister.setMode(aMode);

		if(aMode == MODE.CONTINUOUS) {
			
			// start conversion
			configRegister.setOs(OS_Wo.START_SINGLE_CONVERSION);
		}
		
	} // setMode()
	
	
	public void setDr(final DR aDr) throws I2CErrorException {
		logger.trace("setDr(): aDr = {}", aDr);
		
		if(aDr == null) throw new IllegalArgumentException("aDr can't be null.");

		configRegister.setDr(aDr);
		
	} // setDr()
	
	
	public void setCompQue(final COMP_QUE aCompQue) throws I2CErrorException {
		logger.trace("setCompQue(): aCompQue = {}", aCompQue);
		
		if(aCompQue == null) throw new IllegalArgumentException("aCompQue can't be null.");

		configRegister.setCompQue(aCompQue);
		
	} // setCompQue()
	
	
} // ssalc
