/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : LM_75.java
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


package tempSensor.lm75;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import i2cDevice.I2CDeviceBase;
import i2cDevice.I2CErrorException;
import tempSensor.lm75.configParameter.CMP_INT;
import tempSensor.lm75.configParameter.FAULT_QUEUE;
import tempSensor.lm75.configParameter.OS_POLARITY;
import tempSensor.lm75.configParameter.SHUTDOWN_MODE;
import tempSensor.lm75.registerSet.Lm75ConfigRegister;
import tempSensor.lm75.registerSet.Lm75THystRegister;
import tempSensor.lm75.registerSet.Lm75TOsRegister;
import tempSensor.lm75.registerSet.Lm75TempRegister;

/**
 * Responsibilities:<br>
 * 
 * 
 * <p>
 * Collaborators:<br>
 * 
 * 
 * <p>
 * Description:<br>
 * Implementation of Texas Instruments LM 751 temperature sensor.
 * <p>
 * Please read the related documentation provided by Ti to learn, 
 * what this device can do and how it does it.
 * <p>
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


public class LM_75 extends I2CDeviceBase { // NOSONAR
	
    private final Logger logger = LoggerFactory.getLogger(LM_75.class.getName());
	
	protected static final byte ADR_LOW	= 0x48;
	protected static final byte ADR_HIG	= 0x4F;
	
	public static final int T_MAX	= 125;
	public static final int T_MIN	= -55;
	
	public static final int T2_MAX	= 100;	// values for 2% accuracy
	public static final int T2_MIN	= -25;
	
	protected Lm75ConfigRegister	configRegister;
	
	protected Lm75TempRegister		tempRegister;
	protected Lm75TOsRegister		tOsRegister;
	protected Lm75THystRegister		tHystRegister;

	private SHUTDOWN_MODE shutdownMode;
	
	
	
	public LM_75(final int aI2cBus, final int aI2cAddress) {
		super(aI2cBus, aI2cAddress);
		
		if(aI2cAddress < ADR_LOW || aI2cAddress > ADR_HIG)
			throw new IllegalArgumentException("aI2cAddress is out of range 0x48 .. 0x4F.");
		
		logger.trace(String.format("LM75(): aI2cBus = %d, aI2cAddress = 0x%02X", aI2cBus, aI2cAddress));
		
		configRegister	= new Lm75ConfigRegister(i2cDevice);
		tempRegister	= new Lm75TempRegister(i2cDevice);
		tOsRegister		= new Lm75TOsRegister(i2cDevice);
		tHystRegister	= new Lm75THystRegister(i2cDevice);
		
		
	} // LM75(..)
	
	
	public LM_75 withOsMode(final CMP_INT aOsMode) throws I2CErrorException {
		logger.trace("withOsMode(): aOsMode = {}", aOsMode);
		
		if(aOsMode == null) 
			throw new IllegalArgumentException("aOsMode can't be null.");

		configRegister.setCmpInt(aOsMode);
		
		return this;
		
	} // withOsMode()
	
	
	public LM_75 withOsPolarity(final OS_POLARITY aOsPolarity) throws I2CErrorException {
		logger.trace("withOsPolarity(): aOsPolarity = {}", aOsPolarity);
		
		if(aOsPolarity == null) 
			throw new IllegalArgumentException("aOsPolarity can't be null.");

		configRegister.setOsPolarity(aOsPolarity);
		
		return this;
		
	} // withOsMode()
	
	
	public LM_75 withShutdownMode(final SHUTDOWN_MODE aShutdownMode) throws I2CErrorException {
		logger.trace("withShutdownMode(): aShutdownMode = {}", aShutdownMode);
		
		setShutdown(aShutdownMode);
		
		return this;
		
	} // withShutdownMode()
	
	
	public LM_75 withFaultQueue(final FAULT_QUEUE aFaultQueue) throws I2CErrorException {
		logger.trace("withFaultQueue(): aFaultQueue = {}", aFaultQueue);
		
		if(aFaultQueue == null) 
			throw new IllegalArgumentException("aFaultQueue can't be null.");

		configRegister.setFaultQueue(aFaultQueue);
		
		return this;
		
	} //withFaultQueue()
	
	
	public void setTOs(final int aTOs) throws I2CErrorException {
		logger.trace("setTOs(): aTOs = {}", aTOs);
		
		if(aTOs < T_MIN || aTOs > T_MAX) 
			throw new IllegalArgumentException("aTos is out of range.");
		
		tOsRegister.write(aTOs);
		
	} // setTOs()

	
	public void setTHyst(final int aTHyst) throws I2CErrorException {
		logger.trace("setTHyst(): aTHyst = {}", aTHyst);
		
		if(aTHyst < T_MIN || aTHyst > T_MAX) 
			throw new IllegalArgumentException("aTHyst is out of range.");

		tHystRegister.write(aTHyst);
		
	} // setTOs()


	public void setShutdown(final SHUTDOWN_MODE aShutdownMode) throws I2CErrorException {
		logger.trace("setTHyst(): aShutdownMode = {}", aShutdownMode);
		
		if(aShutdownMode == null) 
			throw new IllegalArgumentException("aShutdownMode can't be null.");
		
		shutdownMode = aShutdownMode;
		
		configRegister.setShutdown(aShutdownMode);

		logger.debug("shutdownMode = {}", shutdownMode);

	} // setTOs()


	public float readTemp() throws I2CErrorException {
		
		logger.debug("shutdownMode = {}", shutdownMode);

		if(shutdownMode == SHUTDOWN_MODE.SHUTDOWN) {
			configRegister.setShutdown(SHUTDOWN_MODE.RUN_PERSISTENT);
		}
				
		float temp = tempRegister.read();
		
		if(shutdownMode == SHUTDOWN_MODE.SHUTDOWN) {
			configRegister.setShutdown(shutdownMode);
		}

		logger.trace("readTemp(): temp = {}", temp);
		
		return temp;
		
	} // readTemp()
	
	
	public float readTOs() throws I2CErrorException {
		
		float tOs = tOsRegister.read();
		
		logger.trace("readTOs(): temp = {}", tOs);
		
		return tOs;
		
	} // readTOs()
	
	
	public float readTHyst() throws I2CErrorException {

		float tHyst = tHystRegister.read();
		
		logger.trace("readTHyst(): tHyst = {}", tHyst);
		
		return tHyst;
		
	} // readTHyst()
	
	
} //ssalc

