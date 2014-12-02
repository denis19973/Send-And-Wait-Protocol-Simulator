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

    }

    /**
     * Send the packet to take control of the communication channel.
     */
    private void sendTakeControlPacket()
    {
        // create a SOT packet
        Packet packet = makePacket(1);

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
}
