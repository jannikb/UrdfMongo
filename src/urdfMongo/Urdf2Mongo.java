package urdfMongo;

import urdfMongo.joint.Joint;
import urdfMongo.link.Link;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class Urdf2Mongo {

	public static void writeUrdfToDB(Urdf urdf, DB database) {
		DBCollection collLinks = createNewCollection(database, "robot_links");
		DBCollection collJoints = createNewCollection(database, "robot_joints");

		for (Link link : urdf.getLinks().values()) {
			insertLink(collLinks, link);
		}

		for (Joint joint : urdf.getJoints().values()) {
			insertJoint(collJoints, joint);
		}
	}

	private static DBCollection createNewCollection(DB db, String name) {
		DBCollection coll = db.getCollection(name);
		if (coll != null)
			coll.drop();
		coll = db.createCollection(name, new BasicDBObject());
		return coll;
	}

	private static void insertLink(DBCollection coll, Link link) {
		BasicDBObject linkObj = new BasicDBObject("name", link.getName())
				.append("xyz", link.getInertial().getXYZ())
				.append("rpy", link.getInertial().getRPY())
				.append("mass", link.getInertial().getMass());
		coll.insert(linkObj);
	}

	private static void insertJoint(DBCollection coll, Joint joint) {
		BasicDBObject jointObj = new BasicDBObject("name", joint.getName())
				.append("xyz", joint.getXYZ()).append("rpy", joint.getRPY())
				.append("type", joint.getJointType().name())
				.append("axis", joint.getAxis())
				.append("child", joint.getChild())
				.append("parent", joint.getParent());

		if (joint.getLimits() != null)
			jointObj.append("upper_limit", joint.getLimits().getUpperLimit())
					.append("lower_limit", joint.getLimits().getLowerLimit())
					.append("velocity_limit", joint.getLimits().getVelocity())
					.append("effort_limit", joint.getLimits().getEffort());

		coll.insert(jointObj);
	}
}
