package com.example.beskorsravniosago.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.core.view.WindowCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beskorsravniosago.collections.coefficients
import com.example.beskorsravniosago.network.Factor
import com.example.beskorsravniosago.viewmodels.ApiStatus
import com.example.beskorsravniosago.viewmodels.FirstScreenViewModel
import com.google.accompanist.insets.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.*


class FirstScreen : Fragment() {

    private val viewModel: FirstScreenViewModel by activityViewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
                WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
                requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            setContent {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = false) {
                BeskorSravniOsagoTheme{
                        HomeScreen()
                    }
                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
    @Composable
    fun FinalScreen(
        viewModel: FirstScreenViewModel,
        content: @Composable () -> Unit,
        title: @Composable () -> Unit,
        onClick: () -> Unit,
        text: Int,
        button: Boolean,
        coefficients: List<Factor>
    ) {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = MaterialTheme.colors.isLight
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons
            )
        }
        val scope = rememberCoroutineScope()

        val confirmStateChange = remember {    mutableStateOf(false)    }

        if (!viewModel.bottomSheetScaffoldState.isVisible && !viewModel.bottomSheetScaffoldState.isAnimationRunning && confirmStateChange.value && viewModel.confirm.value){
            viewModel.unConfirm()
        }
        if (!viewModel.bottomSheetScaffoldState.isVisible && !viewModel.bottomSheetScaffoldState.isAnimationRunning && confirmStateChange.value && viewModel.statusCoefficients.value != ApiStatus.DONE && !viewModel.confirm.value){
            viewModel.getDataCoefficients()
        }
        if (viewModel.bottomSheetScaffoldState.isVisible){
            confirmStateChange.value = true
        }
        if (viewModel.confirm.value && !viewModel.bottomSheetScaffoldState.isVisible && !viewModel.bottomSheetScaffoldState.isAnimationRunning){
            scope.launch {
                viewModel.expand()
            }
        }
        val insets = LocalWindowInsets.current
        val statusPadding = with(LocalDensity.current) { insets.statusBars.top.toDp() }
        val navPadding = with(LocalDensity.current) { insets.navigationBars.bottom.toDp() }
        Box(
            Modifier
                .background(MaterialTheme.colors.onSecondary)
                .padding(top = statusPadding, bottom = navPadding)
        ) {
            Box(modifier = Modifier
                .align(Alignment.TopCenter)) {
                Screen(viewModel,{ content() },{ title() }, coefficients)
            }
            Box(modifier = Modifier
                .align(Alignment.BottomCenter)
                ) {
                Calculation(onClick, text, button)
            }
        }
    }

    @Composable
    fun Screen(
        viewModel: FirstScreenViewModel,
        content: @Composable () -> Unit,
        title: @Composable () -> Unit,
        coefficients: List<Factor>
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
                .background(MaterialTheme.colors.onSecondary)
        ) {
            item {
                title()
            }
            item {
                CardCalc(viewModel, coefficients)
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
        viewModel: FirstScreenViewModel,
        coefficients: List<Factor>
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            val expanded by remember { viewModel.expanded }
            Column(
                Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) { viewModel.refresh() }
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
                    disabledContentColor = MaterialTheme.colors.onPrimary)
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

    @OptIn(ExperimentalComposeUiApi::class,
        ExperimentalMaterialApi::class,
    )
    @Composable
    fun Input(
        field: Int,
    ) {
        val scope = rememberCoroutineScope()
        val keyboardController = LocalSoftwareKeyboardController.current
        if (viewModel.fieldList[field].livedata.value.isBlank()) {
            InputField(field,scope,keyboardController)
        }else{
            AfterInputField(field,scope,keyboardController)
        }
    }

    @OptIn(ExperimentalComposeUiApi::class,
        ExperimentalMaterialApi::class,
    )
    @Composable
    fun InputField(
        field: Int,
        scope: CoroutineScope,
        keyboardController:  SoftwareKeyboardController?,
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

    @OptIn(ExperimentalComposeUiApi::class)
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


    @OptIn(ExperimentalMaterialApi::class,
        ExperimentalComposeUiApi::class
    )
    @Composable
    fun BottomSheetContent() {
        val scope = rememberCoroutineScope()
        BackHandler(
            enabled = viewModel.bottomSheetScaffoldState.isVisible,
            onBack = {
                scope.launch {
                    viewModel.collapse()
                }
            }
        )

        Box(
            Modifier
                .background(MaterialTheme.colors.primaryVariant)
                .padding(bottom = 16.dp)
                .fillMaxHeight(0.9f)
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            val field = viewModel.field.value
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
                    text = stringResource(viewModel.fieldList[field].title),
                    fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                )
                val focusRequester = remember { FocusRequester() }
                if (viewModel.bottomSheetScaffoldState.isVisible){
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                }
                 OutlinedTextField(
                     keyboardOptions = KeyboardOptions(
                     imeAction = ImeAction.Done,
                     keyboardType = viewModel.fieldList[field].keyboardType
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .focusRequester(focusRequester),
                    value = viewModel.fieldList[field].livedata.value,
                    onValueChange = { viewModel.setInput(it) },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colors.onBackground,
                        cursorColor = Color.Black
                    ),
                    placeholder = {
                    Text(
                        text = stringResource(viewModel.fieldList[field].placeholder),
                        color = MaterialTheme.colors.surface
                    )
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsWithImePadding()
                ) {
                    Button(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp)
                            .clip(RoundedCornerShape(18.dp)),
                        onClick = {
                            if (viewModel.field.value < 5) {
                                keyboardController?.show()
                            } else {
                                keyboardController?.hide()
                            }
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
                            } else {
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
                        Button(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
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
                                keyboardController?.show()
                            },
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

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun BottomSheetOffer(
        onBack: () -> Unit
    ) {
        val insets = LocalWindowInsets.current
        val imeBottom = with(LocalDensity.current) { insets.navigationBars.bottom.toDp() }
    val scope = rememberCoroutineScope()
    BackHandler(
            enabled = viewModel.bottomSheetScaffoldState.isVisible,
            onBack = {
                onBack()
                viewModel.unConfirm()
                viewModel.offer(null)
                findNavController().previousBackStackEntry?.savedStateHandle?.set("key", null)
            }
        )
        Box(
            Modifier
                .background(MaterialTheme.colors.primaryVariant)
                .padding(bottom = imeBottom)
        ) {
            Column(
                modifier = Modifier
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
                    text = stringResource(R.string.success),
                    fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 4.dp)
                )
                Box(Modifier.padding(vertical = 10.dp)){
                    viewModel.offer.value?.let {
                        SecondScreen().Offer(it) {}
                    }
                }
                Button(
                    onClick = {
                        scope.launch {
                            viewModel.collapse()
                            viewModel.unConfirm()
                            viewModel.offer.value = null
                            if (viewModel.statusCoefficients.value == ApiStatus.DONE ) {
                                findNavController().navigate(R.id.firstScreen) }
                            findNavController()
                                .previousBackStackEntry?.savedStateHandle?.set("key", null)
                        } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 16.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.primaryVariant)
                )
                {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        text = stringResource(R.string.ok),
                        fontSize = 17.sp,
                        fontFamily = MyFontsFamily,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable
    fun HomeScreen() {
        val button = viewModel.fieldList.all{it.livedata.value.isNotBlank()}
        val coefficients = viewModel.liveCoefficients.value
        val bundle = Bundle()
        bundle.putParcelable("c", coefficients)
        ModalBottomSheetLayout(
            sheetState = viewModel.bottomSheetScaffoldState,
            sheetContent = {
                if (viewModel.confirm.value){
                    BottomSheetOffer {
                        findNavController().navigate(
                            R.id.action_firstScreen_to_secondScreen,
                            bundle
                        )
                    }
                }else{
                    BottomSheetContent()
                }
            },
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetBackgroundColor = Color.White,
        ) {
        FinalScreen(
            viewModel,
            { InputFieldsCard() },
            { FirstTitle() },
            { if (viewModel.statusCoefficients.value == ApiStatus.DONE) {
                findNavController().navigate(R.id.action_firstScreen_to_secondScreen, bundle) } },
            R.string.calc_button_first,
            button,
            coefficients.factors
            )
        }
    }
}