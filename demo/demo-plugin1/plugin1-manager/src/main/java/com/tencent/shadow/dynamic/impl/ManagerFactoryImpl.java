package com.tencent.shadow.dynamic.impl;

import android.content.Context;

import com.tencent.shadow.dynamic.host.ManagerFactory;
import com.tencent.shadow.dynamic.host.PluginManagerImpl;
import com.aidaole.demo.plugin1.manager.Plugin1Manager;

/**
 * 此类包名及类名固定
 */
public final class ManagerFactoryImpl implements ManagerFactory {
    @Override
    public PluginManagerImpl buildManager(Context context) {
        return new Plugin1Manager(context);
    }
}
