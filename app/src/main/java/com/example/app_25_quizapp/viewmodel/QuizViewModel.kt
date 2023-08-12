package com.example.app_25_quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.app_25_quizapp.model.QuestionsList
import com.example.app_25_quizapp.repository.QuizRepository

class QuizViewModel : ViewModel() {
    private var repository : QuizRepository = QuizRepository()

    private var questionsLiveData: LiveData<QuestionsList> = repository.getQuestionsFromAPI()

    fun getQuestionsLiveData() : LiveData<QuestionsList> {
        return questionsLiveData
    }
}