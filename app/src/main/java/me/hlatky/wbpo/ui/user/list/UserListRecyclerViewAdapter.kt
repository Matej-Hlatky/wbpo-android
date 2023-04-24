package me.hlatky.wbpo.ui.user.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.hlatky.wbpo.R
import me.hlatky.wbpo.databinding.ItemUserBinding
import me.hlatky.wbpo.model.User
import me.hlatky.wbpo.store.FollowedUsersStore

/** [RecyclerView.Adapter] that can display a list of [User]. */
class UserListRecyclerViewAdapter(
    private val store: FollowedUsersStore,
    private val coroutineScope: CoroutineScope,
) : ListAdapter<User, UserListRecyclerViewAdapter.ViewHolder>(DiffCallback()) {

    private val placeholder = R.drawable.shape_avatar_placeholder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(ItemUserBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = currentList[position]

        holder.binding.also {
            it.model = user
            it.setAvatar(user)
            it.setupFollowing(user)
        }
    }

    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    private fun ItemUserBinding.setAvatar(user: User) {
        val imageUrl = user.avatar

        if (imageUrl.isNullOrEmpty()) {
            avatarImage.setImageResource(placeholder)
        } else {
            // TODO Cache: https://coil-kt.github.io/coil/image_loaders/
            avatarImage.load(imageUrl) {
                crossfade(true)
                placeholder(placeholder)
                error(R.drawable.ic_broken_image_24)
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun ItemUserBinding.setupFollowing(user: User) {
        val userId = user.id ?: return

        // TODO Move logic into ViewModel and separate repository on User

        coroutineScope.launch {
            val isFollowed = store.getIsFollowed(userId)

            followToggle.also {
                it.isChecked = isFollowed
                it.setOnCheckedChangeListener { _, isChecked ->
                    coroutineScope.launch {
                        if (isChecked) store.followUser(userId) else store.unFollowUser(userId)
                    }
                }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem
    }
}
