import java.util.*;

public class Graph {
	private Node[] nodes;
	private Edge[][] graph;
	private int node_size;

	/*
	 * Method: Graph 
	 * Accepts: int 
	 * Returns: N/A 
	 * Description: Constructor, initializes variables associated with a graph
	 */
	public Graph(int n) {
		node_size = n;
		graph = new Edge[n][n];
		nodes = new Node[n];
		// initialize all nodes with values
		for (int i = 0; i < n; i++) {
			nodes[i] = new Node(i);
			nodes[i].setMark(false);
		}
		// initialize all edges within the adjacency matrix graph to null
		for (int i = 0; i < n; i++) {
			for (int c = 0; c < n; c++)
				graph[nodes[i].getName()][nodes[c].getName()] = null;
		}
	}

	/*
	 * Method: insertEdge 
	 * Accepts: Node, Node, String 
	 * Returns: N/A 
	 * Description: Graph stores all edges for a specific Node in a row. 
	 * 				Since there are two nodes, each node needs an edge. 
	 * 				For example if Node 1 and Node 5 need to be connected 
	 * 				then there will be an edge stored in graph[1][5] and
	 * 				graph[5][1]. For graph[u][v] u is a row for edges that 
	 * 				are connected to Node u, and v is the node that u is 
	 * 				connected to by an edge
	 */
	public void insertEdge(Node u, Node v, String edgeType)
			throws GraphException {
		try {
			if(graph[u.getName()][v.getName()] != null || graph[v.getName()][u.getName()] != null)
				throw new GraphException();
			graph[u.getName()][v.getName()] = new Edge(u, v, edgeType);
			graph[v.getName()][u.getName()] = new Edge(v, u, edgeType);
		} catch (ArrayIndexOutOfBoundsException exception) {
			throw new GraphException();
		}
	}

	/*
	 * Method: getNode 
	 * Accepts: int 
	 * Returns: Node 
	 * Description: Returns a node relative to a graph 
	 * 				that matches the name specified by the argument. If
	 * 				name is non-existent within the array of nodes, 
	 * 				than a GraphException is thrown
	 */
	public Node getNode(int name) throws GraphException {
		try {
			return this.nodes[name];
		} catch (NullPointerException e) {
			throw new GraphException();
		}
	}

	/*
	 * Method: incidentEdges 
	 * Accepts: Node 
	 * Returns: Iterator 
	 * Description: Returns an iterator that contains all 
	 * edges associated with the node provided as an argument
	 */
	public Iterator incidentEdges(Node u) throws GraphException {
		Iterator<Edge> iter;
		boolean is_edges = false;
		Vector<Edge> incidentEdges = new Vector<Edge>();
		try{
		for (int i = 0; i < node_size; i++) {
			int node_u = u.getName();
			if (node_u < 0 || node_u > node_size - 1)
				throw new GraphException();
			if (graph[node_u][i] != null){
				is_edges = true;
				incidentEdges.add(graph[node_u][i]);
			}
		}
		if(is_edges == false)
			return null;
		iter = incidentEdges.iterator();
		return iter;
		}
		catch(Exception e){
			throw new GraphException();
		}
	}

	/*
	 * Method: getEdge Accepts: Node, Node Returns: Edge Description: returns an
	 * edge stored in the adjacency matrix graph variable using two nodes
	 * provided as arguments. if the nodes do not exist within the graph, an
	 * exception is thrown
	 */
	public Edge getEdge(Node u, Node v) throws GraphException {
		try {
			return graph[u.getName()][v.getName()];
		} catch (Exception e) {
			throw new GraphException();
		}
	}

	/*
	 * Method: areAdjacent Accepts: Node, Node Returns: boolean Description:
	 * Calls incident Edges to get a list of all nodes that node u is connected
	 * to, the function then loops through the list of incident edges and checks
	 * if node v is connected to node u
	 */
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		try{
		Iterator incident_edges_u = incidentEdges(u);
		if(incident_edges_u == null)
			return false;
		while (incident_edges_u.hasNext()) {
			Edge edge = (Edge) incident_edges_u.next();
			if (v == edge.secondEndpoint())
				return true;

		}
		return false;
		}
		catch(Exception e){
			throw new GraphException();
		}
	}

}
