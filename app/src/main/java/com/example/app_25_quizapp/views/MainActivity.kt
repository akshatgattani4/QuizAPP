package com.example.app_25_quizapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.app_25_quizapp.R
import com.example.app_25_quizapp.databinding.ActivityMainBinding
import com.example.app_25_quizapp.model.Question
import com.example.app_25_quizapp.model.QuestionsList
import com.example.app_25_quizapp.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var viewModel : QuizViewModel
    lateinit var questionsList: List<Question>

    companion object{
        var result = 0
        var totalQuestions = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //Resetting
        result = 0
        totalQuestions = 0

        viewModel = ViewModelProvider(this)[QuizViewModel::class.java]

        GlobalScope.launch(Dispatchers.Main){
            viewModel.getQuestionsLiveData().observe(this@MainActivity, Observer {
                if(it.isNotEmpty()){
                    questionsList = it
                    val q1 = questionsList[0]
                    Log.i("TAGY", "This is the first qustion : $q1")

                    binding.apply {
                        question.text = q1.question
                        op1.text = q1.option1
                        op2.text = q1.option2
                        op3.text = q1.option3
                        op4.text = q1.option4

                    }
                }
            })
        }

        var i = 1
        binding.apply {
            btn.setOnClickListener(View.OnClickListener {
                val selected = radioGroup?.checkedRadioButtonId
                if(selected != -1){
                    val radioButton = findViewById<View>(selected!!) as RadioButton

                    questionsList.let {
                        if(i < it.size){
                            totalQuestions = it.size
                            if(radioButton.text.toString().equals(it[i-1].correct_option)){
                                result++
                                correct.text = "Correct Answer : $result"
                            }

                            val q1 = questionsList[i]
                            question.text = q1.question
                            op1.text = q1.option1
                            op2.text = q1.option2
                            op3.text = q1.option3
                            op4.text = q1.option4

                            if(i == it.size.minus(1)){
                                btn.text = "FINISH"
                            }

                            radioGroup.clearCheck()
                            i++
                        }else{
                            if(radioButton.text.toString().equals(it[i-1].correct_option)){
                                result++
                                correct.text = "Correct Answer : $result"
                            }else{

                            }

                            val intent = Intent(this@MainActivity, ResultActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    Toast.makeText(this@MainActivity, "Please Select One Option!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}