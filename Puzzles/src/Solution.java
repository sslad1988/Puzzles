import java.util.ArrayList;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

import java.util.ArrayList;
import java.util.Scanner;

enum Color {
	RED, GREEN
}

//https://www.hackerrank.com/challenges/java-vistor-pattern/problem

abstract class Tree {

	private int value;
	private Color color;
	private int depth;

	public Tree(int value, Color color, int depth) {
		this.value = value;
		this.color = color;
		this.depth = depth;
	}

	public int getValue() {
		return value;
	}

	public Color getColor() {
		return color;
	}

	public int getDepth() {
		return depth;
	}

	public abstract void accept(TreeVis visitor);
}

class TreeNode extends Tree {

	private ArrayList<Tree> children = new ArrayList<>();

	public TreeNode(int value, Color color, int depth) {
		super(value, color, depth);
	}

	public void accept(TreeVis visitor) {
		visitor.visitNode(this);

		for (Tree child : children) {
			child.accept(visitor);
		}
	}

	public void addChild(Tree child) {
		children.add(child);
	}
}

class TreeLeaf extends Tree {

	public TreeLeaf(int value, Color color, int depth) {
		super(value, color, depth);
	}

	public void accept(TreeVis visitor) {
		visitor.visitLeaf(this);
	}
}

abstract class TreeVis {
	public abstract int getResult();

	public abstract void visitNode(TreeNode node);

	public abstract void visitLeaf(TreeLeaf leaf);

}

class SumInLeavesVisitor extends TreeVis {
	private int result;

	public int getResult() {
		// implement this
		return result;
	}

	public void visitNode(TreeNode node) {

	}

	public void visitLeaf(TreeLeaf leaf) {
		result = result + leaf.getValue();
	}
}

class ProductOfRedNodesVisitor extends TreeVis {
	private int result = 1;
	private final int M = 1000000007;

	public int getResult() {
		// implement this
		return result;
	}

	public void visitNode(TreeNode node) {
		if (node.getColor().equals(Color.RED))
			result = (result * node.getValue()) % M;
	}

	public void visitLeaf(TreeLeaf leaf) {
		if (leaf.getColor().equals(Color.RED))
			result = (result * leaf.getValue()) % M;
	}
}

class FancyVisitor extends TreeVis {
	private int result;
	int greeLeafSum;
	int evenNodeSum;

	public int getResult() {

		return Math.abs(greeLeafSum - evenNodeSum);
	}

	public void visitNode(TreeNode node) {
		if (node.getDepth() % 2 == 0)
			evenNodeSum = evenNodeSum + node.getValue();
	}

	public void visitLeaf(TreeLeaf leaf) {

		if (leaf.getColor().equals(Color.GREEN))
			greeLeafSum = greeLeafSum + leaf.getValue();
	}
}

public class Solution {

	public static Tree solve() {
		// read the tree from STDIN and return its root as a return value of this
		// function
		Scanner scanner = new Scanner(System.in);
		int nodes = scanner.nextInt();
		int[] vals = new int[nodes];
		for (int i = 0; i < nodes; i++) {
			vals[i] = scanner.nextInt();
		}
		Color[] colors = new Color[nodes];
		for (int i = 0; i < nodes; i++) {
			colors[i] = Color.values()[scanner.nextInt()];
		}

		int[] depths = new int[nodes];

		int[][] edges = new int[nodes][2];
		for (int i = 0; i < nodes - 1; i++) {
			edges[i][0] = scanner.nextInt();
			edges[i][1] = scanner.nextInt();
			depths[edges[i][1] - 1] = depths[edges[i][0] - 1] + 1;
		}

		List<Tree> tree = new ArrayList<Tree>();
		for (int i = 0; i < nodes; i++) {
			Tree treeLeaf = new TreeLeaf(vals[i], colors[i], depths[i]);
			tree.add(i,treeLeaf);
		}

		for (int i = 0; i < nodes - 1; i++) {
			Tree t = tree.get(edges[i][0] - 1);
			TreeNode treeNode;
			if (t instanceof TreeLeaf) {
				t = tree.remove(edges[i][0] - 1);
				treeNode = new TreeNode(t.getValue(), t.getColor(), t.getDepth());
				tree.add(edges[i][0] - 1, treeNode);
			}

		}

		for (int i = 0; i < nodes - 1; i++) {

			((TreeNode) tree.get(edges[i][0] - 1)).addChild(tree.get(edges[i][1] - 1));

		}

		return tree.get(0);
	}

	public static void main(String[] args) {
		Tree root = solve();
		SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
		ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
		FancyVisitor vis3 = new FancyVisitor();

		root.accept(vis1);
		root.accept(vis2);
		root.accept(vis3);

		int res1 = vis1.getResult();
		int res2 = vis2.getResult();
		int res3 = vis3.getResult();

		System.out.println(res1);
		System.out.println(res2);
		System.out.println(res3);
	}
}
