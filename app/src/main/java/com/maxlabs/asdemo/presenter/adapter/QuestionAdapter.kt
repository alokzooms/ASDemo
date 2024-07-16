package com.maxlabs.asdemo.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.maxlabs.asdemo.databinding.QuestionItemBinding
import com.maxlabs.asdemo.model.Question

class QuestionAdapter(private val questions: List<Question>, private val onScoreChanged: (Float) -> Unit) :
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(val binding: QuestionItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = QuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.binding.questionName.text = question.name
        holder.binding.answerChoicesRadioGroup.removeAllViews()

        question.answerChoices.forEach { answerChoice ->
            val radioButton = RadioButton(holder.itemView.context)
            radioButton.text = answerChoice.name
            radioButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onScoreChanged(answerChoice.score)
                }
            }
            holder.binding.answerChoicesRadioGroup.addView(radioButton)
        }
    }

    override fun getItemCount() = questions.size
}
