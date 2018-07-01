package com.sobolcorp.flickrtest.base

import android.databinding.BaseObservable
import android.os.Bundle

const val DATA_SAVED_STATE = "DATA_SAVED_STATE"
const val DATA_ARGUMENT = "DATA_ARGUMENT"
const val TRANSITION_ARGUMENT = "TRANSITION_ARGUMENT"
abstract class BaseViewModel : BaseObservable() {

    open fun initData(data: Bundle?) {}

    open fun restoreData(data: Bundle?) {}

    open fun onCreateView(savedData: Bundle?, arguments: Bundle?) {}

    open fun onSaveInstanceState(data: Bundle) {}

    open fun onStop() {}


}