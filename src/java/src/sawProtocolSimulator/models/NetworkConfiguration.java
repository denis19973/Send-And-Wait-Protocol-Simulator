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
     */
    public NetworkConfiguration(InetAddress sender, int senderPort, InetAddress receiver, int receiverPort)
    {
        this.sender = sender;
        this.senderPort = senderPort;
        this.receiver = receiver;
        this.receiverPort = receiverPort;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Configuration [sender=" + sender + ", senderPort=" + senderPort + ", receiver="
                + receiver + ", receiverPort=" + receiverPort + "]";
    }

}
