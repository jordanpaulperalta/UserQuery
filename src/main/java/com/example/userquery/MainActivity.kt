package com.example.userquery

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var baseURL = "https://gorest.co.in/public-api/"
    lateinit var viewResults : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewResults = findViewById(R.id.viewResults)
        val nameField: EditText = findViewById(R.id.nameField)
        val statusField: EditText = findViewById(R.id.statusField)
        val requestBtn: Button = findViewById(R.id.requestBtn)
        val clearBtn: Button = findViewById(R.id.clearBtn)

        val name =  nameField.editableText
        val status = statusField.editableText

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL).build().create(ApiCall::class.java)

        requestBtn.setOnClickListener {
            viewResults.text = ""
            queryByNameAndStatus(name.toString(), status.toString(), retrofitBuilder)
        }

        clearBtn.setOnClickListener {
            viewResults.text = "Data cleared!"
        }
    }

    private fun queryByNameAndStatus(name: String, status: String, retrofitBuilder: ApiCall){
        val retrofitData = retrofitBuilder.getUserByName(name, status)
        retrofitData.enqueue(object: Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val responseData = responseBody.data
                        for (user in responseBody.data) {
                            var userDetail = " "
                            userDetail = "\n User ID: " + user.id +
                                    "\n Name: " + user.name +
                                    "\n Email: " + user.email +
                                    "\n Gender: " + user.gender +
                                    "\n Status: " + user.status + "\n" + "\n"
                            viewResults.append(userDetail)
                        }
                    } else {
                        Toast.makeText(applicationContext, "No response!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    viewResults.text = "No data found!"
                    Toast.makeText(applicationContext, "Error! Request Not Successful!", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Toast.makeText(applicationContext, "Error! Request failed!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}