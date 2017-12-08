package gcuestab.com.magickeyboard2

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import gcuestab.com.imagekeyboard.IconModel

/**
 * Created by gcuestab on 5/12/17.
 */
class KeyboardIconsAdapter(private val lIcons: ArrayList<IconModel>, fm: FragmentManager) : FragmentStatePagerAdapter(fm), KeyboardIconsFragment.IKeyboardIcons {
    companion object {
        private const val ELEMS_PAGE = 8.0
    }

    interface IKeyboardIcons {
        fun itemClick(idDrawable: Int)
    }

    private var mItemClick: IKeyboardIcons? = null

    fun setItemClick(itemClick: IKeyboardIcons) {
        this.mItemClick = itemClick
    }

    override fun itemClick(idDrawable: Int) {
        if (this.mItemClick != null) {
            this.mItemClick?.itemClick(idDrawable)
        }
    }

    override fun getItem(position: Int): Fragment {
        val initPos = position * ELEMS_PAGE.toInt()
        val endPos =
                if (position == count - 1) this.lIcons.size
                else initPos + ELEMS_PAGE.toInt()

        val lIconsView = this.lIcons.subList(initPos, endPos)
        val fragment = KeyboardIconsFragment.newInstance(ArrayList(lIconsView))
        fragment.mItemClick = this
        return fragment
    }

    override fun getCount(): Int = Math.ceil(this.lIcons.size / ELEMS_PAGE).toInt()

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as KeyboardIconsFragment
        if (fragment.mItemClick == null) {
            fragment.mItemClick = this
        }
        return fragment
    }
}