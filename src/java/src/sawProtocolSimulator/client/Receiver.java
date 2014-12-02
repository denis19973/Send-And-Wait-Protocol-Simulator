package sawProtocolSimulator.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

import sawProtocolSimulator.models.Packet;
import sawProtocolSimulator.network.UDPNetwork;
import sawProtocolSimulator.utilities.PacketUtilities;

public class Receiver extends Client
{
    /**
     * Current sequence number.
     * 
     * Initialized to 0 in the constructor.
     */
    private int            currentSequenceNumber;

    /**
     * The UDP socket, the receiver is listening on.
     */
    private DatagramSocket listen;

    /**
     * Create a client whose sole purpose is to receive from the sender (transmitter).
     * 
     * @param clientMode the client mode.
     */
    public Receiver(ClientMode clientMode)
    {
        super(clientMode);
        this.currentSequenceNumber = 0;

        try
        {
            this.listen = UDPNetwork.createServer(this.configuration.getReceiverPort());
        }
        catch (SocketException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        // listen for SOT
        this.waitForOtherSideToTakeControl();

        // scan each packet

        // send an ack for each packet

        // if a packet arrives again, send ack again

        // listen for EOT
    }

    /**
     * Waits for Sender to send a SOT.
     */
    private void waitForOtherSideToTakeControl()
    {
        try
        {

            Packet sotPacket = UDPNetwork.getPacket(this.listen);

            if (sotPacket.getPacketType() == PacketUtilities.PACKET_START_OF_TRANSMISSION)
            {
                // TODO: print SOT packet.

                // send SOT back to signify receive.
                this.sendPacket(this.makePacket(PacketUtilities.PACKET_START_OF_TRANSMISSION));
            }
            else
            {
                // TODO: exception..close receiver.

                // malicious packet - continue.
                this.waitForOtherSideToTakeControl();
            }
        }
        catch (ClassNotFoundException e)
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

    @Override
    protected Packet makePacket(int packetType)
    {
        return PacketUtilities.makePacket(this.configuration.getTransmitterAddress()
                .getHostAddress(), this.configuration.getTransmitterPort(), this.configuration
                .getReceiverAddress().getHostAddress(), this.configuration.getReceiverPort(),
                packetType, this.currentSequenceNumber, this.currentSequenceNumber,
                this.configuration.getWindowSize());

    }

}
