/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : Ads1115DataRegisterBase.java
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
import adc.ads1115.configParameter.PGA;
import i2cDevice.I2CErrorException;
import i2cDevice.register.AddressableRegister;
import i2cDevice.register.DataType;


public abstract class Ads1115DataRegisterBase extends AddressableRegister {

	private final Logger logger = LoggerFactory.getLogger(Ads1115DataRegisterBase.class.getName());

	private double lsbSize;

	public static final PGA DEFAULT_PGA = PGA.FSR_2_048V;
	
	
	public Ads1115DataRegisterBase(I2C aI2cDevice, Ads1115RegisterMap aAds1115Register, DataType aDataType) {
		super(aI2cDevice, aAds1115Register.value, aDataType);
		
		logger.trace("DataRegisterBase(): aI2cDevice = {}, aAds1115Register = {}, aDataType = {}", 
				aI2cDevice, aAds1115Register, aDataType);

	} // DataRegisterBase()

	
	public double read(final PGA aPga) throws I2CErrorException {
		logger.trace("read(): aPga = {}", aPga);
		
		setPga(aPga);
		readRaw();
		
		int rawInt = (raw[0] << 8) + (raw[1] & 0x00FF);
		
		return (double) (lsbSize * rawInt);
		
	} // read()
	
	
	public void write(final double aValue, final PGA aPga) throws I2CErrorException {
		logger.trace("write(): aValue = {},  aPga = {}", aValue, aPga);

		setPga(aPga);
		
		int rawInt = (int) (aValue / lsbSize);
		
		raw[0] = (byte) ((rawInt & 0x0FFFF) >> 8);
		raw[1] = (byte) (rawInt & 0x00FF);
		
		logger.debug(String.format("aValue = %f, lsbSize = %f, rawInt = 0x%04X, raw[0] = 0x%02X, raw[1] = 0x%02X",
			aValue,
			lsbSize,
			rawInt,
			raw[0],
			raw[1]
			));
		
		writeRaw();
		
	} // write
	
	
	protected void setPga(final PGA aPga) { 
		logger.trace("setPga(): aPga = {}", aPga);
	
		if(aPga == null) throw new IllegalArgumentException("aPga can't be null.");
		
		lsbSize = aPga.lsbSize; 
		
	} // setPga()
	
	
} // ssalc
