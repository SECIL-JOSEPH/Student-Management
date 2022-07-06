package utility;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	private Properties properties;	
	public static Properties loadProperty() 
    {
		Properties properties = new Properties();
        try(InputStream input = Config.class.getClassLoader().getResourceAsStream("Config.properties"))  
        {        	
            properties.load(input);
        }
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        return properties;
    }
}