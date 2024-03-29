package com.wms.wms.ui.receivingDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.wms.wms.R
import com.wms.wms.databinding.FragmentReceivingDetailsBinding
import com.wms.wms.ui.receiving.ReceivingFragment
import com.wms.wms.ui.receivingDetailsCount.ReceivingDetailsCountFragment

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ReceivingDetailsFragment : Fragment() {

    private lateinit var viewModel: ReceivingDetailsViewModel
    private var _binding: FragmentReceivingDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReceivingDetailsBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // getting the recyclerview by its id
        val recyclerview = _binding!!.recyclerview

        viewModel =
            ViewModelProvider(
                this,
                ReceivingDetailsViewModelFactory()
            )[ReceivingDetailsViewModel::class.java]


        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)

        viewModel.list.observe(viewLifecycleOwner) {
            val data = ArrayList<ItemsViewModel>()
            // This loop will create 20 Views containing
            // the image with the count of view
            it.map {
                data.add(ItemsViewModel(R.drawable.ic_baseline_input_24, it.productTitle))
            }
            // This will pass the ArrayList to our Adapter
            val adapter = DetailsAdapter(data,childFragmentManager)
            // Setting the Adapter with the recyclerview
            recyclerview.adapter = adapter
//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
        }

        val receivingId = arguments?.getString("receivingId")!!
        Log.d("receivingId",receivingId)
        lifecycleScope.launchWhenCreated {
            viewModel.fetchList(
                receivingId
            )
        }
        // ArrayList of class ItemsViewModel

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}