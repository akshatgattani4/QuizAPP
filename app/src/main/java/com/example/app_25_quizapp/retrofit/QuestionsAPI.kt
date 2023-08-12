package com.example.app_25_quizapp.retrofit

import com.example.app_25_quizapp.model.QuestionsList
import retrofit2.Response
import retrofit2.http.GET

interface QuestionsAPI {
    @GET("questionsapi.php")
    suspend fun getQuestions() : Response<QuestionsList>
}