package com.dev.railian.mvvmtemplate.architecture

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {
    abstract val layoutId: Int
    abstract val viewModel: T

    open fun onActivityCreated(savedInstanceState: Bundle?) {}

    open fun subscribeOnLiveData() {
        viewModel.messages.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        onActivityCreated(savedInstanceState)
        subscribeOnLiveData()
/*
            For fullscreen activities:

            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                statusBarColor = Color.TRANSPARENT
            }
 */
    }
}