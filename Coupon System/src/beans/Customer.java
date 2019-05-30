package beans;

public class Customer {
	
	//Attributes
	private Long id;
	private String customerName;
	private String password;
	private String email;
	private String status;
	
	//CTORs
	public Customer() {
		
	}
	
	public Customer(Long id, String customerName, String password, String email, String status) {
		setId(id);
		setCustomerName(customerName);
		setPassword(password);
		setEmail(email);
		setStatus(status);
	}

	//Methods
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Customer [ID: " + id + ", Name: " + customerName + ", Password: " + password + ", Email: " + email + ", Status: " + status + "]";
	}
}
