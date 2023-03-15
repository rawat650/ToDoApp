package com.example.todoapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class MainActivity : AppCompatActivity(),ToDoAdapter.DeleteListner,ToDoAdapter.UpdateListner {
    lateinit var binding:ActivityMainBinding
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
              Snackbar.make(binding.rcView,"data is deleted Successfullty ",Snackbar.LENGTH_LONG)
            }


        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rcView)
        }
        val dao = TodoDataBase.getInstance(this).getDao()
        viewModel = ViewModelProvider(this,TodoFactory(TodoRepository(dao))).get(ToDoViewModel::class.java)
        binding.btnFloating.setOnClickListener {
            val intent = Intent(this,EditActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        adapter = ToDoAdapter(this,this,this)
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter

        viewModel.allNotes.observe(this, Observer {
            adapter.updateList(it as ArrayList<NoteEntity>)
        })


    }

    override fun delete(noteEntity: NoteEntity) {
        viewModel.deleteData(noteEntity)
    }

    override fun update(noteEntity: NoteEntity) {
        val intent = Intent(this,EditActivity::class.java)
        intent.putExtra("type","update")
        intent.putExtra("title",noteEntity.noteTitle)
        intent.putExtra("desc",noteEntity.noteDescription)
        startActivity(intent)

    }
}