package com.getbro.api.items;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;
import java.security.*;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

@XmlRootElement
public class User {

    //public static long ID;
    //private int birthdate;
    //private long userID;

    private String name;
    private long phonenumber;
    private boolean activated = false;
    private PublicKey publicKey;

    public User() {
    }

    public User(String publicKey, long phonenumber) {
        //ID++;
        //this.userID = ID;
        this.name = "";
        publicKey(publicKey);
        this.phonenumber = phonenumber;
        this.activated = true;
    }

    public User(User user) {
        //ID++;
        this.name = user.getUsername();
        this.phonenumber = user.getId();
        this.activated = false;
    }

    public int sendConfirmation() {
        //	Random randomGenerator = new Random();
        //	activationcode = randomGenerator.nextInt(99999);
        //Sende SMS an Handynummer mit genereiertem Code
        System.out.println("Sending Activation Code " + activationcode());
        return 1;
    }

    public boolean checkActivation(int code) {
        if (code == activationcode()) {
            System.out.println("Code OK " + activationcode());
            return true;
        } else {
            System.out.println("Wrong Code " + code);
            return false;
        }
    }

    private int activationcode() {
        String digits = Long.toString(phonenumber);
        int x = Integer.parseInt(digits.substring(digits.length() - 4, digits.length()));
        x += Integer.parseInt(Long.toString(phonenumber).substring(0, 4));
        return x % 10000;
    }

    @XmlElement(name = "name")
    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        this.name = name;
    }

    @XmlElement(name = "userId")
    public long getId() {
        return phonenumber;
    }

    public void setId(Long phone_number) {
        this.phonenumber = phone_number;
        System.out.println("Number: " + phonenumber);
    }

    /*@XmlElement(name="publicKey")
    public String publicKey(){
        try{
            System.out.println("SetKey: " + publicKey);
        return savePublicKey(this.publicKey);
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        //return new String(telnr.toByteArray());
        //System.out.println("Number: " + telnr);
        //return "blabla";
    }*/
    private void publicKey(String publicKey) {
        try {
            this.publicKey = loadPublicKey(publicKey);
            System.out.println("SetKey: " + publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //this.phonenumber=phonenumber;
    }

    public String decode(String signature) {
        try {
            System.out.println("Decrypting: " + publicKey.toString());
            return Decrypt(signature, this.publicKey);
        } catch (Exception e) {
            e.printStackTrace();
            return "abc";
        }
    }

    public static String Decrypt(String result, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //Cipher cipher=Cipher.getInstance("RSA");
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(stringToBytes(result));
        System.out.println("Decrypted: " + new String(decryptedBytes));
        return new String(decryptedBytes);
    }

    public static String bytesToString(byte[] b) {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);
    }

    public static byte[] stringToBytes(String s) {
        byte[] b2 = new BigInteger(s, 36).toByteArray();
        return Arrays.copyOfRange(b2, 1, b2.length);
    }

    /*	@XmlElement(name="code")
        public String getCode(){
            //return telnr.toString();
            return Integer.toString(activationcode);
            //return activationcode;
        }
        public void setCode(String code){
            this.activationcode = new Integer(code);
            System.out.println("Code: " +code);
            //this.phonenumber=phonenumber;
        }

        /*	@XmlElement(name="phonenumber")
        public BigInteger phonenumber(){
            return userID;
        }
        public void phonenumber(String phonenumber){
            System.out.println("Test 2" +phonenumber);
            this.userID= BigInteger.valueOf(200);
        }*/
    /*@XmlElement(name="code")
	public String getCode(){
		return activationcode;
	}
	public void setCode(String code){
		
		this.activationcode = Integer.parseInt(code);
		System.out.println("Hier: code " + code);
		//this.phonenumber=phonenumber;
	}*/
    public static PublicKey loadPublicKey(String stored) throws GeneralSecurityException {
        byte[] data = DatatypeConverter.parseBase64Binary(stored);
        System.out.println("String: " + stored);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePublic(spec);
    }


    public static String savePublicKey(PublicKey publ) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = fact.getKeySpec(publ,
                X509EncodedKeySpec.class);
        return DatatypeConverter.printBase64Binary(spec.getEncoded());
    }
}