package JettersRServer.Serialization;
/**
 * Write a description of class SerialMain here.
 *
 * @author: Luke Sullivan
 * @11/22/19
 */
import java.io.*;
import java.util.Random;

public class SerialMain
{
    static void printBytes(byte[] data)
    {
        for(int i = 0; i < data.length; i++)
        {
            System.out.printf("0x%x ", data[i]);
        }
    }
    
    static void saveToFile(String path, byte[] data)
    {
        try
        {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
            stream.write(data);
            stream.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        int[] data = new int[] {1, 2, 3, 4, 5};
        Array array = Array.IntArray("Test", data);
        
        byte[] stream = new byte[array.getSize()];
        array.getBytes(stream, 0);
        //saveToFile("")
    }
}
