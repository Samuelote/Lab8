
public class Coin {
	private int x;
	private int y;
	private String img;
	
	public Coin(int x, int y) {
		this.x = x;
		this.y = y;
		this.img = "File:./src/assets/coins.png";
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String newImg) {
		this.img = newImg;
	}
}
