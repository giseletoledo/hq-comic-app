package com.ebac.hqcomicapp

import androidx.lifecycle.ViewModel

class HQViewModel : ViewModel() {

    fun loadHQDetails() : HQDetails{
        return HQDetails("Minha HQ", "Este é um conteúdo de texto extenso")
    }
}