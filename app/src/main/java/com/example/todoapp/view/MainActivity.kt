package com.example.todoapp.view

import android.content.DialogInterface
import android.content.DialogInterface.OnShowListener
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.room.NoteEntity
import com.example.todoapp.room.TodoDataBase
import com.example.todoapp.view.adapter.ToDoAdapter
import com.example.todoapp.viewmodel.ToDoViewModel
import com.example.todoapp.viewmodel.TodoFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity :BaseActivity() ,ToDoAdapter.DeleteListner,ToDoAdapter.UpdateListner {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: ToDoAdapter
    lateinit var viewModel: ToDoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deleteData = adapter.list[position]
                viewModel.deleteData(deleteData)
                Snackbar.make(
                    binding.rcView,
                    "data is deleted Successfullty ",
                    Snackbar.LENGTH_LONG
                )
            }


        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rcView)
        }
        val dao = TodoDataBase.getInstance(this).getDao()
        viewModel =
            ViewModelProvider(this, TodoFactory(TodoRepository(dao))).get(ToDoViewModel::class.java)
        binding.btnFloating.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
//            this.finish()
        }
        adapter = ToDoAdapter(this, this, this)
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter

        viewModel.allNotes.observe(this, Observer {
            adapter.updateList(it as ArrayList<NoteEntity>)
        })


    }

    override fun delete(noteEntity: NoteEntity) {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Delete File")

        //set message for alert dialog
        builder.setMessage("Do You Want To Delete ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            viewModel.deleteData(noteEntity)
            Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
        }

        //performing cancel action
        builder.setNeutralButton("Cancel") { dialogInterface, which ->
            showToast( "clicked cancel\n operation cancel",)
        }

        builder.setNegativeButton("No") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
        }
        val alertDialog: AlertDialog = builder.create()

        alertDialog.setCancelable(false)
        alertDialog.setOnShowListener(object: OnShowListener{
            override fun onShow(p0: DialogInterface?) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN)

            }

        })
        alertDialog.show()
        }



// pass the
    override fun update(noteEntity: NoteEntity) {
        val intent = Intent(this,EditActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle",noteEntity.noteTitle)
        intent.putExtra("noteDescription",noteEntity.noteDescription)
        intent.putExtra("id",noteEntity.id)
        startActivity(intent)



    }

}