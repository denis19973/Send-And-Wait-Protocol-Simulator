package sawProtocolSimulator.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import sawProtocolSimulator.models.ClientConfiguration;

public abstract class Client
{
    /**
     * Current client mode..since this is a half-duplex protocol.
     */
    private ClientMode          mode;

    /**
     * Configuration of the network module.
     */
    private ClientConfiguration configuration;

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

        // set all configuration
        setConfiguration(networkAddress, networkPort, transmitterAddress, transmitterPort,
                receiverAddress, receiverPort);

    }

    private void setConfiguration(String networkAddress, int networkPort,
            String transmitterAddress, int transmitterPort, String receiverAddress, int receiverPort)
    {
        try
        {
            this.configuration.setNetworkAddress(InetAddress.getByName(networkAddress));
            this.configuration.setNetworkPort(networkPort);
            this.configuration.setTransmitterAddress(InetAddress.getByName(transmitterAddress));
            this.configuration.setTransmitterPort(transmitterPort);
            this.configuration.setReceiverAddress(InetAddress.getByName(receiverAddress));
            this.configuration.setReceiverPort(receiverPort);

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
