package beans;
import java.time.LocalDate;

import enums.CouponType;
import exceptions.CouponException;

public class Coupon  {
	
	//Methods
	private Long id;
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private int amount;
	private CouponType type;
	private String message;
	private Double price;
	private String image;
	private String purchaceStatus;
	private String expirationStatus;
	
	//CTOR's
	public Coupon() {
		
	}
	
	public Coupon(Long id, String title, LocalDate startDate, LocalDate endDate, int amount, CouponType type, String message,
			Double price, String image, String purchaceStatus, String expirationStatus) throws CouponException {
		setId(id);
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setType(type);
		setMessage(message);
		setPrice(price);
		setImage(image);
		setPurchaceStatus(purchaceStatus);
		setExpirationStatus(expirationStatus);

	}

	//Methods
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate localDate) {
		this.startDate = localDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate localDate)throws CouponException {
		this.endDate = localDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPurchaceStatus() {
		return purchaceStatus;
	}

	public void setPurchaceStatus(String purchaceStatus) {
		this.purchaceStatus = purchaceStatus;
	}
	public String getExpirationStatus() {
		return expirationStatus;
	}

	public void setExpirationStatus(String expirationStatus) {
		this.expirationStatus = expirationStatus;
	}
	
	@Override
	public String toString() {
		return "Coupon [ID: "+id+", Title: "+title+", Start Date: "+startDate+", End Date: "+endDate+", Amount: "+amount+", Type: "+type
			+", Message: "+message+", Price: "+price+", Image: "+image+", Purchace Status: "+purchaceStatus+", Expiration Status: "+expirationStatus+"]";
	}
}
