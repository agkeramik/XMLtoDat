import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import jdistlib.Normal;
import jdistlib.rng.MersenneTwister;
import jdistlib.rng.RandomCMWC;
import jdistlib.rng.RandomEngine;

public class Position {
	public double posX;
	public double posY;
	private double rot;
	private static final RandomEngine re = new MersenneTwister();

	public void setRot(double rot) {
		this.rot = rot;
		if (this.rot < 0)
			this.rot += 2 * Math.PI;
		if (this.rot >= 2 * Math.PI)
			this.rot -= 2 * Math.PI;
	}

	@Override
	public String toString() {
		// return "posX=" + posX + " posY" + posY + " rot" + rot;
		DecimalFormat formatter = new DecimalFormat("#.#####",
				DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		formatter.setRoundingMode(RoundingMode.DOWN);
		// return formatter.format(posX) + " " + formatter.format(posY) + " " +
		// formatter.format(Math.cos(rot))+" "+formatter.format(Math.sin(rot))+"\n";
		return formatter.format(posX) + " " + formatter.format(posY)+
			 " "+ formatter.format(rot) + "\n";
	}

	public Position disturb(double sigma_cm, double sigma_rad) {
		Position p = new Position();
		p.posX = posX + Normal.random(0, sigma_cm, re);
		p.posY = posY + Normal.random(0, sigma_cm, re);
		p.setRot(rot + Normal.random(0, sigma_rad, re));
		return p;
	}

	public double getRot() {
		return rot;
	}
}
