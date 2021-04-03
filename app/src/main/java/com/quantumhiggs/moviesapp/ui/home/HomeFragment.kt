package com.quantumhiggs.moviesapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.quantumhiggs.moviesapp.R
import com.quantumhiggs.moviesapp.databinding.FragmentHomeBinding
import com.quantumhiggs.moviesapp.ui.home.adapter.NowPlayingAdapter
import com.quantumhiggs.moviesapp.ui.home.adapter.PopularAdapter
import com.quantumhiggs.moviesapp.ui.home.adapter.TopRatedAdapter
import com.quantumhiggs.moviesapp.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        //default load
        observeNowPlaying(viewModel)

        binding.btnMenu.setOnClickListener {
            val popupMenu = PopupMenu(context, it)

            // Inflating popup menu from popup_menu.xml file
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu);
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.popular -> {
                        observePopular(viewModel)
                        true
                    }
                    R.id.topRated -> {
                        observeTopRated(viewModel)
                        true
                    }
                    R.id.nowPlaying -> {
                        observeNowPlaying(viewModel)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
            popupMenu.show()
        }

        binding.rvMovies.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun observePopular(viewModel: HomeViewModel) {
        val adapter = PopularAdapter()
        binding.rvMovies.adapter = adapter
        viewModel.getPopular().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.data)
            adapter.notifyDataSetChanged()
        })
    }

    private fun observeTopRated(viewModel: HomeViewModel) {
        val adapter = TopRatedAdapter()
        binding.rvMovies.adapter = adapter
        viewModel.getTopRated().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.data)
            adapter.notifyDataSetChanged()
        })
    }

    private fun observeNowPlaying(viewModel: HomeViewModel) {
        val adapter = NowPlayingAdapter()
        binding.rvMovies.adapter = adapter
        viewModel.getNowPlaying().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.data)
            adapter.notifyDataSetChanged()
        })
    }
}