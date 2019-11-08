package mobi.cangol.mobile.actionbar

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Cangol
 */
class ActionMenuItem() : Parcelable {
    /**
     * 图片id -1标示无图片，即文本按钮
     */
    var drawable = -1
    /**
     * 唯一标示
     */
    var id = -1
    /**
     * 标题文本
     */
    var text = -1
    /**
     * 是否显示 1为在actionbar上显示，0或其他为在more菜单中显示（）
     */
    private var show = -1

    val isIcon: Boolean
        get() = drawable != -1

    val isShow: Boolean
        get() = show == 1

    constructor(parcel: Parcel) : this() {
        drawable = parcel.readInt()
        id = parcel.readInt()
        text = parcel.readInt()
        show = parcel.readInt()
    }

    constructor(id: Int, text: Int, drawable: Int, show: Int) : this()  {
        this.id = id
        this.text = text
        this.drawable = drawable
        this.show = show
    }

    fun setShow(show: Int) {
        this.show = show
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(drawable)
        dest.writeInt(id)
        dest.writeInt(text)
        dest.writeInt(show)
    }

    companion object CREATOR : Parcelable.Creator<ActionMenuItem> {
        override fun createFromParcel(parcel: Parcel): ActionMenuItem {
            return ActionMenuItem(parcel)
        }

        override fun newArray(size: Int): Array<ActionMenuItem?> {
            return arrayOfNulls(size)
        }
    }


}
