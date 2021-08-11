package com.veronica.idn.quezzz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.veronica.idn.quezzz.R
import com.veronica.idn.quezzz.databinding.FragmentQuizBinding
import com.veronica.idn.quezzz.model.Quiz

class QuizFragment : Fragment() {

    private val questions: MutableList<Quiz> = mutableListOf(
        Quiz(
            text = "What is Android Jetpack?",
            answers = listOf("All of these", "Tools", "Documentation", "Libraries")
        ),
        Quiz(
            text = "What is the base class for layouts?",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")
        ),
        Quiz(
            text = "What layout do you use for complex screens?",
            answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")
        ),
        Quiz(
            text = "What do you use to push structured data into a layout?",
            answers = listOf("Data binding", "Data pushing", "Set text", "An OnClick method")
        ),
        Quiz(
            text = "What method do you use to inflate layouts in fragments?",
            answers = listOf(
                "onCreateView()",
                "onActivityCreated()",
                "onCreateLayout()",
                "onInflateLayout()"
            )
        ),
        Quiz(
            text = "What's the build system for Android?",
            answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")
        ),
        Quiz(
            text = "Which class do you use to create a vector drawable?",
            answers = listOf(
                "VectorDrawable",
                "AndroidVectorDrawable",
                "DrawableVector",
                "AndroidVector"
            )
        ),
        Quiz(
            text = "Which one of these is an Android navigation component?",
            answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")
        ),
        Quiz(
            text = "Which XML element lets you register an activity with the launcher activity?",
            answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")
        ),
        Quiz(
            text = "What do you use to mark a layout for data binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")
        )
    )
    private var questionIndex = 0
    lateinit var  currentQuestions : Quiz
    lateinit var answers: MutableList<String>
    private val numberIndicatorQuestion = Math.min((questions.size + 1)/ 2, 3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val   quizBinding = DataBindingUtil.inflate<FragmentQuizBinding>(
            inflater,
            R.layout.fragment_quiz,
            container,
            false
        )
        randomQuestion()
        quizBinding.quiz = this
        quizBinding.btnSubmit.setOnClickListener {
            view: View ->
            val checkedId = quizBinding.rgQuiz.checkedRadioButtonId
            if (-1 != checkedId){
                var answerCorrectPosition = 0
                when(checkedId){
                    R.id.rb_option_2 -> answerCorrectPosition = 1
                    R.id.rb_option_3 -> answerCorrectPosition = 2
                    R.id.rb_option_4 -> answerCorrectPosition = 3
                }
                if(answers[answerCorrectPosition] == currentQuestions.answers[0]){
                    questionIndex++
                    if (questionIndex < numberIndicatorQuestion){
                        currentQuestions = questions[questionIndex]
                        setNumberQuestion()
                        quizBinding.invalidateAll()
                    }else{
                        view.findNavController().navigate(R.id.action_quizFragment_to_wonFragment)
                    }
                }else{
                    view.findNavController().navigate(R.id.action_quizFragment_to_overFragment)
                }
            }
        }
        return quizBinding.root
    }


    private fun randomQuestion() {
        questions.shuffle()
        questionIndex = 0
        setNumberQuestion()
    }

    private fun setNumberQuestion() {
        currentQuestions = questions[questionIndex]
        answers = currentQuestions.answers.toMutableList()
        answers.shuffle()

        (activity as AppCompatActivity).supportActionBar?.title = getString(
            R.string.title_quezzz,
            questionIndex + 1,
            numberIndicatorQuestion
        )

    }

}