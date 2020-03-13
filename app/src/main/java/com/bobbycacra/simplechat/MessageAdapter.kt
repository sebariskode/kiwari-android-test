package com.bobbycacra.simplechat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bobbycacra.simplechat.model.Message
import kotlinx.android.synthetic.main.item_message.view.*

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    var messages: MutableList<Message> = mutableListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val from: TextView = view.from
        val date: TextView = view.date
        val textContent: TextView = view.text_content
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        return MessageAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false))
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        var message: Message = messages[position]

        holder.from.text = message.from
        holder.date.text = message.date
        holder.textContent.text = message.textContent
    }
}