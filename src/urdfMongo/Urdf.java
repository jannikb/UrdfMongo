package urdfMongo;

import java.util.Hashtable;

import urdfMongo.joint.Joint;
import urdfMongo.link.Link;

public interface Urdf {
	public String getRobotName();
	public Link getRootLink();
	public Hashtable<String, Joint> getJoints();
	public Hashtable<String, Link> getLinks();
}
