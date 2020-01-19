package com.hackcambridge.recycling;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.appinvite.AppInviteInvitation;

public class MenuActivity extends AppCompatActivity {

    private static final int REQUEST_INVITE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        // set item as selected to persist highlight
                        menuItem.setChecked(true);

                        switch (menuItem.getItemId()) {
                            case R.id.invite_menu:
                                sendInvitation();
                                finish();

                                return true;
                            case R.id.crash_menu:
                                Log.w("Crashlytics", "Crash button clicked!");
                                causeCrash();
                                return true;
                            case R.id.sign_out_menu:
//                                Intent myIntent = new Intent(MenuActivity.this, MenuActivity.class);
//                                com.shenpaul.mhacks11.workhardplayhard.MainActivity.mFirebaseAuth.signOut();
//                                Auth.GoogleSignInApi.signOut(com.shenpaul.mhacks11.workhardplayhard.MainActivity.mGoogleApiClient);
//                                com.shenpaul.mhacks11.workhardplayhard.MainActivity.mFirebaseUser = null;
//                                com.shenpaul.mhacks11.workhardplayhard.MainActivity.mUsername = com.shenpaul.mhacks11.workhardplayhard.MainActivity.ANONYMOUS;
//                                com.shenpaul.mhacks11.workhardplayhard.MainActivity.mPhotoUrl = null;
//
//                                AlertDialog alertDialog = new AlertDialog.Builder(com.shenpaul.mhacks11.workhardplayhard.MapsActivity.this).create();
//                                alertDialog.setTitle("Alert");
//                                alertDialog.setMessage("You have successfully signed out!");
//                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.dismiss();
//                                                System.exit(0);
//                                            }
//                                        });
//                                alertDialog.show();
                                return true;
                            default:
                                return true;
                        }
                    }
                }
        );
    }

    private void causeCrash() {
        throw new NullPointerException("Fake null pointer exception");
    }

    private void sendInvitation() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }
}
