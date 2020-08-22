package com.dongldh.bottomnavigationexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.activity_basic.search_bar
import kotlinx.android.synthetic.main.include_card_view_search_bar.*

/***
 * 혹시.. navigation 에서 선택시 뒤의 effect 색을 바꾸는 방법이 궁금하면 --> Theme설정. (colorControlHighlight)
 */
class ShiftActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)

        navigation.setBackgroundColor(ContextCompat.getColor(this, R.color.amber_600))
        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_recent -> {
                    search_text.text = it.title
                    navigation.setBackgroundColor(ContextCompat.getColor(this, R.color.amber_600))
                    true
                }
                R.id.navigation_favorites -> {
                    search_text.text = it.title
                    navigation.setBackgroundColor(ContextCompat.getColor(this, R.color.cyan_400))
                    true
                }
                R.id.navigation_nearby -> {
                    search_text.text = it.title
                    navigation.setBackgroundColor(ContextCompat.getColor(this, R.color.deep_purple_A200))
                    true
                }
                else -> false
            }
        }

        // y는 상단값이 0, 하단으로 내려갈수록 숫자 증가
        nested_scroll_view.setOnScrollChangeListener { _, _, ny, _, oy ->
            if(ny < oy) { // oy가 더 크므로 최초 스크롤의 위치가 더 내려가있는 경우. --> 위로 swipe
                animateSearchBar(false)
                animateNavigation(false)
            }
            if(ny > oy) {  // ny가 더 크므로 나중 스크롤의 위치가 더 내려가있는 경우. --> 아래로 swipe
                animateSearchBar(true)
                animateNavigation(true)
            }
        }
    }

    var isNavigationHide = false
    var isSearchBarHide = false

    // 애니메이션 효과!
    // translationY 함수는 Y 방향으로 얼마나 이동 시킬 것인가에 대한 함수
    // navigation에 대해서는 아래 방향으로 내리고(양수), searchBar에 대해서는 위로 올린다(음수)
    private fun animateNavigation(hide: Boolean) {
        if(isNavigationHide && hide || !isNavigationHide && !hide) return   // 숨겨져 있는 상태에서 또 숨길려고 하는 경우, 나와있는 상태에서 또 나올려고 하는 경우 --> 그대로 둔다!
        isNavigationHide = hide
        val moveY = if(hide) 2 * navigation.height else 0
        navigation.animate().translationY(moveY.toFloat()).setStartDelay(100).setDuration(300).start()
    }

    private fun animateSearchBar(hide: Boolean) {
        if(isSearchBarHide && hide || !isSearchBarHide && !hide) return
        isSearchBarHide = hide
        val moveY = if(hide) -2 * (search_bar.height) else 0
        search_bar.animate().translationY(moveY.toFloat()).setStartDelay(100).setDuration(300).start()
    }
}