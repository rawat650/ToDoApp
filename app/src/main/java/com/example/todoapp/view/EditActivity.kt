package com.example.todoapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.room.util.findColumnIndexBySuffix
import com.example.todoapp.databinding.ActivityEditBinding
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.room.NoteEntity
import com.example.todoapp.room.TodoDataBase
import com.example.todoapp.viewmodel.ToDoViewModel
import com.example.todoapp.viewmodel.TodoFactory

class EditActivity : BaseActivity() {
    lateinit var binding: ActivityEditBinding
    lateinit var viewModel: ToDoViewModel
    var noteId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dao = TodoDataBase.getInstance(this).getDao()
        viewModel =
            ViewModelProvider(this, TodoFactory(TodoRepository(dao))).get(ToDoViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
             noteId = intent.getIntExtra("id",-1)

            binding.btnSave.text = "Update Note"
            binding.etText.setText(noteTitle)
            binding.etDesc.setText(noteDescription)
        } else {
            binding.btnSave.text = "Save Note"
        }

        binding.btnSave.setOnClickListener {
            val noteTitle = binding.etText.text.toString()
            val noteDescription = binding.etDesc.text.toString()
            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val updatedNote = NoteEntity(noteTitle, noteDescription)
                    updatedNote.id = noteId
                    viewModel.updateData(updatedNote)
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    showToast("Note Updated..")

                }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    viewModel.insertData(NoteEntity(noteTitle, noteDescription))
                    startActivity(Intent(applicationContext, MainActivity::class.java))
//                    finish()

                   showToast("data Added")
                }
                else{
                    showToast("field cannot be empty")
                }
            }
//finish()

        }


    }
}

/*       val  todoType =intent.getStringExtra("datatype")
       Log.d("inser",todoType.toString())

       if(todoType.equals("Edit")){
           val editTitle = intent.getStringExtra("title")
           val editDesc = intent.getStringExtra("desc")
           binding.etText.setText(editTitle)
           binding.etDesc.setText(editDesc)
           binding.btnSave.setText("Update ")
       }
       else{
           binding.btnSave.setText("save ")
       }

       binding.btnSave.setOnClickListener {
           val editTitle = binding.etText.text.toString()
           val editDesc = binding.etDesc.text.toString()

           if(todoType.equals("Edit")){
               if(editTitle.isNotEmpty() && editDesc.isNotEmpty()){
                   val updatedNote = NoteEntity(editTitle,editDesc)
                   viewModel.updateData(updatedNote)
                   Toast.makeText(this,"data  is updated",Toast.LENGTH_LONG).show()
               }
           }
           else{
               viewModel.insertData(NoteEntity(binding.etText.text.toString(),binding.etDesc.text.toString()))
               Toast.makeText(this,"data is added",Toast.LENGTH_LONG).show()

           }
           val intent = Intent(this,MainActivity::class.java)
           startActivity(intent)
           this.finish()


}*/




