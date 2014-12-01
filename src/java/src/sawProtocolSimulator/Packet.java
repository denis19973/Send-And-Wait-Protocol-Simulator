package sawProtocolSimulator;

/**
 * This class contains the structure of a packet.
 */
public class Packet
{
    /**
     * The type of packet, can either be:
     * 
     * - LEAD (when acquiring the channel) - SYN - ACK - EOT (when telling the other side that the
     * transmission has ended)
     */
    private int packetType;

    /**
     * Sequence Number.
     */
    private int seqNum;

    /**
     * Window Size.
     */
    private int windowSize;

    /**
     * Acknowledgement Number.
     */
    private int ackNum;

    /**
     * Default Constructor.
     */
    public Packet() {}

    /**
     * @return the packetType
     */
    public int getPacketType()
    {
        return packetType;
    }

    /**
     * @param packetType the packetType to set
     */
    public void setPacketType(int packetType)
    {
        this.packetType = packetType;
    }

    /**
     * @return the seqNum
     */
    public int getSeqNum()
    {
        return seqNum;
    }

    /**
     * @param seqNum the seqNum to set
     */
    public void setSeqNum(int seqNum)
    {
        this.seqNum = seqNum;
    }

    /**
     * @return the windowSize
     */
    public int getWindowSize()
    {
        return windowSize;
    }

    /**
     * @param windowSize the windowSize to set
     */
    public void setWindowSize(int windowSize)
    {
        this.windowSize = windowSize;
    }

    /**
     * @return the ackNum
     */
    public int getAckNum()
    {
        return ackNum;
    }

    /**
     * @param ackNum the ackNum to set
     */
    public void setAckNum(int ackNum)
    {
        this.ackNum = ackNum;
    }

    
    
}
