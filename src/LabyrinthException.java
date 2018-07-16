
public class LabyrinthException extends Exception {
	/*
	 * Method: LabyrinthException 
	 * Accepts: N/A
	 * Returns: N/A
	 * Description: Constructor, if exception is thrown the user is alerted of error 
	 */
	public LabyrinthException(){
		super("Graph does not exist, problem with input file");
	}
}
