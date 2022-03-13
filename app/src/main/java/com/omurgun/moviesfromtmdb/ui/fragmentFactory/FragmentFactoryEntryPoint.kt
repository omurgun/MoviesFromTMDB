package com.omurgun.moviesfromtmdb.ui.fragmentFactory

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface FragmentFactoryEntryPoint {
    fun getFragmentFactory(): FragmentFactory
}