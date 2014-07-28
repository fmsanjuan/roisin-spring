package com.roisin.spring.utils;

public class Line {

	private double m;

	private double k;

	public Line() {
		m = 0.0;
		k = 0.0;
	}

	public Line(double m, double k) {
		this.m = m;
		this.k = k;
	}

	public double getM() {
		return m;
	}

	public void setM(double m) {
		this.m = m;
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

}
