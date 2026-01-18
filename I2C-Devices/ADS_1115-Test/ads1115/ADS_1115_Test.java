/**
 *
 * **********************************************************************
 * PROJECT       : I2CDevices
 * FILENAME      : ADS_1115_Test.java
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


package ads1115;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import adc.ads1115.ADS_1115;
import adc.ads1115.Ads1115AInput;
import adc.ads1115.configParameter.COMP_LAT;
import adc.ads1115.configParameter.COMP_MODE;
import adc.ads1115.configParameter.COMP_POL;
import adc.ads1115.configParameter.COMP_QUE;
import adc.ads1115.configParameter.DR;
import adc.ads1115.configParameter.MODE;
import adc.ads1115.configParameter.MUX;
import adc.ads1115.configParameter.PGA;
import i2cDevice.I2CErrorException;

/**
 * Description:<br>
 * example of how to use ADS_1115 class. 
 * 
 * <p>
 * @author Stefan
 *
 */

public class ADS_1115_Test { // NOSONAR

	private final Logger logger = LoggerFactory.getLogger(ADS_1115_Test.class.getName());
	
	private ADS_1115 ads1115;
	private int persistentRun = 0;
	
	private PGA pga = PGA.FSR_6_144V;
	
	private Ads1115AInput d01;
	private Ads1115AInput d03;
	private Ads1115AInput d13;
	private Ads1115AInput d23;
	
	private Ads1115AInput s0;
	private Ads1115AInput s1;
	private Ads1115AInput s2;
	private Ads1115AInput s3;
	
	
	/**
	 * 
	 */
	public ADS_1115_Test() {
		
		System.out.println("ADS_1115_Test()");		// NOSONAR

		
	} // LM_75_Test()
	
	
	private void run() {
		
		System.out.println("run()");		// NOSONAR

		
		do { // NOSONAR
			
			try {
				
				logger.debug("read sensor ...");

				logger.info("ADS_1115 d_0-1 : {}", ads1115.readInput(d01));
				logger.info("ADS_1115 d_0-3 : {}", ads1115.readInput(d03));
				logger.info("ADS_1115 d_1-3 : {}", ads1115.readInput(d13));
				logger.info("ADS_1115 d_2-3 : {}", ads1115.readInput(d23));
				
				logger.info("ADS_1115 s0    : {}", ads1115.readInput(s0));
				logger.info("ADS_1115 s1    : {}", ads1115.readInput(s1));
				logger.info("ADS_1115 s2    : {}", ads1115.readInput(s2));
				logger.info("ADS_1115 s3    : {}", ads1115.readInput(s3));
				
				logger.info("ADS_1115 HTh   : {}", ads1115.readHiThresRegister(pga));
				logger.info("ADS_1115 LTh   : {}", ads1115.readLoThresRegister(pga));

				logger.info("ADS_1115 \n");

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

		CmdLineParser cliParser = new CmdLineParser(new ADS_1115_Cli());		 // NOSONAR

		try {
			
			cliParser.parseArgument(args);
			
		} catch (CmdLineException e) {
			
			System.err.println(e.getMessage());		 // NOSONAR
			System.exit(1);
			
		} // yrt
		
		persistentRun = ADS_1115_Cli.run;		 // NOSONAR
		
		d01 = new Ads1115AInput(MUX.DIFF_0_1, pga);
		d03 = new Ads1115AInput(MUX.DIFF_0_3, pga);
		d13 = new Ads1115AInput(MUX.DIFF_1_3, pga);
		d23 = new Ads1115AInput(MUX.DIFF_2_3, pga);
		
		s0  = new Ads1115AInput(MUX.SINGLE_0, pga);
		s1  = new Ads1115AInput(MUX.SINGLE_1, pga);
		s2  = new Ads1115AInput(MUX.SINGLE_2, pga);
		s3  = new Ads1115AInput(MUX.SINGLE_3, pga);
		
		if(ADS_1115_Cli.pga == ADS_1115_Cli.NOT_SET) {		 			// NOSONAR
			System.err.println("-pga is a mandatory parameter!");		// NOSONAR
			System.exit(1);			
		}

		MODE		mode		= MODE.value2type(ADS_1115_Cli.mode);
		PGA			pga			= PGA.value2type(ADS_1115_Cli.pga);					 // NOSONAR
		DR			dr			= DR.value2type(ADS_1115_Cli.dr);
		COMP_MODE	compMode	= COMP_MODE.value2type(ADS_1115_Cli.comp_mode);
		COMP_POL	compPol		= COMP_POL.value2type(ADS_1115_Cli.comp_pol);
		COMP_LAT	compLat		= COMP_LAT.value2type(ADS_1115_Cli.comp_lat);
		COMP_QUE	compQue		= COMP_QUE.value2type(ADS_1115_Cli.comp_que);
		
		try {
			
			ads1115 = new ADS_1115(ADS_1115_Cli.bus, ADS_1115_Cli.getAddress());		 // NOSONAR

			if(mode		!= null) ads1115.withMode(mode);			
			if(dr		!= null) ads1115.withDr(dr);
			if(compMode	!= null) ads1115.withCompMode(compMode);
			if(compPol	!= null) ads1115.withCompPol(compPol);
			if(compLat	!= null) ads1115.withCompLat(compLat);
			if(compQue	!= null) ads1115.withCompQue(compQue);

			if(ADS_1115_Cli.th != ADS_1115_Cli.TH)	ads1115.writeHiThresRegister(ADS_1115_Cli.th, pga);
			if(ADS_1115_Cli.tl != ADS_1115_Cli.TL)	ads1115.writeLoThresRegister(ADS_1115_Cli.tl, pga);
			
		} catch (I2CErrorException e) {

			e.printStackTrace();
			
		} // yrt
	
	} // init
	

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		
		System.out.println("main()");		// NOSONAR

		
		ADS_1115_Test myself = new ADS_1115_Test();
		
		myself.init(args);
		myself.run();

	} // main()

} // ssalc
