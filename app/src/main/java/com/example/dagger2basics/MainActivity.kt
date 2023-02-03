package com.example.dagger2basics

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.dagger2basics.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var sharedPreferenceComponent: SharedPreferenceComponent? = null

    // To avoid 'private field injection' error in Dagger we use @set:Inject
    @set:Inject
    var sharedPreferences: SharedPreferences? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.btSubmit.setOnClickListener {
            if (binding.etInput.text.isNullOrEmpty()) {
                Toast.makeText(this,"Please enter any input.",Toast.LENGTH_SHORT).show()
            }
            else {
                val editor = sharedPreferences!!.edit()
                editor.putString("inputData", binding.etInput.getText().toString().trim())
                editor.apply()

                // Hide keyboard automatically
                val mgr: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                mgr.hideSoftInputFromWindow(binding.etInput.getWindowToken(), 0)

                Toast.makeText(this,"Submitted successfully..",Toast.LENGTH_SHORT).show()

                binding.tvText.text = "Entered data is : " + sharedPreferences!!.getString("inputData", "")
            }
        }

        // Here we are binding dagger to our application
        sharedPreferenceComponent = DaggerSharedPreferenceComponent.builder()
            .sharedPreferenceModule(SharedPreferenceModule(this)).build()

        // injecting the shared preference dependent object
        sharedPreferenceComponent!!.inject(this@MainActivity);


    }
}