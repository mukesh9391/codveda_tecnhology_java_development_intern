package com.bank;

import java.util.Scanner;

import com.bank.service.AdminService;
import com.bank.service.AdminServiceImpl;
import com.bank.service.BankUserService;
import com.bank.service.BankUserServiceImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Scanner scanner = new Scanner(System.in);
    	BankUserService bankUserService = new BankUserServiceImpl();
    	AdminService adminService = new AdminServiceImpl();
    	bankUserService.forSleep("Wel come to Bank Management System ❤️💋");
    	boolean cond = true;
    	while(cond)
    	{
    		System.out.println("\n 1 Enter For UseRregistration \n 2 Enter For Admin Login \n 3 Enter For User Login");
            switch (scanner.nextInt()) 
            {
   		   case 1:
   			   //bankUserService.forSleep("User Registaration");
   			   bankUserService.userRegistration();
   			break;
   		   case 2:
   				  //bankUserService.forSleep("Admin Login");
   			   adminService.adminLogin();
   		   break;
   		   case 3:
   			   bankUserService.forSleep("User Login");
   		   break;
   		   default:System.out.println("Invalid option");
   			break;
            }
   			System.out.println("Do you want continue (yes/no)");
   			if(scanner.next().equalsIgnoreCase("yes"))
   			{
   				
   			}
   			else
   			{
   				bankUserService.forSleep("🙏🙏Thank you visit again🙏🙏");
   				cond=false;
   			}
   			
   		  }
    	}
        
        
    }
