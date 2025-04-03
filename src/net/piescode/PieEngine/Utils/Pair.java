// Author(s): Pie
// E-mail(s): pie@piescode.net
// Desc: This is a simple class designed to hold two values of any type and group them together

package net.piescode.PieEngine.Utils;

public class Pair<X, Y> {
	private final X xValue;
	private final Y yValue;
	
	public Pair(X xValue, Y yValue) {
		this.xValue = xValue;
		this.yValue = yValue;
	}
	
	public X getX() {
		return xValue;
	}
	
	public Y getY() {
		return yValue;
	}
}
