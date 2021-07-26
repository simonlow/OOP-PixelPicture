package a4;

public class GradientPicture implements Picture{

	private Pixel [][] pixelarray;
	
	public GradientPicture(int width, int height, Pixel upper_left, 
			Pixel upper_right, Pixel lower_left, Pixel lower_right) {
		if (upper_left == null || upper_right == null || lower_left == null 
				|| lower_right == null) {
			throw new IllegalArgumentException("Pixels must exist");
		}
		else if (width <=0 || height <= 0)
			throw new IllegalArgumentException("width and height must be above zero" );
		else {
			pixelarray = new Pixel[width][height];
			pixelarray[0][0] = upper_left;
			pixelarray[0][height-1] = lower_left;
			pixelarray[width-1][height-1] = lower_right;
			pixelarray[width-1][0] = upper_right;
			
			double wFactor = 1.0/(double)(width-1);
			double hFactor = 1.0/(double)(height-1);
			
			
			for (int i = 1; i< width-1;i++) {
				pixelarray[i][0] = upper_left.blend(upper_right, wFactor*(double)i);
			}
			for (int i = 1; i< width-1; i++) {
				pixelarray[i][height-1] = lower_left.blend(lower_right, wFactor*(double)i);
			}
			for (int i = 1; i< height-1; i++) {
				pixelarray[0][i] = upper_left.blend(lower_left, hFactor*(double)i);
			}
			for (int i = 1; i< height-1; i++) {
				pixelarray[width-1][i] = upper_right.blend(lower_right, hFactor*(double)i);
			}
			for (int i = 1; i< width-1; i++) {
				for (int j = 1; j < height-1; j++) {
					pixelarray[i][j] =pixelarray[i][0].blend(pixelarray[i][height-1], hFactor*(double)(j));
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
		if (x < 0 || y < 0)
			throw new IllegalArgumentException("x and y must be positive");
		else 
			return pixelarray[x][y];
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
		else if (radius <0 )
			throw new IllegalArgumentException("radius must be postive");
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
