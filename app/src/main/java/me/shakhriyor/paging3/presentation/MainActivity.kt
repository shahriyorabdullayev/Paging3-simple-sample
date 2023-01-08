package me.shakhriyor.paging3.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.shakhriyor.paging3.R
import me.shakhriyor.paging3.databinding.ActivityMainBinding
import me.shakhriyor.paging3.presentation.adapter.RepoListAdapter
import me.shakhriyor.paging3.presentation.adapter.RepoLoadStateAdapter
import me.shakhriyor.paging3.presentation.viewmodel.RepoListViewModel

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val adapter by lazy { RepoListAdapter() }
    private var viewModel: RepoListViewModel? = null
    private var searchJob: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[RepoListViewModel::class.java]

        binding.rvMain.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.rvMain.adapter = adapter.withLoadStateHeaderAndFooter(
            header =RepoLoadStateAdapter(retry = {search("google")}),
            footer = RepoLoadStateAdapter(retry = {search("google")})
        )
        search("google")
    }

    private fun search(userName: String) {
searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel?.searchRepos(userName)?.collect {
                adapter.submitData(it)
            }
        }
    }
}