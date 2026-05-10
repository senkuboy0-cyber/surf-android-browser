# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep GeckoView classes
-keep class org.mozilla.geckoview.** { *; }
-keep interface org.mozilla.geckoview.** { *; }

# Keep database entities
-keep @androidx.room.Entity class *
-keep class com.surf.browser.database.entities.**

# Keep API models
-keep class com.surf.browser.api.** { *; }
-keep class com.surf.browser.api.** { *; }

# Keep Retrofit models
-keep class * implements com.squareup.moshi.JsonAdapter
-keep class * implements retrofit2.Call

# Keep Gson/Moshi serialization
-keepclassmembers class * {
    @com.squareup.moshi.Json <fields>;
    @com.squareup.moshi.Json(name = *) <fields>;
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable
-keep class * extends android.os.Parcelable
-keepclassmembers class * extends android.os.Parcelable {
    public static final ** CREATOR;
}

# ViewBinding classes
-keep public class * extends androidx.viewbinding.ViewBinding
-keep class * extends androidx.viewbinding.ViewBinding {
    public static *** inflate(...);
    public static *** bind(...);
}

# DataBinding
-keep class * extends androidx.databinding.ViewDataBinding
-keepclassmembers class * extends androidx.databinding.ViewDataBinding {
    public void setVariable(int, java.lang.Object);
}

# Room database
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Dao class *
-keepclassmembers class * extends androidx.room.RoomDatabase {
    public *** get*Dao();
}

# ViewModel
-keep class * extends androidx.lifecycle.ViewModel
-keep class * extends androidx.lifecycle.AndroidViewModel
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# LiveData
-keep class * extends androidx.lifecycle.LiveData
-keepclassmembers class * extends androidx.lifecycle.LiveData {
    *** setValue(...);
}

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Keep all custom exceptions
-keep public class * extends java.lang.Exception

# Keep all enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep models used in JSON parsing
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-dontwarn com.squareup.moshi.**

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep all custom views
-keep public class * extends android.view.View
-keep public class * extends android.widget.*
-keep public class * extends androidx.appcompat.widget.*
-keep public class * extends com.google.android.material.*