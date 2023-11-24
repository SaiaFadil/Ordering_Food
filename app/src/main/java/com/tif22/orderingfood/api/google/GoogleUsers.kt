package com.tif22.orderingfood.api.google
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status

class GoogleUsers(private val fragmentActivity: FragmentActivity) {

    companion object {
        const val REQUEST_CODE = 1000
    }

    private var googleApiClient: GoogleApiClient? = null
    private var signInIntent: Intent? = null
    private var account: GoogleSignInAccount? = null
    private var isAccountSelected = false

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        try {
            googleApiClient = GoogleApiClient.Builder(fragmentActivity.applicationContext)
                .enableAutoManage(fragmentActivity) { connectionResult ->
                    Toast.makeText(fragmentActivity.applicationContext, "error", Toast.LENGTH_SHORT).show()
                }
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

            signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient!!)
        } catch (ex: Throwable) {
            ex.printStackTrace()
            Toast.makeText(fragmentActivity, "Fatal Error : ${ex.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun getIntent(): Intent? {
        return signInIntent
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        isAccountSelected = false

        if (requestCode == REQUEST_CODE && data != null) {
            val result: GoogleSignInResult? = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result == null || !result.isSuccess) {
                Toast.makeText(fragmentActivity.applicationContext, "error", Toast.LENGTH_SHORT).show()
            } else {
                account = result.signInAccount
                resetLastSignIn()
                isAccountSelected = true
            }
        } else {
            Toast.makeText(fragmentActivity.applicationContext, "error", Toast.LENGTH_SHORT).show()
        }
    }

    fun getUserData(): GoogleSignInAccount? {
        return account
    }

    fun isAccountSelected(): Boolean {
        return isAccountSelected
    }

    fun resetLastSignIn() {
        if (googleApiClient != null && googleApiClient!!.isConnected) {
            Auth.GoogleSignInApi.signOut(googleApiClient!!).setResultCallback {
                // Handle result if needed
            }
        }
    }

    fun onDestroy() {
        googleApiClient?.let {
            it.stopAutoManage(fragmentActivity)
            it.disconnect()
        }
    }
}
