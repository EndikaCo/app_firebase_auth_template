package com.endcodev.beautifullogin.presentation.ui.components

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.endcodev.beautifullogin.R

fun initPlayer(context: Context): ExoPlayer {
    val player = ExoPlayer.Builder(context).build()
    val uri = Uri.parse("android.resource://${context.packageName}/${R.raw.nubes}")
    val mediaItem = MediaItem.fromUri(uri)
    player.setMediaItem(mediaItem)
    player.repeatMode = ExoPlayer.REPEAT_MODE_ALL
    player.prepare()
    return player
}

@Composable
fun BackgroundComposable(content: @Composable () -> Unit, player: ExoPlayer) {

    Box(modifier = Modifier.fillMaxSize()) {
        MovingBackground(player)
        content()
    }

    LaunchedEffect(player) {
        if(!player.isPlaying)
            player.play()
    }
}

@OptIn(UnstableApi::class)
@Composable
fun MovingBackground(player: ExoPlayer) {
    AndroidView(
        factory = {
            PlayerView(it).apply {
                this.player = player
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            }
        },
        update = { view ->
            view.player = player
        }
    )
}
