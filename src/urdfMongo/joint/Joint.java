package urdfMongo.joint;

import java.util.ArrayList;

import urdfMongo.UrdfImpl;

public class Joint {

	public enum JointType {
		FIXED, PRISMATIC, REVOLUTE, PLANAR, CONTINUOUS
	}

	private String name;
	private JointType jointType;
	private Limit limit;
	private ArrayList<Double> xyz;
	private ArrayList<Double> rpy;
	private ArrayList<Double> axis;
	private String child;
	private String parent;

	public Joint(String name, JointType jointType, String child, String parent,
			ArrayList<Double> xyz, ArrayList<Double> rpy, ArrayList<Double> axis, Limit limit) {
		setName(name);
		setJointType(jointType);
		setXYZ(xyz);
		setRPY(rpy);
		setAxis(axis);
		setLimits(limit);
		setChild(child);
		setParent(parent);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JointType getJointType() {
		return jointType;
	}

	public void setJointType(JointType jointType) {
		this.jointType = jointType;
	}

	public Limit getLimits() {
		return limit;
	}

	public void setLimits(Limit limit) {
		this.limit = limit;
	}

	public ArrayList<Double> getXYZ() {
		return xyz;
	}

	public void setXYZ(ArrayList<Double> xyz) {
		if (xyz == null)
			this.xyz = UrdfImpl.getTriple(0, 0, 0);
		else
			this.xyz = xyz;
	}

	public ArrayList<Double> getRPY() {
		return rpy;
	}

	public void setRPY(ArrayList<Double> rpy) {
		if (rpy== null)
			this.rpy= UrdfImpl.getTriple(0, 0, 0);
		else
			this.rpy = rpy;
	}

	public ArrayList<Double> getAxis() {
		return axis;
	}

	public void setAxis(ArrayList<Double> axis) {
		if (axis== null)
			this.axis= UrdfImpl.getTriple(0, 0, 0);
		else
			this.axis = axis;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}
