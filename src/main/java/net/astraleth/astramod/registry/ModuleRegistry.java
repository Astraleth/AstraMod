package net.astraleth.astramod.registry;

import lombok.Getter;
import net.astraleth.astramod.annotation.DependsOn;
import net.astraleth.astramod.annotation.Module;
import net.astraleth.astramod.module.CustomModule;
import net.astraleth.astramod.module.ModuleBase;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Responsible for loading and initialization of modules
 *
 * @param <T> The class that is used as the base class
 * @author amraleth
 */
public class ModuleRegistry<T extends ModuleBase> {
    private final T baseClazz;
    @Getter
    private final Set<Class<? extends CustomModule<T>>> modules;

    private final Map<String, Class<? extends CustomModule<T>>> moduleMap;
    private final Set<String> loadedModules;

    /**
     * @param baseClazz An instance of the specified base class
     */
    public ModuleRegistry(@NotNull T baseClazz) {
        this.baseClazz = baseClazz;
        this.modules = new HashSet<>();
        this.moduleMap = new HashMap<>();
        this.loadedModules = new HashSet<>();
    }

    /**
     * Add a new module
     *
     * @param moduleMainClazz The main class of the module
     * @return ModuleRegistry
     */
    public ModuleRegistry<T> addModule(@NotNull Class<? extends CustomModule<T>> moduleMainClazz) {
        this.modules.add(moduleMainClazz);
        return this;
    }

    /**
     * Load all modules
     *
     * @throws Exception When an error occurred during the initialization
     */
    public void loadModules() throws Exception {
        for (Class<? extends CustomModule<T>> moduleClazz : this.modules) {
            this.moduleMap.put(getName(moduleClazz), moduleClazz);
        }

        for (String moduleName : this.loadedModules) {
            loadModule(moduleName);
        }
    }

    /**
     * Get the name of a module
     *
     * @param moduleClazz The main class of the module
     * @return The name of the module
     * @throws Exception When an error occurred during the retrieval
     */
    private @NotNull String getName(@NotNull Class<? extends CustomModule<T>> moduleClazz) throws Exception {
        if (!(moduleClazz.isAnnotationPresent(Module.class))) {
            throw new Exception(moduleClazz.getName() + " is missing the module annotation!");
        }
        Module module = moduleClazz.getAnnotation(Module.class);
        return module.moduleId();
    }

    /**
     * Load a specific module, uses recursion for dependency loading
     *
     * @param moduleName The name of the module to load
     * @throws Exception When an error occurred during loading
     */
    private void loadModule(@NotNull String moduleName) throws Exception {
        if (this.loadedModules.contains(moduleName)) {
            return;
        }

        Class<? extends CustomModule<T>> clazz = this.moduleMap.get(moduleName);
        if (clazz == null) {
            throw new Exception("The module " + moduleName + " was not found");
        }

        if (clazz.isAnnotationPresent(DependsOn.class)) {
            DependsOn dependsOn = clazz.getAnnotation(DependsOn.class);

            for (String moduleId : dependsOn.dependencies()) {
                if (moduleId != null) {
                    loadModule(moduleId);
                }
            }

            CustomModule<T> customModule = clazz.getDeclaredConstructor().newInstance();
            customModule.initModule(this.baseClazz);
            this.loadedModules.add(moduleName);
        }
    }
}
