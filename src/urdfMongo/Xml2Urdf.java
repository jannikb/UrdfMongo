package urdfMongo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import urdfMongo.exceptions.InvalidUrdfNotation;
import urdfMongo.joint.Joint;
import urdfMongo.joint.Limit;
import urdfMongo.joint.Joint.JointType;
import urdfMongo.link.Inertial;
import urdfMongo.link.Link;

public class Xml2Urdf extends UrdfImpl {

	public Xml2Urdf(String filename) throws SAXException, IOException,
			ParserConfigurationException, InvalidUrdfNotation {
		super();

		initXml(filename);

		setLinkParentsAndChildren();
	}

	private void initXml(String filename) throws SAXException, IOException,
			ParserConfigurationException, InvalidUrdfNotation {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new File(filename));

		NodeList robotNodes = document.getElementsByTagName("robot");
		Node robotNode;
		if (robotNodes.getLength() != 0 || robotNodes.getLength() > 0) {
			robotNode = robotNodes.item(0);
			robotName = robotNode.getAttributes().getNamedItem("name")
					.getNodeValue();
		} else
			throw new InvalidUrdfNotation();

		ArrayList<Node> jointNodes = new ArrayList<Node>();
		ArrayList<Node> linkNodes = new ArrayList<Node>();
		Node robotChild;
		for (int i = 0; i < robotNode.getChildNodes().getLength(); i++) {
			robotChild = robotNode.getChildNodes().item(i);
			switch (robotChild.getNodeName()) {
			case "link":
				linkNodes.add(robotChild);
				break;
			case "joint":
				jointNodes.add(robotChild);
			}
		}

		Node Node;
		ArrayList<Double> xyz;
		ArrayList<Double> rpy;
		String name;

		Limit limit;
		ArrayList<Double> axis;
		Node axisNode;
		JointType jointType;
		String parent;
		String child;
		for (int i = 0; i < jointNodes.size(); i++) {
			Node = jointNodes.get(i);
			name = Node.getAttributes().getNamedItem("name").getNodeValue();
			jointType = JointType.valueOf(Node.getAttributes()
					.getNamedItem("type").getNodeValue().toUpperCase());
			xyz = parseOrigin(Node, "xyz");
			rpy = parseOrigin(Node, "rpy");
			limit = parseLimit(Node);
			child = getChildNode(Node, "child").getAttributes()
					.getNamedItem("link").getNodeValue();
			parent = getChildNode(Node, "parent").getAttributes()
					.getNamedItem("link").getNodeValue();

			axisNode = getChildNode(Node, "axis");
			if (axisNode == null)
				axis = getTriple(1, 0, 0);
			else
				axis = parseDoubleTriple(axisNode.getAttributes()
						.getNamedItem("xyz").getNodeValue());

			joints.put(name, new Joint(name, jointType, child, parent, xyz,
					rpy, axis, limit));
		}

		Inertial inertial;
		for (int i = 0; i < linkNodes.size(); i++) {
			Node = linkNodes.get(i);
			name = Node.getAttributes().getNamedItem("name").getNodeValue();
			inertial = parseInertial(Node);

			links.put(name, new Link(name, inertial));
		}
	}

	// TODO parse mass
	private Inertial parseInertial(Node linkNode) throws InvalidUrdfNotation {
		Node inertialNode = getChildNode(linkNode, "inertial");
		if (inertialNode == null)
			return new Inertial(null, null, 0);
		ArrayList<Double> xyz = parseOrigin(inertialNode, "xyz");
		ArrayList<Double> rpy = parseOrigin(inertialNode, "xyz");
		double mass = 0;
		return new Inertial(xyz, rpy, mass);
	}

	private Limit parseLimit(Node jointNode) {
		double velocity = 0;
		double effort = 0;
		double upper = 0;
		double lower = 0;
		Node limitNode = getChildNode(jointNode, "limit");
		if (limitNode != null) {
			Node node = limitNode.getAttributes().getNamedItem("velocity");
			if (node != null)
				velocity = Double.parseDouble(node.getNodeValue());
			node = limitNode.getAttributes().getNamedItem("effort");
			if (node != null)
				effort = Double.parseDouble(node.getNodeValue());
			node = limitNode.getAttributes().getNamedItem("upper");
			if (node != null)
				upper = Double.parseDouble(node.getNodeValue());
			node = limitNode.getAttributes().getNamedItem("lower");
			if (node != null)
				lower = Double.parseDouble(node.getNodeValue());
		}
		return new Limit(upper, lower, effort, velocity);
	}

	private ArrayList<Double> parseOrigin(Node jointNode, String field)
			throws InvalidUrdfNotation {
		Node originNode = getChildNode(jointNode, "origin");
		if (originNode == null)
			return getTriple(0, 0, 0);

		Node valueNode = originNode.getAttributes().getNamedItem(field);
		if (valueNode == null)
			return getTriple(0, 0, 0);

		return parseDoubleTriple(valueNode.getNodeValue());
	}

	private ArrayList<Double> parseDoubleTriple(String text)
			throws InvalidUrdfNotation {
		String[] numbers = text.split("\\s+");

		if (numbers.length != 3)
			throw new InvalidUrdfNotation();

		ArrayList<Double> doubles = new ArrayList<Double>(3);
		for (int i = 0; i < numbers.length; i++) {
			doubles.add(Double.parseDouble(numbers[i]));
		}

		return doubles;
	}

	private Node getChildNode(Node node, String name) {
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equals(name))
				return children.item(i);
		}

		return null;
	}

}
