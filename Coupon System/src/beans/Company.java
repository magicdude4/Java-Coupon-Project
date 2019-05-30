package beans;

public class Company {
	
	//Attributes
	private Long id;
	private String companyName;
	private String password;
	private String email;
	private String status;
	
	//CTOR's
	public Company() {
		
	}
	
	public Company(Long id, String companyName, String password, String email, String status) {
		setId(id);
		setCompanyName(companyName);
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
		return "Company [ID: " + getId() + ", Name: " + companyName + ", Password: " + password + ", Email: " + email + ", Status: " + status + "]";
	}
}
