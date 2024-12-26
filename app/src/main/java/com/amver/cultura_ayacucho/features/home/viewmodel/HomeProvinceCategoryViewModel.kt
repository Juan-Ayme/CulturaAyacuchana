package com.amver.cultura_ayacucho.features.home.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.amver.cultura_ayacucho.data.api.ApiState
import com.amver.cultura_ayacucho.data.model.place.PlacesItem
import com.amver.cultura_ayacucho.data.repository.PlaceRepository
import kotlinx.coroutines.launch

class HomeProvinceCategoryViewModel(application: Application):AndroidViewModel(application) {
    private val placeRepository = PlaceRepository()

    private val _placesByCategoryAndProvince = mutableStateListOf<PlacesItem>()
    val placesByCategoryAndProvince: SnapshotStateList<PlacesItem> = _placesByCategoryAndProvince


}