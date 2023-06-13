package com.bangkitcapstone.tipsntrip.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException

class ExplorePagingSource<V : Any>(private val block : suspend (Int,Int) -> List<V>) : PagingSource<Int, V>() {

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = block(position,params.loadSize)
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position-1,
                nextKey = if (responseData.isNullOrEmpty()) null else position+1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }catch (exception : HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, V>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}



