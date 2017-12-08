package gcuestab.com.imagekeyboard

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by gcuestab on 7/12/17.
 */
data class IconModel(
        val idIcon: Int,
        val text: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idIcon)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IconModel> {
        override fun createFromParcel(parcel: Parcel): IconModel {
            return IconModel(parcel)
        }

        override fun newArray(size: Int): Array<IconModel?> {
            return arrayOfNulls(size)
        }
    }
}