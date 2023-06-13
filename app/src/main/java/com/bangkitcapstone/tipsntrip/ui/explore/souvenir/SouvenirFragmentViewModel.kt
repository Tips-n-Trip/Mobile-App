package com.bangkitcapstone.tipsntrip.ui.explore.souvenir

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkitcapstone.tipsntrip.data.lib.souvenir.Souvenir
import com.bangkitcapstone.tipsntrip.data.remote.repository.ExplorePagingRepository

class SouvenirFragmentViewModel(explorePagingRepository: ExplorePagingRepository) : ViewModel() {

    val dataSouvenir: LiveData<PagingData<Souvenir>> =
        explorePagingRepository.getAllDataSouvenir().cachedIn(viewModelScope)
}