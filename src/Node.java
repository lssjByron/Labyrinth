
public class Node {
	private int name;
	private boolean mark;
	
	/*
	 * Method: Node
	 * Accepts: int
	 * Returns: N/A
	 * Description: Constructor, initializes variables associated with a Node
	 */
	public Node(int name){
		this.name = name;
		this.mark = false;
	}
	
	/*
	 * Method: setMark
	 * Accepts: boolean
	 * Returns: N/A
	 * Description: Sets the variable mark for it's associated Node 
	 */
	public void setMark(boolean mark){
		this.mark = mark;
	}
	
	/*
	 * Method: getMark
	 * Accepts: N/A
	 * Returns: boolean
	 * Description: Returns the variable mark relative to a Node
	 */
	boolean getMark(){
		return this.mark;
	}
	
	/*
	 * Method: getName
	 * Accepts: N/A
	 * Returns: int
	 * Description: returns the variable name relative to a Node 
	 */
	int getName(){
		return this.name;
	}
}
