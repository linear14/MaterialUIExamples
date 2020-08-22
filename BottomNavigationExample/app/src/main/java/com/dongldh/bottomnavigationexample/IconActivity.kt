package com.dongldh.bottomnavigationexample

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_icon.*

/***
 * tablayout의 app:tabIndicatorHeight="0dp" 속성을 적용하면, tab layout 하단에 indicator bar가 보이지 않게 된다! (직접 실행해서 비교해보면 알게됨)
 */

class IconActivity : AppCompatActivity() {
    lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icon)

        initToolbar()
        initComponent()
    }
    fun initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        MyDrawableCompat.setColorFilter(toolbar.navigationIcon!!, ContextCompat.getColor(this@IconActivity, R.color.grey_60))
        setSupportActionBar(toolbar)

        actionBar = supportActionBar!!
        actionBar?.title = "Home"
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun initComponent() {
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_home), 0)
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_search), 1)
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_add_box), 2)
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_favorite_border), 3)
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_person), 4)

        MyDrawableCompat.setColorFilter(tab_layout.getTabAt(0)?.icon!!, ContextCompat.getColor(this@IconActivity, R.color.deep_orange_500))
        MyDrawableCompat.setColorFilter(tab_layout.getTabAt(1)?.icon!!, ContextCompat.getColor(this@IconActivity, R.color.grey_60))
        MyDrawableCompat.setColorFilter(tab_layout.getTabAt(2)?.icon!!, ContextCompat.getColor(this@IconActivity, R.color.grey_60))
        MyDrawableCompat.setColorFilter(tab_layout.getTabAt(3)?.icon!!, ContextCompat.getColor(this@IconActivity, R.color.grey_60))
        MyDrawableCompat.setColorFilter(tab_layout.getTabAt(4)?.icon!!, ContextCompat.getColor(this@IconActivity, R.color.grey_60))


        tab_layout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                MyDrawableCompat.setColorFilter(tab.icon!!, ContextCompat.getColor(this@IconActivity, R.color.grey_60))
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                MyDrawableCompat.setColorFilter(tab.icon!!, ContextCompat.getColor(this@IconActivity, R.color.deep_orange_500))
                when(tab.position) {
                    0 -> actionBar.title = "Home"
                    1 -> actionBar.title = "Explore"
                    2 -> actionBar.title = "Story"
                    3 -> actionBar.title = "Activity"
                    else -> actionBar.title = "Profile"
                }
            }

        })
    }
}

object MyDrawableCompat {
    fun setColorFilter(drawable: Drawable, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_IN)
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }
}