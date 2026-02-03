/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : BH_1750_Test.java
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


package bh1750;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import i2cDevice.I2CErrorException;
import lightSensor.bh1750.BH_1750;
import lightSensor.bh1750.configParameter.MEASUREMENT_MODE;
import lightSensor.bh1750.configParameter.POWER_DOWNN_MODE;
import lightSensor.bh1750.configParameter.RESOLUTION_MODE;


/**
 * Description:<br>
 * example of how to use BH_1750 class. 
 * 
 * <p>
 * @author Stefan
 *
 */


public class BH_1750_Test { // NOSONAR

	private final Logger logger = LoggerFactory.getLogger(BH_1750_Test.class.getName());
	
	private BH_1750 bh1750;
	private int persistentRun = 0;

	private RESOLUTION_MODE resolution_mode = null;
	private float sensitivity = BH_1750_Cli.sensitivity;
	
	
	/**
	 * 
	 */
	public BH_1750_Test() {
		
		System.out.println("BH_1750_Test()");		// NOSONAR

		
	} // BH_1750_Test()
	
	
	private void run() {		// NOSONAR
		
		System.out.println("run()");		// NOSONAR

		float[] sensValues = new float[] {
				0.45f,
				1.0f,
				1.5f,
				2.0f,
				2.5f,
				3.0f,
				3.5f,
				3.68f
			};
		
		do { // NOSONAR
			
			try {
				
				logger.debug("read sensor ...");

				if(resolution_mode == null) {
					
					for(RESOLUTION_MODE res : RESOLUTION_MODE.values()) {
						
						bh1750.withResolutionMode(res);

						if(sensitivity == (float) BH_1750_Cli.NOT_SET) {		// NOSONAR
							
							for(float sens : sensValues) {
								
								bh1750.setSensitivity(sens);

								logger.info(String.format("BH_1750 brightness: %.2f, res = %6S, sens = %.2f", bh1750.read(), res, sens));

							} // rof sens
							
							logger.info("-----------------------------------\n");		// NOSONAR
							
						}
						else {

							logger.info(String.format("BH_1750 brightness: %.2f, res = %6S\n", bh1750.read(), res));

						} // fi sensitivity
						
					} // rof RESOLUTION_MODE
					
					logger.info("-----------------------------------\n");
				
				}
				else {
					
					logger.info(String.format("BH_1750 brightness: %.2f\n", bh1750.read()));
					
				} // fi resolution_mode
					
				logger.info("-----------------------------------\n");
				
				Thread.sleep(5_000);
				
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

		CmdLineParser cliParser = new CmdLineParser(new BH_1750_Cli());		 // NOSONAR

		try {
			
			cliParser.parseArgument(args);
			
		} catch (CmdLineException e) {
			
			System.err.println(e.getMessage());	// NOSONAR
			System.exit(1);
			
		} // yrt
		
		persistentRun = BH_1750_Cli.run;													// NOSONAR
		
		POWER_DOWNN_MODE power_down_mode = null;											// NOSONAR
		
		if(BH_1750_Cli.power_down_mode != BH_1750_Cli.NOT_SET)								// NOSONAR
			power_down_mode = POWER_DOWNN_MODE.values()[BH_1750_Cli.power_down_mode];		// NOSONAR
		
		MEASUREMENT_MODE measurement_mode = null;											// NOSONAR
		
		if(BH_1750_Cli.measurement_mode != BH_1750_Cli.NOT_SET)								// NOSONAR
			measurement_mode = MEASUREMENT_MODE.values()[BH_1750_Cli.measurement_mode];		// NOSONAR

		if(BH_1750_Cli.resolution_mode != BH_1750_Cli.NOT_SET)								// NOSONAR
			resolution_mode	= RESOLUTION_MODE.values()[BH_1750_Cli.resolution_mode];		// NOSONAR
		
		if(BH_1750_Cli.sensitivity != (float) BH_1750_Cli.NOT_SET)							// NOSONAR
			sensitivity = BH_1750_Cli.sensitivity;
		
		try {
			
			bh1750 = new BH_1750(BH_1750_Cli.bus, BH_1750_Cli.getAddress());	// NOSONAR

			if(power_down_mode 	!= null)	bh1750.withPowerDownMode(power_down_mode);			
			if(measurement_mode != null)	bh1750.withMeasurementMode(measurement_mode);	
			if(resolution_mode 	!= null)	bh1750.withResolutionMode(resolution_mode);		
			
			if(sensitivity != (float)BH_1750_Cli.NOT_SET)	// NOSONAR
				bh1750.withSensitivity(sensitivity);
			
		} catch (I2CErrorException e) {

			e.printStackTrace();
			
		} // yrt
	
	} // init
	

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		
		System.out.println("main()");		// NOSONAR

		
		BH_1750_Test myself = new BH_1750_Test();
		
		myself.init(args);
		myself.run();

	} // main()
	

} // ssalc
