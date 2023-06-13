package com.bangkitcapstone.tipsntrip.ui.explore.attraction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkitcapstone.tipsntrip.data.lib.attraction.Attraction
import com.bangkitcapstone.tipsntrip.data.remote.repository.ExplorePagingRepository

class AttractionFragmentViewModel(explorePagingRepository: ExplorePagingRepository):ViewModel() {
    val dataAttraction : LiveData<PagingData<Attraction>> =
        explorePagingRepository.getAllDataAttraction().cachedIn(viewModelScope)
}