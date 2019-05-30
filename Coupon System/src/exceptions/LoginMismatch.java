package exceptions;

public class LoginMismatch extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginMismatch(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		String str = "Couldn't log-in.";
		return str;
	}
}
