package com.example.hpjulab.kioskhealthcare;

/**
 * Created by devil on 10/14/15.
 */
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Persister;

/**
 * Convert binary files to xml binary data for easier transmission
 */
public class FileConverter {

    /**
     * Decodes a file.
     * @param inFileName Name of input file
     * @param outFileName Name of output file
     */
    protected static void decodeFile(String inFileName, String outFileName)
    {
        try {
            File inFile = new File(inFileName);
            Serializer sr=new Persister();
            FileData fileData=sr.read(FileData.class, inFile);


            String encodedStr = fileData.getBinaryData();
            byte[] byteArray;

            byteArray = Base64.decode(encodedStr,Base64.DEFAULT);

            File outFile = new File(outFileName);
            FileOutputStream ofStream = new FileOutputStream(outFile);
            BufferedOutputStream boStream = new BufferedOutputStream(ofStream);
            boStream.write(byteArray, 0, byteArray.length);
            boStream.flush();
            boStream.close();
            ofStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Encode a file
     * @param inFileName name of encoded file
     * @param outFileName name of output file
     */
    protected static void encodeFile(String inFileName, String outFileName)
    {
        try {
            File inFile = new File(inFileName);
            FileInputStream ifStream = new FileInputStream(inFile);
            BufferedInputStream biStream = new BufferedInputStream(ifStream);

            byte[] byteArray = new byte[(int)inFile.length()];
            biStream.read(byteArray,0,byteArray.length);
            biStream.close();
            ifStream.close();




            String encodedStr = Base64.encodeToString(byteArray, Base64.DEFAULT);

            FileData fileData = new FileData();
            fileData.setBinaryData(encodedStr);

            File outFile = new File(outFileName);

            Serializer sr=new Persister();
            sr.write(fileData,outFile);

        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }
}


@Root(name="FileData")
class FileData
{
    @Element(required = false)
    String BinaryData;
    public String getBinaryData() {
        return BinaryData;
    }


    public void setBinaryData(String binaryData) {
        this.BinaryData = binaryData;
    }
}