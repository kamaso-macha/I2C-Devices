/**
 *
 * **********************************************************************
 * PROJECT       : I2C-Devices
 * FILENAME      : BH_1750.java
 *
 * More information about this project can be found on Github
 * http://github.com/kamaso-macha/I2C-Devices
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


package lightSensor.bh1750;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import i2cDevice.I2CDeviceBase;
import i2cDevice.I2CErrorException;
import lightSensor.bh1750.configParameter.InstructionSet;
import lightSensor.bh1750.configParameter.MEASUREMENT_MODE;
import lightSensor.bh1750.configParameter.POWER_DOWNN_MODE;
import lightSensor.bh1750.configParameter.RESOLUTION_MODE;

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
 * 
 * 
 * <p>
 * @author Stefan
 *
 */

// DOC
// Created at 2026-01-29 13:56:53

public class BH_1750 extends I2CDeviceBase {	//NOSONAR

	private final Logger logger = LoggerFactory.getLogger(BH_1750.class.getName());

	public static final byte ADR_1 = 0x23;
	public static final byte ADR_2 = 0x5C;
	
	public static final float SENSITIVITY_LOW		= 0.45f;		// MT_REG_MIN
	public static final float SENSITIVITY_DEFAULT	= 1.0f;			// MT_REG_DEFAULT
	public static final float SENSITIVITY_HIGH		= 3.68f;		// MT_REG_MAX
	
	protected static final int MT_REG_MIN			=  31;
	protected static final int MT_REG_MAX			= 254;
	protected static final int MT_REG_DEFAULT		=  69;	// default value from data sheet.
	
	public static final int MT_H_RES = 120;		// ms
	public static final int MT_L_RES =  16;		// ms
	
	// POR values
	protected RESOLUTION_MODE  resolutionMode;
	protected MEASUREMENT_MODE measurementMode;
	protected POWER_DOWNN_MODE powerDownMode; 
	
	protected int mtReg = MT_REG_DEFAULT;
	
	protected byte[] raw = new byte[2];

	
	/**
	 * @param aI2cBusNbr
	 * @param aI2cAddress
	 * @throws I2CErrorException 
	 */
	public BH_1750(final int aI2cBusNbr, final int aI2cAddress) throws I2CErrorException {
		super(aI2cBusNbr, aI2cAddress);
		
		if(aI2cAddress != ADR_1 && aI2cAddress != ADR_2)
			throw new IllegalArgumentException("aI2cAddress must be 0x23 or 0x5C.");
		
		logger.info(String.format("BH_1750(): aI2cBusNbr = %d,  aI2cAddress = 0x%02X",  aI2cBusNbr, aI2cAddress));

		// Power On Reset / recommended values
		setSensitivity(SENSITIVITY_DEFAULT);
		withPowerDownMode(POWER_DOWNN_MODE.POWER_DOWN);
		withMeasurementMode(MEASUREMENT_MODE.ONE_SHOT);
		withResolutionMode(RESOLUTION_MODE.HIGH);
		
	} // BH_1750()
	
	
	public BH_1750 withMeasurementMode(final MEASUREMENT_MODE aMeasurementMode) {
		logger.trace("withMeasurementMode(): aMeasurementMode = {}", aMeasurementMode);
		
		if(aMeasurementMode == null)
			throw new IllegalArgumentException("aMeasurementMode can't be null.");
		
		measurementMode = aMeasurementMode;
		
		return this;
		
	} // withMeasurementMode()
	

	public BH_1750 withPowerDownMode(final POWER_DOWNN_MODE aPowerDownMode) throws I2CErrorException {
		logger.trace("withPowerDownMode(): aPowerDownMode = {}", aPowerDownMode);
		
		if(aPowerDownMode == null)
			throw new IllegalArgumentException("aPowerDownMode can't be null.");
		
		powerDownMode = aPowerDownMode;
		
		byte command = (byte) InstructionSet.RESET.value;	// dummy value to satisfy the compiler ;)
		
		switch(powerDownMode) {		// NOSONAR
		
			case PERMANENT_ON	: command = (byte) InstructionSet.POWER_ON.value; break;
			case POWER_DOWN		: command = (byte) InstructionSet.POWER_DOWN.value; break;
		
		} // hctiws
		
		writeCommand(command);
		
		return this;
		
	} // withPowerDownMode()
	

	public BH_1750 withResolutionMode(final RESOLUTION_MODE aResolutionMode) {
		logger.trace("withResolutionMode(): aResolutionMode = {}", aResolutionMode);
		
		if(aResolutionMode == null)
			throw new IllegalArgumentException("aResolutionMode can't be null.");
		
		resolutionMode = aResolutionMode;
		
		if(aResolutionMode == RESOLUTION_MODE.LOW)
			mtReg = MT_REG_DEFAULT;
		
		return this;
		
	} // withResolutionMode()
	

	public BH_1750 withSensitivity(final float aSensitivity) throws I2CErrorException {
		logger.trace("withSensitivity(): aSensitivity = {}", aSensitivity);
		
		setSensitivity(aSensitivity);
		
		return this;
		
	} // withSensitivity()
	
	
	public float read() throws I2CErrorException {
		logger.trace("read()");
		
		triggerMeasurement();
		
		int result = (raw[0] << 8) & 0x0FF00;
		    result += (raw[1] & 0x000FF);
		
		// prepare 'out of range' return values
		float reading = -1.0f;
		float factor  = -1.0f;
		
		// check if 'in range'
		if(result != 0x0FFFF) {
			
			factor = 1.0f / 1.2f * ((float) MT_REG_DEFAULT / (float) mtReg);
			
			reading = result * factor;
			
			if(resolutionMode == RESOLUTION_MODE.HIGH_2) {
				reading /= 2;
			}
		
		} // fi
		
		float rounded = ((int) ((reading + (reading >= 0 ? 1 : -1) * 0.005f) * 100)) / 100f;
		
		logger.info(String.format("res = %s, raw[0] = 0x%02X, raw[1] = 0x%02X,  result = 0x%04X - %d, mtreg = %d, factor = %f, reading = %f, rounded = %f", 
			resolutionMode,
			raw[0], raw[1],
			result, result,
			mtReg,
			factor,
			reading,
			rounded
		));
		
		// truncate to two decimal places
		return rounded;
		
	} // read()
	
	
	public void reset() {
		logger.trace("reset()");
		
		byte command = (byte) InstructionSet.RESET.value;	// dummy value to satisfy the compiler ;)
		i2cDevice.write(command);
		
	} // reset()


	public void setSensitivity(final float aSensitivity) throws I2CErrorException {
		logger.trace("setSensitivity(): aSensitivity = {}", aSensitivity);
		
		if(aSensitivity < SENSITIVITY_LOW || aSensitivity > SENSITIVITY_HIGH)
			throw new IllegalArgumentException(
				String.format("aSensitivity is out of range %f .. %f.", SENSITIVITY_LOW, SENSITIVITY_HIGH));
		
		if(resolutionMode == RESOLUTION_MODE.LOW) {
			mtReg = MT_REG_DEFAULT;
		}
		else {
			mtReg = (int) (aSensitivity * MT_REG_DEFAULT + 0.08f); // 0.08 to get full range 31 .. 254
		}
		
		byte mt7_5 = (byte) ((mtReg & 0x0E0) >> 5);		// NOSONAR
		byte mt4_0 = (byte) ( mtReg & 0x01F);			// NOSONAR
		
		logger.debug(String.format("res = %s, sens = %f, mtReg = 0x%04X - %d, mt7_5 = 0x%02X, mt4_0 = 0x%02X",
			resolutionMode,
			aSensitivity,
			mtReg, mtReg,
			mt7_5, mt4_0
		));
		
		byte command = (byte) ((InstructionSet.CHG_MEAS_TIME_H.value | mt7_5) & 0x00FF);
		writeCommand(command);
		
		command = (byte) ((InstructionSet.CHG_MEAS_TIME_L.value | mt4_0) & 0x00FF);
		writeCommand(command);
		
	} // setSensitivity()
	
	
	/**
	 * @throws I2CErrorException 
	 * 
	 */
	protected void triggerMeasurement() throws I2CErrorException {
		logger.trace("triggerMeasurement(): measurementMode = {}, resolutionMode = {}, powerDownMode = {}",
				measurementMode, resolutionMode, powerDownMode);

		byte command = (byte) InstructionSet.RESET.value;	// dummy value to satisfy the compiler ;)
		
		if(measurementMode == MEASUREMENT_MODE.ONE_SHOT) {
			
			switch(resolutionMode) {
			
				case LOW	: command = (byte) InstructionSet.ONE_TIME_L_RES.value; break;
				case HIGH	: command = (byte) InstructionSet.ONE_TIME_H_RES.value; break;
				case HIGH_2	: command = (byte) InstructionSet.ONE_TIME_H_RES2.value; break;
			
			} // hctiws
			
		}
		else { // MEASUREMENT_MODE.CONTINUOSLY
			
			switch(resolutionMode) {
			
				case LOW	: command = (byte) InstructionSet.CONT_L_RES.value; break;
				case HIGH	: command = (byte) InstructionSet.CONT_H_RES.value; break;
				case HIGH_2	: command = (byte) InstructionSet.CONT_H_RES2.value; break;
			
			} // hctiws
			
		} // fi
		

		float delayTime = MT_H_RES;
		
		if(resolutionMode == RESOLUTION_MODE.LOW) {
			delayTime = MT_L_RES;
		}
		
		delayTime *= 1.5f * ((float)mtReg / (float)MT_REG_DEFAULT);
		logger.debug(String.format("delayTime = %d, mtReg = %d", (long)delayTime, mtReg));
		
		writeCommand(command);		
		
		try { Thread.sleep((long) delayTime); } 
		catch (InterruptedException e) { e.printStackTrace(); }
		
		readDeviceRaw();		
		
		// shut down after measurement
		if(powerDownMode == POWER_DOWNN_MODE.POWER_DOWN
		&& measurementMode == MEASUREMENT_MODE.CONTINUOSLY) {
			i2cDevice.write((byte)InstructionSet.POWER_DOWN.value);
		}
		
	} // triggerMeasurement()


	/**
	 * @throws I2CErrorException
	 */
	protected void readDeviceRaw() throws I2CErrorException {
		
		int result;
		result = i2cDevice.read(raw);
		
		if(result != 2)
			throw new I2CErrorException("Cant read I2C device.");
		
	} // readDeviceRaw()


	/**
	 * @param command
	 * @throws I2CErrorException
	 */
	protected void writeCommand(byte command) throws I2CErrorException {
		
		int result = i2cDevice.write((command & 0x00FF));

		if(result != 1)
			throw new I2CErrorException("Cant write to I2C device.");
		
	} // writeDevice()
	

}// ssalc
