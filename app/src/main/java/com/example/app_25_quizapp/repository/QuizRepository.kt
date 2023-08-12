package com.example.app_25_quizapp.repository

import android.provider.Settings.Global
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.app_25_quizapp.model.QuestionsList
import com.example.app_25_quizapp.retrofit.QuestionsAPI
import com.example.app_25_quizapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizRepository {
    private var questionsAPI : QuestionsAPI = RetrofitInstance().getRetrofitInstance().create(QuestionsAPI::class.java)

    fun getQuestionsFromAPI() : LiveData<QuestionsList>{
        var data = MutableLiveData<QuestionsList>()
        var questionsList : QuestionsList

        GlobalScope.launch(Dispatchers.IO) {
            var response = questionsAPI.getQuestions()

            if(response != null){
                questionsList = response.body()!!

                //no value but postValue since using GLobalScope(running in background)
                data.postValue(questionsList)
                Log.i("TAGY", "" + data.value)
            }
        }
        return data
    }
}