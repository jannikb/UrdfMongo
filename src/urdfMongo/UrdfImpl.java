package urdfMongo;

import java.util.ArrayList;
import java.util.Hashtable;

import urdfMongo.joint.Joint;
import urdfMongo.link.Link;

public abstract class UrdfImpl implements Urdf {
	
	protected String robotName;
	protected Link rootLink;
	protected Hashtable<String, Joint> joints;
	protected Hashtable<String, Link> links;
	
	public UrdfImpl() {
		joints = new Hashtable<String, Joint>();
		links = new Hashtable<String, Link>();
	}
	
	@Override
	public Hashtable<String, Joint> getJoints() {
		return joints;
	}

	@Override
	public Hashtable<String, Link> getLinks() {
		return links;
	}

	@Override
	public String getRobotName() {
		return robotName;
	}
	
	@Override
	public Link getRootLink() {
		if (rootLink != null)
			return rootLink;
		
		rootLink = findRootLink();
		return rootLink;
	}
	
	protected void setLinkParentsAndChildren() {
		for (Joint joint : joints.values()) {
			links.get(joint.getParent()).addChild(joint);
			links.get(joint.getChild()).setParentJoint(joint);
		}
	}
	
	protected Link findRootLink() {
		for (Link link: links.values()) {
			if (link.getParentJoint() == null)
				return link;
		}
		return null;
	}

	public static ArrayList<Double> getTriple(double a, double b, double c) {
		ArrayList<Double> l = new ArrayList<Double>(3);
		l.add(a);
		l.add(b);
		l.add(c);
		return l;
	}

}
