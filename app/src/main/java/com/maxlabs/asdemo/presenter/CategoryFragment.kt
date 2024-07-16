package com.maxlabs.asdemo.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxlabs.asdemo.R
import com.maxlabs.asdemo.databinding.FragmentCategoryBinding
import com.maxlabs.asdemo.model.Category
import com.maxlabs.asdemo.model.Inspection
import com.maxlabs.asdemo.presenter.adapter.CategoryAdapter
import com.maxlabs.asdemo.presenter.viewModels.InspectionViewModel
import com.maxlabs.asdemo.presenter.viewModels.InspectionViewModelFactory
import com.maxlabs.asdemo.util.Constants
import com.maxlabs.asdemo.util.Resource
import com.maxlabs.asdemo.util.UserPreferences
import com.maxlabs.asdemo.util.UtilMethods
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: InspectionViewModelFactory
    private lateinit var inspectionViewModel: InspectionViewModel
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private var inspection: Inspection? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Initializing viewmodel
        inspectionViewModel = ViewModelProvider(this, viewModelFactory)
            .get(InspectionViewModel::class.java)
        //initializing Binding
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)

        val userPreferences = UserPreferences(requireContext())
        val isLoggingIn = arguments?.getBoolean(Constants.IS_USER_LOGGEDIN, false) ?: false

        // if new user, fetch data from api else fetch data from DB
        if (userPreferences.getUsername() == null || isLoggingIn) {
            inspectionViewModel.getInspectionFromAPI()
        } else {
            inspectionViewModel.getInspectionFromDB()
        }
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this.context)

        // Setup recycler view once the data is collected from DB or API
        inspectionViewModel.inspectionResult.observe(viewLifecycleOwner, Observer { resource ->
            if(resource.code==200){
                setupReyclerView(resource)
            } else {
                Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
            }
        })
        //Submitting the survey if it completed
        binding.submitBtn.setOnClickListener{
            val isSurveyCompleted = UtilMethods().verifyCompletionPercentage(inspection?.survey?.categories)
            if(isSurveyCompleted){
                inspection?.let { it1 -> inspectionViewModel.submitInspection(it1) }
            } else {
                Toast.makeText(context, R.string.complete_servey, Toast.LENGTH_LONG).show()
            }
        }

        // Observe the result of survey submission
        inspectionViewModel.inspectionSubmitResult.observe(viewLifecycleOwner, Observer { resource ->
            if(resource.code==200){
                Toast.makeText(context, R.string.complete_submitted, Toast.LENGTH_SHORT).show()
                logoutofApp(userPreferences)
            } else {
                Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

    /**
     * Logging out form application once the survey is submitted
     */
    private fun logoutofApp(userPreferences: UserPreferences) {
        inspectionViewModel.clearAllData()
        userPreferences.clearUserCredentials()
        val fragment = LoginFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    /**
     * Clean up the binding object as you no longer need it.
     */
    override fun onDestroyView() {
        // Clear the arguments to prevent memory leaks
        this.getArguments()?.clear();
        _binding = null
        super.onDestroyView()
    }

    /**
     * Setup the recycler view with the data
     */
    private fun setupReyclerView(data: Resource<Inspection>?) {
        val resource = data!!
        inspection = resource.data
        //binding.areaTV.text = "Area: ${resource.data?.area?.name}"
        binding.areaTV.text = getString(R.string.area_tv_messages) +  resource.data?.area?.name
        binding.inspectionTypeTV.text = getString(R.string.area_tv_messages) + resource.data?.inspectionType?.name
        // Set up adapter
        val adapter = resource.data?.let { CategoryAdapter(it,  object : CategoryAdapter.OnCategoryClickListener {
            override fun onCategoryClick(category: Category) {
                val fragment = InspectionFragment()
                val bundle = Bundle().apply {
                    putParcelable(Constants.CATEGORY, category)
                }
                fragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null).commit() // Optional: add to back stack to allow back navigation
            }
        })
        }
        binding.categoryRecyclerView.adapter = adapter
    }
}