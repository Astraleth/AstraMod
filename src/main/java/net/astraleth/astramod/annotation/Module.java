package net.astraleth.astramod.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Contains metadata about a module
 *
 * @author amraleth
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Module {

    /**
     * The id of the module
     */
    @NotNull
    String moduleId();

    /**
     * The name of the module. Can be different from the module name
     */
    @NotNull
    String moduleName();

    /**
     * The description of the module
     */
    @NotNull
    String description();
}
