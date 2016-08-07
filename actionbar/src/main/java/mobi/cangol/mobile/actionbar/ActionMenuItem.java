package mobi.cangol.mobile.actionbar;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Cangol
 */
public class ActionMenuItem implements Parcelable {
    public static final Parcelable.Creator<ActionMenuItem> CREATOR = new Creator<ActionMenuItem>() {

        @Override
        public ActionMenuItem createFromParcel(Parcel source) {
            ActionMenuItem p = new ActionMenuItem();
            p.setDrawable(source.readInt());
            p.setId(source.readInt());
            p.setText(source.readInt());
            p.setShow(source.readInt());
            return p;
        }

        @Override
        public ActionMenuItem[] newArray(int size) {
            return new ActionMenuItem[size];
        }
    };
    /**
     * 图片id -1标示无图片，即文本按钮
     */
    private int drawable = -1;
    /**
     * 唯一标示
     */
    private int id = -1;
    /**
     * 标题文本
     */
    private int text = -1;
    /**
     * 是否显示 1为在actionbar上显示，0或其他为在more菜单中显示（）
     */
    private int show = -1;

    public ActionMenuItem(int id, int text, int drawable, int show) {
        this.id = id;
        this.text = text;
        this.drawable = drawable;
        this.show = show;
    }

    public ActionMenuItem() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getText() {
        return this.text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public int getDrawable() {
        return this.drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public boolean isIcon() {
        return drawable != -1;
    }

    public boolean isShow() {
        return show == 1;
    }

    public void setShow(int show) {
        this.show = show;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(drawable);
        dest.writeInt(id);
        dest.writeInt(text);
        dest.writeInt(show);
    }
}
