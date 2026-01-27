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


package pcf8574;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.BIT_STATE;
import gpio.pcf8574.PCF_8574;
import gpio.pcf8574.impl.BIT_NUMBER;
import i2cDevice.I2CErrorException;
import pcf8574.impl.PCF_8574_Cli;

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
// Created at 2026-01-26 11:19:13

public class PCF_8574_Test {		 // NOSONAR

	private final Logger logger = LoggerFactory.getLogger(PCF_8574_Test.class.getName());

	private PCF_8574 pcf8574;
	private int persistentRun;
	private byte value;
	
	
	/**
	 * 
	 */
	public PCF_8574_Test() {
		
		System.out.println("PCF_8574_Test()");		// NOSONAR

		
	} // LM_75_Test()
	
	
	private void run() {
		
		System.out.println("run()");		// NOSONAR

		try {
				 if(PCF_8574_Cli.rd_bit    != PCF_8574_Cli.NOT_SET)		readBit();		// NOSONAR
			else if(PCF_8574_Cli.set_bit   != PCF_8574_Cli.NOT_SET)		setBit();		// NOSONAR
			else if(PCF_8574_Cli.clear_bit != PCF_8574_Cli.NOT_SET)		clearBit();		// NOSONAR
	
			else if(PCF_8574_Cli.rd_byte == true)						rdByte();		// NOSONAR
			else if(PCF_8574_Cli.wr_byte != PCF_8574_Cli.UNDEF)			wrByte();		// NOSONAR
	
			else if(PCF_8574_Cli.toggle_byte != PCF_8574_Cli.UNDEF)		toggle();		// NOSONAR
				 
			else logger.info("no valid option found: {}", PCF_8574_Cli.asString());		// NOSONAR

		}
		catch (I2CErrorException e) {
		
			logger.error("I2C r/w error:", e); // NOSONAR

		} // yrt

		logger.info("terminating ...");

	} // run()


	/**
	 * @throws I2CErrorException 
	 * 
	 */
	private void readBit() throws I2CErrorException {
		logger.info("readBit()");

		logger.info("PCF_8574 readBit: {}", String.format("%s", 
				pcf8574.readBit(BIT_NUMBER.values()[PCF_8574_Cli.rd_bit])));

	} // readBit()


	/**
	 * @throws I2CErrorException 
	 * 
	 */
	private void setBit() throws I2CErrorException {
		logger.info("setBit()");
		
		logger.info("PCF_8574 setBit: {}", String.format("0x%02X", pcf8574.readByte()));		// NOSONAR
		
		pcf8574.writeBit(BIT_NUMBER.values()[PCF_8574_Cli.set_bit], BIT_STATE.HIGH);
		
		logger.info("PCF_8574 setBit: {}", String.format("0x%02X", pcf8574.readByte()));

	} // setBit()


	/**
	 * @throws I2CErrorException 
	 * 
	 */
	private void clearBit() throws I2CErrorException {
		logger.info("clearBit()");
		
		logger.info("PCF_8574 clearBit: {}", String.format("0x%02X", pcf8574.readByte()));
		
		pcf8574.writeBit(BIT_NUMBER.values()[PCF_8574_Cli.clear_bit], BIT_STATE.LOW);
		
		logger.info("PCF_8574 clearBit: {}", String.format("0x%02X", pcf8574.readByte()));

	} // clearBit()


	/**
	 * @throws I2CErrorException 
	 * 
	 */
	private void rdByte() throws I2CErrorException {

		logger.info("PCF_8574 rdByte: {}", String.format("rdByte byte 0x%02X", pcf8574.readByte()));		// NOSONAR

	} // rdByte()


	/**
	 * @throws I2CErrorException 
	 * 
	 */
	private void wrByte() throws I2CErrorException {
		logger.info("wrByte()");
		
		value = (byte) PCF_8574_Cli.hex2dec(PCF_8574_Cli.wr_byte);		 // NOSONAR

		logger.info("PCF_8574 wrByte: {}", String.format("wrByte byte 0x%02X", value));
		
		pcf8574.writeByte(value);
		
	} // wrByte()


	/**
	 * 
	 */
	protected void toggle() {
		logger.info("toggle()");
		
		do { // NOSONAR
			
			try {
				
				logger.info("PCF_8574 wr: {}", String.format("toggle byte 0x%02X", value));

				pcf8574.writeByte(value);
				
				logger.info("PCF_8574 rd: {}", String.format("toggle byte 0x%02X", pcf8574.readByte()));
				
				value = (byte) (value ^ 0x00FF);
				
				Thread.sleep(10_000);
				
			} catch (I2CErrorException e) {
				
				logger.error("I2C r/w error:", e); // NOSONAR

			} catch (InterruptedException e) {
				
				logger.error("Thread error:", e);

			} // yrt
			
		} while(persistentRun != 0);
		
	} // toggle()
	
	
	private void init(final String[] args) {
		
		System.out.println("init()");		// NOSONAR

		CmdLineParser cliParser = new CmdLineParser(new PCF_8574_Cli());		 // NOSONAR

		try {
			
			cliParser.parseArgument(args);
			
		} catch (CmdLineException e) {
			
			System.err.println(e.getMessage());		 // NOSONAR
			System.exit(1);
			
		} // yrt
		
		persistentRun = PCF_8574_Cli.run;		 // NOSONAR
		
		pcf8574 = new PCF_8574(PCF_8574_Cli.bus, PCF_8574_Cli.getAddress());		 // NOSONAR
	
	} // init
	

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		
		System.out.println("main()");		// NOSONAR

		
		PCF_8574_Test myself = new PCF_8574_Test();
		
		myself.init(args);
		myself.run();

	} // main()
	

} // ssalc
