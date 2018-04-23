package health.care.com.BEproj;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
//import health.care.com.BEproj.Medical.MedicalProblems;
import health.care.com.BEproj.NearbyLocations.GMap.ListHealthCenters;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
//import android.os.Bundle;
import android.provider.MediaStore;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class Main4Activity extends AppCompatActivity {
    private ImageView imageView;
    public Button btnChoose, btnUpload,btnhosp;
    private ProgressBar progressBar;
    public static ProgressDialog progressDialog;


    TextView result;

    public static String BASE_URL = "http://192.168.43.188:80/ImageUpload/uploadL.php";
    static final int PICK_IMAGE_REQUEST = 1;
    String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        imageView = (ImageView) findViewById(R.id.imageView);
        btnChoose = (Button) findViewById(R.id.button_choose);
        btnUpload = (Button) findViewById(R.id.button_upload);
        result = (TextView) findViewById(R.id.textView2);
        //btnhosp=(Button)findViewById(R.id.button_Hospital);

        findViewById(R.id.button_Hospital).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hosp();
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageBrowse();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filePath != null) {
                    result.setText("");
                    Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();
                    imageUpload(filePath);

                } else {
                    Toast.makeText(getApplicationContext(), "Image not selected!", Toast.LENGTH_LONG).show();
                }

            }
        });
//        btnhosp.setOnClickListener(new View.OnClickListener() {
//            @Override

//        });
    }

    private void imageBrowse() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
    //
//    private void hosp(){
//        Intent myIntent = new Intent(Main2Activity.this, MainActivity.class);
////        myIntent.putExtra("key", value); //Optional parameters
//        Main2Activity.this.startActivity(myIntent);
//    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if(requestCode == PICK_IMAGE_REQUEST){
                Uri picUri = data.getData();

                filePath = getPath(picUri);

                Log.d("picUri", picUri.toString());
                Log.d("filePath", filePath);

                imageView.setImageURI(picUri);

            }

        }

    }

    private void imageUpload(final String imagePath) {

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
//                            Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();
                            JSONObject jObj = new JSONObject(response);
                            String message = jObj.getString("message");

                            Log.d("Result..", message);
//                            result = (TextView) findViewById(R.id.textView2);
                            result.setText(message);
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        smr.addFile("image", imagePath);
        MyApplication.getInstance().addToRequestQueue(smr);

    }

    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    //    public void hosp(View view) {
//        Intent myIntent = new Intent(Main2Activity.this, ListHealthCenters.class);
////        myIntent.putExtra("key", value); //Optional parameters
//        Main2Activity.this.startActivity(myIntent);
//    }
    public void hosp() {
        if(isNetworkAvailable()) {
            loading("Scanning Location...");
            Intent intent = new Intent(Main4Activity.this, ListHealthCenters.class);
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




