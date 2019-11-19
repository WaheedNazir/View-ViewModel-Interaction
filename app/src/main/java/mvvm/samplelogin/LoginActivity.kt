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

    /**
     * Instance of LoginViewModel
     */
    private lateinit var loginViewModel: LoginViewModel


    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /**
         * Initialize view model
         */
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        initObservables()

        btn_continue.setOnClickListener {
            KeyboardUtils.hideKeyboard(this)
            loginViewModel.login()
        }

        /**
         * Class to Handle Text Change Listener For Email and Password Field
         */
        class CustomTextWatcher(private val view: View) : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?,
                                           start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?,
                                       start: Int, before: Int, count: Int) {
                when {
                    view.id == R.id.email -> {
                        loginViewModel.setEmail(s.toString())
                    }
                    view.id == R.id.password -> {
                        loginViewModel.setPassword(s.toString())
                    }
                }
            }
        }

        /**
         * Attach TextWatcher Listener
         */
        email.addTextChangedListener(CustomTextWatcher(email))
        password.addTextChangedListener(CustomTextWatcher(password))
    }


    /**
     * Start Listening Live Data Observers
     */
    private fun initObservables() {

        /**
         * Login button state update, Observer
         */
        loginViewModel.isButtonSelected().observe(this, Observer<Boolean> {
            btn_continue.isEnabled = it
        })


        /**
         * Progress dialog observer
         */
        loginViewModel.isProgressDialog().observe(this, Observer<Boolean> {
            when (it) {
                true -> {
                    progress_circular.visibility = View.VISIBLE
                }
                false -> {
                    progress_circular.visibility = View.GONE
                }
            }
        })


        /**
         * Observing dummy API Call data
         */
        loginViewModel.userLogin().observe(this, Observer { user ->
            Toast.makeText(this,
                    "Welcome, ${user?.name}, ${user?.personal_image}",
                    Toast.LENGTH_LONG).show()
        })

    }

}
