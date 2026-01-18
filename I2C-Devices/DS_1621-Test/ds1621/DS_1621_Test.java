/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : DS_1621_Test.java
 *
 * More information about this project can be found on Github
 * http://github.com/kamaso-macha/I2CDevisesTest
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


package ds1621;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import i2cDevice.I2CErrorException;
import tempSensor.ds1621.DS_1621;
import tempSensor.ds1621.configParameter.ONE_SHOT;
import tempSensor.ds1621.configParameter.POL;
import tempSensor.ds1621.configParameter.PRECISION;

/**
 * Description:<br>
 * example of how to use DS_1621 class. 
 * 
 * <p>
 * @author Stefan
 *
 */

public class DS_1621_Test {		// NOSONAR

	private final Logger logger = LoggerFactory.getLogger(DS_1621_Test.class.getName());

	private DS_1621 ds1621;
	private int persistentRun = 0;
	
	
	private void run() {
		
		do { // NOSONAR
			
			try {
				
				logger.info("DS 1621: {}", ds1621.readTemperature());
				
				Thread.sleep(10_000);
				
			} catch (I2CErrorException e) {
				
				logger.error("I2C r/w error:", e);		// NOSONAR

			} catch (InterruptedException e) {
				
				logger.error("Thread error:", e);

			} // yrt
			
		} while(persistentRun != 0);
		
	} // run()
	
	
	private void init(final String[] args) {
		
		CmdLineParser cliParser = new CmdLineParser(new DS_1621_Cli());		 // NOSONAR

		try {
			
			cliParser.parseArgument(args);
			
		} catch (CmdLineException e) {
			
			e.printStackTrace();
			
		} // yrt
		
		persistentRun = DS_1621_Cli.run;		 // NOSONAR
		
		ONE_SHOT	oneShot 	= ONE_SHOT.value2type(DS_1621_Cli.one_shot);
		POL			pol			= POL.value2type(DS_1621_Cli.pol);
		
		PRECISION precicision = null;
		
		if(DS_1621_Cli.precicision != DS_1621_Cli.NOT_SET)		 // NOSONAR
			precicision	= PRECISION.values()[DS_1621_Cli.precicision];
		
		try {
			
			ds1621 = new DS_1621(DS_1621_Cli.bus, DS_1621_Cli.getAddress());		 // NOSONAR
			
			if(oneShot		!= null)	ds1621.withConversionMode(oneShot);
			if(pol			!= null)	ds1621.withOutputPolarity(pol);
			if(precicision	!= null)	ds1621.withPrecicision(precicision);
			
			if(DS_1621_Cli.th != DS_1621_Cli.TH)	ds1621.setTh(DS_1621_Cli.th);
			if(DS_1621_Cli.tl != DS_1621_Cli.TL)	ds1621.setTh(DS_1621_Cli.tl);
				
		} catch (I2CErrorException e) {

			e.printStackTrace();
			
		} // yrt
		
	} // init
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DS_1621_Test myself = new DS_1621_Test();
		
		myself.init(args);
		myself.run();

	} // main()

} // ssalc
