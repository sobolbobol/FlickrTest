package com.sobolcorp.flickrtest.utils

import android.databinding.BindingAdapter
import android.os.Build
import android.text.TextWatcher
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ArrayAdapter



@BindingAdapter("bind:textWatcher")
fun setTextWatcher(view: EditText, watcher: TextWatcher?) {
    if (watcher != null) {
        view.addTextChangedListener(watcher)
    }
}

@BindingAdapter("bind:legacySearch")
fun setLegacySearch(view: AutoCompleteTextView, legacyRequests: List<String>) {
        view.setAdapter(ArrayAdapter(view.context,
                android.R.layout.simple_dropdown_item_1line, legacyRequests))
}

@BindingAdapter("bind:transitionName")
fun setTransitionName(view: View, transitionName: String?) {
    if (!transitionName.isNullOrEmpty() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        view.transitionName = transitionName
    }
}