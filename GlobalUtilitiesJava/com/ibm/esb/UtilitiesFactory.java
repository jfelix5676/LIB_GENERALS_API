package com.ibm.esb;

public class UtilitiesFactory {

	static private Utilities instance = null;
	
	static private Utilities getInstance() {
		if (instance == null) {
			instance = new GlobalUtilities();
		}
		return instance;
	}
	
	static public String readIPAddress() {
		String valor = "";
		instance = getInstance();
		valor = instance.readIPAddress();
		return valor;
	}
	
	static public String readHostname() {
		String valor = "";
		instance = getInstance();
		valor = instance.readHostname();
		return valor;
	}
	
	static public Boolean addCache(String mapa, String key, String value) {
		Boolean valor = false;
		instance = getInstance();
		valor = instance.addCache(mapa, key, value);
		return valor;
	}
	
	static public String readCache(String mapa, String key) {
		String valor = "";
		instance = getInstance();
		valor = instance.readCache(mapa, key);
		return valor;
	}
	
	static public Boolean checkCache(String mapa, String key) {
		Boolean valor = false;
		instance = getInstance();
		valor = instance.checkCache(mapa, key);
		return valor;
	}
	
	static public Boolean deteleCache(String mapa, String key) {
		Boolean valor = false;
		instance = getInstance();
		valor = instance.deteleCache(mapa, key);
		return valor;
	}
	
	static public String encrypted(String value) {
		String valor = "";
		instance = getInstance();
		valor = instance.encrypted(value);
		return valor;
	}
	
	static public String decrypted(String value) {
		String valor = "";
		instance = getInstance();
		valor = instance.decrypted(value);
		return valor;
	}
	
	static public String encrypt(String value, String value2) {
		String valor = "";
		instance = getInstance();
		valor = instance.encrypt(value, value2);
		return valor;
	}
	
	static public String decrypt(String value, String value2) {
		String valor = "";
		instance = getInstance();
		valor = instance.decrypt(value, value2);
		return valor;
	}
	
	static public String readPath(String value) {
		String valor = "";
		instance = getInstance();
		valor = instance.readPath(value);
		return valor;
	}
	
	static public String readMethod(String value) {
		String valor = "";
		instance = getInstance();
		valor = instance.readMethod(value);
		return valor;
	}
	
	static public String replaceSpecialCharacters(String value) {
		String valor = "";
		instance = getInstance();
		valor = instance.replaceSpecialCharacters(value);
		return valor;
	}
	
	static public Boolean SendEmail(String receiver, String sender, String subject, String text) {
		boolean valor = false;
		instance = getInstance();
		valor = instance.SendEmail(receiver, sender, subject, text);
		return valor;
	}
	
	static public String URLEncode(String value) {
		String valor = "";
		instance = getInstance();
		valor = instance.URLEncode(value);
		return valor;
	}
	
	static public String URLDecode(String value) {
		String valor = "";
		instance = getInstance();
		valor = instance.URLDecode(value);
		return valor;
	}
}
