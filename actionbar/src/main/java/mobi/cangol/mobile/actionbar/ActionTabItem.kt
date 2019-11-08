package mobi.cangol.mobile.actionbar

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Cangol
 */
class ActionTabItem() : Parcelable {
    /**
     * 唯一标识
     */
    var id = -1
    /**
     * 文本
     */
    var text: String? = null
    /**
     * 是否选中
     */
    var selected = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        text = parcel.readString()
        selected = parcel.readInt()
    }

    constructor(id: Int, text: String, selected: Int) : this() {
        this.id = id
        this.text = text
        this.selected = selected
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(text)
        dest.writeInt(selected)
    }

    companion object CREATOR : Parcelable.Creator<ActionTabItem> {
        override fun createFromParcel(parcel: Parcel): ActionTabItem {
            return ActionTabItem(parcel)
        }

        override fun newArray(size: Int): Array<ActionTabItem?> {
            return arrayOfNulls(size)
        }
    }


}
