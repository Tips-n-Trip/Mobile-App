package com.bangkitcapstone.tipsntrip.ui.explore.destination

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkitcapstone.tipsntrip.data.lib.city.Destination
import com.bangkitcapstone.tipsntrip.data.remote.repository.ExplorePagingRepository

class CityDestinationViewModel(explorePagingRepository: ExplorePagingRepository):ViewModel() {
    val cityDestination : LiveData<PagingData<Destination>> =
        explorePagingRepository.getAllDataDestination().cachedIn(viewModelScope)
}