package controllers;

import dominio.administrador.Administrador;
import dominio.administrador.Rol;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import repositorios.UsuarioRepositorio;
import servicios.SHA1;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

public class ControllerSesion {


    private String mensaje = "";
    private Rol userRol;

    public ModelAndView mostrarLogin(Request req, Response res) {

        return new ModelAndView(null, "login.hbs");
    }

    public ModelAndView cerrarSesion(Request req, Response res) {
        req.session().removeAttribute("user");
        res.redirect("/login");
        return null;
    }

    public ModelAndView iniciarSesion(Request req, Response res) {
        try {
            Optional<Rol> usuarioLogin = UsuarioRepositorio.getInstance().getAdministradores().stream().filter(user->user.getUsuario().equals(req.queryParams("user"))).collect(
                Collectors.toList()).stream().findFirst();
            userRol = usuarioLogin.orElseThrow(()->new RuntimeException("No existe el usuario"));
            String user = req.session().attribute(userRol.getId().toString());
            if(user!=null){
                res.redirect("/perfil");
            }
            req.session().attribute("user",userRol.getId());
            req.session().attribute("rol",userRol.getDecriminatorValue());
            String contraseniaHasheada = SHA1.getInstance().convertirConHash(req.queryParams("pass"));
            userRol.validarContraseniaHash(contraseniaHasheada);
        }
        catch (Exception e) {
            Map<String, Object> model = new HashMap<>();
            System.out.println("ERROR en el login: " + e.getMessage());
            model.put("error", e.getMessage());
            return new ModelAndView(model, "login.hbs");
        }


        res.redirect("/perfil");
        return null;
    }

}
