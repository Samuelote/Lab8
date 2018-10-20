
public class Player {
	
	private int pX;
	private int pY;
	private String name;
	private String img;
	private int numOfItems;
	
	public Player(int pX, int pY, String name, int numOfItems, String img){
		this.pX = pX;
		this.pY = pY;
		this.name = name;
		this.numOfItems = numOfItems;
		this.img = img;
	}
		
	
	public int getpX() {
		return pX;
	}

	public void setpX(int pX) {
		this.pX = pX;
	}

	public int getpY() {
		return pY;
	}

	public void setpY(int pY) {
		this.pY = pY;
	}


	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}


}
