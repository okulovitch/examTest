package com.exam.com.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.exam.com.R;
import com.exam.com.helpers.InputValidation;
import com.exam.com.model.User;
import com.exam.com.sql.DatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by Ge62 on 06.01.2018.
 */
public class RegisterActivity  extends AppCompatActivity  implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;
    private FirebaseAuth mAuth;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private DatabaseReference mDatabase;
    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
      //  firbaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference();
        initViews();
        initListeners();
        initObjects();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);

    }

    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
               // addNewPersonFireBase();
                String email=textInputEditTextEmail.getText().toString().trim();
                String password=textInputEditTextPassword.getText().toString().trim();
                registration(email,password);
                break;
            //String unsecurepassword=textInputEditTextPassword.getText().toString();
          //  String securepassword = md5(unsecurepassword );
            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }
    public void registration (final String email , final String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                    FirebaseUser user1;
                    user1 = mAuth.getCurrentUser();
                    String name=textInputEditTextName.getText().toString().trim();

                assert user1 != null;
                UserInformation u = new UserInformation(user1.getEmail());
                    mDatabase.child("users").child(user1.getUid()).setValue(u);
                    Toast.makeText(RegisterActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                    addNewPersonFireBase();


            }
        });
    }
    private void addNewPersonFireBase(){
        //fireBase
        String name=textInputEditTextName.getText().toString().trim();
        String email=textInputEditTextEmail.getText().toString().trim();
        String password=textInputEditTextPassword.getText().toString().trim();
        String FirebaseHasPass = md5(password );
        UserInformation userInformation=new UserInformation(name,email,FirebaseHasPass);
        FirebaseUser fUser = mAuth.getCurrentUser();
        mDatabase.child("users").child(fUser.getUid()).setValue(userInformation);
    }


    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }

        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            String unsecurepassword=textInputEditTextPassword.getText().toString();
            String securepassword = md5(unsecurepassword );

            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(securepassword);





            databaseHelper.addUser(user);


            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            //emptyInputEditText();


        } else {

            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }







    }



    //Firebase start
    //final FirebaseDatabase database = FirebaseDatabase.getInstance();
   // DatabaseReference ref = database.getReference("server/saving-data/fireblog");


    private String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }




    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}
