package com.bank.userDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.BankUserDetails;
import com.bank.service.AdminService;
import com.bank.service.AdminServiceImpl;

public class BankUserDAOImpl implements BankUserDAO
{
	private static final String url="jdbc:mysql://localhost:3306/teca_64_advance_java?user=root&password=12345";
    private static final String insert ="insert into bank_user_details(user_name, user_emailid, user_aadhar_number, user_pan_number, user_mobile_number, user_address, user_gender,amount, status) values(?,?,?,?,?,?,?,?,?)" ;
    private static final String select_all = "select * from bank_user_details";
    private static final String update = "update bank_user_details set user_PIN=?,user_account_number=?,status=? where user_id=?";
	@Override
	public void insertBankUserDetails(BankUserDetails bankUserDetails) 
	{
		 try 
		 {
		Connection connection = DriverManager.getConnection(url); 
		PreparedStatement preparedStatement = connection.prepareStatement(insert); 
		preparedStatement.setString(1, bankUserDetails.getName());
		preparedStatement.setString(2, bankUserDetails.getEmailid());
		preparedStatement.setLong(3, bankUserDetails.getAdharnumber());
		preparedStatement.setString(4, bankUserDetails.getPannumber());
		preparedStatement.setLong(5, bankUserDetails.getMobilenumber());
		preparedStatement.setString(6, bankUserDetails.getAddress());
		preparedStatement.setString(7, bankUserDetails.getGender());
		preparedStatement.setDouble(8, bankUserDetails.getAmount());
		preparedStatement.setString(9, "pending");
		int result = preparedStatement.executeUpdate();
		if(result!=0)
		{
			System.out.println(bankUserDetails.getName()  + " " + "Registration successfully");
		}
		else
		{
			System.out.println("not successflly");
		}
		 }
		 catch(SQLException e)
		 {
			 e.printStackTrace();
		 }
		
		
	}
	@Override
	public List<BankUserDetails> selectAllBankUserDetails() 
	{
		try {
		Connection connection	= DriverManager.getConnection(url);
		PreparedStatement preparedStatement = connection.prepareStatement(select_all);
		ResultSet resultSet=preparedStatement.executeQuery();
		List<BankUserDetails> list = new ArrayList<BankUserDetails>();
		if(resultSet.isBeforeFirst())
		{
			while(resultSet.next())
			{
				BankUserDetails bankUserDetails = new BankUserDetails();
				bankUserDetails.setId(resultSet.getInt("user_id"));
				bankUserDetails.setEmailid(resultSet.getString("user_emailid"));
				bankUserDetails.setAdharnumber(resultSet.getLong("user_aadhar_number"));
				bankUserDetails.setPannumber(resultSet.getString("user_pan_number"));
				bankUserDetails.setMobilenumber(resultSet.getLong("user_mobile_number"));
				bankUserDetails.setStatus(resultSet.getString("status"));
				bankUserDetails.setName(resultSet.getString("user_name"));
				
				list.add(bankUserDetails);
			}
			
			return list;
		}
		
		else
		{
			return null;
		}
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public int updatePinAndAccountNumber(int pin, int accountnumber,int id) 
	{
		//update bank_user_details set user_PIN=?,user_account_number=?,status=? where user_id=?
		try {
		Connection connection =	DriverManager.getConnection(url);
		PreparedStatement preparedStatement = connection.prepareStatement(update);
		preparedStatement.setInt(1, pin);
		preparedStatement.setInt(2, accountnumber);
		preparedStatement.setString(3, "Accepted");
		preparedStatement.setInt(4, id);
		return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}

}
