package framework;


public class RangeNavigator {
	int current;	//current value in range [0, maxVal-minVal]
	int step;
	int minVal;
	int maxVal;
	int rangeWidth;
	boolean comingBack = false;	// if true, we are flowing back, from maxVal to minVal
	
	public RangeNavigator(int minVal, int maxVal, int step)
	{
		if(minVal > maxVal)
		{
			int sw = minVal;
			minVal = maxVal;
			maxVal = sw;
		}
		
		this.current = 0;
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.step = step;
		this.rangeWidth = maxVal - minVal;
	}
	
	public RangeNavigator(int minVal, int maxVal, int step, int startVal)
	{
		this(minVal, maxVal, step);
		
		if(startVal >= minVal && startVal <= maxVal)
		{
			current = startVal - minVal;
		}
	}
	
	public void setReverseNavigation(boolean reverse)
	{
		this.comingBack = reverse;
	}
	
	private void nextStep()
	{
		// set step
		int currentStep = step;
		
		if(comingBack)
		{
			currentStep = -step;
		}
		
		int next = current + currentStep;
		
		if(next <= 0)
		{
			comingBack = false;
			current = 0;
			
		}
		else if(next >= rangeWidth)
		{
			comingBack = true;
			current = rangeWidth;
		}
		else
		{
			current = next;
		}
	}
	
	public int getNext()
	{
		nextStep();
		
		//System.out.println("RangeNavigator.getNext()=" + current);
		
		return current + minVal;
	}
	
}
