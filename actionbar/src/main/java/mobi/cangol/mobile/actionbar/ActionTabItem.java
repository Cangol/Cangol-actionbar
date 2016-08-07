package mobi.cangol.mobile.actionbar;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Cangol
 */
public class ActionTabItem implements Parcelable {
    public static final Parcelable.Creator<ActionTabItem> CREATOR = new Creator<ActionTabItem>() {

        @Override
        public ActionTabItem createFromParcel(Parcel source) {
            ActionTabItem p = new ActionTabItem();
            p.setId(source.readInt());
            p.setText(source.readString());
            p.setSelected(source.readInt());
            return p;
        }

        @Override
        public ActionTabItem[] newArray(int size) {
            return new ActionTabItem[size];
        }
    };
    /**
     * 唯一标识
     */
    private int id = -1;
    /**
     * 文本
     */
    private String text = null;
    /**
     * 是否选中
     */
    private int selected = 0;

    public ActionTabItem() {

    }

    public ActionTabItem(int id, String text, int selected) {
        super();
        this.id = id;
        this.text = text;
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeInt(selected);
    }
}
