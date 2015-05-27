package urdfMongo.link;

import java.util.ArrayList;

import urdfMongo.joint.Joint;

public class Link {

	private String name;
	private Inertial inertial;
	private Joint parent;
	private ArrayList<Joint> children;

	public Link(String name, Inertial inertial) {
		setName(name);
		setInertial(inertial);
		setParentJoint(null);
		children = new ArrayList<Joint>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Inertial getInertial() {
		return inertial;
	}

	public void setInertial(Inertial inertial) {
		this.inertial = inertial;
	}
	
	public Joint getParentJoint() {
		return parent;
	}
	
	public void setParentJoint(Joint parent) {
		this.parent = parent; 
	}
	
	public ArrayList<Joint> getChildJoints() {
		return children;
	}
	
	public void addChild(Joint child) {
		children.add(child);
	}

}
