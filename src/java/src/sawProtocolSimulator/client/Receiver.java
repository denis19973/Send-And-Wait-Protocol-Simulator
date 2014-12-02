package sawProtocolSimulator.client;

import java.io.IOException;
import java.util.ArrayList;

import sawProtocolSimulator.models.Packet;
import sawProtocolSimulator.network.UDPNetwork;
import sawProtocolSimulator.utilities.Log;
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
     * Create a client whose sole purpose is to receive from the sender (transmitter).
     * 
     * @param clientMode the client mode.
     */
    public Receiver(ClientMode clientMode)
    {
        super(clientMode);
        this.currentSequenceNumber = 0;
        this.ackedPackets = new ArrayList<Packet>();
    }

    @Override
    public void run()
    {
        // initialize udp server
        this.initializeUdpServer(this.configuration.getReceiverPort());

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
                Packet packet = UDPNetwork.getPacket(this.listen);

                switch (packet.getPacketType())
                {
                    case (PacketUtilities.PACKET_END_OF_TRANSMISSION):
                        // listen for EOT

                        Log.d(PacketUtilities.generateClientPacketLog(packet, false));
                        Log.d("[RECEIVER] Total Packets Received: " + totalPackets);
                        Log.d("[RECEIVER] Total Duplicate ACK's:  " + totalDuplicateAcks);

                        keepReceiving = false;

                        break;

                    case (PacketUtilities.PACKET_DATA):

                        // update current sequence number
                        this.currentSequenceNumber = packet.getSeqNum();

                        // craft and send ACK packet
                        Packet ackPacket = this.makePacket(PacketUtilities.PACKET_ACK);
                        this.sendPacket(ackPacket);

                        // if packet hasn't been ACK'ed before.
                        if (!this.findIfPacketAckedBefore(packet.getSeqNum()))
                        {
                            totalPackets++;
                            Log.d(PacketUtilities.generateClientPacketLog(packet, false));
                            Log.d(PacketUtilities.generateClientPacketLog(ackPacket, true));
                        }
                        else
                        {
                            // ACKing again - earlier ACK probably got lost
                            totalDuplicateAcks++;
                            Log.d(PacketUtilities.generateClientResendLog(packet, false));
                        }

                        // add to list of ack'ed packets
                        this.ackedPackets.add(packet);

                        break;
                }

            }
            catch (ClassNotFoundException e)
            {
                Log.d(e.getMessage());
            }
            catch (IOException e)
            {
                Log.d(e.getMessage());
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
                Log.d(PacketUtilities.generateClientPacketLog(sotPacket, false));

                // send SOT back to signify receive.
                this.sendPacket(this.makePacket(PacketUtilities.PACKET_START_OF_TRANSMISSION));
            }
            else
            {
                Log.d(PacketUtilities.generateClientPacketLog(sotPacket, false));

                // malicious packet - continue.
                this.waitForOtherSideToTakeControl();
            }
        }
        catch (ClassNotFoundException e)
        {
            Log.d(e.getMessage());
        }
        catch (IOException e)
        {
            Log.d(e.getMessage());
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
