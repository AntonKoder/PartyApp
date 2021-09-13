package com.a4nt0n64r.partyapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.a4nt0n64r.partyapp.databinding.FragmentPartyBinding
import com.a4nt0n64r.partyapp.models.ui.PartyUI
import com.bumptech.glide.Glide

class PartyFragment : Fragment() {

    private var nullableBinding: FragmentPartyBinding? = null
    private val binding get() = nullableBinding!!

    private lateinit var viewModel: PartyViewModel

    private lateinit var observerOnParty: Observer<PartyUI>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nullableBinding = FragmentPartyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = PartyViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.party.observe(viewLifecycleOwner, observerOnParty)
        viewModel.getParty()
    }

    private fun setupRecyclerView() {
        val adapter = PartyAdapter()
        val recyclerView = binding.partyMembers
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        observerOnParty = Observer {
            val list = it.invites
            adapter.setData(list)
            Glide.with(this)
                .load(it.pictureUrl)
                .into(binding.partyImage)
            Glide.with(this)
                .load(it.pictureOfPartyStarter)
                .into(binding.partyStarterImage)
            binding.partyName.text = it.partyName
            binding.partyStarterName.text = it.partyStarter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.party.removeObserver(observerOnParty)
    }


}