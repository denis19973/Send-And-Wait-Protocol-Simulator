package sawProtocolSimulator;

import sawProtocolSimulator.models.Configuration;

public class Network
{
    /**
     * Configuration for the network module.
     */
    private Configuration configuration;

    /**
     * Constructor the network module.
     * 
     * @param configuration the configuration file.
     */
    public Network(Configuration configuration)
    {
        this.configuration = configuration;
    }

}
