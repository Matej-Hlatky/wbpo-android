package me.hlatky.wbpo.ui.user.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.hlatky.wbpo.R
import me.hlatky.wbpo.data.FollowedUsersStore
import me.hlatky.wbpo.databinding.ItemUserBinding
import me.hlatky.wbpo.model.User

/** [RecyclerView.Adapter] that can display a list of [User]. */
class UserListRecyclerViewAdapter(
    private val store: FollowedUsersStore,
    private val lifecycleScope: CoroutineScope,
    private val items: List<User>
) : RecyclerView.Adapter<UserListRecyclerViewAdapter.ViewHolder>() {

    private val placeholder = R.drawable.shape_avatar_placeholder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(ItemUserBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = items[position]

        holder.binding.also {
            it.model = user
            it.setAvatar(user)
            it.setupFollowing(user)
        }
    }

    override fun getItemCount(): Int = items.size

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

        lifecycleScope.launch {
            val isFollowed = store.getIsFollowed(userId)

            followToggle.also {
                it.isChecked = isFollowed
                it.setOnCheckedChangeListener { _, isChecked ->
                    lifecycleScope.launch {
                        if (isChecked) store.followUser(userId) else store.unFollowUser(userId)
                    }
                }
            }
        }
    }
}
