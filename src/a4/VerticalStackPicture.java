package a4;

public class VerticalStackPicture implements Picture{

	private Pixel [][] pixelarray;
	
	public VerticalStackPicture(Picture top, Picture bottom) {
		
		if (top == null || bottom == null)
			throw new IllegalArgumentException("pictures can't be null");
		else if (top.getWidth() != bottom.getWidth()) {
			throw new IllegalArgumentException("must have same width");
		}
		else {
			pixelarray = new Pixel [top.getWidth()][top.getHeight() + bottom.getHeight()];
		}
		
		for (int i = 0; i< top.getWidth();i++) {
			for (int j = 0; j < top.getHeight(); j++) {
				pixelarray[i][j] = top.getPixel(i, j);
			}
		}
		for (int i = 0; i < bottom.getWidth(); i++)  {
			for (int j = top.getHeight(); j< pixelarray[0].length; j++) {
				pixelarray[i][j] = bottom.getPixel(i, j-top.getHeight());
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
