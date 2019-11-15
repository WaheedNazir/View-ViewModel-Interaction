package mvvm.samplelogin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.waheed.mvvmpoc.utils.KeyboardUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var viewmodel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewmodel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        initObservables()

        btn_continue.setOnClickListener {
            KeyboardUtils.hideKeyboard(this)
            viewmodel?.login()
        }

        class CustomTextWatcher(private val view: View) : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                when {
                    view.id == R.id.email -> {
                        viewmodel?.setEmail(s.toString())
                    }
                    view.id == R.id.password -> {
                        viewmodel?.setPassword(s.toString())
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?,
                                           start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?,
                                       start: Int, before: Int, count: Int) {
                when {
                    view.id == R.id.email -> {
                        viewmodel?.onEmailChanged(s.toString())
                    }
                    view.id == R.id.password -> {
                        viewmodel?.onPasswordChanged(s.toString())
                    }
                }
            }
        }

        email.addTextChangedListener(CustomTextWatcher(email))
        password.addTextChangedListener(CustomTextWatcher(password))
    }

    private fun initObservables() {

        viewmodel?.isButtonSelected()?.observe(this, Observer<Boolean> {
            btn_continue.isEnabled = it
        })


        viewmodel?.isProgressDialog()?.observe(this, Observer<Boolean> {
            when {
                it == true -> {
                    progress_circular.visibility = View.VISIBLE
                }
                it == false -> {
                    progress_circular.visibility = View.GONE
                }
            }
        })


        viewmodel?.userLogin()?.observe(this, Observer { user ->
            Toast.makeText(this, "Welcome, ${user?.name}, ${user?.personal_image}", Toast.LENGTH_LONG).show()
        })


    }

}
