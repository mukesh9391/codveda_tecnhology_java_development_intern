package com.bank.userDAO;

import java.util.List;

import com.bank.model.BankUserDetails;

public interface BankUserDAO 
{
void insertBankUserDetails(BankUserDetails bankUserDetails);
List<BankUserDetails> selectAllBankUserDetails();
int updatePinAndAccountNumber(int pin ,int accountnumber,int id);
}
