package dominio.common.enums;

import java.util.Arrays;

public enum Periodicidad {
    MENSUAL("mensual"),
    ANUAL("anual");

    public String name;

    Periodicidad(String name) {
        this.name = name;
    }

    public static Periodicidad lookup(String name) {
        return Arrays.stream(values()).filter(periodicidad -> periodicidad.name().equalsIgnoreCase(name)).findFirst().get();
    }

}
