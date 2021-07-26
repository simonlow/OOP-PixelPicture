package a4;

public class MonochromePicture implements Picture {
	
	private Pixel [][] pixelarray;
	
	public MonochromePicture(int width, int height, Pixel value) { 
		if (value == null)
			throw new IllegalArgumentException("Pixel value must exist");
		else if (width == 0 || height == 0)
			throw new IllegalArgumentException("Dimensons cannot be zero");
		else {
			pixelarray = new Pixel[width][height];
			for (int i = 0; i < pixelarray.length;i++) {
				for (int j = 0; j < pixelarray[0].length;j++) {
					pixelarray[i][j] = value;
				}
			}
		}
	}
	public int getWidth() {
		return pixelarray.length;
	}
	public int getHeight() {
		return pixelarray[0].length;
	}
	
	public Pixel getPixel(int x, int y) {
		if (x <0 || y < 0|| x> getWidth()- 1 || y > getHeight() -1) 
			throw new IllegalArgumentException("coordinates cannot be negative");
		else
			return pixelarray[x][y];
	}
	
	
	public Picture paint(int x, int y, Pixel p) {
		if (x > getWidth() -1 || y > getHeight() -1 || x<0 || y<0)
			throw new IllegalArgumentException("point is out of range");
		else {
			Pixel [][] newPixelArray = duplicateThisArray();
			newPixelArray[x][y] = new ColorPixel(p.getRed(), p.getGreen(),p.getBlue());
			Picture n = new ImmutablePixelArrayPicture(newPixelArray);
			return n;
		}
		
	}

	public Picture paint(int x, int y, Pixel p, double factor) {
		if (x > getWidth() -1 || y > getHeight() -1 || x<0 || y<0)
			throw new IllegalArgumentException("point is out of range");
		else if (p == null) 
			throw new IllegalArgumentException("pixel must exist");
		else {
			Picture n = new MutablePixelArrayPicture(pixelarray);
			n.paint(x, y, p,factor);
			return n;
		}
	}

	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		if (ax > getWidth() -1 || ay > getHeight() -1 || ax<0 || ay<0 || bx> getWidth()-1 ||
				by > getHeight() -1 || bx <0 || by <0)
			throw new IllegalArgumentException("point is out of range");
		else if (p == null) 
			throw new IllegalArgumentException("pixel must exist");
		else {	
			Picture n = new MutablePixelArrayPicture(pixelarray);
			n.paint(ax, ay, bx,by,p);
			return n;
		}
	}
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		if (ax > getWidth() -1 || ay > getHeight() -1 || ax<0 || ay<0 || bx> getWidth()-1 ||
				by > getHeight() -1 || bx <0 || by <0)
			throw new IllegalArgumentException("point is out of range");
		else if (p == null) 
			throw new IllegalArgumentException("pixel must exist");
		else if (factor < 0 || factor >1) 
			throw new IllegalArgumentException("facotr must be between 0 and 1 inclusive");
		else {
			Picture n = new MutablePixelArrayPicture(pixelarray);
			n.paint(ax, ay, bx,by,p,factor);
			return n;
		}
	}

	public Picture paint(int cx, int cy, double radius, Pixel p) {
		if (p == null) 
			throw new NullPointerException("pixel must exist");
		else {
			Picture n = new MutablePixelArrayPicture(pixelarray);
			n.paint(cx, cy, radius,p);
			return n;
		}
	}
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		if (p == null) 
			throw new NullPointerException("pixel must exist");
		else if (factor <0 || factor >1) 
			throw new IllegalArgumentException("factor must be between 0 and 1");
		else {
			Picture n = new MutablePixelArrayPicture(pixelarray);
			n.paint(cx, cy, radius,p,factor);
			return n;
		}
	}
	public Pixel[][] duplicateThisArray () {
		Pixel [][] newPixelArray = new Pixel[getWidth()][getHeight()];
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				newPixelArray[i][j] = new ColorPixel(pixelarray[i][j].getRed(),
						pixelarray[i][j].getGreen(),pixelarray[i][j].getBlue());
			}
		}
		return newPixelArray;
	}
	
}
