package gcuestab.com.imagekeyboard

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

/**
 * Created by Guille on 09/12/2017.
 */
class KeyboardIconsScrollView : ScrollView {
    interface IKeyboardIconsScrollView {
        fun showKeyboardSV()
    }

    var iKeyboardIcon: IKeyboardIconsScrollView? = null
    var permanentDisableKeyEvent = false
    var oneCycleDisableKeyEvent = false

    constructor(context: Context) : super(context) {
        this.init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        this.init()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        if (changed && !this.permanentDisableKeyEvent && !this.oneCycleDisableKeyEvent) {
            this.iKeyboardIcon?.showKeyboardSV()
        }

        this.oneCycleDisableKeyEvent = false
    }

    private fun init() {
        this.oneCycleDisableKeyEvent = true
        this.permanentDisableKeyEvent = false
    }
}