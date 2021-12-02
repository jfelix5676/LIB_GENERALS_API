package com.ibm.esb;

public interface Utilities {

	public String readIPAddress();
	
	public String readHostname();
	
	public Boolean addCache(String mapa, String key, String valor);
	
	public String readCache(String mapa, String key);
	
	public Boolean checkCache(String mapa, String key);
	
	public Boolean deteleCache(String mapa, String key);
	
	public String encrypted(String valor);
	
	public String decrypted(String valor);
	
	public String encrypt(String valor, String valor2);
	
	public String decrypt(String valor, String valor2);
	
	public String readPath(String valor);
	
	public String readMethod(String valor);
	
	public String replaceSpecialCharacters(String valor);
	
	public Boolean SendEmail(String receiver, String sender, String subject, String text);
	
	public String URLEncode(String valor);
	
	public String URLDecode(String valor);
}
