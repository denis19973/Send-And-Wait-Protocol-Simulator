package sawProtocolSimulator.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import sawProtocolSimulator.models.ClientConfiguration;
import sawProtocolSimulator.models.Packet;
import sawProtocolSimulator.network.UDPNetwork;

public abstract class Client
{
    /**
     * Current client mode..since this is a half-duplex protocol.
     */
    private ClientMode            mode;

    /**
     * Configuration of the network module.
     */
    protected ClientConfiguration configuration;

    /**
     * Constructor. Initializes the client in a mode.
     * 
     * @param clientMode the client mode.
     */
    public Client(ClientMode clientMode)
    {
        this.mode = clientMode;
    }

    /**
     * The main runner..where all the client running occurs (either sending or receiving).
     */
    public abstract void run();

    /**
     * Creates a packet from the configuration, sequence number, packet type and other details.
     * 
     * @param packetType the type of packet to make
     * 
     * @return a Packet
     */
    protected abstract Packet makePacket(int packetType);

    /**
     * Scan some configuration from the user.
     * 
     * Takes in the IP and port for the network emulator.
     */
    public void takeInput()
    {
        Scanner scan = new Scanner(System.in);

        // ######################################################### //

        System.out.println("Network Emulator Address");
        System.out.println("===================================");
        System.out
                .println("The IP Address of the machine which is acting as the network emulator.");
        System.out.print("\nEnter IP Address here:\t");

        String networkAddress = scan.nextLine();

        System.out.println("Network Emulator Port");
        System.out.println("===================================");
        System.out.println("The port of the machine which is acting as the network emulator.");
        System.out.print("\nEnter port here:\t");

        int networkPort = scan.nextInt();

        // ######################################################### //

        System.out.println("Transmitter Address");
        System.out.println("===================================");
        System.out.println("The IP Address of the machine which is acting as the transmitter.");
        System.out.print("\nEnter IP Address here:\t");

        String transmitterAddress = scan.nextLine();

        System.out.println("Transmitter Port");
        System.out.println("===================================");
        System.out.println("The port of the machine which is acting as the transmitter.");
        System.out.print("\nEnter port here:\t");

        int transmitterPort = scan.nextInt();

        // ######################################################### //

        System.out.println("Receiver Address");
        System.out.println("===================================");
        System.out.println("The IP Address of the machine which is acting as the receiver.");
        System.out.print("\nEnter IP Address here:\t");

        String receiverAddress = scan.nextLine();

        System.out.println("Receiver Port");
        System.out.println("===================================");
        System.out.println("The port of the machine which is acting as the receiver.");
        System.out.print("\nEnter port here:\t");

        int receiverPort = scan.nextInt();

        // ######################################################### //

        System.out.println("Window Size");
        System.out.println("===================================");
        System.out.println("The size of our window!");
        System.out.print("\nEnter window size here:\t");

        int windowSize = scan.nextInt();

        // ######################################################### //

        System.out.println("Max Packets to Send");
        System.out.println("===================================");
        System.out.println("The maximum number of packets that will be sent!");
        System.out.print("\nEnter maximum number of packets here:\t");

        int maxPacketsToSend = scan.nextInt();

        // ######################################################### //

        System.out.println("Maximum Timeout Duration (in milliseconds)");
        System.out.println("===================================");
        System.out.println("The maximum timeout duration until a packet can remain unACKed.");
        System.out.print("\nEnter max timeout here (in ms):\t");

        int maxTimeout = scan.nextInt();

        // ######################################################### //

        // set all configuration
        setConfiguration(networkAddress, networkPort, transmitterAddress, transmitterPort,
                receiverAddress, receiverPort, maxPacketsToSend, windowSize, maxTimeout);

    }
    
    /**
     * Send a packet to the network emulator.
     * 
     * @param packet the packet to send.
     */
    protected void sendPacket(Packet packet)
    {
        try
        {
            DatagramSocket socket = UDPNetwork.createSocket();

            // send packet to the network emulator
            UDPNetwork.sendPacket(socket, packet, this.configuration.getNetworkAddress(),
                    this.configuration.getNetworkPort());

            // TODO: print this packet sent
        }
        catch (SocketException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Sets the client configuration object with the scanned details.
     * 
     * @param networkAddress address of the network emulator
     * @param networkPort address of the network port
     * @param transmitterAddress address of the transmitter
     * @param transmitterPort port number of the transmitter
     * @param receiverAddress address of the receiver
     * @param receiverPort port number of the receiver
     * @param maxPacketsToSend max packets that will be sent
     * @param windowSize the window size
     * @param maxTimeout the maximum timeout duration until a packet can remain unACKed.
     */
    private void setConfiguration(String networkAddress, int networkPort,
            String transmitterAddress, int transmitterPort, String receiverAddress,
            int receiverPort, int maxPacketsToSend, int windowSize, int maxTimeout)
    {
        try
        {
            this.configuration.setNetworkAddress(InetAddress.getByName(networkAddress));
            this.configuration.setNetworkPort(networkPort);
            this.configuration.setTransmitterAddress(InetAddress.getByName(transmitterAddress));
            this.configuration.setTransmitterPort(transmitterPort);
            this.configuration.setReceiverAddress(InetAddress.getByName(receiverAddress));
            this.configuration.setReceiverPort(receiverPort);
            this.configuration.setMaxPacketsToSend(maxPacketsToSend);
            this.configuration.setWindowSize(windowSize);
            this.configuration.setMaxTimeout(maxTimeout);
        }
        catch (UnknownHostException e)
        {
            // client entered a network address which wasn't valid
            System.out.println("Couldn't find one of the hosts you entered. Please try again!");

            // get input again
            takeInput();
        }
    }

    /**
     * Prints all configuration for the Client Module.
     */
    public void printConfiguration()
    {
        System.out.println("\n\n");
        System.out.println("Client Mode: " + this.getMode());
        System.out.println("Network Emulator Address: " + this.configuration.getNetworkAddress()
                + ":" + this.configuration.getNetworkPort());
        System.out.println("\n\n");
    }

    /**
     * Return client's current mode (sender or receiver).
     * 
     * @return the mode
     */
    public ClientMode getMode()
    {
        return mode;
    }

    /**
     * Set the client mode (sender or receiver).
     * 
     * @param mode the mode to set
     */
    public void setMode(ClientMode mode)
    {
        this.mode = mode;
    }



}
