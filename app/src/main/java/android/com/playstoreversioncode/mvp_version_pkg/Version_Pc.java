package android.com.playstoreversioncode.mvp_version_pkg;

import android.app.Activity;
import android.com.playstoreversioncode.version_Services_pkg.ApiClient;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Version_Pc implements Version_Pi {
    private static final String TAG = "MYCONTROLLER";
    private Version_View version_view;
    private String network_Error = "Network unavailable, Please try again later !";

    public Version_Pc(Version_View version_view) {
        this.version_view = version_view;
    }


    /**
     * Get latest app version code from play store using Retrofit calling
     */
    @Override
    public void getPlayStoreVersionCode() {
/** com.eot_app is my app id*/

        if (getNetworkavailability()) {
            ApiClient.getservices().getPlayStoreVersionCode("/store/apps/details?id=com.eot_app").enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String html = response.body().string();
                        Document document = Jsoup.parse(html);
                        /** Add Tag in getElementsContainingOwnText("MY_TAG") */
                        Elements elements = document.getElementsContainingOwnText("Current Version");
                        for (Element ele : elements) {
                            if (ele.siblingElements() != null) {
                                Elements sibElemets = ele.siblingElements();
                                for (Element sibElemet : sibElemets) {
                                    String latestVersion = sibElemet.text();
                                    Log.d(TAG, "Your Latest App Version Code--->>" + latestVersion);
                                    version_view.setCurrentVersionCode(latestVersion);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    version_view.dismissProgressBar();
                    Log.e(TAG, "Error Occured!!!");
                }
            });
        } else {
                version_view.dismissProgressBar();
            Toast.makeText(((Context) version_view), "" + network_Error, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Get latest app version code from play store using RX Java /Rx Android
     */
    /**
     * store/apps/details?id=com.eot_app
     * is current package name.
     */

    @Override
    public void getAppVersionCode() {
        if (getNetworkavailability()) {
            ApiClient.getservices().getPlayStoreAppVersion("/store/apps/details?id=com.eot_app").subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<ResponseBody>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Response<ResponseBody> responseBody) {
                            try {
                                String html = responseBody.body().string();
                                Document document = Jsoup.parse(html);
                                /** Add Tag in getElementsContainingOwnText("MY_TAG") */
                                Elements elements = document.getElementsContainingOwnText("Current Version");
                                for (Element ele : elements) {
                                    if (ele.siblingElements() != null) {
                                        Elements sibElemets = ele.siblingElements();
                                        for (Element sibElemet : sibElemets) {
                                            String latestVersion = sibElemet.text();
                                            Log.d(TAG, "Your Latest App Version Code--->>" + latestVersion);
                                            version_view.setCurrentVersionCode(latestVersion);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "Error Occured!!!");
                            version_view.dismissProgressBar();
                        }

                        @Override
                        public void onComplete() {
                         //   version_view.dismissProgressBar();
                        }
                    });
        } else {
            version_view.dismissProgressBar();
            Toast.makeText(((Context) version_view), "" + network_Error, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Check Internet Avaailability from Data or Wifi
     */
    private boolean getNetworkavailability() {
        ConnectivityManager cm = (ConnectivityManager) ((Activity) version_view).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
