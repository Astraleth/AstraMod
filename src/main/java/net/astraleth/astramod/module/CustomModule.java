package net.astraleth.astramod.module;

import org.jetbrains.annotations.NotNull;

/**
 * The base class used for every module
 *
 * @param <T> The main class used for initializing the module
 * @author amraleth
 */
public interface CustomModule<T extends ModuleBase> {

    /**
     * The first method called when initializing a module
     *
     * @param baseClass The base class used for initializing
     * @deprecated Should not be initialized by itself, only by the {@link net.astraleth.astramod.registry.ModuleRegistry}
     */
    @Deprecated
    void initModule(@NotNull T baseClass);
}
