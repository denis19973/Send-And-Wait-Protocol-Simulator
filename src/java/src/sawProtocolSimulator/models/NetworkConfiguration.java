package sawProtocolSimulator.models;

import java.net.InetAddress;

public class NetworkConfiguration
{
    /**
     * The Address of the transmitter (sender).
     */
    private InetAddress sender;

    /**
     * The port number, the transmitter is listening on.
     */
    private int         senderPort;

    /**
     * The Address of the receiver.
     */
    private InetAddress receiver;

    /**
     * The port number, the receiver is listening on.
     */
    private int         receiverPort;

    /**
     * The drop rate.
     * 
     * A random number between 1 and 100 will be generated for each packet received and if that
     * random number is lower than or equal to this drop rate, that packet will be dropped.
     */
    private int         dropRate;

    /**
     * The average delay per packet (in milliseconds).
     * 
     * In seconds: example: 5 ms.
     */
    private int         averageDelayPerPacket;

    /**
     * Default constructor.
     */
    public NetworkConfiguration()
    {}

    /**
     * Creates a Network Configuration object.
     * 
     * @param sender Sender Address
     * @param senderPort Sender Port
     * @param receiver Receiver Address
     * @param receiverPort Receiver Port
     * @param dropRate Drop Rate
     * @param averageDelayPerPacket Average Delay Per Packet
     */
    public NetworkConfiguration(InetAddress sender, int senderPort, InetAddress receiver,
            int receiverPort, int dropRate, int averageDelayPerPacket)
    {
        this.sender = sender;
        this.senderPort = senderPort;
        this.receiver = receiver;
        this.receiverPort = receiverPort;
        this.dropRate = dropRate;
        this.averageDelayPerPacket = averageDelayPerPacket;
    }

    /**
     * Get the sender address.
     * 
     * @return the sender
     */
    public InetAddress getSender()
    {
        return sender;
    }

    /**
     * Set the sender address.
     * 
     * @param sender the sender to set
     */
    public void setSender(InetAddress sender)
    {
        this.sender = sender;
    }

    /**
     * Get the sender port.
     * 
     * @return the senderPort
     */
    public int getSenderPort()
    {
        return senderPort;
    }

    /**
     * Set the sender port.
     * 
     * @param senderPort the senderPort to set
     */
    public void setSenderPort(int senderPort)
    {
        this.senderPort = senderPort;
    }

    /**
     * Get the receiver address.
     * 
     * @return the receiver
     */
    public InetAddress getReceiver()
    {
        return receiver;
    }

    /**
     * Set the receiver address.
     * 
     * @param receiver the receiver to set
     */
    public void setReceiver(InetAddress receiver)
    {
        this.receiver = receiver;
    }

    /**
     * Get the receiver port.
     * 
     * @return the receiverPort
     */
    public int getReceiverPort()
    {
        return receiverPort;
    }

    /**
     * Set the receiver port.
     * 
     * @param receiverPort the receiverPort to set
     */
    public void setReceiverPort(int receiverPort)
    {
        this.receiverPort = receiverPort;
    }

    /**
     * Get the drop rate.
     * 
     * @return the dropRate
     */
    public int getDropRate()
    {
        return dropRate;
    }

    /**
     * Set the drop rate.
     * 
     * @param dropRate the dropRate to set
     */
    public void setDropRate(int dropRate)
    {
        this.dropRate = dropRate;
    }

    /**
     * Get the average delay per packet.
     * 
     * @return the averageDelayPerPacket
     */
    public int getAverageDelayPerPacket()
    {
        return averageDelayPerPacket;
    }

    /**
     * Set the average delay per packet.
     * 
     * @param averageDelayPerPacket the averageDelayPerPacket to set
     */
    public void setAverageDelayPerPacket(int averageDelayPerPacket)
    {
        this.averageDelayPerPacket = averageDelayPerPacket;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "NetworkConfiguration [sender=" + sender + ", senderPort=" + senderPort
                + ", receiver=" + receiver + ", receiverPort=" + receiverPort + ", dropRate="
                + dropRate + ", averageDelayPerPacket=" + averageDelayPerPacket + "]";
    }

}
