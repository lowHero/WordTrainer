package com.nz2dev.wordtrainer.domain.models;

import java.util.Date;

/**
 * Created by nz2Dev on 07.12.2017
 */
public class AccountHistory {

    public static AccountHistory createNow(String accountName) {
        return new AccountHistory(accountName, new Date());
    }

    private String accountName;
    private Date loginDate;

    public AccountHistory(String accountName, Date loginDate) {
        this.accountName = accountName;
        this.loginDate = loginDate;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
}
