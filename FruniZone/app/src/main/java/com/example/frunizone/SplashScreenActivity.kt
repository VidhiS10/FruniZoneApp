package com.example.frunizone



import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.os.Looper
import com.example.frunizone.util.ConstantData
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.RawResourceDataSource

class SplashScreenActivity : AppCompatActivity() {
    private var player: ExoPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val playerView = findViewById<PlayerView>(R.id.playerView)

        player = ExoPlayer.Builder(this).build().also { exo ->
            playerView.player = exo
        }

        val uri = RawResourceDataSource.buildRawResourceUri(R.raw.frunizone_splash)

        Handler(Looper.getMainLooper()).post {
            player?.apply {
                setMediaItem(MediaItem.fromUri(uri))
                prepare()
                playWhenReady = true
                repeatMode = Player.REPEAT_MODE_ALL
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = getSharedPreferences(ConstantData.SP_LOGIN_PREFS, MODE_PRIVATE)
            val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

            startActivity(Intent(this,
                if (isLoggedIn) HomeActivity::class.java else LoginActivity::class.java
            ))
            finish()
        }, 3500)
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }
    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
    }
}