package com.strong.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.ProvideWindowInsets
import com.strong.theme.MyApplicationTheme

@Preview
@Composable
fun MainLayout(name: String) {
    ProvideWindowInsets {
        MyApplicationTheme {
            Text(text = "Hello $name!")
        }
    }
}