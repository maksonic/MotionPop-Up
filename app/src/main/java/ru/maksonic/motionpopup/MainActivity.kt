package ru.maksonic.motionpopup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.maksonic.motionpopup.core.ScreenManager
import ru.maksonic.motionpopup.ui.MainScreen

/**
 * @Author: maksonic on 16.02.2022
 */
class MainActivity : AppCompatActivity(), ScreenManager {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        attachMainScreen()
    }

    override fun attachMainScreen() {
        supportFragmentManager.beginTransaction().add(R.id.container, MainScreen()).commit()
    }
}