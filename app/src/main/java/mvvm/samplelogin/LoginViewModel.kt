package mvvm.samplelogin

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Login button state enabled/disabled LiveData
     */
    private var btnSelected: MutableLiveData<Boolean> = MutableLiveData()

    fun isButtonSelected(): LiveData<Boolean> = btnSelected

    /**
     *  Progress show/hide LiveData
     */
    private var progressDialog: MutableLiveData<Boolean> = MutableLiveData()

    fun isProgressDialog(): LiveData<Boolean> = progressDialog


    /**
     * Email LiveData
     */
    private var email: MutableLiveData<String> = MutableLiveData()

    /**
     * Password LiveData
     */
    private var password: MutableLiveData<String> = MutableLiveData()

    /**
     * User LiveData that would return from API Call
     */
    private var userLogin: MutableLiveData<User> = MutableLiveData()

    fun userLogin(): LiveData<User> = userLogin

    /**
     * Initialize all LiveData fields to default values.
     */
    init {
        btnSelected.value = false
        progressDialog.value = false
        email.value = ""
        password.value = ""
        userLogin = MutableLiveData()
    }


    /**
     * Updating Email to the Live Data and update next button staterr
     */
    fun setEmail(email: String) {
        this.email.value = email
        btnSelected.value = (Util.isEmailValid(email) && password.value!!.length >= 8)
    }

    /**
     * Updating Password to the Live Data and update next button state
     */
    fun setPassword(password: String) {
        this.password.value = password
        btnSelected.value = (Util.isEmailValid(email.value!!) && password.length >= 8)
    }

    /**
     * Click of Login button
     */
    fun login() {
        btnSelected.value = false
        progressDialog.value = true
        /**
         * TODO NOTE:
         * You can pass entered username and password here to your repository,
         * perform API Call and return response as a LiveData that can be observed
         * inside you View(Activity/fragment).
         *
         * You can get the data from these fields like this
         * email = email.value!!
         * password = password.value!!
         *
         * TODO API CALL:
        WebServiceClient.client.create(BackEndApi::class.java)
        .LOGIN(email = email.value!!, password = password.value!!)
        .enqueue(this)
         *
         * */
        Log.e("", "Email: " + email.value!! + "Password: " + password.value!!)

        /**
         * Simulating a delay for API call
         */
        object : CountDownTimer(2000, 2000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                userLogin.postValue(User("Waheed", "Nazir", "\uD83D\uDE0E"))

                btnSelected.value = true
                progressDialog.value = false
            }
        }.start()
    }
}