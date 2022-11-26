package servicios;

public class SHA1 {
    private static SHA1 instanceSHA1;

    public static SHA1 getInstance(){
        if(instanceSHA1==null){
            instanceSHA1 = new SHA1();
        }
        return instanceSHA1;
    }

    public String convertirConHash(String contraseña){
        return org.apache.commons.codec.digest.DigestUtils.sha1Hex( contraseña );
    }
}
