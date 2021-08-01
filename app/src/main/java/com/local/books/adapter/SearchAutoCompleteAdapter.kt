package com.local.books.adapter

import android.app.Activity
import android.widget.ArrayAdapter
import android.widget.Filter
import com.local.books.utils.log

class SearchAutoCompleteAdapter(
    private val activity: Activity,
    resId: Int,
    var data: MutableList<String>
) :
    ArrayAdapter<String>(activity, resId, data) {

    var filtered = ArrayList<String>()

//    override fun clear() {
//        super.clear()
//        filtered.clear()
//        data.clear()
//    }
//    override fun add(`object`: String?) {
//        data.add(`object` as String)
//        super.add(`object`)
//    }

    override fun getCount() = filtered.size

    override fun getItem(position: Int) = filtered[position]

    override fun getFilter() = filter

    private var filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val results = FilterResults()
            val query =
                if (constraint != null && constraint.isNotEmpty()) autocomplete(constraint.toString())
                else arrayListOf()
            results.values = query
            results.count = query.size
            return results
        }
        private fun autocomplete(input: String): ArrayList<String> {
            val results = arrayListOf<String>()
            for (str in data) {
                "input:${input} std: $str".log(this.toString())

                if (str.toLowerCase().contains(input.toLowerCase())) {
                    results.add(str)
                }
            }
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults) {
            filtered = results.values as ArrayList<String>
            if (results.count > 0) {
                notifyDataSetChanged();
            }
            else {
                notifyDataSetInvalidated();
            }
//            "publish result ${filtered.toString()}".log(this.toString())
//            notifyDataSetInvalidated()
//            notifyDataSetChanged()
        }

        //override fun convertResultToString(result: Any) = (result as String)
    }
}