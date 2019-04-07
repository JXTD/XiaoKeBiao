package yanshan.com.xiaokebiao.ui.activity


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceScreen
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import yanshan.com.xiaokebiao.R
import yanshan.com.xiaokebiao.common.BaseActivity
import yanshan.com.xiaokebiao.common.ConstantPool
import yanshan.com.xiaokebiao.entity.EventEntity
import yanshan.com.xiaokebiao.service.CourseInfoService
import yanshan.com.xiaokebiao.ui.fragment.setting.SettingsFragment
import yanshan.com.xiaokebiao.ui.fragment.setting.SettingsFragment.Companion.FOREGROUND_SERVICE_STATUS
import yanshan.com.xiaokebiao.util.ThemeChangeUtil

/**
 * 设置Activity
 *
 * @author itning
 */
class SettingActivity : BaseActivity(), PreferenceFragmentCompat.OnPreferenceStartScreenCallback, SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeChangeUtil.changeTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        EventBus.getDefault().register(this)
        initView()
    }

    /**
     * 初始化View
     */
    private fun initView() {
        Log.d(TAG, "init view")
        //设置返回箭头
        val supportActionBar = supportActionBar
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar.title = "设置"
        }
        val settingsFragment = SettingsFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, settingsFragment)
                .commit()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onMessageEvent(eventEntity: EventEntity) {
        when (eventEntity.id) {
            ConstantPool.Int.APP_COLOR_CHANGE -> {
                ThemeChangeUtil.changeTheme(this)
            }
            else -> {
            }
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPreferenceStartScreen(caller: PreferenceFragmentCompat, pref: PreferenceScreen): Boolean {
        val fragment = SettingsFragment()
        val args = Bundle()
        val key = pref.key
        args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, key)
        fragment.arguments = args
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, fragment)
                .addToBackStack(key)
                .commit()
        return true
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == FOREGROUND_SERVICE_STATUS) {
            if (sharedPreferences.getBoolean(FOREGROUND_SERVICE_STATUS, true)) {
                startService(Intent(this, CourseInfoService::class.java))
            }
        }
    }

    companion object {
        private const val TAG = "SettingActivity"
    }
}
