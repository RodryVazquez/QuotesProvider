package quotesprovider.intelectix.com.quotesprovider.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import quotesprovider.intelectix.com.quotesprovider.Helpers.Preferences;
import quotesprovider.intelectix.com.quotesprovider.MainActivity;
import quotesprovider.intelectix.com.quotesprovider.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubmitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSubmitLogin = (Button)findViewById(R.id.btnSubmitLogin);
        btnSubmitLogin.setOnClickListener(this);

        Preferences preferences = new Preferences(Login.this);
        if (!preferences.isFirstTime()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSubmitLogin:
                EditText userName = (EditText) findViewById(R.id.inputLogin);
                Preferences preferences = new Preferences(Login.this);
                preferences.setUserName(userName.getText().toString().trim());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
        }
    }
}
