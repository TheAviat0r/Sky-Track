package track.homework3;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "DEBUG";
    private final static String ACTIVITY = "MainActivity: ";

    private final static int PERMISSIONS_REQUEST_CODE = 200;
    private final static int WRITE_EXTERNAL_INDEX = 0;
    private final static int READ_EXTERNAL_INDEX = 1;

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private Button askPermissionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, ACTIVITY + "onCreate");

        super.onCreate(savedInstanceState);

        setViews();
        setListeners();
    }

    private void setViews() {
        Log.d(TAG, ACTIVITY + "setViews");

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        askPermissionsButton = (Button) findViewById(R.id.button);

    }

    private void setListeners() {
        Log.d(TAG, ACTIVITY + "setListeners");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        askPermissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermissions()) {
                    requestPermissions();
                } else {
                    Toast.makeText(getApplicationContext(), "Necessary permissions have been already granted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkPermissions() {
        Log.d(TAG, ACTIVITY + "checkPermissions");

        int writeExternalStorageResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readExternalStorageResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        return writeExternalStorageResult == PackageManager.PERMISSION_GRANTED && readExternalStorageResult == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        Log.d(TAG, ACTIVITY + "requestPermissions");

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int grantResults[]) {
        Log.d(TAG, ACTIVITY + "onRequestPermissionsResult");

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeExternalAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (writeExternalAccepted && readExternalAccepted) {
                        Toast.makeText(getApplicationContext(), "All permissions were granted", Toast.LENGTH_SHORT);
                    } else {
                        Toast.makeText(getApplicationContext(), "Permissions were denied", Toast.LENGTH_SHORT);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                    shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                showMessageOkCancel("You need to allow access to both permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions();
                                                }
                                            }
                                        });
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOkCancel(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
