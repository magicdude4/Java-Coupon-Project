package facades;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import beans.Client;
import beans.Company;
import beans.Customer;
import dao.CouponClientFacade;
import dbdao.CompanyDBDAO;
import dbdao.CustomerDBDAO;
import enums.ClientType;
import exceptions.CouponException;
import exceptions.LoginMismatch;
import exceptions.NullResult;
import utility.Methods;

public class AdminFacade extends Client implements CouponClientFacade {
	Collection<Company> tmpComp = new ArrayList<>();
	Collection<Company> tmpCust = new ArrayList<>();
	Customer cust = new Customer();
	CompanyDBDAO Company = new CompanyDBDAO();
	CustomerDBDAO Customer = new CustomerDBDAO();
	
	public AdminFacade() {
		
	}

	public AdminFacade(Long id, String name, String password, String email, ClientType type) {
		super(id, name, password, email, type);
	}
	
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) throws LoginMismatch {
		if ((name.equalsIgnoreCase("admin"))&&(password.equals("1234"))&&(clientType.equals(ClientType.ADMIN))) {
			System.out.println("Logged-in as "+clientType+".");
		}else {
			System.err.println("Couldn't log-in as "+clientType+".");
		}return new AdminFacade();
	}
	
	public Long addCompany(Company company) throws SQLException, NullResult {
		Long id = null;
		try {
			if (Boolean.FALSE.equals(Company.checkIfCompanyExists(company.getCompanyName()))&&Boolean.TRUE.equals(Methods.emailValidation(company.getEmail()))) {
				id = Company.createCompany(company);
			}else if (Boolean.TRUE.equals(Company.checkIfCompanyExists(company.getCompanyName()))&&Company.checkCompanyStatus(Company.getCompanyIdByName(company.getCompanyName())).equals("INACTIVE")) {
				Company.activateCompany(company);
				System.out.println("Company "+company.getCompanyName()+" status changed to 'ACTIVE'.");
			}else if (Boolean.TRUE.equals(Company.checkIfCompanyExists(company.getCompanyName()))&&Company.checkCompanyStatus(Company.getCompanyIdByName(company.getCompanyName())).equals("ACTIVE")) {
				System.err.println("Company "+company.getCompanyName()+" already exists and is active.");
			}
		}catch (Exception e) {
			System.err.println("There was a problem with SQL when attempting to create the specified company.");
		}return id;
	}
	
	public void removeCompany(Company company) throws SQLException, CouponException {
		try {
			Company.removeCompany(company);
			System.out.println("Company "+company.getCompanyName()+" status changed to 'INACTIVE'.");
		}catch (Exception e) {
			System.err.println("Couldn't remove company "+company.getCompanyName()+".");
		}
	}
	
	public void updateCompanyName(Long id, String name) throws SQLException, NullResult {
		try {
			Company.updateCompanyName(id, name);
		}catch (Exception e) {
			System.err.println("Couldn't update company "+ Company.getCompanyNameById(id));
		}
	}
	
	public void updateCompanyPassword(Long id, String password) throws SQLException, NullResult {
		try {
			Company.updateCompanyPassword(id, password);
		}catch (Exception e) {
			System.err.println("Couldn't update company "+ Company.getCompanyNameById(id));
		}
	}
	
	public void updateCompanyEmail(Long id, String email) throws SQLException, NullResult {
		try {
			Company.updateCompanyEmail(id, email);
		}catch (Exception e) {
			System.err.println("Couldn't update company "+ Company.getCompanyNameById(id));
		}
	}
	
	public void viewCompanyList() throws SQLException, NullResult {
		try {
			Company comp = new Company();
			tmpComp = Company.getAllCompanies();
			Iterator<Company> iter = tmpComp.iterator();
			while (iter.hasNext()) {
				comp = iter.next();
				comp.toString();
			}
		}catch (Exception e) {
			System.err.println("Couldn't retrieve company list. "+e.getMessage());
		}
	}
	
	public Company viewCompanyByName(String name) throws Exception, CouponException {
		Company comp = new Company();
		try {
			comp = Company.getCompanyByName(name);
			comp.toString();
		}catch (NullResult e) {
			System.err.println("Company "+name+" doesn't exist.");
		}return comp;
	}
	
	public Company viewCompanyById(Long id) throws SQLException, NullResult {
		Company comp = new Company();
		try {
			comp = Company.getCompanyById(id);
			if(comp!=null) {
				comp.toString();
			}else System.err.println("Company with ID: "+id+" doesn't exist.");
		}catch (Exception e) {
			System.err.println("There was an SQL error attempting to retrieve the requested company, "+e.getMessage());
		}return comp;
	}
	
	public Collection<Company> getAllCompanies() throws NullResult{
		try {
			tmpComp = Company.getAllCompanies();
		}
		catch (Exception e) {
			System.err.println("There was an SQL error while attempting to retireve the existing companies.");
		}if (tmpComp.isEmpty())
			System.err.println("No companies were found in the DB.");
		return tmpComp;
	}
	
	public void viewCompanyDetails(Company company) throws SQLException, NullResult {
		if (Boolean.TRUE.equals(Company.checkIfCompanyExists(company.getCompanyName()))) {
			Company.getCompanyDetails(company);
		}else System.err.println("Company "+company.getCompanyName()+" doesn't exist.");
	}
	
	public Long addCustomer(Customer customer) throws SQLException, NullResult {
		Long id = null;
		if (Boolean.FALSE.equals(Customer.checkIfCustomerExistsByName(customer.getCustomerName()))) {
			if (Boolean.TRUE.equals(Methods.emailValidation(customer.getEmail()))) {
				id = Customer.createCustomer(customer);
			}else System.err.println("Incorrect email pattern.");
		}else System.err.println("Customer "+customer.getCustomerName()+" already exists.");
		return id;
	}
	
	public void removeCustomer(Long id) throws Exception {
		try {
			Customer.removeCustomer(id);
		}catch (Exception e) {
			System.err.println("Couldn't remove customer with ID: "+id+".");
		}
	}
	
	public void updateCustomerDetails(Customer customer, String password, String email) throws NullResult {
		try {
			Customer.updateCustomer(customer, password, email);
		}catch (Exception e) {
			System.err.println("Couldn't update customer "+customer.getCustomerName()+".");
		}
	}
	
	public void viewCustomerList() throws SQLException {
		try {
			Customer.getCustomerList();
		}catch (Exception e) {
			System.err.println("No customers exist in the DB.");
		}
	}
	
	public Customer viewCustomerById(Long id) throws SQLException, CouponException {
		try {
			cust = Customer.getCustomerById(id);
			cust.toString();
		}catch (Exception e) {
			System.err.println("No customer with ID ["+id+"] exists.");
		}return cust;
	}
	
	public Customer viewCustomerByName(String name) throws SQLException, CouponException {
		try {
			cust = Customer.getCustomerByName(name);
			cust.toString();
		}catch (Exception e) {
			System.err.println("Customer "+name+" doesn't exist.");
		}return cust;
	}
}
