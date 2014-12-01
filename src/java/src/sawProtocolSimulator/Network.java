package sawProtocolSimulator;

import sawProtocolSimulator.models.Configuration;

public class Network
{
    /**
     * Configuration for the network module.
     */
    private Configuration configuration;    
    
    /**
     * The drop rate.
     * 
     * A random number will be generated for each packet received and if that random number is below
     * or equal to this drop rate, that packet will be dropped.
     */
    private int dropRate;

    /**
     * Construct the network module.
     * 
     * @param configuration the configuration file.
     */
    public Network(Configuration configuration)
    {
        this.configuration = configuration;
    } 

}
