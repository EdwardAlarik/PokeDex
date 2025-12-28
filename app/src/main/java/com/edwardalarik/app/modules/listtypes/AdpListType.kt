package com.edwardalarik.app.modules.listtypes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edwardalarik.app.api.extensions.setTypeIcon
import com.edwardalarik.app.api.extensions.typeColor
import com.edwardalarik.app.api.models.KModels
import com.edwardalarik.app.databinding.ItemTypeBinding

class AdpListType(private val dataSet: ArrayList<KModels.ListTypes>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onClick: (KModels.ListTypes) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return viewListType(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        listenerType(holder as ViewHolderType, position)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun viewListType(viewGroup: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemTypeBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false
        )
        return ViewHolderType(binding)
    }

    class ViewHolderType(private val binding: ItemTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(item: KModels.ListTypes) {
            binding.nameType.text = item.name

            binding.imageType.setTypeIcon(item.name.lowercase())

            binding.containerType.setCardBackgroundColor(
                context.typeColor(
                    item.name
                )
            )
        }
    }

    private fun listenerType(viewHolder: ViewHolderType, position: Int) {
        viewHolder.bind(dataSet[position])

        viewHolder.itemView.setOnClickListener {
            onClick.invoke(dataSet[position])
        }
    }
}