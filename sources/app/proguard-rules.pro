# Default PV proguard file - use it and abuse it if its useful.

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclassmembers
-verbose

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some of these optimisations on its own).
# -dontoptimize #Only uncomment this if you are addressing Android 2.X or lower)
-dontpreverify

# Note that if you want to enable optimization, you cannot just
# include optimization flags in your own project configuration file;
# instead you will need to point to the
# "proguard-android-optimize.txt" file instead of this one from your
# project.properties file.

# This is generated automatically by the Android Gradle plugin.
-dontwarn java.beans.ConstructorProperties
-dontwarn java.beans.Transient
-dontwarn org.conscrypt.Conscrypt
-dontwarn pl.droidsonroids.gif.GifDrawable

##########
# Maintain all attributes:
# To avoid having to add each in several different places
# below.
#
# You may need to keep Exceptions if using dynamic proxies
# (e. g. Retrofit), Signature and *Annotation* if using reflection
# (e. g. Gson's ReflectiveTypeAdapterFactory).
##########
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,*Annotation*,SourceFile,LineNumberTable,EnclosingMethod
-keep class org.json.**
-keepclassmembers,includedescriptorclasses class org.json.** { *; }

##########
# Android:
##########
##########
# Those are no longer required as this will force ProGuard to keep
# not only real app components and views, but also stuff like
# BaseFragmentActivityApi16, BaseFragmentActivityApi14,
# SupportActivity etc from being merged or removed.
# AAPT generates rules for all classes which were mentioned in XMLs.
##########
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
# Data Binding
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }
#This is used if you are using onClick on the XML.. you shouldn't :-)
-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

##########
# View - Gets and setters - keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
##########
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

##########
#Enums - For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
##########
-keep class * {
    public enum **;
}

##########
# Parcelables: Mantain the parcelables working
##########
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

#############
# Serializables
#############
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

##########
# Kotlin
##########
-dontwarn kotlin.**
-dontnote kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}

#Ignore null checks at runtime
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}

#############
# BottomBar (Needed to call methods via reflection to customize it)
#############
-keep class android.support.design.internal.** { *; }

#############
# WebViews
#############
# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keepclassmembers class life.simple.screen.bodyscan.bridge.BodyScanWebViewInterface {
   public *;
}
-keep class android.support.v8.renderscript.** { *; }

-keepclassmembers class life.simple.feature.paywall.bridge.PaywallWebViewInterface {
   public *;
}


########################################
# External Libraries
########################################


#############
# Google Play Services
#############
-keep class com.google.android.gms.* {  *; }
-dontwarn com.google.android.gms.**
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-dontnote **ILicensingService
-dontnote com.google.android.gms.gcm.GcmListenerService
-dontnote com.google.android.gms.**

-dontwarn com.google.android.gms.ads.**

#############
# Android Support Lib
#############
-keep class android.support.design.widget.TextInputLayout { *; }

#############
# Firebase
#############
-dontnote com.google.firebase.**
-dontwarn com.google.firebase.crash.**
-keep class com.firebase.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.w3c.dom.**

##########
# Android architecture components: Lifecycle ( https://issuetracker.google.com/issues/62113696 )
##########
# LifecycleObserver's empty constructor is considered to be unused by proguard
-keepclassmembers class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}
# ViewModel's empty constructor is considered to be unused by proguard
-keepclassmembers class * extends android.arch.lifecycle.ViewModel {
    <init>(...);
}
# keep Lifecycle State and Event enums values
-keepclassmembers class android.arch.lifecycle.Lifecycle$State { *; }
-keepclassmembers class android.arch.lifecycle.Lifecycle$Event { *; }
# keep methods annotated with @OnLifecycleEvent even if they seem to be unused
# (Mostly for LiveData.LifecycleBoundObserver.onStateChange(), but who knows)
-keepclassmembers class * {
    @android.arch.lifecycle.OnLifecycleEvent *;
}

#############
# Appsflyer
#############
-keep class com.appsflyer.** { *; }
-keep public class com.android.installreferrer.** { *; }

#############
# Retrofit
#############
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

#############
# HttpClient Legacy (Ignore) - org.apache.http legacy
#############
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontwarn org.apache.commons.logging.**
-dontnote org.apache.http.**

# https://simple-app.atlassian.net/browse/DEV-5230
-keep class org.apache.hc.client5.** { *; }

##########
# Glide
##########
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
-dontnote com.bumptech.glide.**

##########
# RxJava 2
##########
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}

#############
# Stetho
#############
-dontnote com.facebook.stetho.**

#############
# Room
#############
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

##########
# Crashlytics:
# Adding this in to preserve line numbers so that the stack traces can be remapped
##########
-renamesourcefileattribute SourceFile
-keep public class * extends java.lang.Exception

#############
# Gson
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
#############
# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

#############
# Flexbox layout
#############
-keepnames public class com.google.android.flexbox.FlexboxLayoutManager

#############
# Markwon
#############
-dontwarn okhttp3.**
-dontwarn okio.**

-keep class com.caverock.androidsvg.** { *; }
-dontwarn com.caverock.androidsvg.**

#############
# Revenuecat
#############
-keep class com.revenuecat.** { *; }

#############
# uCrop
#############
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#############
# Lottie
#############
-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** {*;}

#############
# Threeten
#############
-dontwarn sun.util.calendar.*

#############
# Timber
#############
-dontwarn org.slf4j.**

#############
# Facebook
#############
-keepnames class com.facebook.FacebookActivity
-keepnames class com.facebook.CustomTabActivity

-keep class com.facebook.all.All

-keep class com.revenuecat.purchases.** { *; }
-keep public class com.android.vending.billing.IInAppBillingService {
    public static com.android.vending.billing.IInAppBillingService asInterface(android.os.IBinder);
    public android.os.Bundle getSkuDetails(int, java.lang.String, java.lang.String, android.os.Bundle);
}

#############
# Simple app
# Sort all rules in alphabetical order
#############
-keep class life.simple.config.** { *; }
-keep class life.simple.core.analytics.AnalyticsType {*;}
-keep class life.simple.core.login.model.** { *; }
-keep class life.simple.core.remote_config.** { *; }
-keep class life.simple.core.util.** { *; }
-keep class life.simple.db.** { <fields>; }
-keep class life.simple.deeplink.DeeplinkParser$DeepLinkAction { *; }
-keep class life.simple.jwt.** { *; }
-keep class life.simple.model.** { *; }
-keep class life.simple.network.** { *; }
-keep class life.simple.remoteconfig.** { <fields>; }
-keep class life.simple.repository.** { <fields>; }
-keep class life.simple.feature.legacy_paywall.web.bridge.** { *; }
-keep class life.simple.feature.legacy_paywall.web.models.** { *; }
-keep class life.simple.wording.** { *; }

#############
# A/B Smartly
#############
-keep class com.absmartly.sdk.** { *; }

#############
# View Binding
#############
-keep class life.simple.databinding.** {
    life.simple.databinding.** inflate(android.view.LayoutInflater,android.view.ViewGroup,boolean);
}

#############
# Protobuf
#############
-keep class * extends com.google.protobuf.GeneratedMessageLite { *; }

-keep class org.json.**
-keepclassmembers,includedescriptorclasses class org.json.** { *; }

-keep class com.firebase.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontwarn org.shaded.apache.**
-dontwarn org.ietf.jgss.**

-keep class java.lang.invoke.** { *; }

-dontwarn com.sun.jna.FunctionMapper
-dontwarn com.sun.jna.JNIEnv
-dontwarn com.sun.jna.Library
-dontwarn com.sun.jna.Native
-dontwarn com.sun.jna.Platform
-dontwarn com.sun.jna.Pointer
-dontwarn com.sun.jna.Structure
-dontwarn com.sun.jna.platform.win32.Kernel32
-dontwarn com.sun.jna.platform.win32.Win32Exception
-dontwarn com.sun.jna.platform.win32.WinDef$LPVOID
-dontwarn com.sun.jna.platform.win32.WinNT$HANDLE
-dontwarn com.sun.jna.win32.StdCallLibrary
-dontwarn edu.umd.cs.findbugs.annotations.SuppressFBWarnings
-dontwarn java.lang.instrument.ClassDefinition
-dontwarn java.lang.instrument.IllegalClassFormatException
-dontwarn java.lang.instrument.UnmodifiableClassException
-dontwarn org.mockito.internal.creation.bytebuddy.inject.MockMethodDispatcher
-dontwarn org.opentest4j.AssertionFailedError