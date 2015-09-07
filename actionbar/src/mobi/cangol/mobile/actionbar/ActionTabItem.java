package mobi.cangol.mobile.actionbar;

import android.os.Parcel;
import android.os.Parcelable;

public  class ActionTabItem implements Parcelable{
	private int id=-1;
	private String title=null;
	private int selected=0;
	public ActionTabItem(){
		
	}
	
	public ActionTabItem(int id,String title, int selected) {
		super();
		this.id = id;
		this.title = title;
		this.selected = selected;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
		dest.writeString(title);
		dest.writeInt(selected);
	}

	public static final Parcelable.Creator<ActionTabItem> CREATOR = new Creator<ActionTabItem>() {

		@Override
		public ActionTabItem createFromParcel(Parcel source) {
			ActionTabItem p = new ActionTabItem();
			p.setId(source.readInt());
			p.setTitle(source.readString());
			p.setSelected(source.readInt());
			return p;
		}

		@Override
		public ActionTabItem[] newArray(int size) {
			return new ActionTabItem[size];
		}
	};
}
