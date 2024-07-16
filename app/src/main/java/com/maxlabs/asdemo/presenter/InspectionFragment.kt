package com.maxlabs.asdemo.presenter

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.maxlabs.asdemo.R
import com.maxlabs.asdemo.databinding.FragmentInspectionBinding
import com.maxlabs.asdemo.model.Category
import com.maxlabs.asdemo.presenter.adapter.CategoryAdapter
import com.maxlabs.asdemo.presenter.adapter.QuestionsPagerAdapter
import com.maxlabs.asdemo.presenter.viewModels.InspectionViewModel
import com.maxlabs.asdemo.presenter.viewModels.InspectionViewModelFactory
import com.maxlabs.asdemo.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InspectionFragment : Fragment() {


    @Inject
    lateinit var viewModelFactory: InspectionViewModelFactory
    private lateinit var inspectionViewModel: InspectionViewModel
    private var _binding: FragmentInspectionBinding? = null

    private val binding get() = _binding!!
    //private var totalScore: Float = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        inspectionViewModel = ViewModelProvider(this, viewModelFactory)
            .get(InspectionViewModel::class.java)
        _binding = FragmentInspectionBinding.inflate(inflater, container, false)
        // Get the category from arguments
        val category = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(Constants.CATEGORY, Category::class.java)
        } else {
            arguments?.getParcelable(Constants.CATEGORY)
        }
        Log.d("InspectionFragment", "onCreateView:${category.toString()}")

        category?.let { category ->
            binding.categoryNameTextView.text = category.name
            val adapter = QuestionsPagerAdapter(this, category.questions)
            binding.viewPager.adapter = adapter
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}