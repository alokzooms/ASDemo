package com.maxlabs.asdemo.presenter.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxlabs.asdemo.databinding.CategoryCardItemBinding
import com.maxlabs.asdemo.model.Category
import com.maxlabs.asdemo.model.Inspection

class CategoryAdapter(private val inspection: Inspection,  private val clickListener: OnCategoryClickListener) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

        private val categories: List<Category> = inspection.survey.categories
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)

    }

    override fun getItemCount() = categories.size

    inner class CategoryViewHolder(val binding: CategoryCardItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category: Category){
            // Calculate completion percentage
            val totalQuestions = category.questions.size
            val answeredQuestions = category.questions.count { it.selectedAnswerChoiceId != 0 }
            val completionPercentage = if (totalQuestions > 0) (answeredQuestions * 100 / totalQuestions) else 0
            binding.categoryNameTextView.text = category.name
            binding.completionProgressBar.progress = completionPercentage
            binding.completionPercentageTextView.text = "$completionPercentage% completed"
            binding.root.setOnClickListener {
                clickListener.onCategoryClick(category)
            }
        }
    }
    interface OnCategoryClickListener {
        fun onCategoryClick(category: Category)
    }
}
