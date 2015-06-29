package com.icemobile.framework.core.module;

import android.content.Context;

/**
 * Use this interface when you want to plug in a module into the framework.
 * @author Qi-Wen Hu
 *
 */
public interface Module {

    /**
     * Get the name of this module. Used for looking up modules using the {@link com.icemobile.framework.core.module.provider.ModuleProvider}
     * @return
     */
    public String getName();

	/**
	 * Stop
	 */
	public void stop();

	/**
	 * Start the module. Could be a screen, a service, etc...
	 * @param context
	 */
	public void start(Context context);

	/**
	 * A module can be disabled even though it is registered in the {@link com.icemobile.framework.core.module.provider.ModuleProvider}
	 * @return <code>true</code> when enabled
	 */
	public boolean isEnabled();

	/**
	 * This function will be called after all modules are registered in the {@link com.icemobile.framework.core.module.provider.ModuleProvider}. Here you can initialize your
	 * dependencies on other modules if there are any. DO NOT initialize dependencies in the constructor, because the module you're
	 * dependent on may not be registered yet.
	 * 
	 */
	public void onInitialize();

	/**
	 * Called when {@link #onInitialize()} has been called on the registered modules
	 */
	public void afterAllModulesInitialized();

}
