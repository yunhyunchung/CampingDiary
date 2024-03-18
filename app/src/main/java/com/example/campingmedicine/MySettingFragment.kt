package com.example.campingmedicine

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.preference.*

class MySettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        // 1) id 설정
        val idPreference: EditTextPreference? = findPreference("id")
        idPreference?.title = "ID 변경"
        idPreference?.summary = "ID를 변경할 수 있습니다."
        // 입력한 값을 그대로 가져오는 게 아니라 개발자가 원하는 대로 변경해서 summary 지정
        idPreference?.summaryProvider = Preference.SummaryProvider<EditTextPreference> {
            preference ->
                val text = preference.text
                if(TextUtils.isEmpty(text)) {
                    "ID 설정이 되지 않았습니다."
                } else {
                    "설정된 ID 값은 $text 입니다."
                }
        }
        // 2) 배경색 설정
        val colorPreference: ListPreference? = findPreference("color")
        colorPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
                        // 사용자가 입력/선택한 값(설정값)을 summary에 자동 지정 (입력한 값 그대로 출력)

        // 3) 글자색 설정
        val textColorPreference: ListPreference? = findPreference("textColor")
        textColorPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()

        // 4) 소리 알림 여부
        val notiPreference : SwitchPreference? = findPreference("noti_sound")
        notiPreference?.setOnPreferenceChangeListener { preference, newValue ->
            if (newValue.toString() === "true") {
                val player : MediaPlayer = MediaPlayer.create(context, R.raw.piano)
                player.start()
                Toast.makeText(context, "소리 알림이 설정되었습니다", Toast.LENGTH_SHORT).show()
            }
            else if (newValue.toString() === "false") {
                Toast.makeText(context, "소리 알림이 해제되었습니다", Toast.LENGTH_SHORT).show()
            }
            Log.d("mobileApp", "$newValue")
            true
        }
    }
}