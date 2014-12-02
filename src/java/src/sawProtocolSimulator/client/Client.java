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

        try
        {
            this.configuration.setNetworkAddress(InetAddress.getByName(networkAddress));
            this.configuration.setNetworkPort(networkPort);
        }
        catch (UnknownHostException e)
        {
            // client entered a network address which wasn't valid

            System.out.println("Couldn't find any hosts with the address " + networkAddress + "! "
                    + "Please try again!");
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
