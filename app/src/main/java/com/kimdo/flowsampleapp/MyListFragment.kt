package com.kimdo.flowsampleapp


import android.content.ClipData
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kimdo.flowsampleapp.common.Constants
import com.kimdo.flowsampleapp.databinding.FragmentListBinding
import com.kimdo.flowsampleapp.domain.model.Coin
import com.kimdo.flowsampleapp.presentation.coin_list.CoinListViewModel
import com.kimdo.flowsampleapp.presentation.coin_list.MyListAdapter
import com.kimdo.flowsampleapp.presentation.coin_list.MyListAdapter.OnItemClickListener


class MyListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var listAdapter: MyListAdapter
    private lateinit var viewModel: CoinListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = MyListAdapter(object: OnItemClickListener{
            override fun onItemClick(v: View, data: Coin, pos: Int) {


                Log.d(TAG, "onItemClick: ${data.id.toString()}")



                //.toDetail( pos.toString() )
                val action = MyListFragmentDirections.toDetail(data.id.toString())
                view.findNavController().navigate( action )


            }
        })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        viewModel = ViewModelProvider(activity as ViewModelStoreOwner )[CoinListViewModel::class.java]
        viewModel.state.observe(viewLifecycleOwner) { coinListState ->
            if( coinListState.isLoading ) {
                Log.d(TAG, "onViewCreated: loading")
            }
            if( coinListState.error.isNotEmpty())
                Log.d(TAG, "onViewCreated: loading ${coinListState.error}")
            if( coinListState.coins.size > 0 ) {
                listAdapter.resetLists( coinListState.coins )

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "MyListFragment"
    }

}