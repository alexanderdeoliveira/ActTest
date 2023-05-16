package com.alexander.acttest.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryModel(
    val id: Int,
    val name: String,
    val description: String?
): Parcelable