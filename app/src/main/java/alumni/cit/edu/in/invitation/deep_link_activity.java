package alumni.cit.edu.in.invitation;

/**
 * Created by shank on 14-12-2017.
 */


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.appinvite.AppInviteReferral;


public class deep_link_activity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = deep_link_activity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deep_link_activity);
        findViewById(R.id.button_ok).setOnClickListener(this);

    }
    @Override
    protected void onStart(){
        super.onStart();
        Intent intent=getIntent();
        if(AppInviteReferral.hasReferral(intent)){
            processReferralIntent(intent);

        }
    }

    private void processReferralIntent(Intent intent) {
        String invitationId=AppInviteReferral.getInvitationId(intent);
        String deepLink=AppInviteReferral.getDeepLink(intent);
        Log.d(TAG," referral:"+invitationId +":"+ deepLink);
        ((TextView) findViewById(R.id.deep_link_text))
                .setText("Deep link");
        ((TextView)findViewById(R.id.invitation_id_text))
                .setText("invitation id");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.button_ok:
                finish();
                break;

        }
    }
}



