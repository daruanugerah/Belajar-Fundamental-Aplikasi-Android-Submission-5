package id.daruanugerah.moviecataloguesub4.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;
import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
@Entity(tableName = "catalog", indices = @Index(value = "title", unique = true))
public class Catalog implements Parcelable {

	//manual must generate primary key because room db doesn't create autoincrement primary key
	@PrimaryKey(autoGenerate = true)
	private int uid;

	@SerializedName("catalog_type")
	private int catalogType;

	@SerializedName("overview")
	private String overview;

	@SerializedName("original_language")
	private String originalLanguage;

	@SerializedName(value = "title", alternate = "name")
	private String title;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName(value = "release_date", alternate = "first_air_date")
	private String releaseDate;

	@SerializedName("vote_average")
	private float voteAverage;

	@SerializedName("vote_count")
	private int voteCount;



	//for empty constructor
	public Catalog() {

	}


	//Getter and Setter auto generate
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getCatalogType() {
		return catalogType;
	}

	public void setCatalogType(int catalogType) {
		this.catalogType = catalogType;
	}

	public void setOverview(String overview){
		this.overview = overview;
	}

	public String getOverview(){
		return overview;
	}

	public void setOriginalLanguage(String originalLanguage){
		this.originalLanguage = originalLanguage;
	}

	public String getOriginalLanguage(){
		return originalLanguage;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setPosterPath(String posterPath){
		this.posterPath = posterPath;
	}

	public String getPosterPath(){
		return posterPath;
	}

	public void setReleaseDate(String releaseDate){
		this.releaseDate = releaseDate;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	public void setVoteAverage(float voteAverage){
		this.voteAverage = voteAverage;
	}

	public float getVoteAverage(){
		return voteAverage;
	}

	public void setVoteCount(int voteCount){
		this.voteCount = voteCount;
	}

	public int getVoteCount(){
		return voteCount;
	}


	//Add Parcelable Implementation
	protected Catalog(Parcel in) {
		uid = in.readInt();
		catalogType = in.readInt();
		overview = in.readString();
		originalLanguage = in.readString();
		title = in.readString();
		posterPath = in.readString();
		releaseDate = in.readString();
		voteAverage = in.readFloat();
		voteCount = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(uid);
		dest.writeInt(catalogType);
		dest.writeString(overview);
		dest.writeString(originalLanguage);
		dest.writeString(title);
		dest.writeString(posterPath);
		dest.writeString(releaseDate);
		dest.writeFloat(voteAverage);
		dest.writeInt(voteCount);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Catalog> CREATOR = new Creator<Catalog>() {
		@Override
		public Catalog createFromParcel(Parcel in) {
			return new Catalog(in);
		}

		@Override
		public Catalog[] newArray(int size) {
			return new Catalog[size];
		}
	};
}