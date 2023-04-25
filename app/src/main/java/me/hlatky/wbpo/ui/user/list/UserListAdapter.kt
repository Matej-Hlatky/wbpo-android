package me.hlatky.wbpo.ui.user.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.dispose
import coil.load
import coil.transform.CircleCropTransformation
import me.hlatky.wbpo.R
import me.hlatky.wbpo.databinding.ItemUserBinding
import me.hlatky.wbpo.model.User

/** [RecyclerView.Adapter] that can display a list of [User]. */
class UserListAdapter : PagingDataAdapter<User, UserListAdapter.ViewHolder>(DiffCallback()) {

    private val placeholder = R.drawable.shape_avatar_placeholder

    var changeUserIsFollowListener: OnChangeUserIsFollowListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(ItemUserBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position) // this can be null -> Placeholder

        holder.binding.also {
            it.model = user
            it.setAvatar(user)
            it.setupFollowing(user)
        }
    }

    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    private fun ItemUserBinding.setAvatar(user: User?) {
        val imageUrl = user?.avatar

        if (imageUrl.isNullOrEmpty()) {
            avatarImage.dispose()
            avatarImage.setImageResource(placeholder)
        } else {
            avatarImage.load(imageUrl) {
                crossfade(true)
                placeholder(placeholder)
                error(R.drawable.ic_broken_image_24)
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun ItemUserBinding.setupFollowing(user: User?) {
        if (user != null) {
            followToggle.also {
                // Need to clear the listener before setting to prevent invocation of callback
                // when row was recycled or just updated
                it.setOnCheckedChangeListener(null)
                it.isChecked = user.isFollowed ?: false
                it.setOnCheckedChangeListener { _, isChecked ->
                    changeUserIsFollowListener?.changeUserIsFollowing(user, isChecked)
                    // TODO This state is not preserved when recycled -> need to refresh source list
                    user.isFollowed = isChecked
                }
            }
        }
    }

    fun interface OnChangeUserIsFollowListener {
        fun changeUserIsFollowing(user: User, isFollowing: Boolean)
    }

    private class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem
    }
}
