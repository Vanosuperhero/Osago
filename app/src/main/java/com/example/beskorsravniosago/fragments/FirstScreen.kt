package com.example.beskorsravniosago.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.fragment.findNavController
import com.example.beskorsravniosago.collections.coefficients
import com.example.beskorsravniosago.network.Factor
import com.example.beskorsravniosago.viewmodels.ApiStatus
import com.example.beskorsravniosago.viewmodels.FirstScreenViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.imePadding
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
                    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                        HomeScreen()

                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
    @Composable
    fun FinalScreen(
        viewModel: FirstScreenViewModel,
        content: @Composable () -> Unit,
        title: @Composable () -> Unit,
        onClick: () -> Unit,
        text: Int,
        button: Boolean
    ) {
        if (viewModel.bottomSheetScaffoldState.bottomSheetState.isCollapsed){
            viewModel.getDataCoefficients()
        }
        Box {
            Box(modifier = Modifier.align(Alignment.TopCenter)) {
                Screen(viewModel,{ content() },{ title() })
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                Calculation(onClick, text, viewModel, button)
            }
        }
    }

    @Composable
    fun Screen(
        viewModel: FirstScreenViewModel,
        content: @Composable () -> Unit,
        title: @Composable () -> Unit
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
                .background(MaterialTheme.colors.onSecondary),
        ) {
            item {
                title()
            }
            item {
                CardCalc(viewModel)
            }
            item {
                content()
            }
        }
    }

    @Composable
    fun FirstTitle() {
        Text(
            fontSize = 28.sp,
            text = stringResource(R.string.first_title),
            fontFamily = MyFontsFamily,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }

    @Composable
    fun CardCalc(
        viewModel: FirstScreenViewModel
    ) {
        val coefficients = viewModel.liveCoefficients.value.factors
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.primaryVariant)
        ) {
            val expanded by remember { viewModel.expanded }
            Column(
                Modifier
                    .clickable { viewModel.refresh() }
                    .background(MaterialTheme.colors.primaryVariant)
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 12.dp)
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
                            for (i in 0..5){
                                Text(
                                    text = coefficients[i].headerValue,
                                    fontSize = 16.sp,
                                    fontFamily = MyFontsFamily,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colors.primary
                                )
                                if (i != 5){
                                    Text(
                                        text = stringResource(R.string.x),
                                        fontSize = 15.sp,
                                        color = MaterialTheme.colors.surface
                                    )
                                }
                            }
                        }
                    }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            modifier = Modifier
                                .align(alignment = Alignment.CenterEnd)
                                .padding(top = 29.dp, end = 20.dp, bottom = 29.dp)
                                .size(width = 16.dp, height = 10.dp),
                            painter = if (expanded) {
                                painterResource(R.drawable.check_mark_flip)
                            } else {
                                painterResource(R.drawable.check_mark)
                            },
                            contentDescription = stringResource(R.string.check_mark)
                        )
                    }
                }
                AnimatedVisibility(expanded) {
                    CoefficientColumn(coefficients)
                }
            }
        }
    }

    @Composable
    fun CoefficientColumn(factors: List<Factor>) {
        Column {
            for (i in 0..5){
                Coefficient(factors[i], coefficients.factors[i])
            }
        }
    }

    @Composable
    fun Coefficient(coefficient: Factor, forDetailText: Factor) {
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
    fun Calculation(
        onClick: () -> Unit,
        text: Int,
        viewModel: FirstScreenViewModel,
        button:Boolean
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colors.primaryVariant)
        ) {
            Button(
                enabled = button,
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    disabledBackgroundColor = MaterialTheme.colors.onSecondary,
                    contentColor = MaterialTheme.colors.primaryVariant,
                    disabledContentColor = MaterialTheme.colors.onPrimary
                )
            )
            {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = stringResource(text),
                    fontSize = 15.sp,
                    fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

    @Composable
    fun InputFieldsCard() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.primaryVariant)
        ) {
            Column(modifier = Modifier.padding(vertical = 11.dp)) {
                for (i in 0..5){
                    Input(i)
                }
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun Input(
        field: Int
    ) {
        val scope = rememberCoroutineScope()
        val keyboardController = LocalSoftwareKeyboardController.current
        if (viewModel.fieldList[field].livedata.value.isBlank()) {
            InputField(field,scope,keyboardController)
        }else{
            AfterInputField(field,scope,keyboardController)
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun InputField(
        field: Int,
        scope: CoroutineScope,
        keyboardController:  SoftwareKeyboardController?
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colors.onSecondary)
                .clickable {
                    viewModel.setDataToSheet(field)
                    viewModel.refreshSheet()
                    keyboardController?.show()
                    scope.launch {
                        viewModel.expand()
                    }
                }
        )
        {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .align(alignment = Alignment.CenterStart),
                text = stringResource(viewModel.fieldList[field].title),
                fontSize = 16.sp,
                fontFamily = MyFontsFamily,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colors.background
            )
        }
    }

    @OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)
    @Composable
    fun AfterInputField(
        field: Int,
        scope: CoroutineScope,
        keyboardController:  SoftwareKeyboardController?
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colors.onSecondary)
                .clickable {
                    viewModel.setDataToSheet(field)
                    viewModel.refreshSheet()
                    keyboardController?.show()
                    scope.launch {
                        viewModel.expand()
                    }
                }
        )
        {
            Column {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 9.dp, bottom = 1.dp)
                        .align(alignment = Alignment.Start),
                    text = stringResource(viewModel.fieldList[field].titleAfter),
                    fontSize = 12.sp,
                    fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colors.background
                )
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 6.dp)
                        .align(alignment = Alignment.Start),
                    text = viewModel.fieldList[field].livedata.value,
                    fontSize = 16.sp,
                    fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }


    @OptIn(androidx.compose.material.ExperimentalMaterialApi::class,
        androidx.compose.ui.ExperimentalComposeUiApi::class
    )
    @Composable
    fun BottomSheetContent() {
        val scope = rememberCoroutineScope()
        BackHandler(
            enabled = viewModel.bottomSheetScaffoldState.bottomSheetState.isExpanded,
            onBack = {
                scope.launch {
                    viewModel.collapse()
                }
            }
        )
        Box(Modifier
            .background(MaterialTheme.colors.primaryVariant)
        ) {
            val field = viewModel.field.value
            Column(modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp, max = 750.dp)
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
                    text = stringResource(viewModel.fieldList[field].title),
                    fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                )
                val keyboardController = LocalSoftwareKeyboardController.current
                val focusRequester = remember {FocusRequester()}
                LaunchedEffect(true) {
                    focusRequester.requestFocus()
                }
                OutlinedTextField(
//                    enabled = (viewModel.bottomSheetScaffoldState.bottomSheetState.isExpanded || viewModel.bottomSheetScaffoldState.bottomSheetState.isAnimationRunning),
//                    enabled = viewModel.sheetState.value,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = viewModel.fieldList[field].keyboardType),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .focusRequester(focusRequester),
                    value = viewModel.fieldList[field].livedata.value,
                    onValueChange = {viewModel.setInput(it)},
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(textColor = MaterialTheme.colors.onBackground, cursorColor = Color.Black),
                    placeholder = { Text(text = stringResource(viewModel.fieldList[field].placeholder), color = MaterialTheme.colors.surface) },
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .padding(bottom = 300.dp)
                ){
                    Button(modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(18.dp)),
                        onClick = {
                            if(viewModel.field.value < 5) {
                                keyboardController?.show() }
                            else{
                                keyboardController?.hide() }
                            scope.launch {
                                viewModel.next()
                            }
                        }
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp, start = 7.dp),
                            text = if (field != 5) {
                                stringResource(R.string.next)
                            } else{
                                stringResource(R.string.confirm)
                            },
                            fontSize = 16.sp,
                            fontFamily = MyFontsFamily,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colors.primaryVariant,
                        )
                        if (field != 5) {
                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                            Icon(
                                painter = painterResource(R.drawable.shape),
                                contentDescription = stringResource(R.string.arrow),
                                modifier = Modifier
                                    .size(12.dp)
                                    .padding(top = 2.dp),
                                tint = MaterialTheme.colors.primaryVariant
                            )
                        }
                        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                    }
                    if (field != 0) {
                        Button(modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                            .size(59.dp),
                            elevation = ButtonDefaults.elevation(
                                defaultElevation = 0.dp,
                                pressedElevation = 0.dp,
                                disabledElevation = 0.dp
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colors.onSecondary),
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                            onClick = {
                                viewModel.back()
                                keyboardController?.show()},
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.shape_flip),
                                contentDescription = stringResource(R.string.arrow),
                                modifier = Modifier
                                    .size(14.dp),
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                }
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable
    fun HomeScreen() {
//        val
        val focusManager = LocalFocusManager.current
        var button by remember {mutableStateOf(false)}
        button = viewModel.fieldList.all { it.livedata.value.isNotBlank() }
        focusManager.clearFocus()
        BottomSheetScaffold(
            scaffoldState = viewModel.bottomSheetScaffoldState,
            sheetContent = {
                BottomSheetContent()
            },
            sheetPeekHeight = 0.dp,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetBackgroundColor = Color.White,
        ) {
            FinalScreen(
                viewModel,
                { InputFieldsCard() },
                { FirstTitle() },
                { if (viewModel.statusCoefficients.value == ApiStatus.DONE)
                { findNavController().navigate(R.id.action_firstScreen_to_secondScreen) } },
                R.string.calc_button_first,
                button
            )
        }
    }
}