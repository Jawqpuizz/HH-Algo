package hhalgo;

import static guru.nidi.graphviz.model.Factory.graph;
import static java.util.Collections.sort;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Rank.RankDir;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Node;

public class App {

	public static void main(String[] args) throws IOException {

		List<Vertex> input = readInput();

		List<Drawable> resolvedGraph = resolveGraph(input);

		// print graph
		outputToJFrame(resolvedGraph, 1024, 768);
	}

	private static char numberToChar(int i) {
		return (char) ('A' + i - 1);
	}

	private static List<Vertex> readInput() {
		try (Scanner scanner = new Scanner(System.in)) {
			int inputSize;
			while (true) {
				System.out.print("Please provide the positive number of Vertex: ");
				if (scanner.hasNextInt()) {
					inputSize = scanner.nextInt();
					if (inputSize > 0) {
						break;
					}
				} else {
					// Consume input to clear the input buffer
					scanner.next();
				}
			}

			List<Vertex> input = new ArrayList<Vertex>(inputSize);
			for (int i = 1; i <= inputSize; i++) {
				int degree = 0;
				char vt = numberToChar(i);
				while (true) {
					System.out.print("Please enter the degree of vertex " + vt + " :");
					if (scanner.hasNextInt()) {
						degree = scanner.nextInt();
						if (degree >= 0) {
							break;
						}
					} else {
						// Consume input to clear the input buffer
						scanner.next();
					}
				}
				input.add(new Vertex(vt, degree));
			}

			sort(input);
			int degree = input.get(0).getDegree();
			if (degree >=  inputSize) {
				throw new IllegalArgumentException(
						String.format(" Graph is not graphic! the degree must be less" + "than the number of vertex"));
			}

			int sum = input.parallelStream().mapToInt(Vertex::getDegree).sum();
			if (sum % 2 == 1) {
				throw new IllegalArgumentException("Graph is not graphic! ==> Sum of the degree is odd");
			}
			return input;
		}
	}

	private static List<Drawable> resolveGraph(List<Vertex> input) {
		System.out.println("S = " + input);
		List<Drawable> output = new ArrayList<>(input.size());
	
		System.out.print(input);
		Vertex firstVertex;
		
		while (!(firstVertex = input.get(0)).hasZeroDegree()) {
			int numberOfDecrease = firstVertex.getDegree();
			firstVertex.makeZeroDegree();
			// time complexity for this loop is sum of the degree sequence O(m)
			for (int i = 1; i <= numberOfDecrease; i++) {
				Vertex nextVertex = input.get(i);
				nextVertex.decreaseDegree();
				output.add(Edge.of(firstVertex, nextVertex));
			}
			// time complexity of sort O(n log n)
			sort(input);
		}

		//// for the zero degree
		output.addAll(input);

		return output;
	}


	private static void outputToJFrame(List<Drawable> resolvedGraph, int width, int height) throws IOException {

		List<Node> nodes = resolvedGraph.stream().map(Drawable::toGraph).collect(Collectors.toList());

		BufferedImage img = Graphviz
				.fromGraph(graph("graph").graphAttr().with(Rank.dir(RankDir.LEFT_TO_RIGHT)).with(nodes)).height(height)
				.width(width).render(Format.PNG).toImage();

		SwingUtilities.invokeLater(() -> {
			Dimension d = new Dimension(width, height);
			JFrame frame = new JFrame();
			frame.setMinimumSize(d);
			frame.setPreferredSize(d);
			frame.setMaximumSize(d);
			ImageIcon icon = new ImageIcon(img);
			JLabel label = new JLabel(icon);
			frame.getContentPane().add(label, BorderLayout.CENTER);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		});

	}

}
