package urdfMongo.link;

import java.util.ArrayList;

import urdfMongo.UrdfImpl;

public class Inertial {
	
	private double mass;
	//private ArrayList<Double> inertia;
	private ArrayList<Double> xyz;
	private ArrayList<Double> rpy;
	
	public Inertial(ArrayList<Double> xyz, ArrayList<Double> rpy, double mass) {
		setMass(mass);
		setXYZ(xyz);
		setRPY(rpy);
	}
	
	public ArrayList<Double> getXYZ() {
		return xyz;
	}
	
	public ArrayList<Double> getRPY() {
		return rpy;
	}
	
	public double getMass() {
		return mass;
	}
	
	public void setXYZ(ArrayList<Double> xyz) {
		if (xyz == null)
			this.xyz = UrdfImpl.getTriple(0, 0, 0);
		else
			this.xyz = xyz;
	}
	
	public void setRPY(ArrayList<Double> rpy) {
		if (rpy== null)
			this.rpy= UrdfImpl.getTriple(0, 0, 0);
		else
			this.rpy = rpy;
	}
	
	public void setMass(double mass) {
		this.mass = mass;
	}
	

}
