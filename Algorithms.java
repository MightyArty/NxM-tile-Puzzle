import java.util.*;

/**
 * This class represent the main algorithms for solving the puzzle.
 * @author Tom Shabalin
 */
public class Algorithms {

	public static int NODECOUNT = 1; // Counter for the number of nodes
	public static int CHECK = 0; // Counter for the number of nodes checked
	public static String alg; // Algorithm name
	public static Boolean with_time = false; // Time flag
	public static Boolean with_open_list = false; // Open list flag
	public static final int TOP = 50000; // Top limit for the number of nodes in cases where there is no solution.

	public static String run(Node s) {
		switch (alg) {
			case "A*":
				return aStar(s);
			case "DFBnB":
				return DFBnB(s);
			case "DFID":
				return DFID(s);
			case "IDA*":
				return IDA(s);

			default:
				System.out.println("Invalid algorithm");
				return null;
		}
	}

	/**
	 * DFBnB algorithm
	 * Implemented with a Stack and loop avoidance.
	 * @param start - start node
	 * @return result
	 */
	public static String DFBnB(Node start) {
		start.unMark();
		NODECOUNT = 0;
		Stack<Node> openList = new Stack<Node>();
		Hashtable<Node, Node> H = new Hashtable<Node, Node>();
		openList.add(start);
		H.put(start, start);
		String result = "no path\nNum:";
		/* UpperBound */
		int factorail = 1;
		for (int i = 1; i <= Node.GOAL.length * Node.GOAL[0].length; i++)
			factorail *= i;
		int t = Math.min(factorail, Integer.MAX_VALUE);
		while (!openList.isEmpty()) {
			if (with_open_list)
				System.out.println(Arrays.deepToString(openList.toArray()));
			Node n = openList.pop();

			if (!(n.name.equalsIgnoreCase(""))) {
				int number = Integer.parseInt(n.name.substring(0, 1));
				if (Node.moveCount.containsKey(number)) {
					Node.moveCount.put(number, Node.moveCount.get(number) - 1);

					if (Node.moveCount.get(number) == 0) {
						Node.moveCount.remove(number);
						Node.RED.add(number);
					}
				}
			}
			if (n.isOut)
				H.remove(n);
			else {
				n.markOut();
				openList.add(n);
				ArrayList<Node> N = new ArrayList<Node>();
				for (Node temp : n.operators())
					N.add(temp);
				N.sort((a, b) -> a.f() - b.f());
				for (int i = 0; i < N.size(); i++) {
					Node g = N.get(i);
					NODECOUNT++;
					if (NODECOUNT == t)
						return "no path\nNum: " + NODECOUNT + "\nCost:";
					if (g.f() >= t) {
						int temp = t;
						N.removeIf((Node a) -> a.f() >= temp);
					} else // f(g)<t
					if (H.contains(g) && H.get(g).isOut) { // if H contains g'=g and g' isMarked
						N.remove(g);
					} else {
						if (H.contains(g) && !H.get(g).isOut) { // if H contains g'=g and g' isnt Marked
							if (H.get(g).f() <= g.f()) {
								N.remove(g);
							} else {
								openList.remove(g);
								H.remove(g);
							}
						} else { // if H doesnt contains g && f(g)
							if (g.isGoal()) {
								t = g.f();
								result = g.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + g.getCost();
								int temp = t;
								N.removeIf((Node a) -> a.f() >= temp);
							}
						}
					}
				}
				for (int i = 0; i < N.size(); i++) {
					openList.add(N.get(N.size() - 1 - i));
					H.put(N.get(N.size() - 1 - i), N.get(N.size() - 1 - i));
				}
			}
		}
		if (result.contains("no path"))
			return "no path\nNum: " + NODECOUNT;
		return result;
	}

	/**
	 * IDA* algorithm
	 * Implemented with a Stack and loop avoidance.
	 * @param start - start node
	 * @return result
	 */
	public static String IDA(Node start) {
		start.unMark();
		NODECOUNT = 0;
		Stack<Node> openList = new Stack<Node>();
		Hashtable<Node, Node> H = new Hashtable<Node, Node>();
		int t = start.h();
		while (t != Integer.MAX_VALUE) {
			int minF = Integer.MAX_VALUE;
			start.unMark();
			openList.add(start);
			H.put(start, start);
			while (!openList.isEmpty()) {
				if (with_open_list)
					System.out.println("OpenList::" + Arrays.deepToString(openList.toArray()));
				Node n = openList.pop();
				if (!(n.name.equalsIgnoreCase(""))) {
					int number = Integer.parseInt(n.name.substring(0, 1));
					if (Node.moveCount.containsKey(number)) {
						Node.moveCount.put(number, Node.moveCount.get(number) - 1);

						if (Node.moveCount.get(number) == 0) {
							Node.moveCount.remove(number);
							Node.RED.add(number);
						}
					}
				}
				if (n.isOut()) {
					H.remove(n);
				} else {
					n.markOut();
					openList.add(n);
					for (Node g : n.operators()) {
						NODECOUNT++;
						if (NODECOUNT == TOP)
							return "no path\nNum: " + NODECOUNT + "\nCost:";
						if (g.f() > t) {
							minF = Math.min(minF, g.f());
							continue;
						}
						if (H.contains(g) && H.get(g).isOut()) {
							continue;
						}
						if (H.contains(g) && !H.get(g).isOut()) {
							if (H.get(g).f() > g.f()) {
								openList.remove(H.get(g));
								H.remove(H.get(g));
							} else {
								continue;
							}
						}
						if (g.isGoal()) {
							return g.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + g.getCost();
						}
						openList.add(g);
						H.put(g, g);
					}
				}
			}
			t = minF;
		}
		return "no path\nNum: " + NODECOUNT;
	}

	/**
	 * A* algorithm
	 * Implemented with HashTable and PriorityQueue.
	 * @param start - start node
	 * @return result
	 */
	public static String aStar(Node start) {
		NODECOUNT = 0;
		PriorityQueueNode openList = new PriorityQueueNode();
		Hashtable<Node, Node> openListHash = new Hashtable<>();
		openList.add(start);
		openListHash.put(start, start);
		Hashtable<Node, Node> closeList = new Hashtable<>();
		while (!openList.isEmpty()) {
			if (with_open_list)
				System.out.println(Arrays.deepToString(openList.toArray()));
			Node n = openList.poll();

			if (!(n.name.equalsIgnoreCase(""))) {

				int number = Integer.parseInt(n.name.substring(0, 1));
				if (Node.moveCount.containsKey(number)) {
					Node.moveCount.put(number, Node.moveCount.get(number) - 1);

					if (Node.moveCount.get(number) == 0) {
						Node.moveCount.remove(number);
						Node.RED.add(number);
					}
				}
			}

			openListHash.remove(n);
			if (n.isGoal())
				return n.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + n.getCost();
			closeList.put(n, n);
			for (Node x : n.operators()) {
				NODECOUNT++;
				if (NODECOUNT == TOP)
					return "no path\nNum: " + NODECOUNT + "\nCost:";
				if (!openListHash.contains(x) && !closeList.contains(x)) {
					openList.add(x);
					openListHash.put(x, x);
				} else {
					if (openListHash.contains(x) && openListHash.get(x).f() > x.f()) {
						openList.swapForLowerValue(x);
					}
				}
			}
		}
		return "no path\nNum: " + NODECOUNT;
	}

	/**
	 * DFID algorithm
	 * Implemented with a Hashtable and recursion.
	 * @param start - start node
	 * @return result
	 */
	public static String DFID(Node start) {
		NODECOUNT = 0;
		String result;
		for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {
			Hashtable<Node, Node> ht = new Hashtable<>();
			result = Limited_DFS(start, depth, ht);
			if (!result.equals("Cutoff"))
				if (result.equals("fail"))
					return "no path\nNum: " + NODECOUNT;
				else {
					return result;
				}
		}
		return "no path\nNum: " + NODECOUNT;
	}

	/**
	 * Limited DFS algorithm
	 * @param n - start node
	 * @param limit - depth limit
	 * @param h - Hashtable
	 * @return result
	 */
	private static String Limited_DFS(Node n, int limit, Hashtable<Node, Node> h) {
		if (n.isGoal())
			return n.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + n.getCost();
		if (limit == 0)
			return "Cutoff";
		h.put(n, n);
		boolean isCutoff = false;
		Queue<Node> op = n.operators();
		if (with_open_list)
			System.out.println(Arrays.deepToString(op.toArray()));
		while (!op.isEmpty()) {
			Node g = op.poll();
			if (!(n.name.equalsIgnoreCase(""))) {
				int number = Integer.parseInt(g.name.substring(0, 1));
				if (Node.moveCount.containsKey(number)) {
					Node.moveCount.put(number, Node.moveCount.get(number) - 1);

					if (Node.moveCount.get(number) == 0) {
						Node.moveCount.remove(number);
						Node.RED.add(number);
					}
				}
			}
			NODECOUNT++;
			if (NODECOUNT == TOP)
				return "no path\nNum: " + NODECOUNT + "\nCost:";
			if (h.contains(g))
				continue;
			String result = Limited_DFS(g, limit - 1, h);
			if (result.equals("Cutoff"))
				isCutoff = true;
			else {
				if (!result.equals("fail")) {
					return result;
				}
			}
		}
		h.remove(n);
		if (isCutoff) {
			return "Cutoff";
		} else {
			return "fail";
		}
	}
}
