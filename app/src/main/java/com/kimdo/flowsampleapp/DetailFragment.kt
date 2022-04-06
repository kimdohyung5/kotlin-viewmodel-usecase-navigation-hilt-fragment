package com.kimdo.flowsampleapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.navArgs
import com.kimdo.flowsampleapp.databinding.FragmentDetailBinding
import com.kimdo.flowsampleapp.databinding.FragmentListBinding
import com.kimdo.flowsampleapp.presentation.coin_detail.CoinDetailViewModel
import com.kimdo.flowsampleapp.presentation.coin_list.CoinListViewModel


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel:CoinDetailViewModel

    private val arg: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(activity as ViewModelStoreOwner )[CoinDetailViewModel::class.java]
        
        viewModel.getCoin(arg.itemId)

        viewModel.state.observe(viewLifecycleOwner) { coinDetailState ->
            if( coinDetailState.isLoading ) {
                Log.d(TAG, "onViewCreated: loading")
            }
            if( coinDetailState.error.isNotEmpty())
                Log.d(TAG, "onViewCreated: loading ${coinDetailState.error}")
            if( coinDetailState.coin != null) {
                Log.d(TAG, "onViewCreated coin--> ${coinDetailState.coin}")
                binding.txtName.text = coinDetailState.coin.name
                binding.txtDesc.text = coinDetailState.coin.description
                binding.progressBar.visibility = View.GONE
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "DetailFragment"
    }

}