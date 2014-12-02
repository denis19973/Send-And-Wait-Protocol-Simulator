package sawProtocolSimulator.utilities;

import sawProtocolSimulator.models.Packet;

public class PacketUtilities
{
    /**
     * The start of transmission packet.
     * 
     * Send when acquiring a channel.
     */
    public static final int PACKET_START_OF_TRANSMISSION = 1;

    /**
     * Data packet.
     */
    public static final int PACKET_DATA                  = 2;

    /**
     * ACK packet.
     */
    public static final int PACKET_ACK                   = 3;

    /**
     * The end of transmission packet.
     * 
     * Telling the other side that the transmission has ended.
     */
    public static final int PACKET_END_OF_TRANSMISSION   = 4;

    /**
     * Generates a packet with the details provided.
     * 
     * @param destinationAddress the destination address
     * @param destinationPort the destination port
     * @param sourceAddress the source address
     * @param sourcePort the source port
     * @param packetType the type of packet
     * @param sequenceNumber the sequence number of packet
     * @param acknowledgementNumber the acknowledgement number
     * @param windowSize the window size
     * 
     * @return the generated packet
     */
    public static Packet makePacket(String destinationAddress, int destinationPort,
            String sourceAddress, int sourcePort, int packetType, int sequenceNumber,
            int acknowledgementNumber, int windowSize)
    {
        Packet packet = new Packet();

        switch (packetType)
        {
            case 1:
                // SOT
                packet.setData("SOT - Start of Transmission");

                break;

            case 2:
                // DATA
                packet.setData("Packet Number: " + sequenceNumber);

                break;

            case 3:
                // ACK
                packet.setData("Acknowledgement Number: " + acknowledgementNumber);

                break;

            case 4:
                // EOT
                packet.setData("EOT - End of Transmission");

                break;
        }

        packet.setDestinationAddress(destinationAddress);
        packet.setDestinationPort(destinationPort);
        packet.setSourceAddress(sourceAddress);
        packet.setSourcePort(sourcePort);
        packet.setPacketType(packetType);
        packet.setAckNum(acknowledgementNumber);
        packet.setSeqNum(sequenceNumber);
        packet.setWindowSize(windowSize);

        return packet;
    }

    /**
     * Generates a generic packet log. This can be put on the screen or in the log files.
     * 
     * @param packet the packet to generate the logs for
     * @param network if this was called from the network module
     * @param forwarded if the packet was forwarded or dropped.
     * 
     * @return a generic packet log
     */
    public static String generatePacketLog(Packet packet, boolean network, boolean forwarded)
    {
        StringBuilder log = new StringBuilder();

        if (network)
        {
            if (forwarded)
            {
                log.append("[FORWARDED] ");
            }
            else
            {
                log.append("[DROPPED]   ");
            }
        }

        log.append("Packet Type: ");

        switch (packet.getPacketType())
        {
            case 1:
                log.append("\n");
                log.append("SOT \t");
                log.append("\n");

                break;

            case 2:
                log.append("DATA\t");
                log.append("Packet Number: " + packet.getSeqNum());

                break;

            case 3:
                log.append("ACK \t");
                log.append("ACK Number:    " + packet.getAckNum());

                break;

            case 4:
                log.append("\n");
                log.append("EOT \t");
                log.append("\n");

                break;
        }

        return log.toString();

    }

}
