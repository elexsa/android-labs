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

        // Обробка завантаження з URL
        btnPlayUrl.setOnClickListener {
            // Додаємо .trim(), щоб прибрати випадкові пробіли
            val url = etUrl.text.toString().trim()
            if (url.isNotEmpty()) {
                val uri = Uri.parse(url)
                currentMediaUri = uri
                isVideo = true
                prepareVideo(uri)
            } else {
                Toast.makeText(this, "Введіть URL посилання", Toast.LENGTH_SHORT).show()
            }
        }

        btnPlay.setOnClickListener {
            if (isVideo) {
                videoView.start()
            } else {
                mediaPlayer?.start()
            }
        }

        btnPause.setOnClickListener {
            if (isVideo && videoView.isPlaying) {
                videoView.pause()
            } else if (!isVideo && mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        }

        btnStop.setOnClickListener {
            // Замість повного stop() (який вимагає переініціалізації),
            // ми робимо паузу і перемотуємо на початок - це безпечніший спосіб для лабораторної
            if (isVideo) {
                videoView.pause()
                videoView.seekTo(0)
            } else {
                mediaPlayer?.pause()
                mediaPlayer?.seekTo(0)
            }
        }
    }

    // Підготовка аудіофайлу
    private fun prepareAudio(uri: Uri) {
        releaseMediaPlayer() // Звільняємо ресурси, якщо щось грало до цього
        videoView.visibility = android.view.View.GONE // Ховаємо вікно відео

        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(applicationContext, uri)
                prepare()
            }
            Toast.makeText(this, "Аудіо готове до відтворення", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Помилка завантаження аудіо", Toast.LENGTH_SHORT).show()
        }
    }

    // Підготовка відеофайлу
    private fun prepareVideo(uri: Uri) {
        releaseMediaPlayer() // Зупиняємо аудіо, якщо воно грало
        videoView.visibility = android.view.View.VISIBLE // Показуємо вікно відео

        videoView.setVideoURI(uri)
        videoView.requestFocus()
        Toast.makeText(this, "Відео готове. Натисніть Play", Toast.LENGTH_SHORT).show()
    }

    // Звільнення ресурсів аудіоплеєра
    private fun releaseMediaPlayer() {
        mediaPlayer?.let {
            if (it.isPlaying) it.stop()
            it.release()
        }
        mediaPlayer = null
    }

    // Очищення ресурсів при закритті додатку
    override fun onDestroy() {
        super.onDestroy()
        releaseMediaPlayer()
    }
}