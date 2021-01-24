package hhalgo;

import guru.nidi.graphviz.model.Node;

import static guru.nidi.graphviz.model.Factory.node;

public class Vertex implements Comparable<Vertex>, Drawable {
	private final char name;
	private int degree;
	
	public Vertex(char name, int degree) {
		this.name = name;
		this.degree = degree;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.name);
		sb.append(": ");
		sb.append(degree);
		return sb.toString();
	}

	public char getName() {
		return this.name;
	}
	
	public int getDegree() {
		return this.degree;
	}
	
	public int decreaseDegree() {
		if (this.degree == 0) {
			throw new IllegalStateException("Graph is not graphic!");
		}
		return this.degree--;
	}
	
	public boolean hasZeroDegree() {
		return this.degree == 0;
	}

	public void makeZeroDegree() {
		this.degree = 0;
	}
	
	@Override
	public int compareTo(Vertex o) {
		int result = Integer.compare(this.degree, o.degree) * -1;
		if (result == 0) {
			result = Character.compare(this.name, o.name);
		}
		return result;
	}

	@Override
	public String toConsole() {
		return Character.toString(this.name);
	}

	@Override
	public Node toGraph() {
		return node(Character.toString(this.name));
	}

}
