package com.bank.userDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements AdminDAO
{
  private static final String url = "jdbc:mysql://localhost:3306/teca_64_advance_java?user=root&password=12345";
  private static final String admin_login = "select * from admin_details where Admin_emailid=? and Admin_Password=?";
	@Override
	public boolean getAdminDetailsByUsingEmailidAndPassword(String emailid, String password) 
	{
	   try {
	Connection connection = DriverManager.getConnection(url);
	PreparedStatement preparedStatement = connection.prepareStatement(admin_login);
	preparedStatement.setString(1, emailid);
	preparedStatement.setString(2, password);
    ResultSet resultSet =preparedStatement.executeQuery();
    if (resultSet.next()) {
		return true;
	}
    else
    {
    	return false;
    }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false; 
	}
		
	}

}
