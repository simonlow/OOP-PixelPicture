package a4;


public interface Picture {
	
	// Getters for the dimensions of a picture.
	// Width refers to the number of columns and 
	// height is the number of rows.
	
	public int getWidth();
	public int getHeight();
	
	// getPixel(x, y) retrieves the pixel at position (x,y) in the
	// picture. The coordinate (0,0) is the upper left
	// corner of the picture. The coordinate (getWidth()-1, getHeight()-1)
	// is the lower right of the picture. An IllegalArgumentException
	// is thrown if x or y are not in range.
	
	public Pixel getPixel(int x, int y);
	
	// The various forms of the paint() method return a new
	// picture object based on this picture with certain pixel
	// positions "painted" with a new value.
	
	// paint(int x, int y, Pixel p) paints the pixel at
	// position (x,y) with the pixel value p. The second 
	// form includes a factor parameter that specifies a
	// blending factor. A factor of 0.0 leaves the specified
	// pixel unchanged. A factor of 1.0 results in replacing
	// the value with the specified pixel value. Values between
	// 0.0 and 1.0 blend proportionally.
	
	public Picture paint(int x, int y, Pixel p);
	public Picture paint(int x, int y, Pixel p, double factor);
	
	
	// paint(int ax, int ay, int bx, int by, Pixel p) paints the
	// rectangular region defined by the positions (ax, ay) and
	// (bx, by) with the specified pixel value. The second form
	// should blend with the specified factor as previously described.
	
	public Picture paint(int ax, int ay, int bx, int by, Pixel p);
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor);

	// paint(int cx, int cy, double radius, Pixel p) sets all pixels in the
	// picture that are within radius distance of the coordinate (cx, cy) to the
	// Pixel value p.  Only positive values of radius should be allowed. Any
	// value of cx and cy should be allowed (even if negative or otherwise
	// outside of the boundaries of the frame). 
	
	// Calculate the distance of a particular (x,y) position to (cx,cy) with
	// the expression: Math.sqrt((x-cx)*(x-cx)+(y-cy)*(y-cy))	

	// The second form with factor, blends as previously described.
	
	public Picture paint(int cx, int cy, double radius, Pixel p);
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor);
	
	public default Pixel blendGray(Pixel p1, Pixel p2, double factor) {
		double red1 = p2.getRed()*(1-factor);
		double green1 = p2.getGreen()*(1-factor);
		double blue1 = p2.getBlue()*(1-factor);
		
		double gray2 = factor*p1.getIntensity();
		
		double newRed = red1+gray2;
		double newGreen = green1+gray2;
		double newBlue = blue1+gray2;
		Pixel pix = new ColorPixel(newRed,newGreen,newBlue);
		return pix;
	}
	public default Pixel blendColor(Pixel p1, Pixel p2, double factor) {
		double red1 = p2.getRed()*(1-factor);
		double green1 = p2.getGreen()*(1-factor);
		double blue1 =p2.getBlue()*(1-factor);
		
		double red2 = p1.getRed()*factor;
		double green2 = p1.getGreen()*factor;
		double blue2 = p1.getBlue()*factor;
		
		double newRed = red1+red2;
		double newGreen = green1+green2;
		double newBlue = blue1+blue2;
		
		Pixel pix = new ColorPixel(newRed,newGreen,newBlue);
		return pix;
		
	}
	public default Pixel [][] paintAreaSame(int x1, int y1, int x2, int y2, Pixel p, Pixel [][] pixelarray) {
		
		if (x1 >= x2) {
			
			if (y1 >= y2) {
				for (int i = x2; i <= x1; i++) {
					for (int j = y2; j <= y1; j++) {
						pixelarray[i][j] = p;
					}
				}
			}
			else {
				for (int i = x2; i <= x1; i++) {
					for (int j = y1; j <= y2; j++) {
						pixelarray[i][j] = p;
					}
				}
			}
		}
		else {
			if (y1  >= y2) {
				for (int i = x1; i <= x2; i++) {
					for (int j = y2; j <= y1; j++) {
						pixelarray[i][j] = p;
					}
				}
			}
			else {
				for (int i = x1; i <= x2; i++) {
					for (int j = y1; j <= y2; j++) {
						pixelarray[i][j] = p;
					}
				}
			}
		}
		return pixelarray;
		
	}
	public default Pixel[][] blendArea (int x1, int y1, int x2, int y2, Pixel p, Pixel [][] pixelarray, double factor) {
		
		for (int i = x1; i <= x2-x1; i++) {
			for (int j = y1; j <= y2-y1; j++) {
				
				if (p.getRed() == p.getGreen() && p.getRed() == p.getBlue() )
					pixelarray[i][j] = blendGray(p,pixelarray[i][j],factor);
				else
					pixelarray[i][j] = blendColor(p,pixelarray[i][j],factor);
			}
		}
		return pixelarray;
	}
	
}
