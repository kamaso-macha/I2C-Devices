/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : LM_75_Test.java
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


package lm75;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import i2cDevice.I2CErrorException;
import tempSensor.lm75.LM_75;
import tempSensor.lm75.configParameter.CMP_INT;
import tempSensor.lm75.configParameter.FAULT_QUEUE;
import tempSensor.lm75.configParameter.OS_POLARITY;
import tempSensor.lm75.configParameter.SHUTDOWN_MODE;


/**
 * Description:<br>
 * example of how to use LM_75 class. 
 * 
 * <p>
 * @author Stefan
 *
 */


public class LM_75_Test { // NOSONAR

	private final Logger logger = LoggerFactory.getLogger(LM_75_Test.class.getName());
	
	private LM_75 lm75;
	private int persistentRun = 0;
	
	
	/**
	 * 
	 */
	public LM_75_Test() {
		
		System.out.println("LM_75_Test()");		// NOSONAR

		
	} // LM_75_Test()
	
	
	private void run() {
		
		System.out.println("run()");		// NOSONAR

		
		do { // NOSONAR
			
			try {
				
				logger.debug("read sensor ...");

				logger.info("LM 75 temp  : {}", lm75.readTemp());
				logger.info("LM 75 TOs   : {}", lm75.readTOs());
				logger.info("LM 75 THyst : {}", lm75.readTHyst());
				
				Thread.sleep(10_000);
				
			} catch (I2CErrorException e) {
				
				logger.error("I2C r/w error:", e); // NOSONAR

			} catch (InterruptedException e) {
				
				logger.error("Thread error:", e);

			} // yrt
			
		} while(persistentRun != 0);

		logger.info("terminating ...");

	} // run()
	
	
	private void init(final String[] args) {
		
		System.out.println("init()");		// NOSONAR

		CmdLineParser cliParser = new CmdLineParser(new LM_75_Cli());		 // NOSONAR

		try {
			
			cliParser.parseArgument(args);
			
		} catch (CmdLineException e) {
			
			System.err.println(e.getMessage());
			System.exit(1);
			
		} // yrt
		
		persistentRun = LM_75_Cli.run;
		
		SHUTDOWN_MODE	shutdownMode	= SHUTDOWN_MODE.value2type(LM_75_Cli.shutdown_mode);
		CMP_INT			cmp_int			= CMP_INT.value2type(LM_75_Cli.cmp_int);					 // NOSONAR
		OS_POLARITY		osPolarity		= OS_POLARITY.value2type(LM_75_Cli.os_polarity);
		FAULT_QUEUE		faultQueue		= FAULT_QUEUE.value2type(LM_75_Cli.fault_queue);
		
		try {
			
			lm75 = new LM_75(LM_75_Cli.bus, LM_75_Cli.getAddress());

			if(shutdownMode != null)	lm75.withShutdownMode(shutdownMode);			
			if(cmp_int 		!= null)	lm75.withOsMode(cmp_int);		
			if(osPolarity 	!= null)	lm75.withOsPolarity(osPolarity);		
			if(faultQueue 	!= null)	lm75.withFaultQueue(faultQueue);
			
			if(LM_75_Cli.tOs != LM_75_Cli.TH)	lm75.setTOs(LM_75_Cli.tOs);
			if(LM_75_Cli.tHyst != LM_75_Cli.TL)	lm75.setTHyst(LM_75_Cli.tHyst);
			
		} catch (I2CErrorException e) {

			e.printStackTrace();
			
		} // yrt
	
	} // init
	

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		
		System.out.println("main()");		// NOSONAR

		
		LM_75_Test myself = new LM_75_Test();
		
		myself.init(args);
		myself.run();

	} // main()
	

} // ssalc
