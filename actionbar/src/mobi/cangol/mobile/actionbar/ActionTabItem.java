package mobi.cangol.mobile.actionbar;

import android.os.Parcel;
import android.os.Parcelable;

public  class ActionTabItem implements Parcelable{
	private int id=-1;
	private String tag=null;
	private String title=null;
	private int checked=0;
	public ActionTabItem(){
		
	}
	
	public ActionTabItem(int id, String tag, String title, int checked) {
		super();
		this.id = id;
		this.tag = tag;
		this.title = title;
		this.checked = checked;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}

	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(tag);
		dest.writeString(title);
		dest.writeInt(checked);
	}

	public static final Parcelable.Creator<ActionTabItem> CREATOR = new Creator<ActionTabItem>() {

		@Override
		public ActionTabItem createFromParcel(Parcel source) {
			ActionTabItem p = new ActionTabItem();
			p.setId(source.readInt());
			p.setTag(source.readString());
			p.setTitle(source.readString());
			p.setChecked(source.readInt());
			return p;
		}

		@Override
		public ActionTabItem[] newArray(int size) {
			return new ActionTabItem[size];
		}
	};
}
