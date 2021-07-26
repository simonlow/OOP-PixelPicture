package a4;


public class ImmutablePixelArrayPicture implements Picture {
	
	private Pixel [][] pixelarray;
	// Creates new object using values provided by pixel_array, matching in size.
	public ImmutablePixelArrayPicture(Pixel[][] pixel_array) {
		if (pixel_array == null)
			throw new IllegalArgumentException("array must exist");
		else if (pixel_array.length==0 || pixel_array[0].length == 0)
			throw new IllegalArgumentException("array rows and columns must not be zero");
		for (int i = 0; i < pixel_array.length;i++) {
			if (pixel_array[i] == null)
				throw new IllegalArgumentException("rows cannot be null");
		}
		for (int i = 0; i < pixel_array.length-1;i++) {
			if  (pixel_array[i].length != pixel_array[i+1].length)
				throw new IllegalArgumentException("All columns must be same height");
		}
		for ( int i = 0; i< pixel_array.length; i++) {
			for ( int j = 0; j< pixel_array[0].length;j++) {
				if ( pixel_array[i][j] == null) 
					throw new IllegalArgumentException("pixels cannot be null");
			}
		}
		
				
		
		pixelarray = pixel_array;
	}

	// Creates new object by providing geometry and initial value for all pixels.
	public ImmutablePixelArrayPicture(int width, int height, Pixel initial_value) {
		if (initial_value == null) 
			throw new IllegalArgumentException("pixel must exist");
		else if(width <=0 || height <=0)
			throw new IllegalArgumentException("width and height must be above zero");
		else {
			pixelarray = new Pixel[width][height];
			for (int i = 0; i < pixelarray.length;i++) {
				for (int j = 0; j<pixelarray[0].length;j++) {
					pixelarray[i][j] = initial_value;
				}
			}
		}
	}

	// Creates new object by providing geometry. Initial value should be medium gray.
	public ImmutablePixelArrayPicture(int width, int height) {
		if ( width <= 0 || height <=0)
			throw new IllegalArgumentException("width and height must be positive");
		else {
			pixelarray = new Pixel [width][height];
			for (int i = 0; i < pixelarray.length;i++) {
				for (int j = 0; j<pixelarray[0].length;j++) {
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
		if (x<0 || y < 0)
			throw new IllegalArgumentException("x and y must me actual indeces");
		else 
			return pixelarray [x][y];
	}
	public Picture paint(int x, int y, Pixel p) {
		if (p == null) {	
			throw new IllegalArgumentException("p must exist");
		}
		else if (x < 0 || y < 0 || x>= getWidth() || y>= getHeight()) {
			throw new IllegalArgumentException("point must be in array");
		}
		else {
			Pixel [][] newPixelArray = duplicateThisArray();
			newPixelArray[x][y] = p;
			Picture pp = new ImmutablePixelArrayPicture(newPixelArray);
			return pp;
		}
	}
	public Picture paint(int x, int y, Pixel p, double factor) {
		if (x > getWidth() -1 || y > getHeight() -1 || x<0 || y<0)
			throw new IllegalArgumentException("point is out of range");
		else if (p == null) 
			throw new IllegalArgumentException("pixel must exist");
		else if (factor <0 || factor >1) 
			throw new IllegalArgumentException("factor must be between 0 and 1");
		else {	
			Pixel [][] newPixelArray = duplicateThisArray();
			newPixelArray[x][y] = blendColor(p,newPixelArray[x][y],factor);
			Picture pp = new ImmutablePixelArrayPicture(newPixelArray);
			return pp; 
		}
	}
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		if (  (ax >= getWidth() && bx >=getWidth()) ||
				(ay >= getHeight() && by >= getHeight()) ||
				(ax <0 && bx < 0) || (ay < 0 && by <0)  ) {
			throw new IllegalArgumentException("area is out of range");
		}
		else if (p == null) 
			throw new IllegalArgumentException("pixel must exist");
		else {	
			Pixel [][] newPixelArray = duplicateThisArray();
			paintAreaSame(ax,ay,bx,by, p, newPixelArray);
			Picture pp = new ImmutablePixelArrayPicture (newPixelArray);
			return pp;
		}
	}
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		if (  (ax >= getWidth() && bx >=getWidth()) ||
				(ay >= getHeight() && by >= getHeight()) ||
				(ax <0 && bx < 0) || (ay < 0 && by <0)  )
			throw new IllegalArgumentException("point is out of range");
		else if (p == null) 
			throw new IllegalArgumentException("pixel must exist");
		else if (factor < 0 || factor >1) 
			throw new IllegalArgumentException("facotr must be between 0 and 1 inclusive");
		else {
			Pixel [][] newPixelArray = duplicateThisArray();
			for (int i = 0; i <getWidth(); i++) {
				for (int j = 0; j < getHeight(); j++) {
					newPixelArray[i][j] = blendColor(p, newPixelArray[i][j], factor);
				}
			}
			
			Picture pp = new ImmutablePixelArrayPicture(newPixelArray);
			return pp;
		}
	}
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		if (p == null) 
			throw new NullPointerException("pixel must exist");
		else if (radius <0 )
			throw new IllegalArgumentException("radius must be postive");
		else {	
			Pixel [][] newPixelArray = duplicateThisArray();
			for (int i = 0; i< newPixelArray.length; i++) {
				for (int j = 0; j<newPixelArray[0].length;j++) {
					if ( Math.sqrt((i-cx)*(i-cx)+(j-cy)*(j-cy)) <= radius) {
						newPixelArray[i][j] = p;
					}
				}
			}
			Picture pp = new ImmutablePixelArrayPicture(newPixelArray);
			return pp; 
		}
	}
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		if (p == null) 
			throw new NullPointerException("pixel must exist");
		else if (factor <0 || factor >1) 
			throw new IllegalArgumentException("factor must be between 0 and 1");
		else {
			Pixel [][] newPixelArray = duplicateThisArray();
			for (int i = 0; i< newPixelArray.length; i++) {
				for (int j = 0; j<newPixelArray[0].length;j++) {
					if ( Math.sqrt((i-cx)*(i-cx)+(j-cy)*(j-cy)) <= radius) {
						newPixelArray[i][j] = blendColor(p, newPixelArray[i][j], factor);
					}
				}
			}
			Picture pp = new ImmutablePixelArrayPicture(newPixelArray);
			return pp; 
		}
	}
	public Pixel[][] duplicateThisArray () {
		Pixel [][] newPixelArray = new Pixel[getWidth()][getHeight()];
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				newPixelArray  [i][j] = pixelarray[i][j];
			}
		}
		return newPixelArray;
		
	}
}
