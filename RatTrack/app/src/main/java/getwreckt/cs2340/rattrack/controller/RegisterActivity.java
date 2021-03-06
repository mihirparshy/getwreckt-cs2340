package getwreckt.cs2340.rattrack.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import getwreckt.cs2340.rattrack.R;
import getwreckt.cs2340.rattrack.model.Model;
import getwreckt.cs2340.rattrack.model.User;

/**
 * Created by maya v on 9/21/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText userField;
    private EditText fullNameField;
    private TextInputEditText passField;
    private Button registerButn;
    private Button cancelButn;
    private Spinner userTypeSpinner;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataRef;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDataRef = FirebaseDatabase.getInstance().getReference();
        fullNameField = (EditText) findViewById(R.id.full_name);
        userField = (EditText) findViewById(R.id.email);
        passField = (TextInputEditText) findViewById(R.id.password);
        userTypeSpinner = (Spinner) findViewById(R.id.user_type_spinner);

        //Set up the adapter to display the user types in the spinner

        ArrayList<String> userTypes = new ArrayList<String>();
        userTypes.add("User");
        userTypes.add("Admin");

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

        onRegPressed();
        onCancelPressed();
    }

    /**
     * Adds a new user.
     */
    public void onRegPressed() {
        Log.d("Register", "Register new user");

        registerButn = (Button) findViewById(R.id.register);
        registerButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameField.getText().toString();
                String username = userField.getText().toString();
                String password = passField.getText().toString();
                String userType = userTypeSpinner.getSelectedItem().toString();
                User user = new User(fullName, username, userType);
                createUser(user, password);
            }
        });

    }

    /**
     * Creates a user when the user
     * @param u user who is registering
     * @param pass password of the user
     */
    public void createUser(final User u, final String pass) {
        mAuth.createUserWithEmailAndPassword(u.getUserName(), pass)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Model.setCurrentUser(u);
                            mDataRef.child("users").child(mAuth.getCurrentUser().getUid()).setValue(u);
                            updateUI(mAuth.getCurrentUser());
                        } else {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                            alertDialogBuilder.setMessage(task.getException().toString().split(": ")[1]);
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }
                });
    }

    /**
     * used to change the user app to in app activity
     * @param currUser valid registered user
     */
    public void updateUI(FirebaseUser currUser) {
        Intent toInAppScreen = new Intent(RegisterActivity.this,
                InAppActivity.class);
        startActivity(toInAppScreen);
    }

    /**
     * Cancels registration
     */
    public void onCancelPressed() {
        cancelButn = (Button) findViewById(R.id.cancel);
        cancelButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.super.finish();
            }

        });
    }

    /**
     * Checks whether {@code user} and {@code pass} are nonempty strings
     * @param user the username to check
     * @param pass the password to check
     * @return whether {@code user} and {@code pass} are not empty strings
     */
    public boolean isValidUserPass(String user, String pass) {
        return !user.equals("") && !pass.equals("");
    }
}
