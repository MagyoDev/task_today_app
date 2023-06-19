package com.example.attavl

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attavl.model.Tarefa.Tarefa
import com.example.attavl.ui.theme.AttAvlTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))
        }
    }
}


@Composable
fun MainScreenContent(drawerState: DrawerState) {
    val scaffoldState = rememberScaffoldState(drawerState = drawerState)
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "TaskAppBar") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Drawer Menu"
                        )
                    }
                }
            )
        },
        drawerBackgroundColor = Color.Green,
        drawerGesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Box(
                modifier = Modifier
                    .background(Color.Magenta)
                    .height(16.dp)
            ) {
                Text(text = "Opções")
            }
            Column {
                Text(text = "Opção de Menu 1")
                Text(text = "Opção de Menu 2")
                Text(text = "Opção de Menu 3")
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .background(Color.Green)
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                MySearchField(modifier = Modifier.fillMaxWidth())

                val tProvaDeCalculo = Tarefa(
                    "A",
                    "B",
                    Date(),
                    Date(),
                    status = 0.0
                )

                val tProvaDeKotlin = Tarefa(
                    "A",
                    "B",
                    Date(),
                    Date(),
                    status = 0.0
                )

                val minhaListaDeTarefas = listOf(tProvaDeCalculo, tProvaDeKotlin)

                MyTaskWidgetList(minhaListaDeTarefas)
            }
        },
        bottomBar = {
            BottomAppBar(
                content = { Text("Bar") }
            )
        },
        isFloatingActionButtonDocked = false,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "ADD TASK"
                    )
                },
                text = { Text(text = "ADD") },
                onClick = { /*TODO*/ }
            )
        }
    )
}

@Composable
fun MyTaskWidgetList(listaDeTarefas: List<Tarefa>) {
    listaDeTarefas.forEach { tarefa ->
        MyTaskWidget(modifier = Modifier.fillMaxWidth(), tarefaASerMostrada = tarefa)
    }
}

@Composable
fun MySearchField(modifier: Modifier) {
    TextField(
        value = "",
        onValueChange = {},
        modifier = modifier,
        placeholder = { Text(text = "Pesquisar Tarefas") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        }
    )
}

@Composable
fun MyTaskWidget(
    modifier: Modifier,
    tarefaASerMostrada: Tarefa
) {
    val dateFormatter = SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault())
    Row(modifier = modifier) {
        Column {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Icons of a pendent task"
            )
            Text(
                text = dateFormatter.format(tarefaASerMostrada.pzoFinal),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp
            )
        }
        Column(
            modifier = modifier
                .border(width = 1.dp, color = Color.Black)
                .padding(3.dp)
        ) {
            Text(
                text = tarefaASerMostrada.nome,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = tarefaASerMostrada.detalhes,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))
}