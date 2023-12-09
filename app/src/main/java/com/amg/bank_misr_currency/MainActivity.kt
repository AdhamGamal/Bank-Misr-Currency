package com.amg.bank_misr_currency

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amg.bank_misr_currency.ui.MainVM
import com.amg.bank_misr_currency.ui.UiState
import com.amg.bank_misr_currency.ui.layouts.DropDownField
import com.amg.bank_misr_currency.ui.layouts.EditNumberField
import com.amg.bank_misr_currency.ui.layouts.LayoutLoading
import com.amg.bank_misr_currency.ui.listeners.CurrencySelectListener
import com.amg.bank_misr_currency.ui.theme.BankMisrCurrencyTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainVM: MainVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BankMisrCurrencyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainLayout(object : CurrencySelectListener {
                        override fun onCurrencySelectListener(type: Int, currency: String) {
                            when(type) {
                                R.string.to_label -> {
                                    mainVM.toCurrency.value = currency
                                }
                                R.string.from_label -> {
                                    mainVM.fromCurrency.value = currency
                                }
                            }
                        }
                    }, Modifier.fillMaxSize())
                }
            }
        }
    }
}


@Composable
fun MainLayout(
    listener: CurrencySelectListener,
    modifier: Modifier = Modifier,
    mainVM: MainVM = viewModel()
) {

    when (val uiState = mainVM.uiState) {
        is UiState.Loaded -> {
            MainContent(listener, uiState.symbols, modifier)
        }

        is UiState.Loading -> {
            LayoutLoading(modifier)
        }

        is UiState.Error -> {
            Text(modifier = modifier, text = stringResource(id = R.string.error_Loading_label))
        }
    }

}


@Composable
fun MainContent(
    listener: CurrencySelectListener,
    symbols: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )

        FromToDropdowns(
            modifier = Modifier.padding(bottom = 32.dp),
            listener = listener,
            values = symbols
        )

        ValuesTextFields(
            modifier = Modifier.padding(bottom = 32.dp),
        )
    }
}


@Composable
fun FromToDropdowns(
    modifier: Modifier = Modifier,
    listener: CurrencySelectListener,
    values: List<String>,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        DropDownField(label = R.string.from_label, listener, values)
        DropDownField(label = R.string.to_label, listener, values)
    }
}

@Composable
fun ValuesTextFields(
    modifier: Modifier = Modifier,
) {
    var amountInput by remember { mutableStateOf("1") }
    var tipInput by remember { mutableStateOf("1") }

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        EditNumberField(
            label = R.string.value,
            leadingIcon = R.drawable.money,
            value = amountInput,
            onValueChanged = { amountInput = it },
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .fillMaxWidth(),
        )
        EditNumberField(
            label = R.string.value,
            leadingIcon = R.drawable.money,
            value = tipInput,
            onValueChanged = { tipInput = it },
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .fillMaxWidth(),
        )

    }
}


@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    BankMisrCurrencyTheme {
        MainLayout(object : CurrencySelectListener {
            override fun onCurrencySelectListener(type: Int, currency: String) {
            }
        })
    }
}