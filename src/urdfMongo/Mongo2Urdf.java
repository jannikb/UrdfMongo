package urdfMongo;

import java.util.ArrayList;

import urdfMongo.joint.Joint;
import urdfMongo.joint.Limit;
import urdfMongo.joint.Joint.JointType;
import urdfMongo.link.Inertial;
import urdfMongo.link.Link;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class Mongo2Urdf extends UrdfImpl {

	public Mongo2Urdf(DB database) {
		super();
		
		DBCollection collJoints = database.getCollection("robot_joints");
		DBCollection collLinks = database.getCollection("robot_links");

		readLinks(collLinks);
		readJoints(collJoints);
		
		setLinkParentsAndChildren();
	}

	@SuppressWarnings("unchecked")
	private void readLinks(DBCollection coll) {
		ArrayList<Double> xyz;
		ArrayList<Double> rpy;
		double mass;
		String name;
		Object obj;
		DBCursor cursor = coll.find();
		try {
			while (cursor.hasNext()) {
				cursor.next();
				name = (String) cursor.curr().get("name");
				obj = cursor.curr().get("xyz");
				if (obj == null)
					xyz = null;
				else
					xyz = (ArrayList<Double>) obj;
				obj = cursor.curr().get("rpy");
				if (obj == null)
					rpy = null;
				else
					rpy = (ArrayList<Double>) obj;
				obj = cursor.curr().get("mass");
				if (obj == null)
					mass = Double.NaN;
				else
					mass = (double) obj;
				links.put(name, new Link(name, new Inertial(xyz, rpy, mass)));
			}
		} finally {
			cursor.close();
		}
	}

	@SuppressWarnings("unchecked")
	private void readJoints(DBCollection coll) {
		ArrayList<Double> xyz;
		ArrayList<Double> rpy;
		String name;
		JointType jointType;
		ArrayList<Double> axis;
		String child;
		String parent;
		double upperLimit;
		double lowerLimit;
		double velocityLimit;
		double effortLimit;
		Limit limit = null;
		Object obj;
		DBCursor cursor = coll.find();
		try {
			while (cursor.hasNext()) {
				cursor.next();
				name = (String) cursor.curr().get("name");
				obj = cursor.curr().get("xyz");
				if (obj == null)
					xyz = null;
				else
					xyz = (ArrayList<Double>) obj;
				obj = cursor.curr().get("rpy");
				if (obj == null)
					rpy = null;
				else
					rpy = (ArrayList<Double>) obj;
				obj = cursor.curr().get("axis");
				if (obj == null)
					axis = null;
				else
					axis = (ArrayList<Double>) obj;
				child = (String) cursor.curr().get("child");
				parent = (String) cursor.curr().get("parent");
				if (cursor.curr().get("upper_limit") != null)
				{
					upperLimit = (double) cursor.curr().get("upper_limit");
					lowerLimit = (double) cursor.curr().get("lower_limit");
					velocityLimit = (double) cursor.curr().get("velocity_limit");
					effortLimit = (double) cursor.curr().get("effort_limit");
					limit = new Limit(upperLimit, lowerLimit, effortLimit, velocityLimit);
				}
				jointType = JointType.valueOf((String) cursor.curr().get("type"));
				joints.put(name, new Joint(name, jointType, child, parent, xyz,
						rpy, axis, limit));
			}
		} finally {
			cursor.close();
		}
	}

}
