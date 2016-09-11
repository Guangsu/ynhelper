package cn.edu.qzu.ynhelper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import cn.edu.qzu.ynhelper.entity.User;
import cn.edu.qzu.ynhelper.event.UserEvent;
import cn.edu.qzu.ynhelper.http.UrlConfig;
import cn.edu.qzu.ynhelper.util.SharedPreferencesHelper;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginCallback mLoginCallback = null;

    // UI references.
    private AutoCompleteTextView mTelView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mTelView = (AutoCompleteTextView) findViewById(R.id.atv_tel);

        mPasswordView = (EditText) findViewById(R.id.et_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button btLogin = (Button) findViewById(R.id.bt_login);
        btLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mLoginCallback != null) {
           // return;
        }

        // Reset errors.
        mTelView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String tel = mTelView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(tel)) {
            mTelView.setError(getString(R.string.error_field_required));
            focusView = mTelView;
            cancel = true;
        } else if (!isTelValid(tel)) {
            /*mTelView.setError(getString(R.string.error_invalid_email));
            focusView = mTelView;
            cancel = true;*/
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mLoginCallback = new UserLoginCallback();
            OkHttpUtils.get().tag(this).url(UrlConfig.LOGIN).addParams("username",tel).addParams("password",password).build().execute(mLoginCallback);
        }
    }

    private boolean isTelValid(String email) {
        //TODO: Replace this with your own logic
        //return email.contains("@");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        //return password.length() > 4;
        return true;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



    public class UserLoginCallback extends StringCallback {

        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
            showProgress(true);
        }

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String response) {
            JsonObject json = new JsonParser().parse(response).getAsJsonObject();
            int errCode = json.get("errCode").getAsInt();
            if(errCode == 200){
                User user = new Gson().fromJson(json.get("data"),User.class);
                Toast.makeText(getApplicationContext(),"username="+user.getUsername(),Toast.LENGTH_LONG).show();
                EventBus.getDefault().post(new UserEvent(user));
                SharedPreferencesHelper.setParam(getApplicationContext(),SharedPreferencesHelper.USER,"id",user.getId());
                SharedPreferencesHelper.setParam(getApplicationContext(),SharedPreferencesHelper.USER,"username",user.getUsername());
                SharedPreferencesHelper.setParam(getApplicationContext(),SharedPreferencesHelper.USER,"password",user.getPassword());
                SharedPreferencesHelper.setParam(getApplicationContext(),SharedPreferencesHelper.USER,"img",user.getImg());
                SharedPreferencesHelper.setParam(getApplicationContext(),SharedPreferencesHelper.USER,"token",user.getToken());
                LoginActivity.this.finish();
            }else {
                Toast.makeText(getApplicationContext(),"error code:"+errCode+",error msg:"+json.get("errMsg").getAsString(),Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onAfter() {
            super.onAfter();
            showProgress(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}

