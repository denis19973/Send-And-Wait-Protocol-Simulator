package sawProtocolSimulator.exceptions;

@SuppressWarnings("serial")
public class CouldNotReadConfigurationException extends Exception
{
    
    /**
     * Custom Exception.
     * 
     * @param message details of why the exception happened
     */
    public CouldNotReadConfigurationException(String message)
    {
        super(message);
    }
    
    /**
     * Get the details of why the exception happened.
     * 
     * @return message details of why the exception happened
     */
    public String getMessage()
    {
        return super.getMessage();
    }

}
