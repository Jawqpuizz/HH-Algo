package hhalgo;

import guru.nidi.graphviz.model.Node;

public interface Drawable {
	String toConsole();
	Node toGraph();
}