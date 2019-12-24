package JettersRServer;
/**
 * Write a description of class JettersRServer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.net.InetAddress;
import java.net.UnknownHostException;

public class JettersRServer
{
    public static void main(String[] args)
    {
        Server server = new Server(8192);
        server.start();

        InetAddress address = null;
        try
        {
            address = InetAddress.getByName("192.168.1.10");
        }catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
        int port = 8192;
        server.send(new byte[] {0, 1, 2}, address, port);
    }
}
