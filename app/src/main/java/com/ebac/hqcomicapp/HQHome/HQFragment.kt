package com.ebac.hqcomicapp.HQHome

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.ebac.hqcomicapp.HQViewModel
import com.ebac.hqcomicapp.R
import com.ebac.hqcomicapp.databinding.FragmentItemListBinding

/**
 * A fragment representing a list of Items.
 */
class HQFragment : Fragment(), HQItemListener {

    private lateinit var adapter: MyhqRecyclerViewAdapter

    //pega a view model através do gráfico de navegação
    private val viewModel by navGraphViewModels<HQViewModel>(R.id.hq_graph) {defaultViewModelProviderFactory}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentItemListBinding.inflate(inflater)
        val view = binding.root as RecyclerView

        adapter = MyhqRecyclerViewAdapter(this)

        view.apply {
            this.adapter = this@HQFragment.adapter
            this.layoutManager = LinearLayoutManager(context)
        }
        initObservers()

        return view
    }

    private fun initObservers(){
        viewModel.hqListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
        })
        viewModel.navigationToDetailLiveData.observe(viewLifecycleOwner, Observer {
            val action = HQFragmentDirections.actionHqFragmentToHQDetailsFragment()
            findNavController().navigate(action)
        })
    }


    //implementa interface criada no Adapter
    override fun onItemSelected(position: Int) {
        viewModel.onHQSelected(position)
        //findNavController().navigate(R.id.HQDetailsFragment)
    }
}