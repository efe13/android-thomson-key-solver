/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package thomson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Stage1 {


  static final byte[] HEX_CHAR_TABLE = {
    (byte)'0', (byte)'1', (byte)'2', (byte)'3',
    (byte)'4', (byte)'5', (byte)'6', (byte)'7',
    (byte)'8', (byte)'9', (byte)'a', (byte)'b',
    (byte)'c', (byte)'d', (byte)'e', (byte)'f'
  };

  public static String getHexString(byte[] raw)
    throws UnsupportedEncodingException
  {
    byte[] hex = new byte[2 * raw.length];
    int index = 0;

    for (byte b : raw) {
      int v = b & 0xFF;
      hex[index++] = HEX_CHAR_TABLE[v >>> 4];
      hex[index++] = HEX_CHAR_TABLE[v & 0xF];
    }
    return new String(hex, "ASCII");
  }
	static MessageDigest md;
    public static void main(String[] args) throws UnsupportedEncodingException, IOException
    {
    	FileOutputManager files = new FileOutputManager();
    	files.initAllFiles();
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }

        long begin = System.currentTimeMillis();
        byte[] cp = new byte[12];
        byte[] hash = new byte[19];
        byte[] firstByte = new byte[1];
        int sequenceNumber = 0;
        byte [] ret = new byte [5];
    	cp[0] = (byte) (char)'C';
    	cp[1] = (byte) (char)'P';
  
        int offset = 0;
        for(int y = 4; y < 10; y++)
        {
            cp[2] = (byte) Character.forDigit((y / 10), 10);
            cp[3] = (byte) Character.forDigit((y % 10), 10);
            System.out.println("Done .. " + 100*(y-4)/6 + "%");
            for(int w = 1; w <= 52; w++)
            {
                
                cp[4] = (byte) Character.forDigit((w / 10), 10);
                cp[5] = (byte) Character.forDigit((w % 10), 10);
                for(int a = 0; a < AlphabetCodes.charectbytes.length; a++)
                {
                    cp[6] = AlphabetCodes.charectbytes[a][0];
                    cp[7] = AlphabetCodes.charectbytes[a][1];
                    for(int b = 0; b < AlphabetCodes.charectbytes.length; b++)
                    {
                        cp[8] = AlphabetCodes.charectbytes[b][0];
                        cp[9] = AlphabetCodes.charectbytes[b][1];
                        for(int c = 0; c < AlphabetCodes.charectbytes.length ; c++)
                        {
                            offset += 3;
                            cp[10] = AlphabetCodes.charectbytes[c][0];
                            cp[11] = AlphabetCodes.charectbytes[c][1];
                            md.reset();
                            md.update(cp);
                            hash = md.digest();
                            firstByte[0] = hash[17];
                			ret[0] = hash[18];
                			ret[1] = hash[19];
                			ret[2] = (byte) ( (0xFF0000 & sequenceNumber) >> 16) ;
                			ret[3] = (byte) ( (0xFF00 & sequenceNumber) >> 8) ;
                			ret[4] =(byte) (0xFF & sequenceNumber);
                			sequenceNumber++;
                			files.sendFile(getHexString(firstByte)+".dat", ret);
                        }
                    }
                }
            }
        }     
        files.close();
        long time = System.currentTimeMillis() - begin;
        System.out.println("Done .. 100%! It took " + time + " miliseconds.");
    }

}