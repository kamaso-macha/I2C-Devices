/**
 *
 * **********************************************************************
 * PROJECT       : I2C-Devices
 * FILENAME      : PCF_8574.java
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


package gpio.pcf8574.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.BIT_NUMBER_8;
import common.BIT_STATE;
import i2cDevice.I2CDeviceBase;
import i2cDevice.I2CErrorException;

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
// Created at 2026-01-25 16:52:53

public abstract class PCF_8574_Base extends I2CDeviceBase { // NOSONAR

	private final Logger logger = LoggerFactory.getLogger(PCF_8574_Base.class.getName());

	protected byte[] rawBuffer;
	

	/**
	 * @param aI2cBusNbr
	 * @param aI2cAddress
	 */
	public PCF_8574_Base(final int aI2cBusNbr, final int aI2cAddress, final int aAdrLow, final int aAdrHigh) {
		super(aI2cBusNbr, aI2cAddress);

		logger.trace("PCF_8574_Base()");

		if(aI2cAddress < aAdrLow || aI2cAddress > aAdrHigh)
			throw new IllegalArgumentException(String.format("aI2cAddress is out of range 0x%02X .. 0x%02X.", aAdrLow, aAdrHigh));

		rawBuffer = new byte[1];
		
	} // PCF_8574()
	
	
	public BIT_STATE readBit(final BIT_NUMBER_8 aBitNumber) throws I2CErrorException {
		logger.trace("readBit(): aBitNumber = {}", aBitNumber);
		
		byte currentState = readByte();
		int mask = computeMask(aBitNumber);
		
		byte result = (byte) (currentState & mask);
		
		logger.trace("readByte(): {}", String.format("currentState = 0x%02X, mask = 0x%04X, result = 0x%02X", 
				currentState, mask, result));
		
		if(result == 0) {
			return BIT_STATE.LOW;
		}
		else {
			return BIT_STATE.HIGH;
		}
		
	} // readBit()
	
	
	public byte readByte() throws I2CErrorException {
		
		int result = i2cDevice.read(rawBuffer);

		logger.trace("readByte(): result = {}, byte = {}", result, String.format("0x%02X", rawBuffer[0]));

		if(result != 1) {
			throw new I2CErrorException("Can't read from device: result = " + result);
		}
		else {
			return rawBuffer[0];
		}
		
	} // readByte()
	
	
	public void writeBit(final BIT_NUMBER_8 aBitNumber, final BIT_STATE aBitState) throws I2CErrorException {
		logger.trace("writeBit(): aBitNumber = {}, aBitState = {}", aBitNumber, aBitState);
	
		int currentState = readByte();		
		int mask = computeMask(aBitNumber);
		
		int newState = 0x0000;
		
		if(aBitState == BIT_STATE.LOW) {
			
			// clear mask has all bits set except the desired one
			
			newState = currentState & (mask ^ 0xFFFF);
			
		}
		else {
			
			// set mask has only the desired bit set
			
			newState = currentState | mask;
			
		} // fi
		
		writeByte((byte) newState);
		
	} // writeBit()


	public void writeByte(final byte aByte) throws I2CErrorException {
		logger.trace("writeByte(): aByte = {}", String.format("0x%02X", aByte));
		
		rawBuffer[0] = aByte;
		
		int result = i2cDevice.write(rawBuffer);
		
		if(result != 1) {
			throw new I2CErrorException("Can't read from device: result = " + result);
		}
		
	} // writeByte()
	
	
	/**
	 * @param aBitNumber
	 * @param mask
	 * @return
	 */
	protected int computeMask(final BIT_NUMBER_8 aBitNumber) {

		int mask = 1;

		for(int n = 0; n < aBitNumber.ordinal(); n++) {
			mask = mask << 1;
		}
		
		return mask;
		
	} // computeMask()
	
	
} // ssalc
