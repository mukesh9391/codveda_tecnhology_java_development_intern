package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EmployeeRegistration extends GenericServlet
{

	@Override
	public void service(ServletRequest servletrequest, ServletResponse servletresponse) throws ServletException, IOException 
	{
     System.out.println("Employeee registration successfully");
	   String name =  servletrequest.getParameter("empname");
	    String tempsalary = servletrequest.getParameter("empsalary");
	       double salary =   Double.parseDouble(tempsalary);
	       String tempdepetno = servletrequest.getParameter("empdeptno");
	       int deptno =  Integer.parseInt(tempdepetno);
	      String emailid =   servletrequest.getParameter("empemailid");
	      System.out.println("Employee Name:"+name);
	      System.out.println("Employee Salary:"+salary);
	      System.out.println("Employee DeptNo:"+deptno);
	      System.out.println("Employee Emailid:"+emailid);

          String insert  = "insert into employee_details(Employee_Name, Employee_Salary, Employee_Dept_No, employee_emailid) values(?,?,?,?)";
            try {
            	Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection	= DriverManager.getConnection("jdbc:mysql://localhost:3306/teca_64_advance_java?user=root&password=12345");
			PreparedStatement preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setString(1, name);
			preparedStatement.setDouble(2, salary);
			preparedStatement.setInt(3, deptno);
			preparedStatement.setString(4, emailid);
			  int result = preparedStatement.executeUpdate();
			  /*
          •	PrintWriter is the class which is used to print the ouput or print the response on the webpage
          •	To get an object to the PrintWriter class we need to make use of getwriter method();
          •	getwriter() is the method which is used to give the object to the print writer class
          •	Getwriter () is the nonstatic method which is present in the servlet response interface
          •	To give the content to the Printwriter class we make use of method of println()
          •	println()  method is the nonstatic method which is present in the printwriter class.
          •	Syntax: PrintWriter writer= response.getwriter();
          •	Writer.println(“give the values as an argument which should be printed on the page”);
          *************************************************************************************************
          * To apply the HTML Properties for the the given text we need make use of setContentType() method
          * It is non static method and argument method presrnt in ServeletResponse Interface
          * To apply the HTML tags to the text we make use of
          *  servletresponse.setContentType("text/html")
			   */
			  PrintWriter writer = servletresponse.getWriter();
			  servletresponse.setContentType("text/html");
			  if (result>0) 
			  {
				//System.out.println("Employeee registration successfully");
				writer.println("<center><h1>Employeee registration successfully</h1></center>");
			}
			  else
			  {
				  //System.out.println("Invalid Data");
				  writer.println("Invalid Data");
				  
				  
			  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
	}

}
