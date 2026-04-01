package com.example.laba4

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Оголошуємо змінні для елементів інтерфейсу та плеєра
    private lateinit var videoView: VideoView
    private lateinit var etUrl: EditText
    private var mediaPlayer: MediaPlayer? = null
    private var currentMediaUri: Uri? = null
    private var isVideo: Boolean = false

    // Лаунчер для вибору аудіофайлу
    private val pickAudioLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            currentMediaUri = it
            isVideo = false
            prepareAudio(it)
        }
    }

    // Лаунчер для вибору відеофайлу
    private val pickVideoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            currentMediaUri = it
            isVideo = true
            prepareVideo(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ініціалізація елементів
        videoView = findViewById(R.id.videoView)
        etUrl = findViewById(R.id.etUrl)

        val btnChooseAudio = findViewById<Button>(R.id.btnChooseAudio)
        val btnChooseVideo = findViewById<Button>(R.id.btnChooseVideo)
        val btnPlayUrl = findViewById<Button>(R.id.btnPlayUrl)

        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnPause = findViewById<Button>(R.id.btnPause)
        val btnStop = findViewById<Button>(R.id.btnStop)

        // Обробка натискань на кнопки вибору файлів
        btnChooseAudio.setOnClickListener {
            pickAudioLauncher.launch("audio/*")
        }

        btnChooseVideo.setOnClickListener {
            pickVideoLauncher.launch("video/*")
        }

        // Далі ми додамо логіку для кнопок керування...
    }

    // Заглушки для методів підготовки медіа, які ми напишемо на наступному кроці
    private fun prepareAudio(uri: Uri) {
        Toast.makeText(this, "Аудіо вибрано", Toast.LENGTH_SHORT).show()
    }

    private fun prepareVideo(uri: Uri) {
        Toast.makeText(this, "Відео вибрано", Toast.LENGTH_SHORT).show()
    }
}