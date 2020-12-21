package comp3350.habittracker.Presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.habittracker.Application.Services;
import comp3350.habittracker.DomainObjects.User;
import comp3350.habittracker.Logic.HabitManager;
import comp3350.habittracker.Logic.NotesManager;
import comp3350.habittracker.Logic.UserManager;
import comp3350.habittracker.Persistence.DBUtils;
import comp3350.habittracker.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        DBUtils.copyDatabaseToDevice(getApplicationContext());
        loadManagers(); //setup all the mangers with instances to the db

        SharedPreferences sharedPreferences = getSharedPreferences("account", 0);
        String username = sharedPreferences.getString("username", ""); //get username if its stored, otherwise default to empty string
        String password = sharedPreferences.getString("password", "");

        //if username and pass are both stored, and info hasn't changed
        if(UserManager.login(username, password) == UserManager.SUCCESS){
            launchHomePage(new User(username));
        }

        configLoginButton();
        configRegisterButton();
    }

    private void loadManagers(){
        new UserManager(Services.getUserPersistence());
        new HabitManager(Services.getHabitsPersistence());
        new NotesManager(Services.getNotePersistence());
    }

    private void launchRegisterView(){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    private void launchHomePage(User user){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish(); //remove from back stack
    }

    private void configLoginButton(){
        final Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText loginUserName = findViewById(R.id.userNameLogin);
                String username = loginUserName.getText().toString();
                if(TextUtils.isEmpty(username)) {
                    loginUserName.setError("Please enter your username");
                    return;
                }

                EditText loginPassword = findViewById(R.id.passwordLogin);
                String password = loginPassword.getText().toString();
                if(TextUtils.isEmpty(password)) {
                    loginPassword.setError("Please enter your password");
                    return;
                }

                if(UserManager.login(username,password) == UserManager.SUCCESS){
                    //store username and pass
                    SharedPreferences sharedPreferences = getSharedPreferences("account", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();

                    launchHomePage(new User(username));
                }else {
                    login.setError("");
                    loginUserName.setError("Incorrect username/password");
                    loginPassword.setError("Incorrect username/password");
                }

            }
        });
    }

    private void configRegisterButton(){
        final Button register = findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                launchRegisterView();
            }
        });
    }
}
