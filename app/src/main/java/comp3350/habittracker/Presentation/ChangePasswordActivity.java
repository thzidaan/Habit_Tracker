package comp3350.habittracker.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Logic.UserManager;
import comp3350.habittracker.R;

public class ChangePasswordActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        configButton();
    }

    private void configButton(){
        Button button = findViewById(R.id.changeButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText passwordEdit = findViewById(R.id.newPassword);
                String password = passwordEdit.getText().toString();
                if(TextUtils.isEmpty(password)) {
                    passwordEdit.setError("Please enter a password");
                    return;
                }else if(password.length() > 20){
                    passwordEdit.setError("Max length of password is 20! Current password is over that limit");
                    return;
                }

                UserManager.changePassword(user.getUsername(), password);
                finish();
                //Intent intent = new Intent();
            }
        });
    }

}
