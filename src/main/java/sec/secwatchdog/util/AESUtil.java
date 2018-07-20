package sec.secwatchdog.util;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESUtil {
	/** 
     * ��Կ�㷨 
     */  
    private static final String ALGORITHM = "AES";  
    /** 
     * �ӽ����㷨/����ģʽ/��䷽ʽ 
     */  
    private static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";  
  
    /** 
     * SecretKeySpec����KeySpec�ӿڵ�ʵ����,���ڹ���������Կ�淶 
     */  
    private SecretKeySpec key;
  
    private String sKey = "1234567890123456";
    byte[] srcIv=new byte[16];
    
    public AESUtil() {  
        key = new SecretKeySpec(sKey.getBytes(), ALGORITHM);  
    }  
  
    /** 
     * AES���� 
     * @param data 
     * @return 
     * @throws Exception 
     */  
//    public String encryptData(String data) throws Exception {  
//        Cipher cipher = Cipher.getInstance(ALGORITHM_STR); // ����������  
//        cipher.init(Cipher.ENCRYPT_MODE, key);// ��ʼ��  
//        return new BASE64Encoder().encode(cipher.doFinal(data.getBytes()));  
//    }  
    
//    public String encryptData(String data) throws Exception {  
//        Cipher cipher = Cipher.getInstance(ALGORITHM_STR); // ����������  
//        cipher.init(Cipher.ENCRYPT_MODE, key);// ��ʼ��  
//        byte[] encryptResult = cipher.doFinal(data.getBytes());  
//        String encryptResultStr = parseByte2HexStr(encryptResult);
//        return encryptResultStr;  
//    } 
  
    /** 
     * AES���� 
     * @param base64Data 
     * @return 
     * @throws Exception 
     */  
//    public String decryptData(String base64Data) throws Exception{  
//        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);  
//        cipher.init(Cipher.DECRYPT_MODE, key);  
//        return new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(base64Data)));  
//    }  
    
  
    /** 
     * hex�ַ��� ת byte���� 
     * @param s 
     * @return 
     */  
    private static byte[] hex2byte(String s) {  
        if (s.length() % 2 == 0) {  
            return hex2byte (s.getBytes(), 0, s.length() >> 1);  
        } else {  
            return hex2byte("0"+s);  
        }  
    }  
  
    private static byte[] hex2byte (byte[] b, int offset, int len) {  
        byte[] d = new byte[len];  
        for (int i=0; i<len*2; i++) {  
            int shift = i%2 == 1 ? 0 : 4;  
            d[i>>1] |= Character.digit((char) b[offset+i], 16) << shift;  
        }  
        return d;  
    }  
  
    public static void main(String[] args) throws Exception {  
    	AESUtil util = new AESUtil(); // ��Կ  
        System.out.println("cardNo:"+util.encryptData("admin")); // ����  
        System.out.println("exp:"+util.decryptData("euLUpj0cPhoYeh/Yn0ce9Q==")); // ����  
    }   
    
    /**��������ת����16���� 
     * @param buf 
     * @return 
     */  
    public static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
    }
    
    /**��16����ת��Ϊ������ 
     * @param hexStr 
     * @return 
     */  
    public static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  
                return null;  
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
    }  
    
  //����
    public String encryptData(String sSrc) throws Exception{
        
        if(sKey == null || sKey.length() != 16){
            
            throw new Exception("sKeyΪ��");
        }
        
        byte[] raw=sKey.getBytes();
        
        SecretKeySpec skeySpec=new SecretKeySpec(raw,"AES"); 
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        
        IvParameterSpec iv = new IvParameterSpec(srcIv);
        
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec,iv);
        
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        
        return new BASE64Encoder().encode(encrypted);
        
    }
    
    // ����
    public String decryptData(String sSrc) throws Exception {
        try {
            // �ж�Key�Ƿ���ȷ
            if (sKey == null || sKey .length() != 16) {
                
                throw new Exception("sKeyΪ�ջ���16λ");
            }
            
            byte[] raw = sKey.getBytes("ASCII");
            
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            
            IvParameterSpec iv = new IvParameterSpec(srcIv);
            
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//����base64����
            
            try {
                
                byte[] original = cipher.doFinal(encrypted1);
                
                String originalString = new String(original);
               
                return originalString;
                
            } catch (Exception e) {
                
            	System.out.println(e.getMessage());
                
                return null;
            }
            
        } catch (Exception ex) {
            
            System.out.println(ex.getMessage());
            
            return null;
        }
    }
}
