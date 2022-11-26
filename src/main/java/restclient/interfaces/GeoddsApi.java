package restclient.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;
import restclient.classes.*;

public interface GeoddsApi {
  @GET("distancia")
  Call<DistanciaDto> distancia(@Header("authorization") String auth,
                               @QueryMap() CalcularDistanciaDto calcularDistanciaMap);

}
