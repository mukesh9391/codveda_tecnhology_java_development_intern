package com.bank.service;

import com.bank.model.BankUserDetails;

public interface AdminService 
{
void adminLogin();
void acceptAllAccountRequestDetails();
void acceptUserRequest(BankUserDetails bankUserDetails);
void rejectUserRequest();
}
