package com.ramazanpeker.calculatorcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramazanpeker.calculatorcompose.ui.theme.LightGray
import com.ramazanpeker.calculatorcompose.ui.theme.Orange

@Composable
fun Calculator(
    state: CalculatorState,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 8.dp,
    onActionClick: (CalculatorActions) -> Unit
) {
    val rows = listOf(
        listOf(
            ButtonConfig("AC", LightGray, 2f) { onActionClick(CalculatorActions.Clear) },
            ButtonConfig("Del", LightGray) { onActionClick(CalculatorActions.Delete) },
            ButtonConfig("/", Orange) { onActionClick(CalculatorActions.Operation(CalculatorOperation.Divide)) }
        ),
        (7..9).map {
            ButtonConfig("$it", Color.DarkGray) { onActionClick(CalculatorActions.Number(it)) }
        } + ButtonConfig("x", Orange) { onActionClick(CalculatorActions.Operation(CalculatorOperation.Multiply)) },
        (4..6).map {
            ButtonConfig("$it", Color.DarkGray) { onActionClick(CalculatorActions.Number(it)) }
        } + ButtonConfig("-", Orange) { onActionClick(CalculatorActions.Operation(CalculatorOperation.Substract)) },
        (1..3).map {
            ButtonConfig("$it", Color.DarkGray) { onActionClick(CalculatorActions.Number(it)) }
        } + ButtonConfig("+", Orange) { onActionClick(CalculatorActions.Operation(CalculatorOperation.Add)) },
        listOf(
            ButtonConfig("0", Color.DarkGray, aspectRatio = 2f) { onActionClick(CalculatorActions.Number(0)) },
            ButtonConfig(".", Color.DarkGray) { onActionClick(CalculatorActions.Decimal) },
            ButtonConfig("=", Orange) { onActionClick(CalculatorActions.Calculate) }
        )
    )

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            Display(state = state)
            rows.forEach { row ->
                CalculatorRow(buttonSpacing, row)
            }
        }
    }
}

@Composable
fun Display(state: CalculatorState) {
    Text(
        text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
        textAlign = TextAlign.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
        fontWeight = FontWeight.Light,
        fontSize = 70.sp,
        color = Color.White,
        maxLines = 3
    )
}

@Composable
fun CalculatorRow(
    buttonSpacing: Dp,
    buttons: List<ButtonConfig>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        buttons.forEach { config ->
            CalculatorButton(
                symbol = config.symbol,
                modifier = Modifier
                    .background(config.backgroundColor)
                    .aspectRatio(config.aspectRatio ?: 1f)
                    .weight(config.aspectRatio ?: 1f),
                onClick = { config.onClick() }
            )
        }
    }
}

data class ButtonConfig(
    val symbol: String,
    val backgroundColor: Color,
    val aspectRatio: Float? = null,
    val onClick: () -> Unit
)