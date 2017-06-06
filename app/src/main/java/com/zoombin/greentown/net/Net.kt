package com.zoombin.greentown.net

import android.util.Log
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.HttpMethod
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * Created by gejw on 2017/5/21.
 */

object Net {

    private val TAG = "NET"

    private val BaseURL = "http://112.124.98.9:3004/api/"

    fun get(url: String,
            params: HashMap<String, Any>,
            success: (String) -> Unit,
            failure: (String?) -> Unit) {
        request(HttpMethod.GET, url, params, success, failure)
    }

    fun post(url: String,
            params: HashMap<String, Any>,
            success: (String) -> Unit,
            failure: (String?) -> Unit) {
        request(HttpMethod.POST, url, params, success, failure)
    }

    fun put(url: String,
            params: HashMap<String, Any>,
            success: (String) -> Unit,
            failure: (String?) -> Unit) {
        request(HttpMethod.PUT, url, params, success, failure)
    }

    fun request(type: HttpMethod,
                url: String,
                params: HashMap<String, Any>,
                success: (String) -> Unit,
                failure: (String?) -> Unit) {
        val requestParams = RequestParams(BaseURL + url)
        for ((key, value) in params) {
            requestParams.addParameter(key, value)
        }
        Log.e(TAG, "$type $url params $params")
        x.http().request(type, requestParams, object : Callback.CommonCallback<String> {
            override fun onSuccess(s: String) {
                Log.e(TAG, "$type $url onSuccess $s")
                if (JSONObject(s).getInt("error") == 0) {
                    success(s)
                } else {
                    failure(JSONObject(s).getString("msg"))
                }
            }

            override fun onError(throwable: Throwable, b: Boolean) {
                Log.e(TAG, "$type $url onError $throwable")
                failure(throwable.message)
            }

            override fun onCancelled(e: Callback.CancelledException) {
                Log.e(TAG, "$type $url onCancelled $e")
            }

            override fun onFinished() {
                Log.e(TAG, "$type $url onFinished")
            }
        })
    }
}
