package com.squizzard.about

import com.squizzard.analytics.AnalyticsHelper.sendEvent
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import com.squizzard.misriCalendar.R
import android.os.Build
import android.view.MenuItem
import android.view.View
import com.squizzard.misriCalendar.BuildConfig

class AboutActivity : AppCompatActivity(), View.OnClickListener {
    private val emailIntent = Intent(Intent.ACTION_SEND)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        findViewById<View>(R.id.emailButton).setOnClickListener(this)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onClick(v: View) {
        if (v.id == R.id.emailButton) {
            sendEvent("contact_developer")
            var messageString = """
            OS Version: ${System.getProperty("os.version")}"""
            messageString += """
            OS API Level: ${Build.VERSION.RELEASE}"""
            messageString += """
            Device: ${Build.DEVICE}"""
            messageString += """
            Model : ${Build.MODEL}"""
            messageString += """
            Display: ${Build.DISPLAY}"""
            messageString += """
            Manufacturer: ${Build.MANUFACTURER}"""
            emailIntent.type = "plain/text"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.ouikka_email)))
            emailIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                "MisriCal " + BuildConfig.VERSION_NAME
            )
            emailIntent.putExtra(Intent.EXTRA_TEXT, messageString)
            emailIntent.type = "message/rfc822"
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
        }
    }
}