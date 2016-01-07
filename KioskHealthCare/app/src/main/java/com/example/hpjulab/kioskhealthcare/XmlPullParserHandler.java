package com.example.hpjulab.kioskhealthcare;

/**
 * Created by devil on 9/29/15.
 */

import org.xmlpull.v1.*;

import java.io.InputStream;


public class XmlPullParserHandler {

    public Employee emp;
    public String text;

    public Employee getEmployee(){
        return emp;
    }

    public Employee parse(InputStream is)
    {
        try {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();

            factory.setNamespaceAware(true);

            XmlPullParser parser=factory.newPullParser();
            parser.setInput(is,null);

            int eventType=parser.getEventType();
            emp=new Employee();

            while(eventType!=XmlPullParser.END_DOCUMENT)
            {
                String tagname=parser.getName();

                switch(eventType){

                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text=parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(tagname.equalsIgnoreCase("employee"));

                        else if(tagname.equalsIgnoreCase("employeeId"))
                            emp.setEmployeeId(text);
                        else if(tagname.equalsIgnoreCase("name"))
                            emp.setName(text);
                        else if(tagname.equalsIgnoreCase("ph_no"))
                            emp.setPh_no(text);
                        else if(tagname.equalsIgnoreCase("address"))
                            emp.setAddress(text);
                        else if(tagname.equalsIgnoreCase("country"))
                            emp.setCountry(text);
                        else if(tagname.equalsIgnoreCase("state"))
                            emp.setState(text);
                        else if(tagname.equalsIgnoreCase("gender"))
                            emp.setGender(text);
                        else if(tagname.equalsIgnoreCase("password"))
                            emp.setPassword(text);

                        break;

                    default:
                        break;
                }
                eventType=parser.next();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return emp;

    }
}
