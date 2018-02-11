package io.android.bisiparis

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import io.android.bisiparis.singelton.PersonalNameSingleton

class LoginActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener, CompoundButton.OnCheckedChangeListener {


    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(this) }

    private val chkRememberMe by lazy { findViewById<CheckBox>(R.id.activity_login_chkRememberMe) }
    private val edtName by lazy { findViewById<EditText>(R.id.activity_login_edtName) }
    private val edtPassword by lazy { findViewById<EditText>(R.id.activity_login_edtPassword) }
    private val btnLogin by lazy { findViewById<Button>(R.id.activity_login_btnLogin) }


    private var firstTimeFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val textWatcher = object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {
                if (edtName.text.trim().isNotEmpty() && edtPassword.text.trim().isNotEmpty()) {
                    btnLogin.isEnabled = true
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.e("Text", p0.toString())
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.e("Text", p0.toString())
            }
        }
        edtName.addTextChangedListener(textWatcher)
        edtPassword.addTextChangedListener(textWatcher)

        initEvent()

    }

    private fun initEvent() {

        chkRememberMe.setOnCheckedChangeListener(this)
        val customSharedPreference = getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        if (customSharedPreference.getString("RememberMe", "") != "") {
            chkRememberMe.isChecked = true
            edtName.setText(customSharedPreference.getString("RememberMe", ""))
            edtPassword.setText(customSharedPreference.getString("RememberMe", ""))
        } else {
            firstTimeFlag = false
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        btnLogin.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra("PersonalName", PersonalNameSingleton.personalName.toString())
            startActivity(intent)
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

        if (!firstTimeFlag) {

            val customSharedPreference = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val editor = customSharedPreference.edit()

            customSharedPreference.getInt("key", 2)

            if (isChecked) {

                editor.putString("RememberMe", edtName.text.toString())
                editor.putString("RememberMe", edtPassword.text.toString())

            } else {
                editor.putString("RememberMe", "")
            }

            editor.apply()

        } else {
            firstTimeFlag = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}
