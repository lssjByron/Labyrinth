public class Edge {
	private Node u;
	private Node v;
	private String type;
	
	/*
	 * Method: Edge
	 * Accepts: Node, Node, String
	 * Returns: N/A
	 * Description: Constructor, initializes variables associated with an Edge
	 */
	public Edge(Node u, Node v, String type){
		this.u = u;
		this.v = v;
		this.type = type;
	}
	
	/*
	 * Method: firstEndpoint 
	 * Accepts: N/A
	 * Returns: Node
	 * Description: Returns the first node relative to an edge
	 */
	public Node firstEndpoint(){
		return this.u;
	}
	
	/*
	 * Method: secondEndpoint 
	 * Accepts: N/A
	 * Returns: Node
	 * Description: Returns the second node relative to an edge
	 */
	public Node secondEndpoint(){
		return this.v;
	}
	
	/*
	 * Method: getType 
	 * Accepts: N/A
	 * Returns: String
	 * Description: Returns the variable type relative to an edge
	 */
	public String getType(){
		return this.type;
	}
	
	/*
	 * Method: setType
	 * Accepts: String
	 * Returns: N/A
	 * Description: Sets the variable type relative to an edge 
	 */
	public void setType(String type){
		this.type = type;
	}
}
