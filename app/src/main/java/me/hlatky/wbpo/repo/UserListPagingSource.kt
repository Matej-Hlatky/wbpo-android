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
        val currentPage = params.key ?: 1

        return try {
            repository.getList(currentPage, params.loadSize).asPage()
        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? = null

    private fun GetUsersResponse.asPage(): LoadResult.Page<Int, User> = LoadResult.Page(
        data = data,
        prevKey = if (page == 1) null else -1,
        nextKey = if (page < totalPages) page + 1 else null
    )
}
