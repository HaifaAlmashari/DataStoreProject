package com.example.datastoreproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var userManager: UserManager
    var age = 0
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get reference to our userManager class
        userManager = UserManager(this)

        buttonSave()

        observeData()

    }

    private fun observeData() {

        //Updates age
        userManager.userAgeFlow.asLiveData().observe(this, {
            age = it
            tv_age.text = it.toString()
        })

        //Updates name
        userManager.userNameFlow.asLiveData().observe(this, {
            name = it
            tv_name.text = it.toString()
        })
    }

    private fun buttonSave() {

        //Gets the user input and saves it
        btn_save.setOnClickListener {
            name = et_name.text.toString()
            age = et_age.text.toString().toInt()

            //Stores the values
            // Why GlobalScope is used here why not Coroutine Scope instead??
            //I don't know
            GlobalScope.launch {
                userManager.storeUser(age, name)
            }
        }
    }

}