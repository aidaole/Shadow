package com.aidaole.demo.host

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.aidaole.demo.host.databinding.ActivityMainBinding
import com.tencent.shadow.dynamic.host.EnterCallback

class MainActivity : Activity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.openPlugin1.setOnClickListener {
            val pluginManager = InitApplication.getPluginManager()
            binding.openPlugin1.isEnabled = false
            pluginManager.enter(
                this@MainActivity,
                1001,
                Bundle(),
                object : EnterCallback {
                    override fun onShowLoadingView(view: View) {
                        this@MainActivity.setContentView(view) //显示Manager传来的Loading页面
                    }

                    override fun onCloseLoadingView() {
                        this@MainActivity.setContentView(binding.root)
                    }

                    override fun onEnterComplete() {
                        binding.openPlugin1.isEnabled = true
                    }
                })
        }
        binding.openPlugin2.setOnClickListener {
            val pluginManager = InitApplication.getsPlugin2Manager()
            pluginManager.enter(
                this@MainActivity,
                1001,
                Bundle().apply {
                    putString("KEY_ACTIVITY_CLASSNAME", "com.aidaole.plugin2.app.MainActivity")
                    putString("pluginZipPath", "/data/local/tmp/plugin-debug2.zip")
                    putString("KEY_PLUGIN_PART_KEY", "demo-plugin2")
                },
                object : EnterCallback {
                    override fun onShowLoadingView(view: View) {
                        this@MainActivity.setContentView(view) //显示Manager传来的Loading页面
                    }

                    override fun onCloseLoadingView() {
                        this@MainActivity.setContentView(binding.root)
                    }

                    override fun onEnterComplete() {
                        binding.openPlugin2.isEnabled = true
                    }
                })
        }
    }
}