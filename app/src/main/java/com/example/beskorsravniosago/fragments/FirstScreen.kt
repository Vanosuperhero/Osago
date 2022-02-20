package com.example.beskorsravniosago.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import com.example.beskorsravniosago.R
import com.example.beskorsravniosago.ui.theme.*
import androidx.compose.material.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.beskorsravniosago.collections.InputFieldData
import com.example.beskorsravniosago.network.Coefficients
import com.example.beskorsravniosago.network.coefficients
import com.example.beskorsravniosago.viewmodels.FirstScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class FirstScreen : Fragment() {

    private val viewModel: FirstScreenViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                BeskorSravniOsagoTheme{
                    HomeScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun FinalScreen(
        scope: CoroutineScope,
        modalBottomSheetState: BottomSheetScaffoldState
    ) {
        if (modalBottomSheetState.bottomSheetState.isCollapsed) {
            viewModel.getDataCoefficients()
        }
        Box {
            Box(modifier = Modifier.align(Alignment.TopCenter)) {
                Screen(scope, modalBottomSheetState)
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                Calculation()
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Screen(
        scope: CoroutineScope,
        modalBottomSheetState:  BottomSheetScaffoldState
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
                .background(MaterialTheme.colors.onSecondary),
        ) {
            item {
                Text(
                    fontSize = 28.sp,
                    text = stringResource(R.string.main_title),
                    fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
            item {
                CardCalc(viewModel.liveCoefficients.value.factors)
            }
            item {
                InputFieldsCard(scope, modalBottomSheetState)
            }
        }
    }

    @Composable
    fun CardCalc(coefficients: List<Coefficients>) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.primaryVariant),
            elevation = 50.dp,
        ) {
            val expanded: State<Boolean> = viewModel.expanded
            Column(
                Modifier
                    .clickable { viewModel.refresh() }
                    .background(MaterialTheme.colors.primaryVariant)) {
                Row {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colors.onSecondary)
                            .size(width = 36.dp, height = 36.dp),
                    ) {
                        Image(
                            modifier = Modifier
                                .size(15.dp)
                                .align(alignment = Alignment.Center),
                            painter = painterResource(R.drawable.calculator),
                            contentDescription = stringResource(R.string.calculator),
                        )
                    }
                    Column {
                        Text(
                            text = stringResource(R.string.coefs),
                            fontSize = 12.sp,
                            fontFamily = MyFontsFamily,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colors.surface,
                            modifier = Modifier
                                .padding(top = 16.dp)
                        )
                        Row {
                            Text(
                                text = coefficients[0].headerValue,
                                fontSize = 16.sp,
                                fontFamily = MyFontsFamily,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colors.primary
                            )
                            Text(
                                text = stringResource(R.string.x),
                                fontSize = 15.sp,
                                color = MaterialTheme.colors.surface
                            )
                            Text(
                                text = coefficients[1].headerValue,
                                fontSize = 16.sp,
                                fontFamily = MyFontsFamily,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colors.primary
                            )
                            Text(
                                text = stringResource(R.string.x),
                                fontSize = 15.sp,
                                color = MaterialTheme.colors.surface
                            )
                            Text(
                                text = coefficients[2].headerValue,
                                fontSize = 16.sp,
                                fontFamily = MyFontsFamily,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colors.primary
                            )
                            Text(
                                text = stringResource(R.string.x),
                                fontSize = 15.sp,
                                color = MaterialTheme.colors.surface
                            )
                            Text(
                                text = coefficients[3].headerValue,
                                fontSize = 16.sp,
                                fontFamily = MyFontsFamily,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colors.primary
                            )
                            Text(
                                text = stringResource(R.string.x),
                                fontSize = 15.sp,
                                color = MaterialTheme.colors.surface
                            )
                            Text(
                                text = coefficients[5].headerValue,
                                fontSize = 16.sp,
                                fontFamily = MyFontsFamily,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colors.primary
                            )
                            Text(
                                text = stringResource(R.string.x),
                                fontSize = 15.sp,
                                color = MaterialTheme.colors.surface
                            )
                            Text(
                                text = coefficients[4].headerValue,
                                fontSize = 16.sp,
                                fontFamily = MyFontsFamily,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            modifier = Modifier
                                .align(alignment = Alignment.CenterEnd)
                                .padding(top = 29.dp, end = 20.dp, bottom = 29.dp)
                                .size(width = 16.dp, height = 10.dp),
                            painter = if (expanded.value) {
                                painterResource(R.drawable.check_mark_flip)
                            } else {
                                painterResource(R.drawable.check_mark)
                            },
                            contentDescription = stringResource(R.string.check_mark)
                        )
                    }
                }
                AnimatedVisibility(expanded.value) {
                    CoefficientColumn(coefficients)
                }
            }
        }
    }

    @Composable
    fun CoefficientColumn(coefficient: List<Coefficients>) {
        Column {
            Coefficient(coefficient[0], coefficients.factors[0])
            Coefficient(coefficient[1], coefficients.factors[1])
            Coefficient(coefficient[2], coefficients.factors[2])
            Coefficient(coefficient[3], coefficients.factors[3])
            Coefficient(coefficient[4], coefficients.factors[4])
            Coefficient(coefficient[5], coefficients.factors[5])
        }
    }

    @Composable
    fun Coefficient(coefficient: Coefficients, forDetailText: Coefficients) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Column {
                Row {
                    Text(
                        text = coefficient.title,
                        fontSize = 16.sp,
                        fontFamily = MyFontsFamily,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.secondary
                    )
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                            .padding(start = 4.dp),
                        text = "(${coefficient.name})",
                        fontSize = 12.sp,
                        fontFamily = MyFontsFamily,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colors.surface
                    )
                }
                Text(
                text = forDetailText.detailText,
                fontSize = 12.sp,
                fontFamily = MyFontsFamily,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colors.background
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
                    text = coefficient.value,
                    fontSize = 16.sp,
                    fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.secondary
                )

            }
        }
    }

    @Composable
    fun Calculation() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colors.primaryVariant)
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onSecondary)
            )
            {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    text = stringResource(R.string.calc_button),
                    fontSize = 16.sp,
                    fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun InputFieldsCard(
        scope: CoroutineScope,
        modalBottomSheetState:  BottomSheetScaffoldState) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.primaryVariant)
        ) {
            Column(modifier = Modifier.padding(vertical = 11.dp)) {
                InputField(viewModel.field1, scope, modalBottomSheetState)
                InputField(viewModel.field2, scope, modalBottomSheetState)
                InputField(viewModel.field3, scope, modalBottomSheetState)
                InputField(viewModel.field4, scope, modalBottomSheetState)
                InputField(viewModel.field5, scope, modalBottomSheetState)
                InputField(viewModel.field6, scope, modalBottomSheetState)
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun InputField(
        input: InputFieldData,
        scope: CoroutineScope,
        modalBottomSheetState:  BottomSheetScaffoldState,
        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colors.onSecondary)
                .clickable {
                    viewModel.setDataToSheet(input)
                    scope.launch {
                        modalBottomSheetState.bottomSheetState.expand()
                    }
                }
        )
        {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .align(alignment = Alignment.CenterStart),
                text = input.livedata.value.ifBlank {
                    stringResource(input.title)
                },
                fontSize = 16.sp,
                fontFamily = MyFontsFamily,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colors.background
            )
        }
    }

    @Composable
    fun BottomSheetContent() {
        Box(Modifier.background(MaterialTheme.colors.onSecondary)) {
            Column(modifier = Modifier
                .fillMaxWidth()
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        modifier = Modifier
                            .align(alignment = Alignment.TopCenter)
                            .padding(top = 8.dp),
                        painter = painterResource(R.drawable.tip),
                        contentDescription = stringResource(R.string.tip)
                    )
                }
                Text(
                    fontSize = 20.sp,
                    text = stringResource(viewModel.field.value.title),
                    fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                )
                val focusManager = LocalFocusManager.current
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = viewModel.field.value.keyboardType),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = viewModel.field.value.livedata.value,
                    onValueChange = {viewModel.field.value.setFun(it)},
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(textColor = MaterialTheme.colors.onBackground),
                    placeholder = { Text(text = stringResource(viewModel.field.value.placeholder), color = MaterialTheme.colors.surface) },
                )
                Spacer(modifier = Modifier.padding(vertical = 80.dp))
                Box(modifier = Modifier
                    .fillMaxWidth()
                ){
                    Button(modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(18.dp)),
                        onClick = { viewModel.getDataCoefficients() }
                    ) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 10.dp, start = 7.dp),
                                text = stringResource(R.string.next),
                                fontSize = 16.sp,
                                fontFamily = MyFontsFamily,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colors.primaryVariant,
                            )
                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                            Icon(
                                painter = painterResource(R.drawable.shape),
                                contentDescription = stringResource(R.string.arrow),
                                modifier = Modifier
                                    .size(12.dp)
                                    .padding(top = 2.dp),
                                tint = MaterialTheme.colors.primaryVariant
                            )
                            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                        }
                }
                Spacer(modifier = Modifier.padding(vertical = 190.dp))
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable
    fun HomeScreen() {
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
        )
        val scope = rememberCoroutineScope()
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                BottomSheetContent()
            },
            sheetPeekHeight = 0.dp,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetBackgroundColor = Color.White,
        ) {
            FinalScreen(scope, bottomSheetScaffoldState)
        }
    }
}