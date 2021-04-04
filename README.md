# CoreBase
A base for modding by LCLP

## 1.16.x
Just put the mod inside your `/mods` folder.

<hr>

### Using CoreBase in your project
Of course, you are welcome to use CoreBase in your own projects.
<br>
To do this, just add this repository to your build.gradle:
```groovy
repositories {
    maven { url 'https://repo.lclpnet.work/repository/internal' }
}
```
Now add the following dependency:
```groovy
dependencies {
    implementation fg.deobf("work.lclpnet.mods:CoreBase:VERSION")
}
```
You need to replace VERSION with the version you want to use.
To see all versions available, you can [check the repository](https://repo.lclpnet.work/#artifact~internal/work.lclpnet.mods/CoreBase).
