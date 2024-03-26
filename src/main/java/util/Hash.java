/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author PróVida
 */
public class Hash {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // TODO code application logic here
        
         // String senha = encriptPassword("1324");
    
  //  System.out.println("Hash: " + senha);
    }
    
     public static String encriptPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
          
          MessageDigest messageDigest =  MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes("UTF-8"));
       
   
              
       return new BigInteger(1, messageDigest.digest()).toString(16); 
    }
    
}
