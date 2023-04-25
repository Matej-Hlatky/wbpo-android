package me.hlatky.wbpo.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.hlatky.wbpo.model.GetUsersResponse
import me.hlatky.wbpo.model.User

/** [PagingSource] to load list of [User]. */
class UserListPagingSource(
    private val repository: UserRepository,
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val pageSize = params.loadSize
        val currentPage = params.key ?: 0
        // Using page "0" as hack to display placeholders asap

        if (currentPage == 0) {
            return LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = 1,
                itemsAfter = pageSize,
            )
        }

        return try {
            repository.getList(currentPage, params.loadSize).asPage(pageSize)
        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? = null

    private fun GetUsersResponse.asPage(pageSize: Int): LoadResult.Page<Int, User> {
        val itemsAfter = total - page * pageSize

        return LoadResult.Page(
            data = data,
            prevKey = if (page == 1) null else page - 1,
            nextKey = if (page < totalPages) page + 1 else null,
            itemsAfter = itemsAfter,
        )
    }
}
