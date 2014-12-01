package sawProtocolSimulator;

import java.io.Serializable;

/**
 * This class contains the structure of a packet.
 */
public class Packet implements Serializable
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
     * Data to be sent in the packet.
     */
    private String data;

    /**
     * Default Constructor.
     */
    public Packet() {}

    /**
     * Get the packet type.
     * 
     * @return the packetType
     */
    public int getPacketType()
    {
        return packetType;
    }

    /**
     * Set the packet type.
     * 
     * @param packetType the packetType to set
     */
    public void setPacketType(int packetType)
    {
        this.packetType = packetType;
    }

    /**
     * Get the sequence number.
     * 
     * @return the seqNum
     */
    public int getSeqNum()
    {
        return seqNum;
    }

    /**
     * Set the sequence number.
     * 
     * @param seqNum the seqNum to set
     */
    public void setSeqNum(int seqNum)
    {
        this.seqNum = seqNum;
    }

    /**
     * Get the window size.
     * 
     * @return the windowSize
     */
    public int getWindowSize()
    {
        return windowSize;
    }

    /**
     * Set the window size
     * 
     * @param windowSize the windowSize to set
     */
    public void setWindowSize(int windowSize)
    {
        this.windowSize = windowSize;
    }

    /**
     * Get the Acknowledgement Number
     * 
     * @return the ackNum
     */
    public int getAckNum()
    {
        return ackNum;
    }

    /**
     * Set the acknowledgement number.
     * 
     * @param ackNum the ackNum to set
     */
    public void setAckNum(int ackNum)
    {
        this.ackNum = ackNum;
    }

    /**
     * Get the data in a packet.
     * 
     * @return the data
     */
    public String getData()
    {
        return data;
    }

    /**
     * Set the data in a packet.
     * 
     * @param data the data to set
     */
    public void setData(String data)
    {
        this.data = data;
    }

}
