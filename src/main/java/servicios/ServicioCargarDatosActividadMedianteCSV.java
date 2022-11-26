package servicios;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import dominio.common.ConfigProperties;
import dominio.common.DatoActividad;
import dominio.common.excepcions.ErrorAlObtenerDatosMedicion;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//@Entity

public class ServicioCargarDatosActividadMedianteCSV extends ServicioCargaDatosActividad {
  private static final String CSV_CONFIG_DEFAULT = "fileCsv";
  private final String csvPath;

  public ServicioCargarDatosActividadMedianteCSV(String pathCSV) {
    if (pathCSV != null) {
      this.csvPath = pathCSV;
    } else {
      this.csvPath = ConfigProperties.getInstance().getProperties().getProperty(CSV_CONFIG_DEFAULT);
    }
  }

  public ServicioCargarDatosActividadMedianteCSV() {
    Properties prop = ConfigProperties.getInstance().getProperties();
    this.csvPath = prop.getProperty(CSV_CONFIG_DEFAULT);
  }

  public List<DatoActividad> obtenerDatosActividad() {
    return convertCSVtoList(this.csvPath);
  }

  private List<DatoActividad> convertCSVtoList(String csvPath) {
    try {
      Path path = Paths.get(csvPath);
      Reader reader = Files.newBufferedReader(path);
      List<DatoActividad> listaLectura = new ArrayList<>();
      CSVParser parser =
          new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
      CSVReader lectorCSV = new CSVReaderBuilder(reader).withCSVParser(parser).build();
      String[] linea;
      while ((linea = lectorCSV.readNext()) != null) {
        DatoActividad dato = new DatoActividad(linea[0], linea[4], linea[5], linea[6]);
        listaLectura.add(dato);
      }
      reader.close();
      lectorCSV.close();
      return listaLectura;
    } catch (Exception e) {
      throw new ErrorAlObtenerDatosMedicion(e.getMessage());
    }
  }

  public List<DatoActividad> convertCSVFromBytes(byte[] yourByteArray) {
    try {
      List<DatoActividad> listaLectura = new ArrayList<>();

      CSVReader lectorCSV = new CSVReader(
          new InputStreamReader(
              new ByteArrayInputStream(yourByteArray)));
          new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();

      String[] linea;
      while ((linea = lectorCSV.readNext()) != null) {
        DatoActividad dato = new DatoActividad(linea[0], linea[4], linea[5], linea[6]);
        listaLectura.add(dato);
      }
      lectorCSV.close();
      return listaLectura;
    } catch (Exception e) {
      throw new ErrorAlObtenerDatosMedicion(e.getMessage());
    }
  }
}


