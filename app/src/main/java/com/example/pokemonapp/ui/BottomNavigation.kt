package com.example.pokemonapp.ui


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pokemonapp.ui.model.Routes
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun BottomNavigation(navigationController: NavHostController) {
    var index by remember { mutableStateOf(0)}

    androidx.compose.material.BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(12.dp)
            //.alpha(0.5f)
            .clip(RoundedCornerShape(20.dp))

    ) {
        BottomNavigationItem(
            selected = index == 0,
            onClick = {
                index = 0
                navigationController.navigate(Routes.PokemonsScreen.route)
                },
            icon = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home"
            ) 
        }, label = { Text(text = "Home")})
        BottomNavigationItem(
            selected = index == 1,
            onClick = {
                index = 1
                navigationController.navigate(Routes.SearchScreen.route)
            },
            icon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        }, label = { Text(text = "Search")})
    }
}