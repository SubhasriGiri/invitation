package alumni.cit.edu.in.invitation;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;


import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

public class MainActivity extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_INVITE = 0;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.invite_button).setOnClickListener(this);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(AppInvite.API)
                .enableAutoManage(this, this)
                .build();
        boolean autoLaunchDeepLink=true;
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient,this,autoLaunchDeepLink)
                .setResultCallback(
                        new ResultCallback<AppInviteInvitationResult>() {
                            @Override
                            public void onResult(AppInviteInvitationResult result) {
                                Log.d(TAG,"getInvitation:onResult:"+result.getStatus());
                                if (result.getStatus().isSuccess()){
                                    Intent intent=result.getInvitationIntent();
                                    String deepLink= AppInviteReferral.getDeepLink(intent);
                                    String invitationId=AppInviteReferral.getInvitationId(intent);
                                }
                            }


                        });




    }



    @Override
    public void onConnectionFailed( ConnectionResult connectionResult) {
        Log.d(TAG,"onConnectionFailed:"+connectionResult);
        showMessage("Google Play Services Error");

    }
    private void onInviteClicked(){
        Intent intent=new AppInviteInvitation.IntentBuilder("Send App Invite Quickstart Invitation")
                .setMessage("This app is terrific,give it a try and get off")
                .setDeepLink(Uri.parse("https://invitaion.in/"))
                .setCustomImage(Uri.parse("http://www.google.com/images/branding/googlelogo/2x/go..."))
                .setCallToActionText("Install!")
                .build();
        startActivityForResult(intent,REQUEST_INVITE);

    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Log.d(TAG,"onActivityResult: requestCode="+requestCode+",resultCode="+resultCode);
        if(requestCode==REQUEST_INVITE){
            if(resultCode==RESULT_OK){
                String [] ids=AppInviteInvitation.getInvitationIds(resultCode,data);
                for(String id:ids) {
                    Log.d(TAG, "onActivityResult: sent invitation"+ id);
                }
            }else{
                showMessage("Sending invitations failed");


            }
        }
    }
    private void showMessage(String msg){
        ViewGroup container=(ViewGroup)findViewById(R.id.snackbar_layout);
        Snackbar.make(container,msg,Snackbar.LENGTH_SHORT).show();

    }
    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.invite_button:
                onInviteClicked();
                break;

        }

    }

}

