/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package thomson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luis
 */
public class Main {

	static char charect[] = {
		'A', 'B', 'C', 'D',
		'E', 'F', 'G', 'H',
		'I', 'J', 'K', 'L',
		'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X',
		'Y', 'Z', '0', '1',
		'2', '3', '4', '5',
		'6', '7', '8', '9'
		};

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
    	
        FileOutputStream fos;
        try {
            fos = new FileOutputStream("auxtable.dat");
        } catch (FileNotFoundException ex) {
            System.out.println("Error!" + ex);
            return;
        }

        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }

        long begin = System.currentTimeMillis();
        byte[] cp = new byte[12];
        byte[] hash = new byte[19];
        byte[] week = new byte[3*36*36*36];
    	cp[0] = (byte) (char)'C';
    	cp[1] = (byte) (char)'P';
        int offset = 0;
        for(int y = 4; y <= 10; y++)
        {
            cp[2] = (byte) Character.forDigit((y / 10), 10);
            cp[3] = (byte) Character.forDigit((y % 10), 10);
            System.out.println("Done .. " + 100*(y-4)/7 + "%");
            for(int w = 1; w <= 52; w++)
            {
                offset = 0;
                cp[4] = (byte) Character.forDigit((w / 10), 10);
                cp[5] = (byte) Character.forDigit((w % 10), 10);
                for(int a = 0; a < unkown.charectbytes.length; a++)
                {
                    cp[6] = unkown.charectbytes[a][0];
                    cp[7] = unkown.charectbytes[a][1];
                    for(int b = 0; b < unkown.charectbytes.length; b++)
                    {
                        cp[8] = unkown.charectbytes[b][0];
                        cp[9] = unkown.charectbytes[b][1];
                        for(int c = 0; c < unkown.charectbytes.length ; c++)
                        {
                            offset += 3;
                            cp[10] = unkown.charectbytes[c][0];
                            cp[11] = unkown.charectbytes[c][1];
                            md.reset();
                            md.update(cp);
                            hash = md.digest();
                            week[offset - 3 + 0] = hash[17];
                            week[offset - 3 + 1] = hash[18];
                            week[offset - 3 + 2] = hash[19];
                        }
                    }
                }
                fos.write(week);
            }
        }        
        long time = System.currentTimeMillis() - begin;
        System.out.println("Done .. 100%! It took " + time + " miliseconds.");
        fos.close();
    }
}