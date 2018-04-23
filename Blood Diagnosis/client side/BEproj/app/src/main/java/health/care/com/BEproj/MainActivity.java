
package health.care.com.BEproj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import health.care.com.BEproj.Medical.MedicalProblems;
import health.care.com.BEproj.NearbyLocations.GMap.ListHealthCenters;

public class MainActivity extends AppCompatActivity {

    /** progressDialog variable*/
    public static ProgressDialog progressDialog;

    /**
     * onCreate method calls when application opened
     * @param savedInstanceState
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** initializing onClickListener method for medical button
         * @param onClickListener
         * */
        /*findViewById(R.id.medicalButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicalProblems();
            }
        });*/

        /** initializing onClickListener method for hospital button
         * @param onClickListener
         * */
        findViewById(R.id.hospitalButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hospitalLocations();
            }
        });

    }

    /**
     * medicalProblems method to show problems list
     * */
   /* void medicalProblems() {
        loading("Loading...");
        Intent intent = new Intent(MainActivity.this, MedicalProblems.class);
        startActivity(intent);
    }
    */

    /**
     * hospitalLocations method to start activity which shows nearby hospital locations
     * */
    void hospitalLocations() {
        if(isNetworkAvailable()) {
            loading("Scanning Location...");
            Intent intent = new Intent(MainActivity.this, ListHealthCenters.class);
            startActivity(intent);
        }else
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
    }

    /**
     * isNetworkAvailable
     * @return true if internet connection is available and @return false if not available
     * */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * show progress dialog while loading maps or problems
     * */
    void loading(String message){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
