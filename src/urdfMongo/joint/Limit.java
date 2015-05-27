package urdfMongo.joint;

public class Limit {

	private double upper;
	private double lower;
	private double effort;
	private double velocity;
	
	public Limit() {}
	
	public Limit(double upper, double lower, double effort, double velocity) {
		this.setUpperLimit(upper);
		this.setLowerLimit(lower);
		this.setEffort(effort);
		this.setVelocity(velocity);
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public double getEffort() {
		return effort;
	}

	public void setEffort(double effort) {
		this.effort = effort;
	}

	public double getLowerLimit() {
		return lower;
	}

	public void setLowerLimit(double lower) {
		this.lower = lower;
	}

	public double getUpperLimit() {
		return upper;
	}

	public void setUpperLimit(double upper) {
		this.upper = upper;
	}
	
	
}
