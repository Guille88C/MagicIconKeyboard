package gcuestab.com.magickeyboard2

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gcuestab.com.imagekeyboard.IconModel
import gcuestab.com.imagekeyboard.LIST_ICONS
import gcuestab.com.imagekeyboard.R
import kotlinx.android.synthetic.main.fragment_kayboard_icons.*

class KeyboardIconsFragment : Fragment(), IconsGridAdapter.IIconsGridAdapter {
    companion object {
        fun newInstance(lIcons: ArrayList<IconModel>): KeyboardIconsFragment {
            val f = KeyboardIconsFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(LIST_ICONS, lIcons)
            f.arguments = bundle
            return f
        }
    }

    interface IKeyboardIcons {
        fun itemClick(idDrawable: Int)
    }

    var mItemClick: IKeyboardIcons? = null

    override fun itemClick(idDrawable: Int) {
        if (this.mItemClick != null) {
            this.mItemClick?.itemClick(idDrawable)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_kayboard_icons, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val lIcons = bundle.getParcelableArrayList<IconModel>(LIST_ICONS)

        val gvAdapter = IconsGridAdapter(this.activity, lIcons)
        gvAdapter.setItemClick(this)
        this.gvIcons.adapter = gvAdapter
    }
}
