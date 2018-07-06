package com.example.sayone.firebasetrial

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var User = mAuth.currentUser

        // checking if the user has verified email
        if (User!=null){

            //e FirebaseUser object is cached within an app session, so if you want to check on the verification state of a user
            User.reload()

            if(User.isEmailVerified()){
                status_txt.text="verified ${User.uid}"
            }
            else{
                status_txt.text="Not verified"
            }
        }
    }
}
