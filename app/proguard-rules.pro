-dontoptimize
-dontobfuscate

# If 'Class not found' error is thrown, keep the classes instead of discarding

# TODO: work out if it should be a **



-dontnote android.net.http.*
-dontnote com.google.apphosting.api.*
-dontnote com.google.auto.common.*
-dontnote com.google.common.util.concurrent.*
-dontnote kotlin.internal.*
-dontnote kotlin.internal.jdk8.*
-dontnote kotlin.jvm.internal.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**
-dontnote sun.misc.*
-dontwarn androidx.room.*
-dontwarn android.arch.persistence.room.*
-dontwarn android.arch.persistence.room.ext.*
-dontwarn android.arch.persistence.room.log.*
-dontwarn android.arch.persistence.room.paging.LimitOffsetDataSource
-dontwarn android.arch.persistence.room.processor.*
-dontwarn android.arch.util.paging.CountedDataSource
-dontwarn com.google.appengine.api.*
-dontwarn com.google.auto.common.*
-dontwarn com.google.auto.common.MoreElements.*
#-dontwarn com.google.auto.common.MoreTypes
-dontwarn com.google.common.*
-dontwarn com.google.common.cache.LocalCache$Segment
-dontwarn com.google.common.collect.*
-dontwarn com.google.common.net.HostAndPort.*
-dontwarn com.google.common.reflect.*
-dontwarn com.google.common.util.concurrent.*
-dontwarn com.google.gson.internal.*
-dontwarn com.squareup.javapoet.*
-dontwarn javax.annotation.*
-dontwarn javax.annotation.concurrent.*
#-dontwarn javax.annotation.concurrent.GuardedBy
-dontwarn javax.annotation.processing.*
-dontwarn javax.lang.model.element.*
-dontwarn javax.lang.model.type.*
-dontwarn javax.lang.model.util.*
-dontwarn javax.swing.*
-dontwarn javax.swing.event.*
-dontwarn kotlin.internal.*
-dontwarn kotlin.internal.jdk8.*
-dontwarn me.eugeniomarletti.kotlin.metadata.*
-dontwarn me.eugeniomarletti.kotlin.metadata.jvm.*
-dontwarn me.eugeniomarletti.kotlin.processing.*
-dontwarn org.abego.treelayout.*
-dontwarn org.antlr.stringtemplate.StringTemplate
-dontwarn org.antlr.v4.gui.*
-dontwarn org.jetbrains.annotations.ReadOnly
-dontwarn org.jetbrains.annotations.*
-dontwarn org.stringtemplate.v4.gui.*
#-dontwarn org.stringtemplate.v4.gui.STViz
-dontwarn sun.misc.*


# Core
-keep interface com.lydiaralph.decisiontracker.* { *; }
-keep, includedescriptorclasses class com.lydiaralph.decisiontracker.* { *; }
-keep class android.support.v4.app.* { *; }
-keep interface android.support.v4.app.* { *; }
-keep class android.support.design.widget.AppBarLayout { *; }
-keep class androidx.appcompat.* { *; }
-keep class androidx.legacy.* {*;}
-keep class androidx.recyclerview.* {*;}
-keep class androidx.room.* {*;}
-keep class androidx.lifecycle.* {*;}
-keep interface androidx.lifecycle.* {*;}
-keep interface androidx.** { *; }
-keep class androidx.** { *; }


# Javax
-keep class javax.lang.model.* { *; }
-keep, includedescriptorclasses class javax.tools.* { public  *; }
-keep class javax.lang.model.element.* { public *; }
-keep class javax.swing.** { public *; }

-keep class javax.swing.text.** { public *; }


# Arch

-keep, includedescriptorclasses class android.arch.lifecycle.* { public *; }

-keep class android.arch.persistence.db.* { public *; }

-keep, includedescriptorclasses class android.arch.persistence.room.* { public *; }

-keep, includedescriptorclasses class android.arch.persistence.room.ext.* { *; }

-keep class android.arch.persistence.room.RoomSQLiteQuery { public *; }

-keep , includedescriptorclasses class android.arch.persistence.room.log.* {
public *;
}

-keep, includedescriptorclasses class android.arch.persistence.room.vo.Field {
 public *;
}

-keep, includedescriptorclasses  class android.arch.persistence.room.verifier.DatabaseVerifier {
   public *;
}

-keep, includedescriptorclasses  class android.arch.persistence.room.DatabaseConfiguration {
  public *;
}

-keep, includedescriptorclasses  public class android.arch.persistence.room.processor.* {
    public *;
}


# SQL

-keep, includedescriptorclasses class org.sqlite.core.* { public *; }

-keep class org.sqlite.Function { public *; }


