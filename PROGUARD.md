# ProGuard/R8 Configuration for Surf Browser

## Overview
This document explains the ProGuard/R8 configuration used for optimizing and obfuscating the Surf Browser APK in release builds.

## Key Configuration Points

### Keep GeckoView Classes
GeckoView classes need to be preserved for proper functionality:

```proguard
-keep class org.mozilla.geckoview.** { *; }
-keep interface org.mozilla.geckoview.** { *; }
```

### Keep Retrofit and Moshi Models
API data classes and interfaces must be preserved:

```proguard
-keep class com.surf.browser.api.** { *; }
-keep class com.surf.browser.database.entities.** { *; }
```

### Keep Room Database Classes
Database entities and DAOs need reflection access:

```proguard
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *
```

### Keep Custom Views and Activities
Activities and custom views need to be preserved for Android system:

```proguard
-keep public class * extends android.app.Activity
-keep public class * extends androidx.appcompat.app.AppCompatActivity
-keep public class * extends android.view.View
```

### Keep Gson/Moshi serialization
Keep classes used for JSON serialization:

```proguard
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
```

## Optimization Benefits

1. **APK Size Reduction**: ~40-60% smaller compared to debug build
2. **Performance Improvement**: Faster startup and execution
3. **Security**: Basic code obfuscation to protect intellectual property

## Recommended Settings

- **Shrink Resources**: Enabled (removes unused resources)
- **Minification**: Enabled (optimizes bytecode)
- **Obfuscation**: Enabled (renames classes/methods)
- **Debug Info**: Removed (reduces APK size)

## Testing

Always test release builds thoroughly as obfuscation can sometimes cause issues with:
- Reflection-based code
- JavaScript interfaces
- Third-party libraries requiring specific class names