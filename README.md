# AstraMod

*AstraMod* is a basic module system designed for large projects with lots of different things going on that depend on
each other. It is designed for use on the Astraleth minecraft server.

## Setup

To use it in your project, you need to import the repository and the dependency:

```kotlin
maven {
    url = uri("https://repo.astraleth.net/repository/astraleth")
}
```

```kotlin
implementation("net.astraleth:astramod:VERSION")
```

## Usage

To start using AstraMod, the first step is creating a base class that will be used for dependency passing. It is
required to extend the ModuleBase class:

```java
import net.astraleth.astramod.module.ModuleBase;

public class AstralethServer extends ModuleBase {

}
```

After that, you can start defining your Modules. We will first create a module called ``WelcomeModule`` that depends on
a ``DatabaseModule``:

```java
import net.astraleth.astramod.annotation.DependsOn;
import net.astraleth.astramod.annotation.Module;
import net.astraleth.astramod.module.CustomModule;
import org.jetbrains.annotations.NotNull;

@Module(moduleId = "welcome", moduleName = "WelcomeModule", description = "Welcomes the player on join")
@DependsOn(dependencies = "database")
public class WelcomeModule implements CustomModule<AstralethServer> {

    @Override
    public void initModule(@NotNull AstralethServer baseClass) {
        System.out.println("Hello from module 1");
        DatabaseModule databaseModule = DatabaseModule.INSTANCE;
        databaseModule.loadDatabase();

        System.out.println("Welcome to the server " + databaseModule.getCoins());
    }
}
```

```java
import lombok.Getter;
import net.astraleth.astramod.annotation.Module;
import net.astraleth.astramod.module.CustomModule;
import org.jetbrains.annotations.NotNull;

@Module(moduleId = "database", moduleName = "DatabaseModule", description = "Provides access to the database")
@Getter
public class DatabaseModule implements CustomModule<AstralethServer> {
    public static DatabaseModule INSTANCE;

    @Override
    public void initModule(@NotNull AstralethServer baseClass) {
        System.out.println("Hello from module 2");
        INSTANCE = this;
    }

    public void loadDatabase() {
        // Database code
    }

    public int getCoins() {
        // Code for getting coins
        return 0;
    }
}
```

As you can see, the WelcomeModule depends on the WelcomeModule by using the ``DependsOn`` annotation.
The last step in the setup is the registration of the modules:

```java
import net.astraleth.astramod.module.ModuleBase;
import net.astraleth.astramod.registry.ModuleRegistry;

public class AstralethServer extends ModuleBase {

    public void startServer() throws Exception {
        new ModuleRegistry<>(this)
                .addModule(DatabaseModule.class)
                .addModule(WelcomeModule.class)
                .loadModules();
    }
}

```

When running the server, the output shows the order in which the modules are loaded:

```
Hello from module 2 <- Module 2 is loaded first because of the dependency
Hello from module 1 
Welcome to the server 0
```