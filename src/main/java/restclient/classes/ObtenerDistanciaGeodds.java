package restclient.classes;

import dominio.common.Distancia;
import dominio.common.Ubicacion;
import dominio.common.excepcions.ObtenerDistanciaApiException;
import dominio.common.ObtenerDistancia;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import restclient.interfaces.GeoddsApi;

public class ObtenerDistanciaGeodds extends ObtenerDistancia {
  //TODO: llevar url y token a config externa
  private static final String URL_API = "https://ddstpa.com.ar/api/";
  private static final String BEARER_TOKEN = "Bearer PL8YsD8L/qHceWSOK1aycCyJyufgbRCg2/yXSB/6oc4=";

  private static ObtenerDistanciaGeodds instancia = null;
  private final Retrofit retrofit;

  public ObtenerDistanciaGeodds() {
    this.retrofit = new Retrofit.Builder()
        .baseUrl(URL_API)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  //TODO: eliminar singleton?
  public static ObtenerDistanciaGeodds getInstancia() {
    if (instancia == null) {
      instancia = new ObtenerDistanciaGeodds();
    }
    return instancia;
  }

  @Override
  public Distancia obtenerDistancia(Ubicacion puntoA, Ubicacion puntoB) {
    try {
      GeoddsApi geoddsApi = this.retrofit.create(GeoddsApi.class);
      Call<DistanciaDto> requestDistancia = geoddsApi.distancia(BEARER_TOKEN,
          new CalcularDistanciaDto(puntoA, puntoB));
      Response<DistanciaDto> distanciaDtoResponse = requestDistancia.execute();

      return distanciaDtoResponse.body().createDistancia();
    } catch (Exception e) {
      //throw new ObtenerDistanciaApiException(e.getMessage());
      return new Distancia(10L, "mts");
    }
  }
}
