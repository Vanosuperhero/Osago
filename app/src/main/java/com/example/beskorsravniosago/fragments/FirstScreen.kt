package com.example.sravniru_android.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.createBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.beskorsravniosago.fragments.FirstScreenViewModel
//import androidx.compose.runtime.livedata.observeAsState
import com.example.beskorsravniosago.R
import com.example.beskorsravniosago.ui.theme.*
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState


class FirstScreen : Fragment() {

    private val viewModel: FirstScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                FinalScreen(viewModel)
            }
        }
    }

    @Composable
    fun FinalScreen(viewModel: FirstScreenViewModel) {
        Box {
            Box(modifier = Modifier.align(Alignment.TopCenter)) {
                Screen(viewModel)
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                Calculation()
            }
        }
    }


    @Composable
    fun Screen(viewModel: FirstScreenViewModel) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
                .background(Accent_Blue_06),
        ) {
            item {
                Text(
                    fontSize = 28.sp,
                    text = "Калькулятор ОСАГО",
                    fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Bold,
                    color = almostBlack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
            item {
                CardCalc(viewModel)
            }
            item {
                InputFieldsCard()
            }
        }
    }


    @Composable
    fun CardCalc(viewModel: FirstScreenViewModel) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
//        Без ViewModel:
//        var expanded by remember { mutableStateOf(false) }
//        Column(Modifier.clickable { expanded = !expanded }) {

            val expanded: State<Boolean?> = viewModel.expanded.observeAsState()
            Column(Modifier.clickable { viewModel.refresh()}) {
                Row() {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Main_Main_06)
                            .size(width = 36.dp, height = 36.dp),

                        ) {
                        Image(
                            modifier = Modifier
                                .size(15.dp)
                                .align(alignment = Alignment.Center),
                            painter = painterResource(R.drawable.calculator),
                            contentDescription = "Значок калькулятора"
                        )
                    }
                    Column() {
                        Text(
                            text = "Ваши коэффициенты",
                            fontSize = 12.sp,
                            fontFamily = MyFontsFamily,
                            fontWeight = FontWeight.Normal,
                            color = Main_Main_30,
                            modifier = Modifier
                                .padding(top = 16.dp)
                        )
                        Row() {
                            Text(
                                text = "БТ",
                                fontSize = 16.sp,
                                fontFamily = MyFontsFamily,
                                fontWeight = FontWeight.Medium,
                                color = Accent_Blue_100
                            )
//                        Найти SF PRO TEXT
                            Text(text = "x", fontSize = 15.sp, color = Main_Main_30)
                            Text(
                                text = "КМ",
                                fontSize = 16.sp,
                                fontFamily = MyFontsFamily,
                                fontWeight = FontWeight.Medium,
                                color = Accent_Blue_100
                            )
                            Text(text = "x", fontSize = 15.sp, color = Main_Main_30)
                            Text(
                                text = "КТ",
                                fontSize = 16.sp,
                                fontFamily = MyFontsFamily,
                                fontWeight = FontWeight.Medium,
                                color = Accent_Blue_100
                            )
                            Text(text = "x", fontSize = 15.sp, color = Main_Main_30)
                            Text(
                                text = "КБМ",
                                fontSize = 16.sp,
                                fontFamily = MyFontsFamily,
                                fontWeight = FontWeight.Medium,
                                color = Accent_Blue_100
                            )
                            Text(text = "x", fontSize = 15.sp, color = Main_Main_30)
                            Text(
                                text = "КО",
                                fontSize = 16.sp,
                                fontFamily = MyFontsFamily,
                                fontWeight = FontWeight.Medium,
                                color = Accent_Blue_100
                            )
                            Text(text = "x", fontSize = 15.sp, color = Main_Main_30)
                            Text(
                                text = "КВС",
                                fontSize = 16.sp,
                                fontFamily = MyFontsFamily,
                                fontWeight = FontWeight.Medium,
                                color = Accent_Blue_100
                            )
                        }
                    }
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            modifier = Modifier
                                .align(alignment = Alignment.CenterEnd)
                                .padding(top = 29.dp, end = 20.dp, bottom = 29.dp)
                                .size(width = 16.dp, height = 10.dp),
                            painter = painterResource(R.drawable.check_mark),
                            contentDescription = "Стрелка вниз"
                        )
                    }
                }
                AnimatedVisibility(expanded.value!!) {
                    CoefficientCollumn()
                }
            }
        }
    }


    @Composable
    fun CoefficientCollumn() {
        Column {
            Coefficient(
                "БТ",
                "базовый тариф",
                "Устанавливает страховая" + "\n" + "компания",
                "2 754 - 4 432"
            )
            Coefficient(
                "КМ",
                "коэфф. мощности",
                "Чем мощнее автомобиль," + "\n" + "тем дороже страховой полис",
                "0,6 - 1,6"
            )
            Coefficient(
                "КТ",
                "территориальный коэфф.",
                "Определяется по прописке" + "\n" + "собственника автомобиля",
                "0,64 - 1,99"
            )
            Coefficient(
                "КБМ",
                "коэфф. безаварийности",
                "Учитывается только самый" + "\n" + " высокий коэффициент из всех" + "\n" + "водителей",
                "0,5 - 2,45"
            )
            Coefficient(
                "КВС",
                "коэфф. возраст/стаж",
                "Чем больше возраст и стаж" + "\n" + "у вписанного в полис водителя," + "\n" + "тем дешевле будет полис",
                "0,90 - 1,93"
            )
            Coefficient(
                "КО",
                "коэфф. ограничений",
                "Полис с ограниченным списком" + "\n" + "водителей будет стоить дешевле",
                "1 или 1,99"
            )
        }
    }

    @Composable
    fun Coefficient(coef: String, name: String, describ: String, calc: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Column {
                Row() {
                    Text(
                        text = coef,
                        fontSize = 16.sp,
                        fontFamily = MyFontsFamily,
                        fontWeight = FontWeight.Medium,
                        color = Main_Main_100
                    )
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                            .padding(start = 4.dp),
                        text = "($name)",
                        fontSize = 12.sp,
                        fontFamily = MyFontsFamily,
                        fontWeight = FontWeight.Normal,
                        color = Main_Main_30
                    )
                }
                Text(
                    text = describ,
                    fontSize = 12.sp,
                    fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Normal,
                    color = Main_Main_60
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd),
                    text = calc,
                    fontSize = 16.sp,
                    fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Medium,
                    color = Main_Main_100
                )

            }
        }
    }

    @Composable
    fun InputField(text: String) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Main_Main_06)
                .clickable { println("Hello") }
        )
        {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .align(alignment = Alignment.CenterStart),
                text = text,
                fontSize = 16.sp,
                fontFamily = MyFontsFamily,
                fontWeight = FontWeight.Normal,
                color = Main_Main_60
            )
        }

    }

    @Composable
    fun InputFieldsCard() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            Column(modifier = Modifier.padding(vertical = 11.dp)) {
                InputField(text = "Город регистрации" + "\n" + "собственника")
                InputField(text = "Мощность автомобиля")
                InputField(text = "Сколько водителей")
                InputField(text = "Возраст младшего из водителей")
                InputField(text = "Минимальный стаж водителей")
                InputField(text = "Сколько лет не было аварий")
            }
        }
    }


    @Composable
    fun Calculation() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Main_Main_06)
                    .clickable { println("Hello") }
            )
            {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .align(alignment = Alignment.Center),
                    text = "Рассчитать ОСАГО",
                    fontSize = 16.sp,
                    fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Normal,
                    color = Main_Main_60
                )
            }
        }
    }


}