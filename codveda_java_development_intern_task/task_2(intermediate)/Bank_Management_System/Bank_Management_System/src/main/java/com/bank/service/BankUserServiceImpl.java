package com.bank.service;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.bank.exception.BankUserException;
import com.bank.model.BankUserDetails;
import com.bank.userDAO.BankUserDAO;
import com.bank.userDAO.BankUserDAOImpl;

public class BankUserServiceImpl implements BankUserService
{
     Scanner sc = new Scanner(System.in);
     BankUserDAO bankUserDAO = new BankUserDAOImpl();
	@Override
	public void forSleep(String value) 
	{
		for (int i = 0; i < value.length();i++)
		{
			System.out.print(value.charAt(i));
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
		
	}

	@Override
	public void userRegistration() 
	{
		List<BankUserDetails> allBankUserDetails =  bankUserDAO.selectAllBankUserDetails();
		BankUserDetails bankUserDetails = new BankUserDetails();
		System.out.println("Enter your name");
		String name = sc.next();
		bankUserDetails.setName(name);
		
		
		
		boolean status = true;
		while(status)
		{
			try
			{
		System.out.println("Enter your Emailid");
		String emailid = sc.next();
		if(emailid.endsWith("@gmail.com"))
		{
			long emailidcount  = allBankUserDetails.stream().filter(user->user.getEmailid().equals(emailid)).count();
			if(emailidcount==0)
			{
				bankUserDetails.setEmailid(emailid);
				status=false;
			}
			else
			{
				throw new BankUserException("Already Emaild Existed");
			}
		}
		else
		{
			System.out.println("Invalid emailid");
		}
		 }
			catch(BankUserException e)
			{
				System.out.println(e.getMsg());
			}
		}
		
		
		
		boolean status1 = true;
		while(status1)
		{
			try
			{
		    System.out.println("Enter your Mobile Number");
		   long mobilenumber = sc.nextLong();
		   if(mobilenumber>=6000000000l && mobilenumber<=9999999999l)
		   {
			long mobilenumbercount  = allBankUserDetails.stream().filter(user->user.getMobilenumber()==mobilenumber).count();
			if(mobilenumbercount==0)
			{
				bankUserDetails.setMobilenumber(mobilenumber);
				status1=false;
			}
			else
			{
				throw new BankUserException("Already Emaild Existed");
			}
		   } 
		   else
		   {
			throw new  BankUserException("Invalid mobile Number");
		   }
			}
			catch(BankUserException e )
			{
				System.out.println(e.getMsg());
			}
		}
		boolean aadharstatus =true;
		while(aadharstatus)
		{
			try
			{
		System.out.println("Enter your Adhar Number");
		long adharnumber = sc.nextLong();
		if(adharnumber>=100000000000l && adharnumber<=999999999999l)
		{
			long aadharnumbercount  = allBankUserDetails.stream().filter(user->user.getAdharnumber()==adharnumber).count();
			if(aadharnumbercount==0)
			{
				bankUserDetails.setAdharnumber(adharnumber);;
				aadharstatus=false;
			} 
			else
			{
				throw new BankUserException("Already Emaild Existed");
			}
		}
		else
		{
			throw new BankUserException("Invalid Aadhar Number");
		}
			}
			catch(BankUserException e)
			{
				System.out.println(e.getMsg());
			}
		}
		boolean panstatus = true;
		while(panstatus) {
			try {
				System.out.println("Enter Your PAN Number");
		        String pannum = sc.next();
		        if(pannum.length() != 10) {
					throw new BankUserException("Pan number length is not valid");
				}else {
					for(int i=0; i<5; i++) {
						if(Character.isUpperCase(pannum.charAt(i)) && Character.isAlphabetic(pannum.charAt(i))) {
						}else {
							throw new BankUserException("Alphabet should be capital");
						}
					}
					for(int i=5; i<9; i++) {
						if(Character.isDigit(pannum.charAt(i))) {

						}else {
							throw new BankUserException("Must be digit should placed in this position");
						}
					}
					if(Character.isUpperCase(pannum.charAt(9)) && Character.isAlphabetic(pannum.charAt(9))){
					}else {
						throw new BankUserException("Alphabet should be capital in last position");
					}
				}
		        long pancount = allBankUserDetails.stream()
		        		.filter(user->user.getPannumber().equals(pannum)).count();
		        if(pancount == 0) {
		        	bankUserDetails.setPannumber(pannum);;
		        	panstatus = false;
		        }else {
		        	throw new BankUserException("Pan Number Already exists");
		        }
			}catch (BankUserException e) {
				System.out.println(e.getMsg());
			}
		}
		System.out.println("Enter your Address");
		String address = sc.next();
		bankUserDetails.setAddress(address);
		boolean gendercheck=true;
		while(gendercheck)
		{
			try
			{
		System.out.println("Enter your Gender");
		String gender = sc.next();
		if(gender.equalsIgnoreCase("male")||gender.equalsIgnoreCase("female")||gender.equalsIgnoreCase("other"))
		{
			gendercheck=false;
			bankUserDetails.setGender(gender);
		}
		else
		{
			System.out.println("Invalid gender");
		}
		}
			catch(BankUserException e)
			{
				System.out.println(e.getMsg());
			}
	}
		System.out.println("Enter your Amount");
		double amount = sc.nextDouble();
		bankUserDetails.setAmount(amount);
		bankUserDAO.insertBankUserDetails(bankUserDetails);
	}

}
