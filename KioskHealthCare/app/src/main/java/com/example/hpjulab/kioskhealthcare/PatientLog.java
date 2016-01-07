package com.example.hpjulab.kioskhealthcare;

import org.simpleframework.xml.*;

import java.util.ArrayList;

/**
 * Created by devil on 10/25/15.
 */

@Root(name = "patientLog")
public class PatientLog {

        @Element(required = false)
        String Emergency;
        @Element(required = false)
        String  Normal;

        String  getnormal()
        {
            return Normal;
        }

        void setnormal(String normal)
        {
            Normal = normal;
        }

        String getemergency()
        {
            return Emergency;
        }
        void setemergency(String emergency)
        {
            Emergency = emergency;
        }
}

