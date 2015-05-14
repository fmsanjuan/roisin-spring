package com.roisin.spring.utils;

/**
 * This class represents a line in the cartesian coordinate system
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
public class Line {

	/**
	 * Slope
	 */
	private double m;

	/**
	 * Y axis cut
	 */
	private double k;

	public Line() {
		m = 0.0;
		k = 0.0;
	}

	public Line(final double m, final double k) {
		this.m = m;
		this.k = k;
	}

	public double getM() {
		return m;
	}

	public void setM(final double m) {
		this.m = m;
	}

	public double getK() {
		return k;
	}

	public void setK(final double k) {
		this.k = k;
	}

}
