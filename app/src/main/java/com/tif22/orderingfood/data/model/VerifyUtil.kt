package com.tif22.orderingfood.data.model

import android.content.Context
import java.sql.Types.NULL

class VerifyUtil(private val context: Context, model: VerifyModel? = null) {

    private val dataShared: DataShared = DataShared(context)


    init {
        if (model != null) {
            setEmail(model.email)
            setOtp(model.otp)
            setResend(model.resend)
        }
    }

    constructor(context: Context) : this(context, null)

    private fun getData(key: KEY): String {
        return (dataShared.getData(key) ?: NULL).toString()
    }

    fun getEmail(): String {
        return getData(KEY.VERIFY_EMAIL)
    }

    fun setEmail(email: String) {
        dataShared.setData(KEY.VERIFY_EMAIL, email)
    }

    fun getOtp(): String {
        return getData(KEY.VERIFY_OTP_CODE)
    }

    fun setOtp(otp: String) {
        dataShared.setData(KEY.VERIFY_OTP_CODE, otp)
    }

    fun getType(): String {
        return getData(KEY.VERIFY_TYPE)
    }

    fun setType(type: String) {
        dataShared.setData(KEY.VERIFY_TYPE, type)
    }

    fun getDevice(): String {
        return getData(KEY.VERIFY_DEVICE)
    }

    fun setDevice(device: String) {
        dataShared.setData(KEY.VERIFY_DEVICE, device)
    }

    fun setResend(type: Int) {
        dataShared.setData(KEY.VERIFY_RESEND, type.toString())
    }

    fun getResend(): Int {
        return try {
            getData(KEY.VERIFY_RESEND).toInt()
        } catch (ex: Throwable) {
            ex.printStackTrace()
            1
        }
    }



    fun getCreated(): String {
        return getData(KEY.VERIFY_CREATED)
    }

    fun setCreated(created: String) {
        dataShared.setData(KEY.VERIFY_CREATED, created)
    }

    fun haveOtp(): Boolean {
        val have = dataShared.contains(KEY.VERIFY_EMAIL) &&
                dataShared.contains(KEY.VERIFY_CREATED) &&
                dataShared.contains(KEY.VERIFY_OTP_CODE) &&
                dataShared.contains(KEY.VERIFY_START_MILLIS) &&
                dataShared.contains(KEY.VERIFY_END_MILLIS) &&
                dataShared.contains(KEY.VERIFY_TYPE) &&
                dataShared.contains(KEY.VERIFY_DEVICE) &&
                dataShared.contains(KEY.VERIFY_RESEND)

        if (have) {
            val hash = dataShared.getData(
                KEY.VERIFY_EMAIL, KEY.VERIFY_CREATED,
                KEY.VERIFY_OTP_CODE, KEY.VERIFY_START_MILLIS,
                KEY.VERIFY_END_MILLIS, KEY.VERIFY_TYPE,
                KEY.VERIFY_DEVICE, KEY.VERIFY_RESEND
            )

            for (key in hash.keys) {
                val data = hash[key]
                if (data == null || data.isEmpty()) {
                    return false
                }
            }

            try {
                return java.lang.Long.parseLong(hash[KEY.VERIFY_END_MILLIS] ?: "0") > System.currentTimeMillis()
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
        return false
    }




    fun removeOtp() {
        dataShared.setNullData(KEY.VERIFY_OTP_CODE)
        dataShared.setNullData(KEY.VERIFY_EMAIL)
        dataShared.setNullData(KEY.VERIFY_START_MILLIS)
        dataShared.setNullData(KEY.VERIFY_END_MILLIS)
        dataShared.setNullData(KEY.VERIFY_RESEND)
        dataShared.setNullData(KEY.VERIFY_TYPE)
        dataShared.setNullData(KEY.VERIFY_DEVICE)
        dataShared.setNullData(KEY.VERIFY_CREATED)
    }
}

enum class KEY {
    VERIFY_EMAIL,
    VERIFY_OTP_CODE,
    VERIFY_TYPE,
    VERIFY_DEVICE,
    VERIFY_RESEND,
    VERIFY_CREATED,
    VERIFY_START_MILLIS,
    VERIFY_END_MILLIS
}

class DataShared(private val context: Context) {

    fun setData(key: KEY, value: String) {
        // Implement method to store data using key-value pair in SharedPreferences
    }

    fun getData(key: KEY): String? {
        // Implement method to retrieve data using key from SharedPreferences
        return ""
    }

    fun contains(key: KEY): Boolean {
        // Implement method to check if key exists in SharedPreferences
        return false
    }

    fun getData(vararg keys: KEY): HashMap<KEY, String?> {
        // Implement method to retrieve multiple data using keys from SharedPreferences
        return hashMapOf()
    }

    fun setNullData(key: KEY) {
// Implement method to set key-value pair
    }}