package android.com.playstoreversioncode.version_Services_pkg;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ServicesApis {

    /**
     * Version code applicable only @GET
     */

    /**
     * Using Retrofit
     */
    @GET
    Call<ResponseBody> getPlayStoreVersionCode(@Url String myUrl);

    /**
     * Using RX java
     */
    @GET
    Observable<Response<ResponseBody>> getPlayStoreAppVersion(@Url String myUrl);
}
