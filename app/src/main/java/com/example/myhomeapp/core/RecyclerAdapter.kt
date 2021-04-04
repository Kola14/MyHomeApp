package com.example.myhomeapp.core

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class RecyclerAdapter<T> : RecyclerView.Adapter<RecyclerAdapter.BindingHolder<T>>() {

    var items: List<T>? = null
        private set

    fun submit(items: List<T>?, force: Boolean = false) {
        if (this.items == null || force) {
            if (items != null) {
                this.items = items
                notifyDataSetChanged()
            }
        } else if (items == null) {
            notifyItemRangeRemoved(0, itemCount)
            this.items = null
        } else {
            val diff = DiffUtil.calculateDiff(DiffCallback(this.items, items))
            this.items = items
            diff.dispatchUpdatesTo(this)
        }
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: BindingHolder<T>, position: Int) {
        val item = items!![position]
        holder.bind(item, position)
    }

    abstract class BindingHolder<T>(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        abstract fun bind(model: T, position: Int)
    }

    protected open fun isItemsSame(old: T, new: T): Boolean {
        return old === new
    }

    protected open fun isContentSame(old: T, new: T): Boolean {
        return old == new
    }

    private inner class DiffCallback(

        private val mOldList: List<T>?,
        private val mNewList: List<T>?

    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return mOldList?.size ?: 0
        }

        override fun getNewListSize(): Int {
            return mNewList?.size ?: 0
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val a = mNewList!![newItemPosition]
            val b = mOldList!![oldItemPosition]
            return isItemsSame(a, b)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val a = mNewList!![newItemPosition]
            val b = mOldList!![oldItemPosition]
            return isContentSame(a, b)
        }
    }
}
