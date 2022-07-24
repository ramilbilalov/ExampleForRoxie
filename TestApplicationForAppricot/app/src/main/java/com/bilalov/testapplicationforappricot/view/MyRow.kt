package com.bilalov.testapplicationforappricot.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bilalov.testapplicationforappricot.data.request.rowData.RowInfo
import com.bilalov.testapplicationforappricot.navigation.Screen
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MyRow(item: RowInfo) {
    val isoReformat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ROOT)
    val date = item.orderTime.replace("\\+0([0-9])\\:00".toRegex(), "+0$100")
    val price =
        "${item.priceAmount.dropLast(2)}.${item.priceAmount.get(item.priceAmount.length - 2)}${
            item.priceAmount.get(item.priceAmount.length - 1)
        } ${item.priceCurrency}"
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                item.navController.navigate(
                    Screen.SecondView.withArgs(
                        item.photo,
                        item.startAddress,
                        isoReformat
                            .parse(date)
                            .toString(),
                        item.endAddress,
                        price,
                        item.vehicleName,
                        item.vehicleCar,
                    )
                )
            },
        shape = RoundedCornerShape(12.dp),
        elevation = 5.dp,
        border = BorderStroke(1.dp, Color.Black)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        )
        {
            Column {
                Text(
                    text = "From: ${item.startAddress}",
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "To: ${item.endAddress}",
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Order date: ${item.orderTime.dropLast(15)}",
                    modifier = Modifier.padding(8.dp)
                )
                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = 5.dp,
                    modifier = Modifier
                        .padding(8.dp),
                    border = BorderStroke(1.dp, Color.Red)
                ) {
                    Text(
                        text = price,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}
