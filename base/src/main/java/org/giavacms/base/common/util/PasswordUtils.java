/*
 * Copyright 2013 GiavaCms.org.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.giavacms.base.common.util;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class PasswordUtils implements Serializable
{

   private static final long serialVersionUID = 1L;

   public static String createPassword(String pwd)
   {
      return createPasswordWithHashAlgorithm(pwd, "SHA-256");
   }

   public static String createPasswordWithHashAlgorithm(String pwd,
            String algorithm)
   {
      MessageDigest md;
      try
      {
         // es MD5, SHA-256
         md = MessageDigest.getInstance(algorithm);
         md.update(pwd.getBytes());
         byte[] enc = md.digest();

         // Encode bytes to base64 to get a string
         String encode = new BASE64Encoder().encode(enc);
         System.out.println(encode);
         return encode;
      }
      catch (NoSuchAlgorithmException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         return null;
      }
   }

   public static void main(String[] args)
   {
      System.out.println(createPassword("admin"));
      System.out.println(createPassword("admin"));
   }
}
