/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS_1621.java
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


package tempSensor.ds1621;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import i2cDevice.I2CDeviceBase;
import i2cDevice.I2CErrorException;
import tempSensor.ds1621.configParameter.DONE;
import tempSensor.ds1621.configParameter.NVB;
import tempSensor.ds1621.configParameter.ONE_SHOT;
import tempSensor.ds1621.configParameter.POL;
import tempSensor.ds1621.configParameter.PRECISION;
import tempSensor.ds1621.configParameter.THF;
import tempSensor.ds1621.configParameter.TLF;
import tempSensor.ds1621.registerSet.DS1621ConfigRegister;
import tempSensor.ds1621.registerSet.DS1621TempRegister;
import tempSensor.ds1621.registerSet.DS1621TempRegister0p5;
import tempSensor.ds1621.registerSet.DS1621TempRegister1p0;
import tempSensor.ds1621.registerSet.DS1621TempRegisterHiRes;
import tempSensor.ds1621.registerSet.DS1621ThRegister;
import tempSensor.ds1621.registerSet.DS1621TlRegister;

/**
 * Implementation of Dallas DS 1621 temperature sensor.
 * <p>
 * Please read the related documentation provided by Dallas to learn, 
 * what this device can do and how it does it.
 * <p>
 * In ONE_SHOT mode, a StartConvert command is issued by readTemperature() invocation.
 * In CONTINUOS mode, this StartConvert command is omitted.
 * 
 * To start the CONTINUOS mode, invoke a withConversionMode(ONE_SHOT.CONTINUOUSLY)
 * To stop the CONTINUOS mode, invoke a withConversionMode(ONE_SHOT.ONE_SHOT)
 * 
 * Default precision is RES_1_0_DEGREE.
 * 
 * <p>
 * @author Stefan
 *
 */

public class DS_1621  extends I2CDeviceBase {		// NOSONAR
	
    private final Logger logger = LoggerFactory.getLogger(DS_1621.class.getName());
	
	protected static final byte ADR_LOW	= 0x48;
	protected static final byte ADR_HIG	= 0x4F;
	
	protected static final float T_MAX	= 125.0f;
	protected static final float T_MIN	= -55.0f;
	
	protected static final float T2_MAX	= 100.0f;	// values for 2% accuracy
	protected static final float T2_MIN	= -25.0f;
	
	protected DS1621ConfigRegister		configRegister;
	protected DS1621TempRegister		tempRegister;
	protected DS1621ThRegister			tHRegister;
	protected DS1621TlRegister			tLRegister;
	
	
	public DS_1621(final int aI2cBus, final int aI2cAddress) {
		super(aI2cBus, aI2cAddress);
		
		if(aI2cAddress < ADR_LOW || aI2cAddress > ADR_HIG)
			throw new IllegalArgumentException("aI2cAddress is out of range 0x48 .. 0x4F.");
		
		logger.info(String.format("DS_1621(): aI2cBusNbr = %d,  aI2cAddress = 0x%02X",  aI2cBus, aI2cAddress));
		
		configRegister	= new DS1621ConfigRegister(i2cDevice);
		tempRegister	= new DS1621TempRegister1p0(i2cDevice, configRegister);
		tHRegister		= new DS1621ThRegister(i2cDevice, configRegister);
		tLRegister		= new DS1621TlRegister(i2cDevice, configRegister);
		
	} // DS_1621(..)
	
	
	public DS_1621 withOutputPolarity(final POL aPolarity) throws I2CErrorException {
		logger.trace("withOutputPolarity(): aPolarity = {}", aPolarity);
		
		configRegister.setPOL(aPolarity);
		
		return this;
		
	} // withOutputPolarity()
	
	
	public DS_1621 withConversionMode(final ONE_SHOT aConversionMode) throws I2CErrorException {
		logger.trace("withConversionMode(): aConversionMode = {}", aConversionMode);
		
		configRegister.set1SHOT(aConversionMode);

		return this;
		
	} // withConversionMode()
	
	
	public DS_1621 withPrecicision(final PRECISION aPrecicision) {
		logger.trace("with(): aPrecicision = {}", aPrecicision);
		
		switch(aPrecicision) {
		
			case RES_1_0_DEGREE:	tempRegister = new DS1621TempRegister1p0(i2cDevice, configRegister); break;
			case RES_0_5_DEGREE:	tempRegister = new DS1621TempRegister0p5(i2cDevice, configRegister); break;
			case RES_HIGH:			tempRegister = new DS1621TempRegisterHiRes(i2cDevice, configRegister); break;
		
			default:				tempRegister = new DS1621TempRegister1p0(i2cDevice, configRegister); break;
			
		} // hctiws
		
		return this;
		
	} // withPrecicision()
	
	
	public DONE getDone() throws I2CErrorException {
		logger.trace("getDone()");
		
		return configRegister.getDONE();
		
	} // getDone()
	
	
	public THF getThf() throws I2CErrorException {
		logger.trace("getThf()");
		
		return configRegister.getTHF();
		
	} // getThf()
	
	
	public TLF getTlf() throws I2CErrorException {
		logger.trace("getTlf()");
		
		return configRegister.getTLF();
		
	} // getTlf()
	
	
	public void set1Shot(final ONE_SHOT aOneShot) throws I2CErrorException {
		logger.trace("set1Shot(): aOneShot = {}", aOneShot);
		
		if(aOneShot == null) throw new IllegalArgumentException("aOneShot can't be null.");
		
		configRegister.set1SHOT(aOneShot);
		
	} // set1Shot()
	
	
	public void setTh(final float aTh) throws I2CErrorException {
		logger.trace("setTh(): aTh = {}", aTh);
		
		whileNvbBusy();
		tHRegister.write(aTh);
		
	} // setTh()


	public void setTl(final float aTl) throws I2CErrorException {
		logger.trace("setTl(): aTl = {}", aTl);
		
		whileNvbBusy();
		tLRegister.write(aTl);
		
	} // setTl()
	
	
	public float readTemperature() throws I2CErrorException { 
		logger.trace("readTemperature()");
		
		return tempRegister.readTemperature(); 
		
	} // readTemperature()
	
		
	public float readTh() throws I2CErrorException {
		logger.trace("readTh()");
		
		return tHRegister.read(); 
		
	} // readTh()
	
	
	public float readTl() throws I2CErrorException {
		logger.trace("readTl()");
		
		return tLRegister.read(); 
		
	} // readTl()
	
	
	protected void whileNvbBusy() throws I2CErrorException {
		logger.trace("whileNvbBusy()");
		
		while(configRegister.getNVB() == NVB.IN_PROGRESS) {
			
			try { Thread.sleep(5); } 
			catch (InterruptedException e) { e.printStackTrace(); }
			
		}
		
	} // whileNvbBusy()
	
	
} // ssalc
