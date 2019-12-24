package JettersRServer;
/**
 * Write a description of class Server here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
//import java.net.RCDatabase;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.InetAddress;
import java.io.IOException;

public class Server
{
    private int port;
    private Thread listenThread;
    private boolean listening = false;
    private DatagramSocket socket;

    private final int MAX_PACKET_SIZE = 1024;
    private byte[] receivedDataBuffer = new byte[MAX_PACKET_SIZE * 10];

    public Server(int port)
    {
        this.port = port;
    }

    public void start()
    {
        try
        {
            socket = new DatagramSocket(port);
        }catch(SocketException e)
        {
            e.printStackTrace();
            return;
        }

        listening = true;

        listenThread = new Thread(() -> listen(), "JettersRServer-ListenThread");
        listenThread.start();
    }

    private void listen()
    {
        while(listening)
        {
            DatagramPacket packet = new DatagramPacket(receivedDataBuffer, MAX_PACKET_SIZE);
            try
            {
                socket.receive(packet);
            }catch(IOException e)
            {
                e.printStackTrace();
            }
            process(packet);
        }
    }

    private void process(DatagramPacket packet)
    {
        //If RCDB
        byte[] data = packet.getData();
        if(new String(data, 0, 4).equals("RCDB"))
        {
            //RCDatabase database = RCDataBase.Deserialize(data);
            //process(database);
        }
        else
        {
            switch(data[0])
            {
                case 1:
                //Connection Packet
                break;
                
                case 2:
                //Ping Packet
                break;
                
                case 3:
                //Login Attempt Packet
                break;
            }
        }
        //assert();
    }

    public void send(byte[] data, InetAddress address, int port)
    {
        assert(socket.isConnected());
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        try
        {
            socket.send(packet);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
