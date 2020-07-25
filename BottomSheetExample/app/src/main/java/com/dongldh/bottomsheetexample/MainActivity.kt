package com.dongldh.bottomsheetexample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.view.*

// https://developer.android.com/reference/com/google/android/material/bottomsheet/BottomSheetBehavior

class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*** Task1. 다이얼로그 구현 ***/

        // 방법1 (다이얼로그 구현법) -> 방법1과 방법2는 주변이 어두워짐. 어두운 부분 누르면 다이얼로그 내려갑니다.
        // 독립적으로 사용하기 위한 방법
        // BottomSheetDialog 를 이용한 구현법

        /*val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogView)
        dialog.show()*/

        // 방법2 (프래그먼트 다이얼로그 구현법)
        // BottomSheetDialogFragment 를 이용한 구현법 (BottomSheetFragment 클래스 참고)

        /*val bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)*/

        // 방법3 (xml 상에서 include를 통한 방법) -> 주변 화면 어두워지지않고 사용 가능
        /*
        app:behavior_hideable="true"
        app:behavior_peekHeight="100dp"
        app:layout_behavior="com.google.android.material.bottomsheet.BottomSheetBehavior"

        위의 세개의 속성을 bottom_sheet.xml에 넣어주어야 함.
        behavior_hideable 속성은, 아예 안보이게 내리게끔 할 수 있도록 설정
        behavior_peekHeight 속성은, 기본으로 어느정도 높이까지 보여지게 할 것인지에 대한 설정 속성
        */


        /***
         *   Task2. 버튼 눌러 다이얼로그 Expand/Collapse 조절하기
         *   (직접 슬라이드해서 Expand/Collapse 조절 X)
         ****/

        fun setButtonTextExpand() {
            bottomSheet.sheetControlButton.text = "Bottom Sheet Expand"
        }

        fun setButtonTextClose() {
            bottomSheet.sheetControlButton.text = "Bottom Sheet Close"
        }

        val sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheet.sheetControlButton.setOnClickListener {
            if(sheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                setButtonTextClose()
            } else {
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                setButtonTextExpand()
            }
        }

        /***
         *   Task3. BottomSheet 의 state 가 변하는 것을 감지하는 Callback 사용
         *   (버튼으로 확장/축소 시키는 것 이외에도, 직접 슬라이드를 통해 상태 변화 시키는 경우 포함)
         ****/

        sheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        setButtonTextExpand()
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        setButtonTextClose()
                    }
                }
            }
        })

        /*** Task4. 중간 크기 (STATE_HALF_EXPANDED) 만큼만 확장시키기 ***/

        // 확장했을 경우 중간 사이즈 없이 바로 전체로 가도록 (true). 중간을 거치고 가도록 (false)
        sheetBehavior.isFitToContents = false
        // 혹은   app:behavior_fitToContents="false"  속성을 xml에 부여

        // 중간 사이즈를 어느정도로 할 것인지에 대한 비율
        sheetBehavior.halfExpandedRatio = 0.7f

        /*** Task5. BottomSheet 가 화면상에서 모두 사라졌을 경우, 다시 끌어올리기
         * (behavior_hideable = true 인 경우에 사용 가능) ***/
        // 움직이는 도중에 처리하는 방법이 없을까? -> 조금 더 생각해보기

        var (y1, y2) = 1f to 0f
        // view의 좌측상단이 (0,0).
        navigation.setOnTouchListener { v, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    y1 = event.y
                }

                MotionEvent.ACTION_UP -> {
                    y2 = event.y
                    val diffY = y2 - y1
                    Log.d("diffTest", "y1: $y1, y2: $y2, diff: $diffY")
                    if(diffY < 0) {
                        // swipe up
                        if(sheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
                            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }
                    }
                }
            }
            true
        }
    }
}

// 방법2 에서 이용하는 클래스
class BottomSheetFragment: BottomSheetDialogFragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }
}