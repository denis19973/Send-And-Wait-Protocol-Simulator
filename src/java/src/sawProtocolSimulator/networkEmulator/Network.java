package sawProtocolSimulator.networkEmulator;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.Scanner;

import sawProtocolSimulator.models.NetworkConfiguration;
import sawProtocolSimulator.models.Packet;
import sawProtocolSimulator.network.UDPNetwork;
import sawProtocolSimulator.utilities.Log;
import sawProtocolSimulator.utilities.PacketUtilities;

public class Network
{
    /**
     * Configuration for the network module.
     */
    private NetworkConfiguration configuration;

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
        // run switch for the loop
        boolean runNetwork = true;

        // stats
        int totalPackets = 0;
        int totalPacketsDropped = 0;
        int totalPacketsForwarded = 0;

        // run forever - basically
        while (runNetwork)
        {
            try
            {
                DatagramSocket socket = UDPNetwork.createServer(9000);
                Packet packet = UDPNetwork.getPacket(socket);

                totalPackets++;
                
                // if it's a control packet, let it go through.
                if (packet.getPacketType() == 1 || packet.getPacketType() == 4)
                {
                    UDPNetwork.sendPacket(socket, packet);
                    Log.d(PacketUtilities.generateNetworkPacketLog(packet, true));
                    totalPacketsForwarded++;
                }
                else
                {
                    // if packet drop rate is lower than the threshold, drop it.
                    if (this.getDropRateThreshold() <= this.configuration.getDropRate())
                    {
                        Log.d(PacketUtilities.generateNetworkPacketLog(packet, false));
                        totalPacketsDropped++;
                    }
                    else
                    {
                        // packet drop rate is greater than the threshold, let it go through.

                        // delay the packet by averageDelayPerPacket
                        Thread.sleep(this.configuration.getAverageDelayPerPacket());

                        UDPNetwork.sendPacket(socket, packet);

                        Log.d(PacketUtilities.generateNetworkPacketLog(packet, true));
                        totalPacketsForwarded++;
                    }
                }

                Log.d("[NETWORK] Total packets:           " + totalPackets);
                Log.d("[NETWORK] Total packets dropped:   " + totalPacketsDropped);
                Log.d("[NETWORK] Total packets forwarded: " + totalPacketsForwarded);
            }
            catch (SocketException e)
            {
                Log.d(e.getMessage());
                System.exit(0); // fatal
            }
            catch (ClassNotFoundException e)
            {
                Log.d(e.getMessage());
            }
            catch (IOException e)
            {
                Log.d(e.getMessage());
            }
            catch (InterruptedException e)
            {
                Log.d(e.getMessage());
            }
        }
    }

    /**
     * Returns a randomly calculated drop rate threshold. If the packet drop rate specified by the
     * user is lower than or equal to this number, it is dropped.
     * 
     * Essentially a random number between 1 and 100, inclusive.
     * 
     * @return a random drop rate threshold
     */
    private int getDropRateThreshold()
    {
        Random random = new Random();

        int threshold = random.nextInt((100 - 1) + 1) + 1;

        return threshold;
    }

    /**
     * Scan configuration from the user.
     * 
     * Takes in the packet drop rate and average delay per packet.
     */
    public void takeInput()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n\n");
        System.out.println("Packet Drop Rate (1 and 100)");
        System.out.println("=================================");
        System.out.println("A random number between 1 and 100 will be generated for "
                + "each packet received and if that random number "
                + "is below or equal to this drop rate, that packet will be dropped.");
        System.out.print("\nEnter Drop Rate (1-100):\t");

        if(scan.hasNextInt())
        {
//            this.dropRate = scan.nextInt();
        }

        System.out.println("\n\n");
        System.out.println("Average Delay (in milliseconds)");
        System.out.println("=================================");
        System.out.println("This is the average delay per packet. Each packet will "
                + "be delayed by the time interval specified here."
                + "\nExample delay: 0.01 seconds");
        System.out.print("\nEnter Average Delay Per Packet (in ms):\t");

        if(scan.hasNextInt())
        {
//            this.averageDelayPerPacket = scan.nextInt();
        }
        
        scan.close();
    }

    /**
     * Prints all configuration for the Network Module.
     */
    public void printConfiguration()
    {
        System.out.println("\n\n");
        System.out.println("Drop Rate (between 1 and 100): " + this.configuration.getDropRate());
        System.out.println("Average Delay Per Packet (in ms): " + this.configuration.getAverageDelayPerPacket());
        System.out.println("Sender: " + this.configuration.getSender() + ":"
                + this.configuration.getSenderPort());
        System.out.println("Receiver: " + this.configuration.getReceiver() + ":"
                + this.configuration.getReceiverPort());
        System.out.println("\n\n");
    }
    
}
