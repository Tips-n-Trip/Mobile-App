package com.bangkitcapstone.tipsntrip.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkitcapstone.tipsntrip.data.remote.repository.ExplorePagingRepository
import com.bangkitcapstone.tipsntrip.ui.explore.attraction.AttractionFragmentViewModel
import com.bangkitcapstone.tipsntrip.ui.explore.souvenir.SouvenirFragmentViewModel

@Suppress("UNCHECKED_CAST")
class PagingSourceFactory(private val pagingRepository: ExplorePagingRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SouvenirFragmentViewModel::class.java)){
            return SouvenirFragmentViewModel(pagingRepository) as T
        }
        if (modelClass.isAssignableFrom(AttractionFragmentViewModel::class.java)){
            return AttractionFragmentViewModel(pagingRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class : ${modelClass.name}")
    }
}