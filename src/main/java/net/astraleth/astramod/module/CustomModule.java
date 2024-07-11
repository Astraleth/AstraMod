package net.astraleth.astramod.module;

import org.jetbrains.annotations.NotNull;

/**
 * The base class used for every module
 *
 * @param <T> The main class used for initializing the module
 */
public interface CustomModule<T extends ModuleBase> {

    /**
     * The first method called when initializing a module
     *
     * @param baseClass The base class used for initializing
     */
    void initModule(@NotNull T baseClass);
}
