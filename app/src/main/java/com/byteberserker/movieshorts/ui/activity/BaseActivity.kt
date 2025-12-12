package com.byteberserker.movieshorts.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Base activity that handles edge-to-edge display and window insets.
 * All activities should extend this to prevent content from being drawn under the status bar.
 * Window insets are automatically applied to the root view.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable edge-to-edge display
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        applyWindowInsetsToRoot()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        applyWindowInsetsToRoot()
    }

    /**
     * Automatically applies window insets to the root view.
     * This prevents content from being drawn under the status bar.
     * Only applies top inset to avoid issues with bottom navigation.
     */
    private fun applyWindowInsetsToRoot() {
        val rootView = window.decorView.findViewById<View>(android.R.id.content)
        rootView?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { view, insets ->
                val statusBarInset = insets.getInsets(WindowInsetsCompat.Type.statusBars())
                view.setPadding(
                    0,  // No left padding
                    statusBarInset.top,  // Only top padding for status bar
                    0,  // No right padding
                    statusBarInset.bottom   // No bottom padding - let bottom nav handle itself
                )
                insets
            }
        }
    }
}
