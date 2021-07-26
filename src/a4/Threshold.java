package a4;

public class Threshold implements PixelTransformation {
	
	private double threshold;
	public Threshold (double threshold) {
		this.threshold = threshold;
	}
	public Pixel transform(Pixel p) {
		if (p.getIntensity() >threshold) {
			Pixel pp = new GrayPixel(1.0);
			return pp;
		}
		else {
			Pixel pp = new GrayPixel(0.0);
			return pp;
		}
			
	}
}
