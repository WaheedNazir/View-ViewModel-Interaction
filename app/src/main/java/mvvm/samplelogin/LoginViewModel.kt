package mvvm.samplelogin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import android.os.CountDownTimer


class LoginViewModel(application: Application) : AndroidViewModel(application) {


    private var btnSelected: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false) // You can initialize it here or in init bloc as well
    fun isButtonSelected(): LiveData<Boolean> = btnSelected


    private var progressDialog: MutableLiveData<Boolean>
    fun isProgressDialog(): LiveData<Boolean> = progressDialog

    private var email: MutableLiveData<String>
    private var password: MutableLiveData<String>


    private var userLogin: MutableLiveData<User>
    fun userLogin(): LiveData<User> = userLogin

    init {
        progressDialog = MutableLiveData<Boolean>()
        email = MutableLiveData("")
        password = MutableLiveData<String>("") // Type <String> is optional it is type inference like I've done this for password field
        userLogin = MutableLiveData<User>()
    }

    fun setEmail(email: String) {
        this.email.value = email
    }


    fun setPassword(password: String) {
        this.password.value = password
    }

    fun onEmailChanged(email: String) {
        btnSelected.postValue((Util.isEmailValid(email) && password.value!!.length >= 8))
    }

    fun onPasswordChanged(password: String) {
        btnSelected.postValue((Util.isEmailValid(email.value!!) && password.length >= 8))
    }


    fun login() {
        btnSelected.postValue(false)
        progressDialog.postValue(true)
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

                btnSelected.postValue(true)
                progressDialog.postValue(false)
            }
        }.start()
    }

}