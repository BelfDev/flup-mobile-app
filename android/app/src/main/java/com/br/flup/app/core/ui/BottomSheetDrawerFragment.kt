package com.br.flup.app.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.br.flup.app.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.core_bottom_sheet_drawer, container, false)
    }

}