package wrappers;


import commands.Command;
import java.io.Serializable;

/**
 * The NetworkWrapper is used any time the server and client must communicate by 
 * associating a Command Enum with a specific Object (if needed) listed in the 
 * ClientCommand and ServerCommand documentation. It contains commands being 
 * sent between the server and client and the data being passed with it and 
 * methods to get those values.
 *
 * @author  Jacob Francis
 * @version 1.0       - Nov 3, 2015
 */
public class NetworkWrapper implements Serializable 
{
    /**
     * Either a ServerCommand or ClientCommand (or any other Enum that 
     * implements the Command interface). This is the first thing the client
     * or server looks at when it receives an object from one another. Each 
     * Command may be associated with an object.
     */
    private final Command cmd;
    
    /**
     * The object associated with the Command to be passed between the client
     * and server.
     */
    private final Object data;
    
    /**
     * Construct a NetworkWrapper from a given Command and Object
     * 
     * @param cmd the Command Enum
     * @param data the object to be passed between the client and server. May 
     * be null if no data is needed.
     */
    public NetworkWrapper(Command cmd, Object data)
    {
        this.cmd = cmd;
        this.data = data;
    }
    
    /**
     * Get the Command.
     * 
     * @return the Command cmd.
     */
    public Command getCommand()
    {
        return cmd;
    }
    
    /**
     * Get the Object.
     * 
     * @return the Object data.
     */
    public Object getObject()
    {
        return data;
    }
}
