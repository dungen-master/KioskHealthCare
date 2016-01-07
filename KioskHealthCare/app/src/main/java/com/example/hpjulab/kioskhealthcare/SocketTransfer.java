package com.example.hpjulab.kioskhealthcare;

import java.io.Serializable;
import java.net.Socket;

/**
 * Created by devil on 9/23/15.
 */
public class SocketTransfer implements Serializable {
    public static Socket socket;
    public String xray,prevpres,blood,ecg,other,SERVER;
    public Employee employee;
    public int PORT;

    public SocketTransfer()
    {
        xray=prevpres=blood=ecg=other="";
    }

    public void setSocket(Socket s)
    {
        socket=s;
    }
    public Socket getSocket()
    {
        return socket;
    }

    public void setXray(String s)
    {
        this.xray=s;
    }

    public String getXray()
    {
        return xray;
    }

    public void setEcg(String s)
    {
        this.ecg=s;
    }

    public String getEcg()
    {
        return ecg;
    }

    public void setPrevPres(String s)
    {
        this.prevpres=s;
    }

    public String getPrevpres()
    {
        return prevpres;
    }

    public void setBlood(String s)
    {
        this.blood=s;
    }

    public String getBlood()
    {
        return blood;
    }

    public void setOther(String s)
    {
        this.other=s;
    }

    public String getOther()
    {
        return other;
    }

    public void setEmployee(Employee emp)
    {
        employee=emp;
    }
    public Employee getEmployee()
    {
        return employee;
    }

    public void setSERVER(String server)
    {
        SERVER=server;
    }
    public String getSERVER()
    {
        return SERVER;
    }

    public void setPORT(int port)
    {
        PORT=port;
    }
    public int getPORT()
    {
        return PORT;
    }
}
