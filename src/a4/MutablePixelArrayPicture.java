package a4;

public class MutablePixelArrayPicture implements Picture{


	private Pixel [][] pixelarray;
	
	// Creates new object using values provided by pixel_array, matching in size. 
	public MutablePixelArrayPicture(Pixel[][] pixel_array) {
		
		
		if(pixel_array == null) 
			throw new IllegalArgumentException("array must exist");
		for ( int i = 0; i < pixel_array.length; i++) {
			if (pixel_array[i] == null)
				throw new IllegalArgumentException("arrays within the 2-dimensional array must exist");
		}
		
		for ( int i = 0; i < pixel_array.length;i++) {
			for (int j = 0; j < pixel_array[i].length;j++) {
				if (pixel_array[i][j] == null)
					throw new IllegalArgumentException("Pixels in array must exist");
			}
		}
		for (int i = 0; i < pixel_array.length-1 ;i++) {
			if (pixel_array[i].length != pixel_array[i+1].length)
				throw new IllegalArgumentException("columns must be same length");
		}
		if (pixel_array.length == 0 || pixel_array[0].length == 0)
			throw new IllegalArgumentException("width and height cannot be zero");
		else {
			pixelarray = new Pixel[pixel_array.length][pixel_array[0].length];
			for ( int i = 0; i < pixel_array.length; i++) {
				for (int j = 0; j < pixel_array[0].length;j++) {
					pixelarray[i][j] = pixel_array[i][j];
				}
			}
		}
			
		
	}

	// Creates new object by providing geometry of picture and an initial value for all pixels.
	public MutablePixelArrayPicture(int width, int height, Pixel initial_value) {
		if (initial_value == null)
			throw new IllegalArgumentException("Pixel must exist");
		else if (width == 0|| height == 0) 
			throw new IllegalArgumentException("Dimensions cannot be zero");
		else {
			pixelarray = new Pixel [width][height];
			for ( int i = 0; i < width;i++) {
				for (int j = 0; j < height;j++) {
					pixelarray[i][j] = initial_value;
				}
			}
		}
	}

	// Creates new object by providing geometry of picture. 
	// Initial value of all pixels should be medium gray (i.e., a grayscale pixel with intensity 0.5)
	public MutablePixelArrayPicture(int width, int height) {
		if (width == 0 || height == 0)
			throw new IllegalArgumentException("dimensions cannot be zero");
		else {
			pixelarray = new Pixel [width][height];
			for ( int i = 0; i < pixelarray.length;i++) {
				for (int j = 0; j < pixelarray[0].length;j++) {
					pixelarray[i][j] = new GrayPixel(0.5);
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
		if (x > getWidth() -1 || y > getHeight() -1 || x<0 || y<0)
			throw new IllegalArgumentException("point is out of range");
		else
			return pixelarray[x][y];
	}
	public Picture paint(int x, int y, Pixel p) {
		if (x > getWidth() -1 || y > getHeight() -1 || x<0 || y<0)
			throw new IllegalArgumentException("point is out of range");
		else if (p == null) 
			throw new IllegalArgumentException("pixel must exist");
		else {
			pixelarray[x][y] = new ColorPixel(p.getRed(),p.getGreen(),p.getBlue());
			return this;
		}
	}
	public Picture paint(int x, int y, Pixel p, double factor) {
		if (x > getWidth() -1 || y > getHeight() -1 || x<0 || y<0)
			throw new IllegalArgumentException("point is out of range");
		else if (p == null) 
			throw new IllegalArgumentException("pixel must exist");
		else if (factor < 0 || factor >1) 
			throw new IllegalArgumentException("facotr must be between 0 and 1 inclusive");
		else { 
			if (p.getRed() != p.getGreen() && p.getRed() !=p.getBlue()) {
				Pixel pp = pixelarray[x][y].blend(p, factor);
				pixelarray[x][y] = pp;
				return this;
			}
			else {
				Pixel pix = pixelarray[x][y].blend(p, factor);
				pixelarray[x][y] = pix;
				return this;
			}
		}
		
	}

	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		if (ax > getWidth() -1 || ay > getHeight() -1 || ax<0 || ay<0 || bx> getWidth()-1 ||
				by > getHeight() -1 || bx <0 || by <0)
			throw new IllegalArgumentException("point is out of range");
		else if (p == null) 
			throw new IllegalArgumentException("pixel must exist");
		else {
			pixelarray = paintAreaSame(ax,ay,bx,by,p,pixelarray);
		}
		System.out.println(pixelarray[1][1].getBlue());
		return this;
	}
		
	
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		if (ax > getWidth() -1 || ay > getHeight() -1 || ax<0 || ay<0 || bx> getWidth()-1 ||
				by > getHeight() -1 || bx <0 || by <0)
			throw new IllegalArgumentException("point is out of range");
		else if (p == null) 
			throw new IllegalArgumentException("pixel must exist");
		else if (factor < 0 || factor >1) 
			throw new IllegalArgumentException("factor must be between 0 and 1 inclusive");
		else  {
				if (ax >= bx) {	
					if (ay >= by) {
						pixelarray = blendArea(bx,by,ax,ay,p,pixelarray,factor);
					}
					else {
						pixelarray = blendArea(bx,ay,ax,by,p,pixelarray,factor);
					}
				}
				else {
					if (ay  >= by) {
						pixelarray = blendArea(ax,by,bx,ay,p,pixelarray,factor);
					}
					else {
						pixelarray = blendArea(ax,ay,bx,by,p,pixelarray,factor);
					}
				}	
			return this;
		}
	}
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		if (cx > getWidth() -1 || cy > getHeight() -1 || cx <0 || cy < 0) 
			throw new IllegalArgumentException("Point is not in the picture");
		else if (p == null) 
			throw new IllegalArgumentException("pixel must exist");
		else {
			for (int i = 0; i< pixelarray.length; i++) {
				for (int j = 0; j<pixelarray[0].length;j++) {
					if ( Math.sqrt((i-cx)*(i-cx)+(j-cy)*(j-cy)) <= radius) {
						pixelarray[i][j] = p;
					}
				}
			}
			return this;
		}
	}
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		if (cx > getWidth() -1 || cy > getHeight() -1 || cx <0 || cy < 0) 
			throw new IllegalArgumentException("Point is not in the picture");
		else if (p == null) 
			throw new IllegalArgumentException("pixel must exist");
		else if (factor >1 || factor <0) 
			throw new IllegalArgumentException("factor must be between 0 and 1"); 
		else {	
			for (int i = 0; i< pixelarray.length; i++) {
				for (int j = 0; j<pixelarray[0].length;j++) {
					if ( Math.sqrt((i-cx)*(i-cx)+(j-cy)*(j-cy)) <= radius) {
						if (p.getRed() == p.getGreen() && p.getRed() == p.getBlue() ) {
							
							pixelarray[i][j] = blendGray(p,pixelarray[i][j],factor);
						}
						else {
							pixelarray[i][j] = blendColor(p,pixelarray[i][j],factor);
						}
					}
				}
			}
			return this;
		}
	}
	
	
}
