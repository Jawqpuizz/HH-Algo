package hhalgo;

import static guru.nidi.graphviz.model.Factory.node;

import guru.nidi.graphviz.model.Node;

public class Edge implements Drawable {

	private final char frVertex;
	private final char toVertex;

	private Edge(char edge1, char edge2) {
		this.frVertex = edge1;
		this.toVertex = edge2;
	}
	
	static Edge of(Vertex v1, Vertex v2) {
		char n1 = v1.getName();
		char n2 = v2.getName();
		
		return new Edge(n1, n2);
	}
	
	@Override
	public String toConsole() {
		StringBuilder sb = new StringBuilder();
		sb.append(frVertex);
		sb.append(" -- ");
		sb.append(toVertex);
		return sb.toString();
	}
	
	@Override
	public Node toGraph() {
		return node(Character.toString(this.frVertex))
				.link(
						node(Character.toString(this.toVertex))
						);
	}
	
	@Override
	public String toString() {
		return toConsole();
	}
	
}
