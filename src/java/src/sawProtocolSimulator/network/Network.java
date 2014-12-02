package sawProtocolSimulator.network;

import java.util.Scanner;

import sawProtocolSimulator.models.NetworkConfiguration;

public class Network
{
    /**
     * Configuration for the network module.
     */
    private NetworkConfiguration configuration;

    /**
     * The drop rate.
     * 
     * A random number between 1 and 100 will be generated for each packet received and if that
     * random number is below or equal to this drop rate, that packet will be dropped.
     */
    private int           dropRate;

    /**
     * The average delay per packet.
     * 
     * In seconds: example: 0.05 seconds.
     */
    private double        averageDelayPerPacket;

    /**
     * Construct the network module.
     * 
     * @param configuration the configuration file.
     */
    public Network(NetworkConfiguration configuration)
    {
        this.configuration = configuration;
    }
    
    /**
     * The main runner..where all the main Network emulation occurs!
     */
    public void run()
    {
        
    }
    
    /**
     * Scan configuration from the user.
     * 
     * Takes in the packet drop rate and average delay per packet.
     */
    public void takeInput()
    {
        System.out.println("Packet Drop Rate (1 and 100)");
        System.out.println("=================================");
        System.out.println("A random number between 1 and 100 will be generated for "
                + "each packet received and if that random number "
                + "is below or equal to this drop rate, that packet will be dropped.");
        System.out.print("\nEnter Drop Rate (1-100):\t");

        Scanner scan = new Scanner(System.in);
        this.dropRate = scan.nextInt();

        System.out.println("Average Delay");
        System.out.println("=================================");
        System.out.println("This is the average delay per packet. Each packet will "
                + "be delayed by the time interval specified here."
                + "\nExample delay: 0.01 seconds");
        System.out.print("\nEnter Average Delay Per Packet (in seconds):\t");
        this.averageDelayPerPacket = scan.nextDouble();
    }

    /**
     * Prints all configuration for the Network Module.
     */
    public void printConfiguration()
    {
        System.out.println("\n\n");
        System.out.println("Drop Rate (between 1 and 100): " + this.dropRate);
        System.out.println("Average Delay Per Packet (in seconds): " + this.averageDelayPerPacket);
        System.out.println("Sender: " + this.configuration.getSender() + ":"
                + this.configuration.getSenderPort());
        System.out.println("Receiver: " + this.configuration.getReceiver() + ":"
                + this.configuration.getReceiverPort());
        System.out.println("\n\n");
    }
}
