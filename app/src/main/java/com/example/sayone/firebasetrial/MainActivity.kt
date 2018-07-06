package com.example.sayone.firebasetrial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mAuth:FirebaseAuth =FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // this button create user
        ok_btn.setOnClickListener(){view->
            createUser(email_txt.text.toString(),pass_txt.text.toString())
        }


        // to check if the email has been verifiewd by the user
        forgot_btn.setOnClickListener({ v ->

            var email:String
            if (!email_txt.text.isEmpty()){

                email=email_txt.text.toString()
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(){task ->
                    if (task.isSuccessful){
                        Toast.makeText(applicationContext,"check email for reseting password",Toast.LENGTH_LONG).show()
                    }
                }
            }

        })

        // clicking login button to log into account
        login_ok_btn.setOnClickListener(){
            mAuth.signInWithEmailAndPassword(login_email_txt.text.toString(),login_pass_txt.text.toString())
                    .addOnCompleteListener(){task ->
                        if (task.isSuccessful){
                            val i = Intent(applicationContext,Main2Activity::class.java)
                            startActivity(i)
                        }else
                            Toast.makeText(applicationContext,"wrong email or password",Toast.LENGTH_LONG).show()

                    }

        }

    }

    // for checking if the user instance is available when the app starts
    override fun onStart() {
        super.onStart()

        val firebaseUser=mAuth.currentUser

        if (firebaseUser!=null) {

            updateUI(firebaseUser)
        }

    }

    fun updateUI(firebaseUser: FirebaseUser){
        if (firebaseUser!=null){
            Toast.makeText(applicationContext,"user already logged in",Toast.LENGTH_LONG).show()

        }
    }

    // function to just create a user in firebase
    fun createUser(emailStr:String,passStr:String){
        mAuth.createUserWithEmailAndPassword(emailStr,passStr)
               .addOnCompleteListener(this, OnCompleteListener { task->

                   if (task.isSuccessful){
                       var user = mAuth.currentUser
                       Toast.makeText(applicationContext,"created user account ",Toast.LENGTH_LONG).show()
                       if (user != null) {
                           sendVerficationEmail(user)
                       }
                   }
                   
               }).addOnFailureListener(OnFailureListener { exception ->
                    //Toast.makeText(applicationContext,"failed ${exception.message}",Toast.LENGTH_LONG).show()
                   System.out.println("failed ${exception.message}")
                })
    }

    //verify email
    fun sendVerficationEmail(user: FirebaseUser){
        user.sendEmailVerification().addOnCompleteListener(OnCompleteListener {task ->
            if (task.isSuccessful){
                Toast.makeText(this,"Verification email sent to " + user.getEmail(),Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"Failed to send verification email.",Toast.LENGTH_SHORT).show()
            }

        })
    }

}


