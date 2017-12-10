package gcuestab.com.magickeyboard2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TextWatcher {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.kivKeyboard.initComponents()
        this.kivKeyboard.init()
        this.init()
    }

    override fun onBackPressed() {
        if (!this.kivKeyboard.backPressed()) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        this.kivKeyboard.clear()
        this.clear()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun init() {
        this.etText.addTextChangedListener(this)

        this.tvTextCount.text = this.resources.getString(R.string.tv_characters_counter, MAX_TEXT_CHARACTERS.toString())
    }

    private fun clear() {
        this.etText.removeTextChangedListener(this)
    }

    // ==== TextWatcher ====

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (p0 != null) {
            val num = MAX_TEXT_CHARACTERS - p0.length
            this.tvTextCount.text = this.resources.getString(R.string.tv_characters_counter, num.toString())
        }
    }
    // ==== END TextWatcher ====
}
