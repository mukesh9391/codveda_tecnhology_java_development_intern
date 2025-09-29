package com.bank.service;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.bank.exception.AdminException;
import com.bank.model.BankUserDetails;
import com.bank.userDAO.AdminDAO;
import com.bank.userDAO.AdminDAOImpl;
import com.bank.userDAO.BankUserDAO;
import com.bank.userDAO.BankUserDAOImpl;

public class AdminServiceImpl implements AdminService
{
     Scanner sc = new Scanner(System.in);
     AdminDAO adminDAO = new AdminDAOImpl();
     BankUserDAO bankUserDAO = new BankUserDAOImpl();
     List<BankUserDetails> allbankuserdetails;
     int count=1;
	@Override
	public void adminLogin() 
	{
     allbankuserdetails  =	 bankUserDAO.selectAllBankUserDetails();
		System.out.println("Enter Admin Email id");
		String emailid = sc.next();
		System.out.println("Enter Admin password");
		String password = sc.next();
		try
		{
		if(adminDAO.getAdminDetailsByUsingEmailidAndPassword(emailid, password))
		{
			System.out.println("Enter \n1.To Get All User Details\n2.To Get All Account Request Details\n3.To Get All Account Closing Request Details");
			switch (sc.nextInt()) {
			case 1:
				  System.out.println("All User Details");
				break;
             case 2:
				 System.out.println("All Account Request Details");
				 acceptAllAccountRequestDetails();
				break; 
             case 3:
 				System.out.println("All Account Closing Rquest Details");
 				break;
			default:
				break;
			}
		}
		else
		{
		  throw new AdminException("Invalid credential");
		}
		}
		catch (AdminException e) {
			System.out.println(e.getMsg());
		}
	}
	@Override
	public void acceptAllAccountRequestDetails() 
	{
		List<BankUserDetails> listofpendingData = allbankuserdetails.stream()
				.filter((user->user.getStatus().equals("pending")))
				.collect(Collectors.toList());
		
		listofpendingData.forEach((pendingdata)->{
			System.out.println("S.No:"+count++);
			System.out.println("User Name :"+pendingdata.getName());
			System.out.println("User Email ID:"+pendingdata.getEmailid());
			System.out.println("User Status:"+pendingdata.getStatus());
			long mobile = pendingdata.getMobilenumber();
			String mobilestr = Long.toUnsignedString(mobile);
			String mobileresult1 = "";
			String mobileresult2 = "";
			for (int i = 0; i < mobilestr.length()-8; i++) 
			{
				mobileresult1=mobileresult1+mobilestr.charAt(i);
			}
			for (int i = 8; i < mobilestr.length(); i++) 
			{
				mobileresult2=mobileresult2+mobilestr.charAt(i);
			}
			System.out.println("User Mobiile Number:"+mobileresult1+"******"+mobileresult2);
			long aadhar = pendingdata.getAdharnumber();
			String aadharstr = Long.toString(aadhar);
			String aadharresult1 = "";
			String aadharresult2 = "";
			for (int i = 0; i < aadharstr.length()-7; i++) 
			{
				aadharresult1=aadharresult1+aadharstr.charAt(i);
			}
			for (int i = 7; i < aadharstr.length(); i++) 
			{
				aadharresult2=aadharresult2+aadharstr.charAt(i);
			}
			System.out.println("user Adhar Number:"+aadharresult1+"****"+aadharresult2);
			System.out.println("**--------***----***----****");
		});
		System.out.println("Enter S.no To Select User Details");
		int sno=sc.nextInt();
		BankUserDetails bankUserDetails=listofpendingData.get(sno-1);
		System.out.println(bankUserDetails);
		System.out.println("Enter \n1.To Accept \n2.To Reject");
		switch (sc.nextInt()) {
		case 1:
			System.out.println("Accept");
			acceptUserRequest(bankUserDetails);
			break;
        case 2:
			System.out.println("Reject");
			break;
		default:System.out.println("Invalid option");
			break;
		}
		
	}
	@Override
	public void acceptUserRequest(BankUserDetails bankUserDetails) 
	{
		Random random = new Random();
	    int pin =random.nextInt(10000);
	    if(pin<1000)
	    {
	    	pin+=1000;
	    }
	    
	    int accountnumber =   random.nextInt(1000000);
	    if(accountnumber<1000000)
	    {
	    	accountnumber+=1000000;
	    }
	int resultset =	bankUserDAO.updatePinAndAccountNumber(pin, accountnumber, bankUserDetails.getId());
		if(resultset>0)
		{
			System.out.println(bankUserDetails.getName()+"Account accepted successfully");
			System.out.println("with TheAccount number:"+accountnumber);
			System.out.println("with The PIN Number :"+pin);
		}
		else
		{
			throw new AdminException("Invalid credetials");
		}
	}
	@Override
	public void rejectUserRequest() {
		// TODO Auto-generated method stub
		
	}

}
