package gcuestab.com.magickeyboard2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import gcuestab.com.imagekeyboard.IconModel
import gcuestab.com.imagekeyboard.R


/**
 * Created by gcuestab on 5/12/17.
 */
class IconsGridAdapter(private val mContext: Context, private val lIcons: List<IconModel>) : BaseAdapter() {
    interface IIconsGridAdapter {
        fun itemClick(idDrawable: Int)
    }

    private var mItemClick : IIconsGridAdapter? = null

    fun setItemClick(itemClick: IIconsGridAdapter) {
        this.mItemClick = itemClick
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val imageView: ImageView
        val textView: TextView
        val view: View
        if (p1 == null) {
            val inflater = this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.view_icons_grid_item, p2, false)
            imageView = view.findViewById(R.id.ivIcon)
            textView = view.findViewById(R.id.tvIcon)

            imageView.setOnClickListener {
                if (this.mItemClick != null) {
                    this.mItemClick?.itemClick(this.lIcons[p0].idIcon)
                }
            }
        }
        else {
            view = p1
            imageView = p1.findViewById(R.id.ivIcon)
            textView = p1.findViewById(R.id.tvIcon)
        }

        imageView.setImageResource(this.lIcons[p0].idIcon)
        textView.text = this.lIcons[p0].text
        return view
    }

    override fun getItem(p0: Int): Any? = null

    override fun getItemId(p0: Int): Long = 0

    override fun getCount(): Int = this.lIcons.size
}