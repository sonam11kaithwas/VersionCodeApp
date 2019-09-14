package android.com.playstoreversioncode;


import android.app.ProgressDialog;
import android.com.playstoreversioncode.mvp_version_pkg.Version_Pc;
import android.com.playstoreversioncode.mvp_version_pkg.Version_Pi;
import android.com.playstoreversioncode.mvp_version_pkg.Version_View;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Version_View {
    private static final String TAG = "MyActivity";
    private Version_Pi version_pi;
    private Button version_btn;
    private TextView versionCode_txt;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeMyViews();
    }

    private void initializeMyViews() {
        version_btn = findViewById(R.id.version_btn);
        version_btn.setOnClickListener(this);
        versionCode_txt = findViewById(R.id.versionCode_txt);

        version_pi = new Version_Pc(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.version_btn:
                /** Get Play store version code of your App */

                getProgressBar();
                /** ReTrofit Calling*/
                version_pi.getPlayStoreVersionCode();

//                /**Rx Android Calling*/
//                version_pi.getAppVersionCode();

                break;
        }
    }


    @Override
    public void setCurrentVersionCode(String latestVersion) {
        dismissProgressBar();
        versionCode_txt.setVisibility(View.VISIBLE);
        version_btn.setVisibility(View.VISIBLE);
        versionCode_txt.setText("EYEONTASK Current version on play store--->>>" + latestVersion);
    }


    @Override
    public void dismissProgressBar() {
        if (progress.isShowing()) {
            progress.dismiss();
        }
    }

    private void getProgressBar() {
        // Create progressBar dynamically...
        progress = new ProgressDialog(this);
        progress.setMessage("Loading....");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }
}
