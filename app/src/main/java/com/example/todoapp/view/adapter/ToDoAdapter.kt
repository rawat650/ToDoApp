package com.example.todoapp.view.adapter

import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemViewBinding
import com.example.todoapp.room.NoteEntity

class ToDoAdapter(val context: Context,val deleteListner: DeleteListner,val updateListner: UpdateListner) :RecyclerView.Adapter<ToDoAdapter.TodoViewHolder>(){
    val list :ArrayList<NoteEntity> = ArrayList()

    fun updateList(newList:ArrayList<NoteEntity>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()

    }
    class TodoViewHolder(binding:ItemViewBinding):RecyclerView.ViewHolder(binding.root){
        val txtTitle = binding.txtTitle
        val txtDesc = binding.txtDesc
        val imgDelete = binding.imgDelete


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false
        ))


    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        with(holder){
            with(list[position]){
             txtTitle.text = this.noteTitle
                txtDesc.text = this.noteDescription
                imgDelete.setOnClickListener {
                    deleteListner.delete(list.get(position))

                }
                holder.itemView.setOnClickListener {
                    updateListner.update(list.get(position))
                }

            }
        }

    }

    override fun getItemCount(): Int  = list.size

    interface DeleteListner{
        fun delete(noteEntity:NoteEntity)
    }
    interface UpdateListner{
        fun update(noteEntity: NoteEntity)
    }
}