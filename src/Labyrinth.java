import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Labyrinth {
	private Graph graph;
	private Node beginning;
	private Node end;

	private int width;
	private int length;
	private int bombsAllowed;
	private int acidBombsAllowed;
 	
	/*
	 * Method: Labyrinth
	 * Accepts: String
	 * Returns: N/A
	 * Description:  Constructor, initialize variables based on the input file. 
	 * 				 First reads from the file width and length, then bombs, then
	 * 				 the labyrinth. The constructor reads the labyrinth provided
	 * 				 by the input file line by line in a for loop, within the 
	 * 				 for loop is a nested for loop that that reads each character
	 * 				 of the line, a switch statement is implemented to determine
	 * 				 whether a Node or an Edge is being read, if it is an edge than
	 * 				 the type of edge is also determined. Edges are then 
	 * 				 created with their respected first and second end points
	 * 				 based on where the edge is found in the graph, and whether
	 * 				 the edge is horizontal and vertical 
	 */
	public Labyrinth(String inputFile) throws LabyrinthException {
		BufferedReader input;
		try {
			int number_nodes;
			input = new BufferedReader(new FileReader(inputFile));
			// Process first four lines of the file
			Integer.parseInt(input.readLine()); //don't need first line
			width = Integer.parseInt(input.readLine());
			length = Integer.parseInt(input.readLine());
			bombsAllowed = Integer.parseInt(input.readLine());
			acidBombsAllowed = Integer.parseInt(input.readLine());

			number_nodes = width * length;
			this.graph = new Graph(number_nodes);

			String line;
			String type = "";
			int row = 0;
			Node u = null;
			Node v = null;
			int node_id = 0;
			for (;;) {
				line = input.readLine();
				if (line == null) { // End of file
					input.close();
					return;
				}

				
				boolean is_node = false;
				//switch statement to determine if node, and they type of wall
				for (int i = 0; i < line.length(); ++i) {
					switch (line.charAt(i)) {
					case 'h':
					case 'v':
						type = "wall";
						is_node = false;
						break;
					case 'H':
					case 'V':
						type = "thickWall";
						is_node = false;
						break;
					case 'm':
					case 'M':
						type = "metalWall";
						is_node = false;
						break;
					case 'b':
						is_node = true;
						if (i != 0 && row != 0)
							node_id++;
						beginning = graph.getNode(node_id);
						break;
					case 'x':
						is_node = true;
						if (i != 0 && row != 0)
							node_id++;
						end = graph.getNode(node_id);
						break;
					case ' ':
						is_node = true;
						break;
					case '-':
					case '|':
						is_node = false;
						type = "corridor";
						break;
					case '+':
						is_node = true;
						if (i != 0 || row != 0)
							node_id++;

					}
					if (is_node == false) {
						if ((row % 2) == 0) { //row contains only horizontal
							u = graph.getNode(node_id);
							v = graph.getNode(node_id + 1);
						} else { //row is contains only vertical edges
							int location_first_node = width //helps get the first number in a row
									* ((row - 1) / 2);  //go down a row and dividing by two will get the right number for the first node
														//in a row
							location_first_node += (i / 2); //used to get location of first node
							int location_second_node = width
									* ((row + 1) / 2);
							location_second_node += (i / 2); //used to get location of second node
							u = graph.getNode(location_first_node);
							v = graph.getNode(location_second_node);
						}
					}

					if (u != null && v != null) {
						graph.insertEdge(u, v, type);
						u = null;
						v = null;
					}

				}
				++row;

			}
		} catch (Exception e) {
			throw new LabyrinthException();
		}
	}

 	/*
	 * Method: getGraph 
	 * Accepts: N/A
	 * Returns: Graph
	 * Description: returns a graph associated with a Labyrinth,
	 * 				if graph does not exist LabyrinthException
	 * 				is thrown
	 */
	public Graph getGraph() throws LabyrinthException{
		if(graph == null)
			throw new LabyrinthException();
		return this.graph;
	}

 	/*
	 * Method: solve 
	 * Accepts: N/A
	 * Returns: Iterator
	 * Description: sends an empty vector to the function findPath
	 * 				for it to be inserted with the correct Nodes
	 * 				used to escape the Labyrinth. Returns an iterator the
	 * 				the vector containing the nodes used to escape
	 * 				the labyrinth, if no escape is found, return null
	 */
	public Iterator solve() throws GraphException {
		Vector<Node> path = new Vector<Node>();

		//Iterator<Edge> incident_edges = graph.incidentEdges(beginning);
		boolean is_path = findPath(path, beginning);
		if(!is_path)
			return null;
		Iterator<Node> found_path = path.iterator();
		return found_path;
	}

 	/*
	 * Method: findPath
	 * Accepts: Vector<Node>, Node
	 * Returns: boolean
	 * Description: Recursive function that iterates through a nodes edges to find
	 * 				the end point of the labyrinth. A player no longer being able 
	 * 				to continue along a path is determined when all end points of 
	 * 				a node's edges are checked and the end points require bombs that
	 * 				the player no longer has or node is already marked. If this happens
	 * 				the player goes back a node and the function checks all other edges.
	 * 				If no path is found the function returns false, otherwise if
	 * 				a path is found, the function returns true 
	 * 				
	 */
	private boolean findPath(Vector<Node> path, Node u) throws GraphException {
		path.add(u);
		u.setMark(true);
		Iterator<Edge> incident_edges = graph.incidentEdges(u);
		if (u == end)
			return true;

		while (incident_edges.hasNext()) {
			Edge edge = incident_edges.next();
			Node v = edge.secondEndpoint();
			//get rid of bombs
			if (edge.getType() == "metalWall")
				acidBombsAllowed--;
			else if (edge.getType() == "thickWall")
				bombsAllowed -= 2;
			else if (edge.getType() == "wall")
				bombsAllowed--;
			
			if (acidBombsAllowed >= 0 && bombsAllowed >= 0) { //don't let player continue without bombs
				if (!v.getMark()) { //if player hasn't already been here
					boolean is_true = findPath(path, v); //recursive if the player can continue
					if (is_true == true)
						return true; //path found
				}
			}
			//give bombs back when going back a node, as all edges have been checked
			if (edge.getType() == "metalWall")
				acidBombsAllowed++;
			else if (edge.getType() == "thickWall")
				bombsAllowed += 2;
			else if (edge.getType() == "wall")
				bombsAllowed++;
		}
		
		u.setMark(false);
		path.remove(u);
		return false;
	}
}
