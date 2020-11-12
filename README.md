# CoreBase
A base for modding by LCLP

## 1.16.x
Just put the mod inside your `/mods` folder.

## 1.15.x ONLY:
In this version, <a href="https://www.curseforge.com/minecraft/mc-mods/mixinbootstrap">MixinBootstrap</a> is required.
**Please note:** If you are using one of the latest forge 1.15.2 versions, Mixins should also be supported without MixinBootstrap, as in 1.16.

<hr>

### Building the deobfuscated jar (for development):
1. Clone this repository
2. Checkout the version you want to build the deobfuscated jar file for. (e.g. `git checkout 1.15` for 1.15.x or `git checkout master` for the latest version)
3. Execute the gradle build task: `./gradlew build` (make sure you are using JDK 8)

The deobfuscated jar file will be located in the `build/libs` directory along with the obfuscated jar file and the sources jar file.
**Please note:** The `./gradlew publish` task does not create the deobfuscated jar file.

You may also need to adjust the mapping version in the build.gradle to your mod's build.gradle mapping version. The resulting compilation errors (method name changes) then have to be resolved before building.
