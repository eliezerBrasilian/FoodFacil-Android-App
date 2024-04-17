package com.foodfacil.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.foodfacil.dataclass.Salgado

@Composable
 fun ChartTop(
    cvm: List<Salgado>?,
    incrementOnClick: (salgadoId: String) -> Unit = { s: String -> },
    decrementOnClick: (salgadoId: String) -> Unit = { s: String -> }
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .heightIn(max = 250.dp)
    ) {
        //lazycolumn
        if (!cvm.isNullOrEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(cvm) { salgado ->
                    ChartItem(
                        salgado,
                        increment = incrementOnClick, decrement = decrementOnClick
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}