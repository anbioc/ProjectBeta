package projectbeta.amir

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.google.android.material.composethemeadapter.MdcTheme
import projectbeta.amir.feature_detail.databinding.DetailFragmentBinding


class DetailFragment: Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
//    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeContainer.setContent {
            MdcTheme {
                initMainView()
            }
        }
//        viewModel.handleEvent(GetItems)
    }

    @Preview
    @Composable
    private fun initMainView() {
        Scaffold {
            Box(modifier = Modifier.offset(0.dp, 16.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(modifier = Modifier.offset(0.dp, 16.dp))
                Text(
                    text = "Item processing",
                    modifier = Modifier.offset(0.dp, 16.dp)
                )
            }
        }
    }
}