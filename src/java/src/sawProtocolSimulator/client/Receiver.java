package sawProtocolSimulator.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

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
    private int               currentSequenceNumber;

    /**
     * Total number of ack'ed packets.
     */
    private ArrayList<Packet> ackedPackets;

    /**
     * The UDP socket, the receiver is listening on.
     */
    private DatagramSocket    listen;

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

        /**
         * Receive until this is true.
         */
        boolean keepReceiving = true;

        int totalPackets = 0;
        int totalDuplicateAcks = 0;

        while (keepReceiving)
        {
            try
            {
                // scan each packet
                Packet packet = UDPNetwork.getPacket(listen);

                switch (packet.getPacketType())
                {
                    case (PacketUtilities.PACKET_END_OF_TRANSMISSION):
                        // listen for EOT
                        // TODO: print EOT .. session stats and end.

                        keepReceiving = false;

                        break;

                    case (PacketUtilities.PACKET_DATA):

                        // if packet hasn't been ACK'ed before.
                        if (!this.findIfPacketAckedBefore(packet.getSeqNum()))
                        {
                            totalPackets++;
                        }
                        else
                        {
                            // ACKing again - earlier ACK probably got lost
                            totalDuplicateAcks++;
                        }

                        // update current sequence number
                        this.currentSequenceNumber = packet.getSeqNum();

                        // TODO: print data

                        // craft and send ACK packet
                        this.sendPacket(this.makePacket(PacketUtilities.PACKET_ACK));

                        // add to list of ack'ed packets
                        this.ackedPackets.add(packet);

                        // TODO: print ACK packet

                        break;

                    default:
                        // TODO: packet type not valid - do something

                        break;
                }

                // send an ack for each packet

                // if a packet arrives again, send ack again

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
    }

    /**
     * Find if the current packet has ever been ack'ed before.
     * 
     * @param seqNum sequence number of the current packet
     * @return true if it has been ack'ed before, false if not.
     */
    private boolean findIfPacketAckedBefore(int seqNum)
    {
        for (int i = 0; i < this.ackedPackets.size(); i++)
        {
            if (this.ackedPackets.get(i).getSeqNum() == seqNum)
            {
                return true;
            }
        }

        return false;
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
