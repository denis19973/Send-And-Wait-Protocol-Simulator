package sawProtocolSimulator.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import sawProtocolSimulator.models.Packet;
import sawProtocolSimulator.network.UDPNetwork;
import sawProtocolSimulator.utilities.PacketUtilities;

public class Sender extends Client
{

    /**
     * The current sequence number.
     * 
     * Initialized to 0 in the constructor.
     */
    private int               sequenceNumber;

    /**
     * The current packet window.
     */
    private ArrayList<Packet> packetWindow;

    /**
     * Create a client, whose sole purpose is to send (transmit) to the receiver.
     * 
     * @param clientMode the client mode.
     */
    public Sender(ClientMode clientMode)
    {
        super(clientMode);
        this.sequenceNumber = 0;
        this.packetWindow = new ArrayList<Packet>();
    }

    @Override
    public void run()
    {
        //take control of the channel
        this.sendTakeControlPacket();
        
        //generate packets for a window
        
        //send the packets in the window
        
        //wait for ack's for each packet
        
        //if ack's don't arrive in time, send packet again.
        
        //once, all ack's arrive, empty window, and move onto the next window
        
        //when all window packets sent, send EOT
    }

    /**
     * Send the packet to take control of the communication channel.
     */
    private void sendTakeControlPacket()
    {
        // create a SOT packet
        Packet packet = this.makePacket(PacketUtilities.PACKET_START_OF_TRANSMISSION);

        // send the packet
        this.sendPacket(packet);
    }

    /**
     * Creates a packet from the configuration, sequence number, packet type and other details.
     * 
     * @param packetType the type of packet to make
     * 
     * @return a Packet
     */
    private Packet makePacket(int packetType)
    {
        return PacketUtilities.makePacket(this.configuration.getReceiverAddress().getHostAddress(),
                this.configuration.getReceiverPort(), this.configuration.getTransmitterAddress()
                        .getHostAddress(), this.configuration.getTransmitterPort(), packetType,
                this.sequenceNumber, this.sequenceNumber, this.configuration.getWindowSize());

    }

    /**
     * Send a packet to the network emulator.
     * 
     * @param packet the packet to send.
     */
    private void sendPacket(Packet packet)
    {
        try
        {
            DatagramSocket socket = UDPNetwork.createSocket();

            // send packet to the network emulator
            UDPNetwork.sendPacket(socket, packet, this.configuration.getNetworkAddress(),
                    this.configuration.getNetworkPort());
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

}
