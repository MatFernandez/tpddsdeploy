package dominio.administrador;

import dominio.administrador.exceptions.PasswordInvalida;
import dominio.administrador.interfaces.ReglaPassword;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReglaDenylist implements ReglaPassword {

  @Override
  public void aplicarRegla(String password) {
    boolean estaEnBlacklist = getBlacklistPasswords().stream().anyMatch(p -> p.equals(password));
    if (estaEnBlacklist) {
      throw new PasswordInvalida("La contraseña es insegura, esta en la blacklist de passwords");
    }
  }

  public List<String> getBlacklistPasswords() {
    List<String> blacklist = new ArrayList<>();
    String file = "src/main/resources/password-inseguras.txt";
    try (InputStream in = Files.newInputStream(Paths.get(file));
         Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         BufferedReader buffer = new BufferedReader(reader)) {
      String line;
      while ((line = buffer.readLine()) != null) {
        blacklist.add(line);
      }
      return blacklist;
    } catch (IOException e) {
      throw new RuntimeException("No se encontro el archivo de contraseñas inseguras.");
    }
  }
}
