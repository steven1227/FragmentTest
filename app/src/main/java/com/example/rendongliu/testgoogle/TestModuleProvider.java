package com.example.rendongliu.testgoogle;

import com.icemobile.framework.core.module.Module;
import com.icemobile.framework.core.module.provider.ModuleProvider;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by rendong.liu on 26/06/15.
 */
public class TestModuleProvider implements ModuleProvider {

    private Hashtable<String, Module> modules;

    public TestModuleProvider() {
        modules = new Hashtable<>();
    }

    @Override
    public void registerModule(Module module) {
        modules.put(module.getName(), module);
    }

    @Override
    public Module getModule(String name) {
        return modules.get(name);
    }

    @Override
    public List<Module> getAllModules() {
        List<Module> result = new ArrayList<>();


        Enumeration<Module> elemensEnum = modules.elements();
        while (elemensEnum.hasMoreElements()) {
            result.add(elemensEnum.nextElement());
        }

        return result;
    }

    @Override
    public boolean hasModules() {
        return !modules.isEmpty();
    }
}
