package com.kotlinstrong.stronglib.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.kotlinstrong.stronglib.listener.LongFunction
import com.kotlinstrong.stronglib.listener.Function
import com.kotlinstrong.stronglib.listener.ViewMap
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class BaseAdapter <T>() : RecyclerView.Adapter<BaseAdapter<T>.RVViewHolder>() {

    constructor(context: Context?, variableId: Int, viewMap: ViewMap<T>?) : this() {
        this.context = context
        this.list = ArrayList<T>()
        this.event = SparseArray()
        this.longevent = SparseArray()
        this.viewMap = viewMap
        this.variableId = variableId
    }

    constructor(context: Context?, variableId: Int, layoutId: Int) : this() {
        this.context = context
        this.list = ArrayList<T>()
        this.event = SparseArray()
        this.longevent = SparseArray()
        this.variableId = variableId
        this.layoutId = layoutId
    }

    fun setNewList(newList: MutableList<T>) {
        this.list = newList
        notifyDataSetChanged()
    }

    private var context: Context? = null
    private var list: MutableList<T>? = null
    private var variableId: Int = 0
    private var layoutId: Int = 0
    private var dataVersion: Int = 0
    private var viewMap: ViewMap<T>? = null
    private var event: SparseArray<Function<T>>? = null
    private var longevent: SparseArray<LongFunction<T>>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        return RVViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), viewType, parent, false))
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewMap?.layoutId(list!![position]) ?: layoutId
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        holder.bindTo(list!![position])
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.size > 0) {
            holder.payloadsbindTo(list!![position]!!)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    @SuppressLint("StaticFieldLeak", "CheckResult")
    fun setList(update: MutableList<T>?, runnable: Runnable?) {
        dataVersion++
        if (update == null) {
            val oldSize = list!!.size
            list = ArrayList<T>()
            notifyItemRangeRemoved(0, oldSize)
        } else {
            val startVersion = dataVersion
            val oldItems = ArrayList<T>(list as ArrayList)
            Observable.just(DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return oldItems.size
                }

                override fun getNewListSize(): Int {
                    return update.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = oldItems[oldItemPosition]
                    val newItem = update[newItemPosition]
                    return oldItem == newItem || oldItem != null && oldItem == newItem
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = oldItems[oldItemPosition]
                    val newItem = update[newItemPosition]
                    return oldItem == newItem || oldItem != null && oldItem == newItem
                }
            })).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
                .subscribe { diffResult ->
                    if (startVersion != dataVersion) {
                        if (startVersion != dataVersion) {
                            return@subscribe
                        }
                        list = ArrayList(update)
                        diffResult.dispatchUpdatesTo(this@BaseAdapter)
                        runnable?.run()
                    }
                    list = ArrayList(update)
                    diffResult.dispatchUpdatesTo(this@BaseAdapter)
                    runnable?.run()
                }
        }
    }

    fun setList(update: MutableList<T>?, needDiff: Boolean) {
        if (needDiff) {
            setList(update, null)
        } else {
            if (update == null) {
                this.list = ArrayList()
            } else {
                this.list = update
            }
            notifyItemRangeChanged(0, list!!.size, list)
        }
    }

    fun setList(update: MutableList<T>) {
        setList(update, null)
    }

    fun addList(@IntRange(from = 0) position: Int, add: Collection<T>?) {
        if (add != null && add.isNotEmpty()) {
            list!!.addAll(position, add)
            notifyItemRangeInserted(position, add.size)
        }
    }

    fun addList(add: Collection<T>?) {
        if (add != null && add.isNotEmpty()) {
            list!!.addAll(add)
            notifyItemRangeInserted(list!!.size - add.size, add.size)
        }
    }

    fun addData(index: Int, add: T) {
        list!!.add(index, add)
        notifyDataChanged()
    }

    fun addData(add: T) {
        list!!.add(add)
        notifyDataChanged(add)
    }

    fun remove(index: Int) {
        this.list!!.removeAt(index)
        notifyItemRangeRemoved(index, 1)
    }

    fun remove(del: T) {
        if (this.list!!.contains(del)) {
            remove(this.list!!.indexOf(del))
        }
    }

    fun notifyDataChanged(t: T) {
        if (list!!.contains(t)) {
            notifyItemChanged(list!!.indexOf(t), list!![list!!.indexOf(t)])
        }
    }

    fun notifyDataChanged() {
        notifyItemRangeChanged(0, list!!.size, list)
    }

    fun getList(): List<T> {
        return list!!
    }

    inner class RVViewHolder(internal var binding: ViewDataBinding) : BaseViewHolder(binding.root) {

        fun bindTo(value: T) {
            binding.setVariable(variableId, value)
            for (i in 0 until event!!.size()) {
                binding.setVariable(event!!.keyAt(i), event!!.valueAt(i))
            }
            for (i in 0 until longevent!!.size()) {
                binding.setVariable(longevent!!.keyAt(i), longevent!!.valueAt(i))
            }
            binding.executePendingBindings()
        }

        fun payloadsbindTo(value: Any) {
            binding.setVariable(variableId, value)
            for (i in 0 until event!!.size()) {
                binding.setVariable(event!!.keyAt(i), event!!.valueAt(i))
            }
            for (i in 0 until longevent!!.size()) {
                binding.setVariable(longevent!!.keyAt(i), longevent!!.valueAt(i))
            }
            binding.executePendingBindings()
        }
    }

    fun addEvent(variableId: Int, function: Function<T>) {
        event!!.put(variableId, function)
    }

    fun addLongEvent(variableId: Int, function: LongFunction<T>) {
        longevent!!.put(variableId, function)
    }

}