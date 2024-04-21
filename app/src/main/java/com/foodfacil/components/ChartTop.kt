package com.foodfacil.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.foodfacil.dataclass.Adicional
import com.foodfacil.dataclass.Salgado

@Composable
 fun ChartTop(
    cvm: List<Salgado>?,
    adicionaisSelectedStateList: State<List<Adicional>?>,
    incrementOnClick: (salgado: Salgado) -> Unit = { s -> },
    decrementOnClick: (salgado: Salgado) -> Unit = { s -> },
    incrementAdicionalOnClick: (adicional: Adicional) -> Unit = { a -> },
    decrementAdicionalOnClick: (adicional: Adicional) -> Unit = { a -> }
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp)
    ) {
        //lazycolumn
        if (!cvm.isNullOrEmpty()) {
            cvm.forEach{
                Column {
                    ChartItem(it, increment = incrementOnClick, decrement = decrementOnClick)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        if(adicionaisSelectedStateList.value?.isNotEmpty() == true){
            adicionaisSelectedStateList.value?.forEach{adicionalItem->
                    Column {
                        AdicionalItem(adicionalItem,
                            increment = incrementAdicionalOnClick,
                            decrement = decrementAdicionalOnClick)
                        Spacer(modifier = Modifier.height(10.dp))
                    }

            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}