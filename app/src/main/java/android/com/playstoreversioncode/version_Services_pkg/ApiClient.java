package android.com.playstoreversioncode.version_Services_pkg;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    static String uRL = "https://play.google.com";
    private static Retrofit retrofit = null;

    public static Retrofit getInstance() {
        OkHttpClient client;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        client = builder.build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl(uRL)
                    .build();
        }
        return retrofit;
    }

    public static ServicesApis getservices() {
        return getInstance().create(ServicesApis.class);
    }
}
