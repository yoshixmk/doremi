package doremi;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class DoremiApp {

	public static void main(String[] args) {
		LCD.drawString("Plugin Test", 0, 4);
		Delay.msDelay(5000);
		
		System.exit(0);
	}
}
