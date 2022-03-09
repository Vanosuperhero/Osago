package com.example.beskorsravniosago.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
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
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.beskorsravniosago.network.Factor
import com.example.beskorsravniosago.network.Factors
import com.example.beskorsravniosago.network.Offer
import com.example.beskorsravniosago.viewmodels.ApiStatus
import com.example.beskorsravniosago.viewmodels.FirstScreenViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SecondScreen : Fragment() {

    private val viewModel: FirstScreenViewModel by activityViewModels()

    private var coefficients: MutableState<List<Factor>?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            arguments?.getParcelable<Factors>("c")?.let {
                coefficients.value = it.factors
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            viewModel.getDataOffers()
            setContent {
                BeskorSravniOsagoTheme {
                    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                        coefficients.value?.let {
                            FirstScreen().FinalScreen(
                                viewModel,
                                content = { if (viewModel.statusOffers.value == ApiStatus.DONE) {
                                    viewModel.liveOffers.value?.let { it1 -> OfferList(it1.offers) }
                                } else { BlankList() }},
                                title = { SecondTitle() },
                                onClick = {},
                                text = R.string.calc_button_second,
                                button = viewModel.statusOffers.value == ApiStatus.DONE,
                                coefficients = it
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SecondTitle() {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 23.dp, vertical = 12.dp)) {
            Icon(
                painter = painterResource(R.drawable.shape_flip),
                contentDescription = stringResource(R.string.arrow),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 2.dp)
                    .size(14.dp)
                    .clickable {
                        findNavController()
                            .popBackStack()
                    },
                tint = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.padding(horizontal = 7.dp))
            Text(
                fontSize = 20.sp,
                text = stringResource(R.string.second_title),
                fontFamily = MyFontsFamily,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }

    @Composable
    fun Offer(
        offer: Offer,
        onClick: () -> Unit
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 4.dp),
            elevation = 4.dp
        ) {
            val bundle = Bundle()
            bundle.putParcelable("o", offer)
            Row(
                Modifier
                    .clickable { onClick() }
                    .background(MaterialTheme.colors.primaryVariant)
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 12.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(android.graphics.Color.parseColor("#${offer.branding.backgroundColor}")))
                        .size(width = 36.dp, height = 36.dp)
                ) {
                    val image = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = offer.branding.bankLogoUrlSVG)
                            .decoderFactory(SvgDecoder.Factory())
                            .build()
                    )
                    Image(
                        painter = image,
                        modifier = Modifier
                            .align(alignment = Alignment.Center),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = stringResource(R.string.icon),
                    )
                }
                Column(
                    Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = offer.name,
                        fontSize = 14.sp,
                        fontFamily = MyFontsFamily,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.secondary,
                    )
                    Row(
                        modifier = Modifier.padding(bottom = 3.dp, top = 2.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.star_1),
                            contentDescription = stringResource(R.string.star),
                            modifier = Modifier
                                .padding(start = 1.5.dp, top = 1.5.dp)
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 5.5.dp),
                            text = offer.rating.toString(),
                            fontSize = 12.sp,
                            fontFamily = MyFontsFamily,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colors.secondary,
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterVertically)
                        .padding(end = 16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterEnd),
                        text = "${offer.price} ₽",
                        fontSize = 20.sp,
                        fontFamily = MyFontsFamily,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }

    @Composable
    fun OfferBlank() {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 4.dp),
            elevation = 4.dp,
            ) {
            Row(
                Modifier
                    .clickable { }
                    .background(MaterialTheme.colors.primaryVariant)
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 12.dp)
                        .size(width = 36.dp, height = 36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colors.onSecondary)
                )
                Column(
                    Modifier.align(Alignment.CenterVertically)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 2.dp)
                            .size(width = 151.dp, height = 20.dp)
                            .background(MaterialTheme.colors.onSecondary)
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 1.dp, bottom = 16.dp)
                            .size(width = 151.dp, height = 12.dp)
                            .background(MaterialTheme.colors.onSecondary)
                    )
                }
                Box(
                    Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterVertically)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                            .size(width = 64.dp, height = 20.dp)
                            .background(MaterialTheme.colors.onSecondary)
                    )
                }
            }
        }
    }

    @Composable
    fun OfferList(
        offers: List<Offer>
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            for (offer in offers){
                Offer(offer) {
                    viewModel.offer(offer)
                    viewModel.confirm()
                    findNavController().navigate(
                        R.id.action_secondScreen_to_firstScreen
                    )}
                }
            }
        }

    @Composable
    fun BlankList(){
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            for(i in 0..3){
                OfferBlank()
            }
        }
    }
}

