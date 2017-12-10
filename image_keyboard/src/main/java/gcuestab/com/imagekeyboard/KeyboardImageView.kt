package gcuestab.com.imagekeyboard

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ScrollView
import gcuestab.com.magickeyboard2.KeyboardIconsAdapter
import kotlinx.android.synthetic.main.view_keyboard_icons.view.*

/**
 * Created by gcuestab on 6/12/17.
 */
class KeyboardImageView : ConstraintLayout, KeyboardIconsAdapter.IKeyboardIcons, TextWatcher, KeyboardIconsEditText.IKeyboardIconEditText, KeyboardIconsScrollView.IKeyboardIconsScrollView {
    companion object {
        private const val SINGLE_IMAGE_MODE = 0
        private const val ADVANCE_IMAGE_MODE = 1

        private val imageMode = SINGLE_IMAGE_MODE

        private const val IMAGE_CHARACTER = "c"
    }

    private var idEtText: Int = -1
    private var etText: EditText? = null
    private var idSvContainer: Int = -1
    private var svContainer: ScrollView? = null
    private var focusEnable = true
    private var prevImagePosition = -1

    constructor(context: Context) : super(context) {
        this.inflateView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.inflateView(context)
        this.initAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.inflateView(context)
        this.initAttrs(context, attrs)
    }

    private fun inflateView(context: Context) {
        View.inflate(context, R.layout.view_keyboard_icons, this)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.keyboard_icon)
        this.idEtText = a.getResourceId(R.styleable.keyboard_icon_keyboard_icon_edit_text, -1)
        this.idSvContainer = a.getResourceId(R.styleable.keyboard_icon_keyboard_icon_scroll_view, -1)
        a.recycle()
    }

    fun initComponents() {
        if (this.idEtText != -1) {
            this.etText = this.rootView.findViewById(this.idEtText)

            if (this.etText is KeyboardIconsEditText) {
                (this.etText as KeyboardIconsEditText).iKeyboardIcon = this
            }
        }

        if (this.idSvContainer != -1) {
            this.svContainer = this.rootView.findViewById(this.idSvContainer)

            if (this.svContainer is KeyboardIconsScrollView) {
                (this.svContainer as KeyboardIconsScrollView).iKeyboardIcon = this
            }
        }
    }

    fun init() {
        this.tvText.setOnClickListener { _ ->
            if (this.svContainer is KeyboardIconsScrollView) {
                (this.svContainer as KeyboardIconsScrollView).permanentDisableKeyEvent = true
            }

            this.enableKeyboard()
            this.selectAbcTab()
        }

        this.tvEmoji.setOnClickListener { _ ->
            if (this.svContainer is KeyboardIconsScrollView) {
                (this.svContainer as KeyboardIconsScrollView).permanentDisableKeyEvent = true
            }

            this.enableIcons()
            this.enableEmojiVP()
            this.selectEmojiTab()
        }

        this.tvEmotion.setOnClickListener { _ ->
            if (this.svContainer is KeyboardIconsScrollView) {
                (this.svContainer as KeyboardIconsScrollView).permanentDisableKeyEvent = true
            }

            this.enableIcons()
            this.enableEmotionVP()
            this.selectEmotionTab()
        }

        this.etText?.setOnTouchListener { _, _ ->
            // return false - edit text manage the event.
            // return true - I manage the event.
            return@setOnTouchListener !this.focusEnable
        }

        this.etText?.setOnLongClickListener {
            // return false - edit text manage the event.
            // return true - I manage the event.
            return@setOnLongClickListener !this.focusEnable
        }

        this.etText?.addTextChangedListener(this)

        val fm =
                if (this.context is AppCompatActivity) (this.context as AppCompatActivity).supportFragmentManager
                else (this.context as Fragment).activity.supportFragmentManager

        val lIcons = arrayListOf(IconModel(R.drawable.toad, ""), IconModel(R.drawable.toad, ""), IconModel(R.drawable.toad, ""))
        val vpEmojiAdapter = KeyboardIconsAdapter(lIcons, fm)
        vpEmojiAdapter.setItemClick(this)
        this.vpEmoji.adapter = vpEmojiAdapter

        val lEmotions = arrayListOf(IconModel(R.drawable.toad, "Emotion1"))
        val vpEmotionAdapter  = KeyboardIconsAdapter(lEmotions, fm)
        vpEmotionAdapter.setItemClick(this)
        this.vpEmotions.adapter = vpEmotionAdapter

        this.unselectAll()
    }

    fun clear() {
        this.etText?.removeTextChangedListener(this)
    }

    // True - this class manage the back event.
    // False - this class does not manage the back event.
    fun backPressed(): Boolean {
        if (!this.focusEnable) {
            this.disableIcons()
            return true
        }

        return false
    }

    private fun enableKeyboard() {
        // Show keyboard.
        this.showKeyboard()
        // Hide
        this.disableIcons()
    }

    private fun enableIcons() {
        // I show the icon's keyboard & disable the "focusEnable" flag.
        this.flIcons.visibility = View.VISIBLE
        this.focusEnable = false
        // Last thing I do is to hide the keyboard. It must be in this order.
        this.hideKeyboard()
    }

    private fun showKeyboard() {
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this.etText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        if (this.etText != null) {
            val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.etText?.windowToken, 0)
        }
    }

    private fun disableIcons() {
        this.flIcons.visibility = View.GONE
        this.focusEnable = true
    }

    private fun enableEmojiVP() {
        this.vpEmoji.visibility = View.VISIBLE
        this.vpEmotions.visibility = View.GONE
    }

    private fun enableEmotionVP() {
        this.vpEmoji.visibility = View.GONE
        this.vpEmotions.visibility = View.VISIBLE
    }

    private fun getUnselectedColor(): Int = ContextCompat.getColor(this.context, R.color.tab_unselected)

    private fun getSelectedColor(): Int = ContextCompat.getColor(this.context, R.color.tab_selected)

    private fun selectAbcTab() {
        this.tvText.setBackgroundColor(this.getSelectedColor())
        this.tvEmoji.setBackgroundColor(this.getUnselectedColor())
        this.tvEmotion.setBackgroundColor(this.getUnselectedColor())
    }

    private fun selectEmojiTab() {
        this.tvText.setBackgroundColor(this.getUnselectedColor())
        this.tvEmoji.setBackgroundColor(this.getSelectedColor())
        this.tvEmotion.setBackgroundColor(this.getUnselectedColor())
    }

    private fun selectEmotionTab() {
        this.tvText.setBackgroundColor(this.getUnselectedColor())
        this.tvEmoji.setBackgroundColor(this.getUnselectedColor())
        this.tvEmotion.setBackgroundColor(this.getSelectedColor())
    }

    private fun unselectAll() {
        this.tvText.setBackgroundColor(this.getUnselectedColor())
        this.tvEmoji.setBackgroundColor(this.getUnselectedColor())
        this.tvEmotion.setBackgroundColor(this.getUnselectedColor())
    }

    // ==== IKeyboardIcons ====
    override fun itemClick(idDrawable: Int) {
        if (this.etText != null) {
            val posSelector: Int
            val addWhite: Boolean
            when (imageMode) {
                SINGLE_IMAGE_MODE -> {
                    val aStyles = this.etText!!.text!!.getSpans(0, this.etText!!.length(), ImageSpan::class.java)
                    if (aStyles.isEmpty()) {
                        posSelector = this.etText!!.selectionEnd
                        addWhite = true
                    } else {
                        posSelector = this.prevImagePosition
                        addWhite = false
                    }
                }
                else -> { // ADVANCE IMAGE MODE
                    posSelector = this.etText!!.selectionEnd
                    addWhite = true
                }
            }

            // Get text from edit text, append a space character and update the edit text value.
            val text = this.etText!!.text
            if (addWhite) {
                text.insert(posSelector, IMAGE_CHARACTER)
                this.etText!!.text = text
            }

            // Create a spannable to add the image.
            val spannable = SpannableStringBuilder(text)

            // Create the image span.
            val size = this.etText!!.textSize
            val d = ContextCompat.getDrawable(this.context, idDrawable)
            d.setBounds(0, 0, size.toInt(), size.toInt())
            val imageSpan = ImageSpan(d, ImageSpan.ALIGN_BOTTOM)

            // Add the image span to the spannable.
            spannable.setSpan(imageSpan, posSelector, posSelector + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            // Add the spannable to the edit text.
            this.etText!!.text = spannable
            this.etText!!.setSelection(posSelector + 1)

            // Update "prevImagePosition".
            this.prevImagePosition = posSelector
        }
    }
    // ==== END IKeyboardIcons ====

    // ==== TextWatcher ====
    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (p1 <= this.prevImagePosition) {
            this.prevImagePosition = if (p2 > p3) this.prevImagePosition - 1 else this.prevImagePosition + 1
        }
    }
    // ==== END TextWatcher ====

    override fun hideKeyboardET() {
        if (this.svContainer is KeyboardIconsScrollView) {
            (this.svContainer as KeyboardIconsScrollView).permanentDisableKeyEvent = false
            (this.svContainer as KeyboardIconsScrollView).oneCycleDisableKeyEvent = true
        }

        this.unselectAll()
    }
    // ==== END IKeyboardIconsEditText ====

    override fun showKeyboardSV() {
        if (this.svContainer is KeyboardIconsScrollView) {
            (this.svContainer as KeyboardIconsScrollView).permanentDisableKeyEvent = true
        }

        this.selectAbcTab()
    }
    // ==== END IKeyboardIconsScrollView ====

}