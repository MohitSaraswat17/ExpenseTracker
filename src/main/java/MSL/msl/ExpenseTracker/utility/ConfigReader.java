package MSL.msl.ExpenseTracker.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

//***Exception****
import java.io.IOException;
import java.io.InputStream;
//*** property Class ****
import java.util.Properties;

public class ConfigReader {
	
	 public static Properties getPropertyObject() throws IOException {
		 Properties prop = new Properties();

		// Load properties from XML resource file
		try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream("Config/config.xml")) {
			if (inputStream == null) {
				throw new FileNotFoundException("Config/config.xml not found in resources");
			}
			prop.loadFromXML(inputStream);
		}

		 
		 prop.setProperty("mobileRegexp", "^[0-9]{10}$");
		 prop.setProperty("nameRegexp", "^[a-zA-Z]{1,24}(?:\\s[a-zA-Z]{1,24}){0,24}$");
		 prop.setProperty("passwordRegexp", "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
		 prop.setProperty("emailRegexp", "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
		 
		// Optionally, save the properties back to the XML file (not recommended for JAR)
		// try (OutputStream outputStream = new FileOutputStream("Config/config.xml")) {
		//     prop.storeToXML(outputStream, "Regular Expression data");
		// }

		return prop;
	 }
	 
	 public static String getNameRegexp() throws IOException {
		 return getPropertyObject().getProperty("nameRegexp");
	 }
	 
	 public static String getPasswordRegexp() throws IOException {
		 return getPropertyObject().getProperty("passwordRegexp");
	 }
	 
	 public static String getMobileRegexp() throws IOException {
		 return getPropertyObject().getProperty("mobileRegexp");
	 }
	 
	 public static String getEmailRegexp() throws IOException {
		 return getPropertyObject().getProperty("emailRegexp");
	 }

}

