package com.maxlabs.asdemo.presenter.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maxlabs.asdemo.model.Question
import com.maxlabs.asdemo.presenter.QuestionFragment
import com.maxlabs.asdemo.util.Constants

class QuestionsPagerAdapter(
    fragment: Fragment,
    private val questions: List<Question>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = questions.size

    override fun createFragment(position: Int): Fragment {
        return QuestionFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.QUESTION, questions[position])
                putBoolean(Constants.ISLASTQUESTION, position == questions.size - 1)
            }
        }
    }
}
