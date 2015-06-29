package com.icemobile.framework.core.module;


import android.content.Context;

import com.icemobile.framework.core.module.Module;
import com.icemobile.framework.core.module.provider.ModuleProvider;


public abstract class BaseModule implements Module {

	private final Context context;
	private final ModuleProvider moduleProvider;

	public BaseModule(final Context context, final ModuleProvider moduleProvider) {
		this.context = context.getApplicationContext();
		this.moduleProvider = moduleProvider;
	}

	@Override
	public void stop() {

	}

	@Override
	public void start(final Context activity) {

	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void onInitialize() {

	}

	@Override
	public void afterAllModulesInitialized() {

	}

    protected final ModuleProvider getModuleProvider() {
        return moduleProvider;
    }

    protected final Context getContext() {
        return context;
    }
}
