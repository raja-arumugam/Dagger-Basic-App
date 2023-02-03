package com.example.dagger2basics

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class SharedPreferenceModule @Inject constructor(val context: Context?) {

    @Singleton
    @Provides
    fun provideContext(): Context? {
        return context
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context?): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(context!!)
    }

    // @Singleton indicates that only single instance of dependency object is created
    // @Provide annotations used over the methods that will provides the object of module class
    // This method will return the dependent object
}