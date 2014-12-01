package sawProtocolSimulator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

import sawProtocolSimulator.exceptions.CouldNotReadConfigurationException;
import sawProtocolSimulator.models.Configuration;

public class Main
{

    /**
     * The main entry point of the entire application.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        //Ask user what this current instance will be: sender, receiver or network module.
        
        //If network module, read the network configuration and create the object.
        try
        {
            Configuration configuration = networkConfiguration();
        }
        catch (CouldNotReadConfigurationException e)
        {
            //LOG
            
            //critical thing failed, close the program.
            return;
        }
        
    }

    /**
     * Parse the network configuration file and fill up the Configuration model with it.
     * 
     * @return Configuration the configuration parsed from the config file; null if nothing read.
     * @throws CouldNotReadConfigurationException if the file cannot be parsed.
     */
    public static Configuration networkConfiguration() throws CouldNotReadConfigurationException
    {
        Properties networkProperties = new Properties();
        InputStream fileInputStream = null;
        Configuration configuration = new Configuration();
        
        try
        {
            //create a stream to the properties file
            fileInputStream = new FileInputStream("NetworkConfiguration.properties");
            
            //load the configuration file.
            networkProperties.load(fileInputStream);
            
            //set configuration properties
            configuration.setReceiver(InetAddress.getByName(networkProperties.getProperty("receiverAddress")));
            configuration.setReceiverPort(Integer.parseInt(networkProperties.getProperty("receiverPort")));
            configuration.setSender(InetAddress.getByName(networkProperties.getProperty("senderAddress")));
            configuration.setSenderPort(Integer.parseInt(networkProperties.getProperty("senderPort")));
            
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            
            throw new CouldNotReadConfigurationException("Couldn't read the configuration file.");
        }
        catch (IOException e)
        {
            e.printStackTrace();

            throw new CouldNotReadConfigurationException("Couldn't load the configuration file.");
        }
        finally
        {
            if(fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();

                    throw new CouldNotReadConfigurationException("Couldn't close the configuration file.");
                }
            }
        }
        
        return configuration;
    }

}
