package dominio.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

  public static final String CONFIG_PROPERTIES = "src/main/resources/config.properties";
  private static ConfigProperties INSTANCE;

  private ConfigProperties() {
  }

  public static ConfigProperties getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ConfigProperties();
    }
    return INSTANCE;
  }

  public Properties getProperties() {
    Properties prop = new Properties();
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(CONFIG_PROPERTIES);
      prop.load(fis);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return prop;
  }
}
