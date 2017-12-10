package gcuestab.com.imagekeyboard

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.EditText

/**
 * Created by Guille on 09/12/2017.
 */
class KeyboardIconsEditText : EditText {
    companion object {
        const val TAG = "KeyboardIconsEditText"
    }

    interface IKeyboardIconEditText {
        fun hideKeyboardET()
    }

    var iKeyboardIcon: IKeyboardIconEditText? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP) {
            this.iKeyboardIcon?.hideKeyboardET()
        }

        return super.onKeyPreIme(keyCode, event)
    }
}