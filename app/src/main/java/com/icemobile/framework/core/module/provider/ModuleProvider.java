package com.icemobile.framework.core.module.provider;

import com.icemobile.framework.core.module.Module;

import java.util.List;

public interface ModuleProvider {
	/**
	 * Register a module and load its configuration
	 * @param module module
	 */
	public void registerModule(Module module);
	
	/**
	 * Get module by name if registered and enabled. Otherwise return null.
	 * @param name module name
	 * @return module
	 */
	public Module getModule(String name);
	
	/**
	 * Get all registered modules
	 * @return
	 */
	public List<Module> getAllModules();
	
	/**
	 * Check if there modules registered
	 * @return true if at least one module is registered; false otherwise
	 */
	public boolean hasModules();
}
