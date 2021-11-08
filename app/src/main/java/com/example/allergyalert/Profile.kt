package com.example.allergyalert

import java.util.*

class Profile (
    val id: String,
    val name: String,
    val DOB: Calendar,
    val weight: Float,
    val height: Float,
    val notes: String,
    val allergens: ArrayList<String>,
    val productAllergens: ArrayList<String>
    )