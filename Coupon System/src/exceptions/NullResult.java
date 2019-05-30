package exceptions;

public class NullResult extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NullResult(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		String str = "Couldn't find the query in the DB.";
		return str;
	}
}
