package sawProtocolSimulator.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import sawProtocolSimulator.models.Packet;

public class UDPNetwork
{

    /**
     * Creates a UDP Socket that listens on a specified port.
     * 
     * @param port the port to listen on
     * 
     * @return the created socket
     * 
     * @throws SocketException if the socket can't be created
     */
    public static DatagramSocket createServer(int port) throws SocketException
    {
        return new DatagramSocket(port);
    }

    /**
     * Creates a UDP socket on any available port.
     * 
     * @return the created socket
     * 
     * @throws SocketException if the socket can't be created
     */
    public static DatagramSocket createClient() throws SocketException
    {
        return new DatagramSocket();
    }

    /**
     * Read a Packet from the UDP socket.
     * 
     * @param socket the UDP socket to read the packet from
     * @return the packet.
     * 
     * @throws IOException if it can't be read
     * @throws ClassNotFoundException if the specified class can't be found.
     */
    public static Packet getPacket(DatagramSocket socket) throws IOException,
            ClassNotFoundException
    {
        byte[] dataBytes = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(dataBytes, dataBytes.length);
        socket.receive(datagramPacket);
        dataBytes = datagramPacket.getData();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dataBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        Packet packet = (Packet) objectInputStream.readObject();
        return packet;
    }

    /**
     * Send a Packet from the UDP socket.
     * 
     * @param socket the UDP socket to send the packet through
     * @param packet the Packet to send
     * 
     * @throws IOException if the packet can't be sent
     */
    public static void sendPacket(DatagramSocket socket, Packet packet) throws IOException
    {
        byte[] dataBytes = new byte[1024];

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(packet);
        objectOutputStream.close();

        dataBytes = byteArrayOutputStream.toByteArray();

        InetAddress destinationAddress = InetAddress.getByName(packet.getDestinationAddress());

        DatagramPacket datagramPacket =
                new DatagramPacket(dataBytes, dataBytes.length, destinationAddress,
                        packet.getDestinationPort());

        socket.send(datagramPacket);

    }
}
