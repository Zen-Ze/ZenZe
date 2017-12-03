package com.ucd.comp41690.team21.zenze.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import com.ucd.comp41690.team21.zenze.R;

public class ShareActivity extends Activity {
    ImageView imageView;
    TextView txtName,txtGender,txtBd;
    String Message = "www.happyZENZE.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        imageView = (ImageView) findViewById(R.id.imgPhoto);
        txtName = (TextView) findViewById(R.id.txtName);
        txtGender = (TextView) findViewById(R.id.txtGender);
        txtBd = (TextView) findViewById(R.id.txtBd);
        GetUserInfo();
        //Share Dialog
        //You cannot preset the shared link in design time, if you do so, the fb share button will
        //look disabled. You need to set in the code as below
        ShareButton fbShareButton = (ShareButton) findViewById(R.id.fb_share_button);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(Message))
                .setImageUrl((Uri.parse("")))
                .build();
        fbShareButton.setShareContent(content);
    }

    private void GetUserInfo(){
        /**obtain information from facebook,
         If Add some other field which not show here, we can go to https://developers.facebook.com/docs/graph-api/using-graph-api/
         **/
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try{
                            String gender = object.getString("gender");
                            String birthday = object.getString("birthday");
                            String name = object.getString("name");

                            txtName.setText(name);
                            txtGender.setText(gender);
                            txtBd.setText(birthday);
                            if (object.has("picture")) {
                                String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                Picasso.with(ShareActivity.this).load(profilePicUrl).into(imageView);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,gender,name,birthday,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();

    }



}

