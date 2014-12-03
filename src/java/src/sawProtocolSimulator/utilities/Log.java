package sawProtocolSimulator.utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log
{

    /**
     * Name of the log file.
     */
    public static String logFile;
    
    /**
     * Simply logs to the command line and a file.
     * 
     * @param log the message to log.
     */
    public static void d(String log)
    {
        log = Log.getCurrentTime() + " " + log;
        
        // print to screen for now
        System.out.println(log);

        // log to file
        try
        {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(Log.logFile, true)));
            printWriter.println(log);
            printWriter.close();
        }
        catch (IOException e)
        {
            //ignore
        }
    }

    /**
     * Returns the current time and date.
     * 
     * @return current time and date
     */
    public static String getCurrentTime()
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Calendar calobj = Calendar.getInstance();

        return (df.format(calobj.getTime()));
    }

}
