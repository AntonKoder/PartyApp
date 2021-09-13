package com.a4nt0n64r.partyapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.a4nt0n64r.partyapp.R
import com.a4nt0n64r.partyapp.models.ui.PersonUI
import com.bumptech.glide.Glide

class PartyAdapter : RecyclerView.Adapter<PartyAdapter.InviteViewHolder>() {

    private var invites = emptyList<PersonUI>()

    fun setData(list: List<PersonUI>) {
        invites = list
        notifyDataSetChanged()
    }

    class InviteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = itemView.findViewById<TextView>(R.id.name)
        val image = itemView.findViewById<ImageView>(R.id.person_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InviteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.member_item, parent, false)
        return InviteViewHolder(view)
    }

    override fun onBindViewHolder(holder: InviteViewHolder, position: Int) {
        holder.name.text = invites[position].name
        Glide.with(holder.image.context)
            .load(invites[position].picture)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return invites.size
    }
}
