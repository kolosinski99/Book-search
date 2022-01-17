package com.wojtek.rx_java_mvvm_example.ui.main

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout.VERTICAL
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wojtek.rx_java_mvvm_example.databinding.ActivityMainBinding
import com.wojtek.rx_java_mvvm_example.utils.ResultEvent
import com.wojtek.rx_java_mvvm_example.utils.onQueryTextChanged
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var my_adapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeUI()

        viewModel = getViewModel()

        viewModel.resultLiveData.observe(this) {
            it?.let { _result ->
                when (_result) {
                    is ResultEvent.Success -> {
                        hideError()
                    }

                    is ResultEvent.Error -> {
                        showError(_result.message)
                    }
                }
                hideProgressBar()
            }
        }
        viewModel.bookList.observe(this) {
            my_adapter.submitList(it.items)
        }


    }

    private fun initializeUI() {
        binding.apply {
            mainSearch.onQueryTextChanged {
                viewModel.searchQuery(it, false)
                showProgressBar()
            }

            mainRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                val decoration = DividerItemDecoration(applicationContext, VERTICAL)
                addItemDecoration(decoration)
                my_adapter = MainAdapter()
                adapter = my_adapter
            }
        }
    }

    private fun showProgressBar() {
        binding.mainProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.mainProgressBar.visibility = View.INVISIBLE
    }

    private fun showError(message: String) {
        binding.mainErrorTxt.apply {
            text = message
            visibility = View.VISIBLE
        }
    }

    private fun hideError() {
        binding.mainErrorTxt.apply {
            text = ""
            visibility = View.INVISIBLE
        }
    }
}