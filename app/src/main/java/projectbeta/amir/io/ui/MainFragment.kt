package projectbeta.amir.io.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import projectbeta.amir.core.MainItemDomainEntity
import projectbeta.amir.io.R
import projectbeta.amir.io.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeContainer.setContent {
            MdcTheme {
                initMainView()
            }
        }
        viewModel.handleEvent(GetItems)
    }

    @Preview
    @Composable
    private fun initMainView() {
        val state = viewModel.liveData.observeAsState()
        Scaffold {
            Box(modifier = Modifier.offset(0.dp, 16.dp))
            when (state.value) {
                InitialState -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = getString(R.string.hello_there))
                        CircularProgressIndicator(modifier = Modifier.offset(0.dp, 16.dp))
                        Text(
                            text = getString(R.string.items_processing),
                            modifier = Modifier.offset(0.dp, 16.dp)
                        )
                    }
                }
                is ItemsAvailable -> {
                    createList(state)
                }
            }
        }
    }

    @Composable
    private fun createList(state: State<MainViewModelState?>) {
        LazyColumn(
            modifier = Modifier.offset(16.dp, 40.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                items = (state.value as ItemsAvailable).data,
                itemContent = {
                    prepareListItems(it)
                }
            )
        }
    }

    @Composable
    private fun prepareListItems(item: MainItemDomainEntity) {
        Row {
            Column(modifier = Modifier.offset(0.dp, 24.dp)) {
                ClickableText(text = AnnotatedString("Hello There!", ), onClick = {
                    findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
                })
                Text(text = item.name, style = typography.h6, color = Color.Green)
                Text(text = getString(R.string.view_detail), style = typography.caption)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}