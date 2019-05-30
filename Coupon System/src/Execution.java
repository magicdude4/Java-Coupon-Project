import beans.*;
import connectionhandling.ConnectionPool;
import dao.CouponClientFacade;
import dbdao.CompanyDBDAO;
import dbdao.CouponDBDAO;
import enums.ClientType;
import enums.CouponType;
import exceptions.CouponException;
import exceptions.LoginMismatch;
import facades.AdminFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;
import utility.CouponSystem;
import utility.DailyCouponExpirationTask;

import java.time.LocalDate;
import java.util.Collection;

public class Execution {
	

    public static void main(String args[]) throws Exception {

        String name;
        String password;
        String email;
        String titleU = "Title";
        String SDU = "StartDate";
        String EDU = "EndDate";
        String amountU = "Amount";
        String typeU = "Type";
        String messageU = "Message";
        String priceU = "Price";
        String imageU = "Image";
        Long abcdId = null, abcId = null, tmpCoupId = null;
        CouponClientFacade client;
        ClientType type;
        Collection<Company> companies;
        Company testCompany;
        Customer testCostumer = new Customer();
        AdminFacade admin = new AdminFacade();
        
        DailyCouponExpirationTask couponRemover = new DailyCouponExpirationTask();
        couponRemover.run();
        Company abc=new Company();
        Company abcd=new Company();
        CompanyDBDAO comp = new CompanyDBDAO();
        CouponDBDAO coup = new CouponDBDAO();
        abc.setCompanyName("abc");
        abc.setEmail("abc@abc.abc");
        abc.setPassword("abcabc");
        abcd.setCompanyName("Around the World");
        abcd.setPassword("superman");
        abcd.setEmail("abcd@abcd.abcd");
        System.out.println(abc.toString());

        Coupon coupon=new Coupon();
        Coupon falseCoup = new Coupon();
        Coupon coupon2 = new Coupon();   
        Coupon coupon3 = new Coupon();
        coupon.setTitle("Free Java project");
        coupon.setStartDate(LocalDate.now());
        try {
            coupon.setEndDate(LocalDate.of(2019,12,1));
        } catch (CouponException e) {
            e.printStackTrace();
        }
        coupon.setAmount(1);
        coupon.setType(CouponType.STUDIES);
        coupon.setMessage("Want a 100% free coupon system project?");
        coupon.setPrice(0.01);
        coupon.setImage("http://www.imagevine.com/info/images/100-coup.gif");
        coupon.setExpirationStatus("ACTIVE");
        coupon.setPurchaceStatus("AVAILABLE");
        coupon2.setTitle("Like never before.");
        coupon2.setStartDate(LocalDate.now());
        try {
            coupon2.setEndDate(LocalDate.of(2032,9,3));
        } catch (CouponException e) {
            e.printStackTrace();
        }
        coupon2.setAmount(3);
        coupon2.setType(CouponType.BULKPURCHACE);
        coupon2.setMessage("Carpe Diem");
        coupon2.setPrice(69.69);
        coupon2.setImage("http://www.imgur.com/info/images/100-coup.gif");
        coupon2.setExpirationStatus("ACTIVE");
        coupon2.setPurchaceStatus("AVAILABLE");
        coupon3.setTitle("Like never before.");
        coupon3.setStartDate(LocalDate.now());
        try {
            coupon3.setEndDate(LocalDate.of(2032,9,3));
        } catch (CouponException e) {
            e.printStackTrace();
        }
        coupon3.setAmount(3);
        coupon3.setType(CouponType.BULKPURCHACE);
        coupon3.setMessage("Carpe Diem");
        coupon3.setPrice(69.69);
        coupon3.setImage("http://www.blabla.com/info/images/100-coup.gif");
        coupon3.setExpirationStatus("ACTIVE");
        coupon3.setPurchaceStatus("AVAILABLE");



        try{
            System.out.println("you have "+ConnectionPool.getInstance().avilabelConnections()+" available connections");
        }catch (Throwable e){
            System.out.println(e.getMessage());
        }

        //Methods.buildCompanyDB();
		//Methods.buildCouponDB();
		//Methods.buildCustomerDB();
		//Methods.buildCompanyCouponDB();
		//Methods.buildCustomerCouponDB();
        
        //****Admin Test****//

        //(1) bad login//
        name= "admin";
        password="12348";
        System.out.println("(1)\nchecking bad login as admin:");
        try{
            client=CouponSystem.getInstance().login(name,password,ClientType.ADMIN);
        }catch (LoginMismatch e){
            System.out.println(e.getMessage());
        }

        name= "admin";
        password="1234";

        
        try {
            // (2)get Admin facade
        	System.out.println("Checking normal log-in as admin:");
            client=CouponSystem.getInstance().login(name,password,ClientType.ADMIN);
            admin=(AdminFacade)client;

            // (3) print a costumer
//            testCostumer=admin.getCostumer(1);
//            System.out.println("(3)\nget costumer:\n"+testCostumer);

            // (4) print all companies
            companies=admin.getAllCompanies();
            System.out.println("(4)\nlist of companies:\n");
            admin.viewCompanyList();
            System.out.println("End of (4)\n");
            // (5) print a company
          //  testCompany=admin.getCompany(10);
          //  System.out.println("(5)\nget company:\n"+testCompany+"\nEnd of (5)");

            // (6) create a company
            
            abcId = admin.addCompany(abc);
            System.out.println("--------------------------------");
            admin.viewCompanyDetails(abc);
            admin.updateCompanyPassword(abcId, "hahaha");
            admin.viewCompanyDetails(abc);

           // abc=admin.getCompany(40); //the ID is set at the company creation in intervals of 10. so in order to get the company id i need to pull it back from server. Alternatively i can just set it to 40, but this is safer.

            // (7) checking the company was added
            System.out.println("(7)\nlist of companies: ");
            admin.viewCompanyList();
            System.out.println("End of list(7)\n");
            // (8) remove company

            admin.viewCompanyById((long)60);
            System.out.println("--------------------------------");
            admin.removeCompany(abc);
            System.out.println("list of companies after removal:");
            //System.out.println("(8)\ncompany removed");
            admin.viewCompanyList();
            System.out.println("End of list(8)");
            //recreate the company for later use.
            abcdId = admin.addCompany(abcd);
            abcId = admin.addCompany(abc);
            System.out.println("--------------------------------");
            admin.viewCompanyList();
        } catch (CouponException|LoginMismatch|NullPointerException e) {
            e.printStackTrace();
        }
        client=null;

        //*** Company test****///
        name="Around the World";
        password="superman1";
        type= ClientType.COMPANY;

        try{
            // (9) bad login as company
        	System.out.println(name+", "+ password);
            System.out.println("(9)\nbad login as company:");
            client=CouponSystem.getInstance().login(name,password,type);
            CompanyFacade companyFacade=(CompanyFacade)client;
            System.out.println("--------------------------------");
        }catch (LoginMismatch e){
            System.out.println(e.getMessage());
        }

        System.out.println("abc test");
        try{
        	abcId = comp.getCompanyIdByName(abc.getCompanyName());
        	abcdId = comp.getCompanyIdByName(abcd.getCompanyName());
            // (10) login as company abc (the new company)
            System.out.println("(10)\nlogin as company abc:");
            admin.viewCompanyList();
            client=CouponSystem.getInstance().login(abc.getCompanyName(), "hahaha", ClientType.COMPANY);
            CompanyFacade companyFacade=(CompanyFacade)client;
            System.out.println("--------------------------------");
            // (11) create coupon
            tmpCoupId = coup.createCoupon(coupon);
            companyFacade.addCoupon(coupon, abcId);

            System.out.println("(11)\ncoupon added:");
            System.out.println(companyFacade.viewAllCompanyCoupons(abcId));

            //(12) remove coupon of other company
            System.out.println("(12)\nremove false coupon:");
            falseCoup.setId((long)1);
            falseCoup.toString();
            try {
                companyFacade.removeCoupon(falseCoup);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println("--------------------------------");
            // (13) remove coupon
            System.out.println("(13)\nremove coupon:");
            companyFacade.removeCoupon(coupon);
            System.out.println("remove succeeded");

            //recreate coupon for next tests
            companyFacade.viewCouponDetails(coupon);
            //(14) update coupon to expire
            System.out.println("(14)\nupdate coupon to expire:");
            try {
                companyFacade.updateCoupon(tmpCoupId, LocalDate.now(), EDU);
            }catch (CouponException | LoginMismatch e){
                System.out.println(e.getMessage());
            }
            System.out.println("--------------------------------");
            companyFacade.viewCompanyCouponsByAmount(abcId, 1);
            //(15) update coupon expire date
            System.out.println("(15)\nupdate coupon:");
            companyFacade.updateCoupon(tmpCoupId,LocalDate.of(2016,3,20), EDU);
            System.out.println("coupon updated");

            //(16) update price of a coupon
            System.out.println("(16)\nupdate coupon price:");
            companyFacade.updateCoupon(tmpCoupId,11.11, priceU);
            System.out.println("price updated");

            companyFacade.removeCoupon(coupon);
            System.out.println("--------------------------------");
            //(17) list of coupons (a company with more than one coupon)
            client=CouponSystem.getInstance().login(abcd.getCompanyName(),abcd.getPassword(),type);
            CompanyFacade tempComp=(CompanyFacade)client;
            System.out.println("(17)\nlist of all coupons of a company:");
            tmpCoupId = coup.createCoupon(coupon2);
            System.out.println("--------------------------------");
            companyFacade.addCoupon(coupon2, abcdId);
            System.out.println(tempComp.viewAllCompanyCoupons(abcdId)+"\nEnd of list(17)");

        }catch (CouponException | LoginMismatch e){
            System.out.println(e.getMessage());
        }

        //*** Costumer test****///
        name="Han Solo";
        password="Falcon";
        email="millenium@mil.com";
        type= ClientType.CUSTOMER;
        testCostumer.setCustomerName(name);
        testCostumer.setPassword(password);
        testCostumer.setEmail(email);

        try{
            // (18) bad login as costumer
        	
            System.out.println("(18)\nbad login as costumer:");
            client=CouponSystem.getInstance().login(name,password+"1",type);
        }catch (CouponException | LoginMismatch e){
            System.out.println(e.getMessage());
        }

        try{
        	System.out.println("--------------------------------");
            //(19) login as costumer:
        	Long custId = admin.addCustomer(testCostumer);
        	System.out.println("--------------------------------");
        	admin.viewCompanyDetails(abc);
            System.out.println("(19)\n login as costumer:");
            client=CouponSystem.getInstance().login(testCostumer.getCustomerName(),testCostumer.getPassword(),type);
            CustomerFacade costumerFacade=(CustomerFacade)client;

            //(20) get coupons by type
            CouponType Ctype=CouponType.BULKPURCHACE;
            System.out.println("(20)\nAvailable coupons by type: ");
            Collection<Coupon> Cset=costumerFacade.viewCustomerCouponsByType(custId, Ctype);
            System.out.println("\nEnd of list (20)");

            //(21) get all coupons
            System.out.println("customer id: "+custId);
            Cset=costumerFacade.viewPurchaseHistory(custId);
            System.out.println("(21)\nAvailable coupons:\n"+Cset+"\nEnd of list (21)");

            //(22) buy a coupon
            System.out.println("coupon id: "+coup.getCouponDBIdByImage(coupon.getImage()));
            System.out.println("--------------------------------");
            costumerFacade.buyCoupon(custId, coupon2);

            //(23) see costumer coupons
            Cset=costumerFacade.viewPurchaseHistory(custId);
            System.out.println("(23)\npurchased Coupons with amount of 2:\n"+Cset+"\nEnd of list (23)");

            //(24) see costumer coupons by type
            Cset=costumerFacade.viewCustomerCouponsByAmount(custId, 2);
            System.out.println("(24)\npurchased Coupons with type "+Ctype+":\n......\n"+"End of list (24)");
            Cset=costumerFacade.viewCustomerCouponsByType(custId, Ctype);
            //(25)see available coupons by price:
            Double price=69.69;
            Cset=costumerFacade.viewCustomerCouponsByPrice(custId, price);
            System.out.println("(25)\navailable coupons priced at "+price+":\n"+Cset+"\nEnd of list (25)");

            //(26) buy coupons with amount 0:
            try {
            	coup.createCoupon(coupon3);
               // System.out.println("(26)\nTry to buy a coupon with amount 0 ");        
            }catch (LoginMismatch e){
                System.out.println(e.getMessage());
            }

            //(27) buy the same coupon twice:
            try{
                System.out.println("(27)\nbuy a coupon one already have:");
                //costumerFacade.buyCoupon(abc.getId(), Client.getId(), 2);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            //(28) coupon by price that a costumer has:
            System.out.println("(28)\nFilter ones coupons up to the price of "+price+":");
            Cset=costumerFacade.viewCustomerCouponsByPrice(custId, price);
            System.out.println(Cset);
            
        }catch (CouponException e){
            System.out.println(e.getMessage());
        }



        // (29) returning the DB to its initial situation for next run (not part of the test)
        try {
            System.out.println("\nEnd of Test");
            System.out.println("***********");
            System.out.println("retrieving DB to its initial condition");
            System.out.println("login as admin");
            client=CouponSystem.getInstance().login("admin","1234", ClientType.ADMIN);
            admin=(AdminFacade)client;
            abc.setId((long)40);            
        } catch (CouponException e) {
            e.printStackTrace();
        }

        try {
        	//Methods.deleteDBs("all");
            CouponSystem.getInstance().shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("cya!");
    }
}


