package com.lowagie.text.pdf.wmf;

/*
 * FileUtil.java
 *
 * Created on October 27, 2001, 5:46 PM
 */
import java.io.*;
import java.awt.Color;
/**
 *
 * @author  Administrator
 * @version 
 */
public class InputMeta {
    
    InputStream in;
    int length;
    
    public InputMeta(InputStream in) {
        this.in = in;
    }

    public int readWord() throws IOException{
        length += 2;
        int k1 = in.read();
        if (k1 < 0)
            return 0;
        return (k1 + (in.read() << 8)) & 0xffff;
    }

    public int readShort() throws IOException{
        int k = readWord();
        if (k > 0x7fff)
            k -= 0x10000;
        return k;
    }

    public int readInt() throws IOException{
        length += 4;
        int k1 = in.read();
        if (k1 < 0)
            return 0;
        int k2 = in.read() << 8;
        int k3 = in.read() << 16;
        return k1 + k2 + k3 + (in.read() << 24);
    }
    
    public int readByte() throws IOException{
        ++length;
        return in.read() & 0xff;
    }
    
    public void skip(int len) throws IOException{
        length += len;
        while (len > 0) {
            len -= in.skip(len);
        }
    }
    
    public int getLength() {
        return length;
    }
    
    public Color readColor() throws IOException{
        int red = readByte();
        int green = readByte();
        int blue = readByte();
        readByte();
        return new Color(red, green, blue);
    }
}
