package me.hlatky.wbpo.ui.user.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.hlatky.wbpo.databinding.ItemUserBinding
import me.hlatky.wbpo.model.User

/** [RecyclerView.Adapter] that can display a list of [User]. */
class UserListRecyclerViewAdapter(
    private val items: List<User>
) : RecyclerView.Adapter<UserListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(ItemUserBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.model = items[position]
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}
