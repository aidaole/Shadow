package com.aidaole.plugin2.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;

import com.tencent.shadow.core.manager.installplugin.InstalledPlugin;
import com.tencent.shadow.dynamic.host.EnterCallback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Plugin2Manager extends FastPluginManager {
    private static final String TAG = "Plugin2Manager";

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Context mCurrentContext;

    public Plugin2Manager(Context context) {
        super(context);
        mCurrentContext = context;
    }

    /**
     * @return PluginManager实现的别名，用于区分不同PluginManager实现的数据存储路径
     */
    @Override
    protected String getName() {
        return "plugin2-manager";
    }

    /**
     * @return 宿主中注册的PluginProcessService实现的类名
     */
    @Override
    protected String getPluginProcessServiceName(String partKey) {
        return "com.aidaole.demo.host.MainPluginProcessService2";
    }

    @Override
    public void enter(final Context context, long fromId, Bundle bundle, final EnterCallback callback) {
        if (fromId == Constant.FROM_ID_NOOP) {
            //do nothing.
        } else if (fromId == Constant.FROM_ID_START_ACTIVITY) {
            onStartActivity(context, bundle, callback);
        } else if (fromId == Constant.FROM_ID_CLOSE) {
            close();
        } else if (fromId == Constant.FROM_ID_LOAD_VIEW_TO_HOST) {
            loadViewToHost(context, bundle);
        } else {
            throw new IllegalArgumentException("不认识的fromId==" + fromId);
        }
    }

    private void loadViewToHost(final Context context, Bundle bundle) {
        Intent pluginIntent = new Intent();
        pluginIntent.setClassName(
                context.getPackageName(),
                "com.tencent.shadow.sample.plugin.app.lib.usecases.service.HostAddPluginViewService"
        );
        pluginIntent.putExtras(bundle);
        try {
            mPluginLoader.startPluginService(pluginIntent);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void onStartActivity(final Context context, Bundle bundle, final EnterCallback callback) {
        final String pluginZipPath = bundle.getString(Constant.KEY_PLUGIN_ZIP_PATH);
        final String partKey = bundle.getString(Constant.KEY_PLUGIN_PART_KEY);
        final String className = bundle.getString(Constant.KEY_ACTIVITY_CLASSNAME);
        if (className == null) {
            throw new NullPointerException("className == null");
        }
        final Bundle extras = bundle.getBundle(Constant.KEY_EXTRAS);

        android.util.Log.d(TAG, "onStartActivity: "+pluginZipPath);
        android.util.Log.d(TAG, "onStartActivity: "+partKey);
        android.util.Log.d(TAG, "onStartActivity: "+className);

        if (callback != null) {
            final View view = LayoutInflater.from(mCurrentContext).inflate(R.layout.activity_load_plugin, null);
            callback.onShowLoadingView(view);
        }

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    InstalledPlugin installedPlugin = installPlugin(pluginZipPath, null, true);

                    loadPlugin(installedPlugin.UUID, partKey);
                    callApplicationOnCreate(partKey);
//                    loadPlugin(installedPlugin.UUID, Constant.PART_KEY_PLUGIN_MAIN_APP);
//                    callApplicationOnCreate(Constant.PART_KEY_PLUGIN_MAIN_APP);

                    Intent pluginIntent = new Intent();
                    pluginIntent.setClassName(
                            context.getPackageName(),
                            className
                    );
                    if (extras != null) {
                        pluginIntent.replaceExtras(extras);
                    }
                    Intent intent = mPluginLoader.convertActivityIntent(pluginIntent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mPluginLoader.startActivityInPluginProcess(intent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (callback != null) {
                    callback.onCloseLoadingView();
                }
            }
        });
    }
}
