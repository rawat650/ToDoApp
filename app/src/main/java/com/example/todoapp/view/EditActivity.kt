package com.example.todoapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.databinding.ActivityEditBinding
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.room.NoteEntity
import com.example.todoapp.room.TodoDataBase
import com.example.todoapp.viewmodel.ToDoViewModel
import com.example.todoapp.viewmodel.TodoFactory

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    lateinit var viewModel: ToDoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dao = TodoDataBase.getInstance(this).getDao()
        viewModel = ViewModelProvider(this, TodoFactory(TodoRepository(dao))).get(ToDoViewModel::class.java)

        val todoUpdate = intent.getStringExtra("update")
        if(todoUpdate =="update"){
            val title = intent.getStringExtra("title")
            val desc = intent.getStringExtra("desc")
            binding.etText.setText(title)
            binding.etDesc.setText(desc)
            binding.btnSave.setText("Update ")
        }
        else{
            binding.btnSave.setText("save ")
        }

        binding.btnSave.setOnClickListener {

            if(todoUpdate == "edit"){
                if(binding.etText.text.isNotEmpty() && binding.etDesc.text.toString().isNotEmpty()){
                    viewModel.updateData(NoteEntity(binding.etText.text.toString(),binding.etDesc.text.toString()))
                    Toast.makeText(this,"toast is updated",Toast.LENGTH_LONG).show()
                }
            }
            else{
                viewModel.insertData(NoteEntity(binding.etText.text.toString(),binding.etDesc.text.toString()))
                Toast.makeText(this,"data is added",Toast.LENGTH_LONG).show()

            }
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            this.finish()

}


    }
}