package com.ibm.esb;

import com.ibm.broker.config.common.Base64;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbGlobalMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.spec.KeySpec;
import java.text.Normalizer;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GlobalUtilities implements Utilities {
	
	public String readIPAddress() {
		String result = "";
		
		try {
			result = InetAddress.getLocalHost().getHostAddress();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String readHostname() {
		String result = "";
		
		try {
			result = InetAddress.getLocalHost().getHostName();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Boolean addCache(String cacheName, String key, String value) {
		if (cacheName == null || key == null || value == null) {
			System.err.println("invalid parameters cacheName: " + cacheName + " key:" + key + " value:" + value);
			return false;
		}
		
		try {
			MbGlobalMap map = MbGlobalMap.getGlobalMap(cacheName.trim());
			
			Object test = (Object) map.get(key.trim());
			if (test == null) {
				map.put(key.trim(), value.trim().getBytes());
			} else {
				map.update(key.trim(), value.trim().getBytes());
			}
		} catch (MbException e) {
			System.err.println("error writing into cache");
			return false;
		}
		
		return true;
	}
	
	public String readCache(String cacheName, String key) {
		if (cacheName == null || key == null) {
			System.err.println("invalid parameters cacheName:" + cacheName + " key:" + key);
			return "";
		} 
		
		try {
			MbGlobalMap map = MbGlobalMap.getGlobalMap(cacheName.trim());
			return new String((byte[]) map.get(key.trim()), "UTF-8");
		} catch (MbException e) {
			System.err.println("error reading  from  cache");
		} catch (Exception e) {
			System.err.println("error reading  from  cache");
		}
		return "";
	}
	
	public Boolean checkCache(String cacheName, String key) {
		if (cacheName == null || key == null) {
			System.err.println("invalid parameters cacheName:" + cacheName + " key:" + key);
			return false;
		} 
		
		try {
			MbGlobalMap map = MbGlobalMap.getGlobalMap(cacheName.trim());

			return map.containsKey(key.trim());
		} catch (MbException e) {
			System.err.println("error checking into cache");
			return false;
		}
	}
	
	public Boolean deteleCache(String cacheName, String key) {
		if (cacheName == null || key == null) {
			System.err.println("invalid parameters cacheName:" + cacheName + " key:" + key);
			return false;
		} 
		
		try {
			MbGlobalMap map = MbGlobalMap.getGlobalMap(cacheName.trim());
			Object test = (Object) map.get(key.trim());
				
			if (test == null) {
				return false;
			} else {
				map.remove(key.trim());
			}
		} catch (MbException e) {
			System.err.println("error reading  from  cache");
			return false;
		}
		return true;
	}

	private byte[] SALT = "_Ng{qi-eQ4_evg?z".getBytes();
	
	public String encrypted(String plainText) {

		if (plainText == null) {
			return null;
		}

		Key salt = new SecretKeySpec(SALT, "AES");

		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, salt);
			byte[] encodedValue = cipher.doFinal(plainText.getBytes());
			return Base64.encode(encodedValue);
		} catch (Exception e) {
			e.printStackTrace();
		}

		throw new IllegalArgumentException("Failed to encrypt data");
	}

	public String decrypted(String encodedText) {

		if (encodedText == null) {
			return null;
		}

		Key salt = new SecretKeySpec(SALT, "AES");
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, salt);
			byte[] decodedValue = Base64.decode(encodedText);
			byte[] decValue = cipher.doFinal(decodedValue);
			return new String(decValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String encrypt(String strToEncrypt, String privateKey) {
		try
	    {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	         
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(privateKey.toCharArray(), SALT, 65536, 256);
	        SecretKey tmp = factory.generateSecret(spec);
	        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
	         
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
	        
	        return Base64.encode(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	    } 
	    catch (Exception e) 
	    {
	        System.out.println("Error while encrypting: " + e.toString());
	    }
		
	    return null;
	}
	
	public String decrypt(String strToDecrypt, String privateKey) {
		try
	    {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	         
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(privateKey.toCharArray(), SALT, 65536, 256);
	        SecretKey tmp = factory.generateSecret(spec);
	        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
	         
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
	        return new String(cipher.doFinal(Base64.decode(strToDecrypt)));
	    } 
	    catch (Exception e) {
	        System.out.println("Error while decrypting: " + e.toString());
	    }
		
	    return null;
	}

	public String readPath(String value) {
		String result = "";
		
		try {
		 	result = value.substring(0, value.lastIndexOf("/")) + "/";
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String readMethod(String value) {
		String result = "";
		
		try {
		 	result = value.substring(value.lastIndexOf("/") + 1, value.length());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String replaceSpecialCharacters(String value) {
		String result = "";
		
		try {
			value =  Normalizer.normalize(value, Normalizer.Form.NFD);
			value = value.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
			result = value.replaceAll("[^a-zA-Z0-9\\s]", "");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Boolean SendEmail(String receiver, String sender, String subject, String text)  {   
		boolean result = false;
		
        // Sending email from gmail
        String host = "smtp-relay.gmail.com";

        // Port of SMTP
        String port = "587";

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        // Create session object passing properties and authenticator instance
        Session session = Session.getInstance(properties);

        try {        	
            // Create MimeMessage object
            Message message = new MimeMessage(session);
            
            // Set the Senders mail to From
            message.setFrom(new InternetAddress(sender));
            
            // Set the recipients email address
            if (receiver.contains(",")) {
            	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
    		} else {
    			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
    		}

            // Subject of the email
            message.setSubject(subject);
            
            // Body of the email             
            message.setContent(text, "text/html");

            // Send email.
            Transport.send(message);
            
            System.out.println("Mail sent successfully");
            
            result = true;
        } catch (MessagingException me) {
            me.printStackTrace();
        }
        
        return result;
    }
	
	public String URLEncode(String value) {
		String result = "";
		
		try {
			result = URLEncoder.encode(value, "UTF-8");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String URLDecode(String value) {
		String result = "";
		
		try {
			result = URLDecoder.decode(value, "UTF-8");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
