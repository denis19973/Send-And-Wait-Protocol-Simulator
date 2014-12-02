package sawProtocolSimulator.models;

import java.net.InetAddress;

public class ClientConfiguration
{
    /**
     * Address of the network emulator.
     */
    private InetAddress networkAddress;
    
    /**
     * Port number, the network emulator is listening on.
     */
    private int networkPort;
    
    /**
     * Default constructor.
     */
    public ClientConfiguration()
    {}
    
    /**
     * Creates a Client Configuration Object.
     * 
     * @param networkAddress the address of the network emulator
     * @param networkPort the port number of the network emulator
     */
    public ClientConfiguration(InetAddress networkAddress, int networkPort)
    {
        this.networkAddress = networkAddress;
        this.networkPort = networkPort;
    }

    /**
     * Get the Address of the network emulator.
     * 
     * @return the networkAddress
     */
    public InetAddress getNetworkAddress()
    {
        return networkAddress;
    }

    /**
     * Set the address of the network emulator.
     * 
     * @param networkAddress the networkAddress to set
     */
    public void setNetworkAddress(InetAddress networkAddress)
    {
        this.networkAddress = networkAddress;
    }

    /**
     * Get the port of the network emulator.
     * 
     * @return the networkPort
     */
    public int getNetworkPort()
    {
        return networkPort;
    }

    /**
     * Set the port of the network emulator.
     * 
     * @param networkPort the networkPort to set
     */
    public void setNetworkPort(int networkPort)
    {
        this.networkPort = networkPort;
    }
    
    
}
