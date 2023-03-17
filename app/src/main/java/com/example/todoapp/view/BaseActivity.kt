package com.example.todoapp.view

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity :AppCompatActivity(){
    open fun showToast(mesage:String){
        Toast.makeText(this,mesage,Toast.LENGTH_LONG).show()
    }

}