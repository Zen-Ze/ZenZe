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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        imageView = (ImageView) findViewById(R.id.imgPhoto);
        txtName = (TextView) findViewById(R.id.txtName);
        txtGender = (TextView) findViewById(R.id.txtGender);
        txtBd = (TextView) findViewById(R.id.txtBd);
        GetUserInfo();

        String contentUrl = "http://www.Zenze.com";
        String url = "http://image.baidu.com/search/detail?" +
                "ct=503316480&z=0&ipn=d&word=%E6%AC%A7%E6%B4%B2&step_word=&hs=0&" +
                "pn=1&spn=0&di=58484965380&pi=0&rn=1&tn=baiduimagedetail&" +
                "is=2924553577%2C2444639737&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&" +
                "cs=3523611660%2C2434430674&os=2557678455%2C1513553277&simid=0%2C0&adpicid=0&lpn=0&" +
                "ln=1980&fr=&fmq=1510230114391_R&fm=index&ic=0&s=undefined&se=&sme=&tab=0&" +
                "width=&height=&face=undefined&ist=&jit=&cg=&bdtype=17&oriquery=&objurl=http%3A%2F%2Fg.hiphotos.baidu" +
                ".com%2Fimage%2Fpic%2Fitem%2F472309f7905298225c690a28ddca7bcb0b46d486.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fi" +
                "jw1stgj_z%26e3Bkwt17_z%26e3Bv54AzdH3FvfAzdH3Fla8ndnjlw1vb1ldmm89wa9da9989auvj&gsm=&rpstart=0&rpnum=0";

        //Share Dialog
        //You cannot preset the shared link in design time, if you do so, the fb share button will
        //look disabled. You need to set in the code as below
        ShareButton fbShareButton = (ShareButton) findViewById(R.id.fb_share_button);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(contentUrl))
                .setImageUrl((Uri.parse(url)))
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

