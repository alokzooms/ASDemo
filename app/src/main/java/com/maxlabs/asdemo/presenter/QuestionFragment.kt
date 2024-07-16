package com.maxlabs.asdemo.presenter

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.maxlabs.asdemo.R
import com.maxlabs.asdemo.databinding.FragmentQuestionBinding
import com.maxlabs.asdemo.model.Question
import com.maxlabs.asdemo.presenter.viewModels.InspectionViewModel
import com.maxlabs.asdemo.presenter.viewModels.InspectionViewModelFactory
import com.maxlabs.asdemo.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuestionFragment : Fragment() {

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: InspectionViewModelFactory
    private lateinit var inspectionViewModel: InspectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        inspectionViewModel = ViewModelProvider(this, viewModelFactory)
            .get(InspectionViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val question = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(Constants.QUESTION, Question::class.java)

        } else {
            arguments?.getParcelable<Question>(Constants.QUESTION)
        }
        var isLastQuestion = arguments?.getBoolean(Constants.ISLASTQUESTION, false) ?: false


        question?.let {
            binding.questionTextView.text = it.name
            // Add radio buttons for each answer choice
            it.answerChoices.forEach { answerChoice ->
                val radioButton = RadioButton(context).apply {
                    text = answerChoice.name
                    textSize = 16f
                    id = answerChoice.id
                }
                binding.answerChoicesRadioGroup.addView(radioButton)
            }

            // Set the selected radio button if any
            if (it.selectedAnswerChoiceId != 0) {
                binding.answerChoicesRadioGroup.check(it.selectedAnswerChoiceId)
            }

             //Handle radio button selection
            binding.answerChoicesRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                inspectionViewModel.updateSelectedAnswer(it.id, checkedId)
            }
            binding.nextButton.text = if (isLastQuestion) getString(R.string.finish) else getString(R.string.next)
            // Handle Next button click
            binding.nextButton.setOnClickListener {
                val selectedId = binding.answerChoicesRadioGroup.checkedRadioButtonId

                if (selectedId != -1 ) {
                    if (isLastQuestion) {
                        activity?.supportFragmentManager?.popBackStack()
                    } else {
                        navigateToNextQuestion()
                    }
                } else {
                    // Handle case when no radio button is selected
                    Toast.makeText(activity, getString(R.string.select_ans), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToNextQuestion() {
        (activity?.findViewById<ViewPager2>(R.id.viewPager))?.let {
            val currentItem = it.currentItem
            if (currentItem < (it.adapter?.itemCount ?: 0) - 1) {
                it.setCurrentItem(currentItem + 1, true)
                //binding.nextButton.visibility = View.VISIBLE
            } else {
                //binding.nextButton.text = getString(R.string.finish)
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }
}
