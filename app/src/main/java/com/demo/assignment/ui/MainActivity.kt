package com.demo.assignment.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.assignment.databinding.ActivityMainBinding
import com.demo.assignment.model.pojo.Item
import com.demo.assignment.util.visible
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ItemViewModel by viewModels()
    private lateinit var itemList: ArrayList<Item>
    private lateinit var itemListFiltered: ArrayList<Item>
    private lateinit var itemAdapter: ItemAdapter

    companion object {
        const val TAG = "APP_TAG_VIEW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.swipeRefreshLayout.isRefreshing = true

        setAdapter()
        observeDataForRepositories()
        setListeners()
        handleError()
    }

    private fun setAdapter() {
        itemList = ArrayList()
        itemListFiltered = ArrayList()
        binding.rvRepositories.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        itemAdapter = ItemAdapter(this, itemListFiltered)
        binding.rvRepositories.adapter = itemAdapter
    }

    private fun getData() {
        binding.swipeRefreshLayout.isRefreshing = true
        viewModel.getRepositories()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeDataForRepositories() {
        viewModel.itemLiveData.observe(this, {
            it?.let {
                itemList.clear()
                itemListFiltered.clear()
                itemList.addAll(it)
                itemListFiltered.addAll(itemList)
                itemAdapter.notifyDataSetChanged()
                binding.swipeRefreshLayout.isRefreshing = false
                Log.d(TAG, "${it.size} Repositories rendered default")
            }
        })
    }

    private fun setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            getData()
        }
        binding.btnRetry.setOnClickListener {
            getData()
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                repoFilter(s.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun repoFilter(keyword: String) {
        if (keyword.isBlank()) {
            itemListFiltered.clear()
            itemListFiltered.addAll(itemList)
        } else {
            itemListFiltered.clear()
            for (item in itemList) {
                val name = item.name
                name?.let {
                    if (name.contains(keyword.lowercase())) {
                        itemListFiltered.add(item)
                    }
                }
            }
        }
        itemAdapter.notifyDataSetChanged()
    }

    private fun handleError() {
        viewModel.isErrorLiveData.observe(this, {
            when (it) {
                null -> {
                    binding.clErrorLayout.visible(false)
                }
                else -> {
                    binding.clErrorLayout.visible(it)
                }
            }
            binding.swipeRefreshLayout.isRefreshing = false

        })
    }

}