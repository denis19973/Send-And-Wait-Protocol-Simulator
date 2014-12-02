package sawProtocolSimulator.utilities;

import sawProtocolSimulator.models.Packet;

public class PacketUtilities
{

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

}
