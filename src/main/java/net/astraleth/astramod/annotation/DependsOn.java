package net.astraleth.astramod.annotation;

import net.astraleth.astramod.module.CustomModule;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines what other modules this module depends on
 *
 * @author amraleth
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DependsOn {

    /**
     * An array of all base module classes this module depends on
     */
    @Nullable String[] dependencies();
}
