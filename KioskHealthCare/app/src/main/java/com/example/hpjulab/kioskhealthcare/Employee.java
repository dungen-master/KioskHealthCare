package com.example.hpjulab.kioskhealthcare;

/**
 * Created by hpjulab on 9/15/2015.
 */

import org.simpleframework.xml.*;

@Root
public class Employee{

    @Element(required = false)
    private String employeeId,name,ph_no,address,country,state,gender,password;


    public String getName()
    {
        return name;
    }
    public void setName(String s)
    {
        this.name=s;
    }

    public String getEmployeeId()
    {
        return employeeId;
    }
    public void setEmployeeId(String s)
    {
        this.employeeId=s;
    }

    public String getPh_no()
    {
        return ph_no;
    }
    public void setPh_no(String s)
    {
        this.ph_no=s;
    }

    public String getCountry()
    {
        return country;
    }
    public void setCountry(String s)
    {
        this.country=s;
    }

    public String getAddress()
    {
        return address;
    }
    public void setAddress(String s)
    {
        this.address=s;
    }

    public String getState()
    {
        return state;
    }
    public void setState(String s)
    {
        this.state=s;
    }

    public String getGender()
    {
        return gender;
    }
    public void setGender(String s)
    {
        this.gender=s;
    }

    public String getPassword()
    {
        return password;
    }
    public void setPassword(String s)
    {
        this.password=s;
    }

}