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
import androidx.fragment.app.viewModels
import com.example.beskorsravniosago.R
import com.example.beskorsravniosago.ui.theme.*
import androidx.compose.material.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.navigation.fragment.findNavController
import com.example.beskorsravniosago.network.Offer
import com.example.beskorsravniosago.viewmodels.ApiStatus
import com.example.beskorsravniosago.viewmodels.FirstScreenViewModel
import com.google.accompanist.insets.ProvideWindowInsets

class SecondScreen : Fragment() {

    private val viewModel: FirstScreenViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            viewModel.getDataCoefficients()
            setContent {
                BeskorSravniOsagoTheme {
                    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                        FirstScreen().FinalScreen(
                            viewModel,
                            content = { if (viewModel.statusOffers.value == ApiStatus.DONE)
                            {
                                OfferList(viewModel,viewModel.liveOffers.value.offers)
                            } else { BlankList() }},
                            title = { SecondTitle() },
                            onClick = {},
                            text = R.string.calc_button_second,
//                            button = true
                        )
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
                        findNavController().navigate(R.id.firstScreen)
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
        viewModel: FirstScreenViewModel,
        offer: Offer,
        index: Int
    ) {
        Card(
            elevation = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.primaryVariant),
        ) {
            Row(
                Modifier
                    .clickable { }
                    .background(MaterialTheme.colors.primaryVariant)
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 12.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colors.onSecondary)
                        .size(width = 36.dp, height = 36.dp)
                ) {
                    Image(
                        painter = painterResource(viewModel.setImage(index)),
                        modifier = Modifier
                            .align(alignment = Alignment.Center),
                        contentScale = ContentScale.FillHeight,
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
            elevation = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 4.dp)
                .background(MaterialTheme.colors.primaryVariant)
                .clip(RoundedCornerShape(16.dp)),
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
                        .background(MaterialTheme.colors.onSecondary)
                        .clip(RoundedCornerShape(16.dp))
                )
                Column(
                    Modifier.align(Alignment.CenterVertically)
                ) {
                    Box(modifier = Modifier
                        .padding(top = 16.dp, bottom = 2.dp)
                        .size(width = 151.dp, height = 20.dp)
                        .background(MaterialTheme.colors.onSecondary))
                    Box(modifier = Modifier
                        .padding(top = 2.dp, bottom = 16.dp)
                        .size(width = 151.dp, height = 12.dp)
                        .background(MaterialTheme.colors.onSecondary))
                }
                Box(
                    Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterVertically)){
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
        viewModel: FirstScreenViewModel,
        offers: List<Offer>
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            for (offer in offers){
                Offer(viewModel, offer, offers.indexOf(offer))
            }
        }
    }
    @Composable
    fun BlankList(){
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            OfferBlank()
            OfferBlank()
            OfferBlank()
            OfferBlank()
        }
    }
}
