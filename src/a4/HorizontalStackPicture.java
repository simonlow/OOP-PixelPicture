package a4;

public class HorizontalStackPicture implements Picture {

	private Pixel [][] pixelarray;
	
	public HorizontalStackPicture(Picture left, Picture right) {
		
		if (left == null || right == null)
			throw new IllegalArgumentException("Picture must not be null");
		else if (left.getHeight() != right.getHeight()) {
			throw new IllegalArgumentException("Pictures must be same height");
			
		}
		else {
			pixelarray = new Pixel[left.getWidth() +right.getWidth()][left.getHeight()];
		}
		for(int i = 0; i< left.getWidth(); i++) {
			for(int j = 0; j<left.getHeight();j++) {
				pixelarray[i][j] = left.getPixel(i,j);
			}
		}
		for(int i = 0; i< right.getWidth();i++) {
			for (int j = 0; j< right.getHeight();j++) {
				pixelarray[i + left.getWidth()][j] = right.getPixel(i, j);
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
			throw new IllegalArgumentException("point must be valid");
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
			pixelarray[x][y] = p;
			return this;
		}
	}
	public Picture paint(int x, int y, Pixel p, double factor) {
		if (x > getWidth() -1 || y > getHeight() -1 || x<0 || y<0)
			throw new IllegalArgumentException("point is out of range");
		else if (p == null) 
			throw new IllegalArgumentException("pixel must exist");
		else if (factor <0 || factor >1) 
			throw new IllegalArgumentException("factor must be between 0 and 1");
		else if (p.getRed() != p.getGreen() && p.getRed() !=p.getBlue()) {
			Pixel pp = blendColor(p, pixelarray[x][y], factor);
			pixelarray[x][y] = pp;
			return this;
		}
		else {
			Pixel pix = blendGray(p,pixelarray[x][y],factor);
			pixelarray[x][y] = pix;
			return this;
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
		else  {
			paintAreaSame(ax,ay,bx,by,p,pixelarray);
		}
			return this;
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
		else  {
			
				if (ax >= bx) {	
					if (ay >= by) {
						pixelarray = blendArea(bx,by,ax,ay,p, pixelarray,factor);
					}
					else {
						pixelarray = blendArea(bx,ay,ax,by,p, pixelarray,factor);
					}
				}
				else {
					if (ay  >= by) {
						pixelarray = blendArea(ax,by,bx,ay,p, pixelarray,factor);
					}
					else {
						pixelarray = blendArea(ax,ay,bx,by, p, pixelarray,factor);
					}
				}
			
				
			return this;
		}
	}
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		if (p == null) 
			throw new NullPointerException("pixel must exist");
		else if (radius <0 )
			throw new IllegalArgumentException("radius must be postive");
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
		for (int i = 0; i< pixelarray.length; i++) {
			for (int j = 0; j<pixelarray[0].length;j++) {
				if ( Math.sqrt((i-cx)*(i-cx)+(j-cy)*(j-cy)) <= radius) {
					if (p.getRed() != p.getGreen() && p.getRed() != p.getBlue() ) {
						pixelarray[i][j] = blendColor(p,pixelarray[i][j],factor);
					}
					else {
						pixelarray[i][j] = blendGray(p,pixelarray[i][j],factor);
					}
				}
			}
		}
		return this;
	}
	
	
}
