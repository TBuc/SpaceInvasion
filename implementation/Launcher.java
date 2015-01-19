/*
 * Buc Space Invasion, v.1.0
 * 
 * (c) 2013 Antonio Bucciol
 * 
 * antonio@bucciol.org
 * 
 */
package implementation;

import resources.Resources;


public class Launcher {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("Carico risorse...");
		
		Resources res = Resources.getInstance();
		
		System.out.println("Risorse caricate.");
		
		new Game(res).run();
	}

}
