package com.bangkitcapstone.tipsntrip.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.bangkitcapstone.tipsntrip.data.ExplorePagingSource
import com.bangkitcapstone.tipsntrip.data.remote.api.ApiService

class ExplorePagingRepository(private val apiService: ApiService) {

    /**
    function to help make Paging Source
     **/
    fun <V : Any> createPager(
        pageSize: Int = 5,
        block: suspend (Int, Int) -> List<V>
    ) = Pager(config = PagingConfig(pageSize),
        pagingSourceFactory = { ExplorePagingSource(block)}
    ).liveData


    fun getAllDataDestination() = createPager { page, size ->
        apiService.getAllDestinationPagination(page,size).destinations
    }

    fun getAllDataAttraction() = createPager { page, size ->
        apiService.getAllAttractionPagination(page,size).attractions
    }

    fun getAllDataSouvenir() = createPager{ page, size ->
        apiService.getAllSouvenirPagination(page, size).souvenirs
    }

}