package exceptions;

public class CouponException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponException(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		String str = "Couldn't retrieve coupon(s).";
		return str;
	}
}
