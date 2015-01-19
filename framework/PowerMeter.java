package framework;


public class PowerMeter {
	private int maxPower = 0;
	private int curPower = 0;

	
	public PowerMeter(int power)
	{
		this(power, power);
	}
	
	public PowerMeter(int curPower, int maxPower)
	{
		// Set curPower
		if(curPower > 0)
		{
			this.curPower = curPower;
		}
		else
		{
			this.curPower = 1;
		}
		
		// Set maxPower
		if(maxPower >= this.curPower)
		{
			this.maxPower = maxPower;
		}
		else
		{
			this.maxPower = this.curPower;
		}
	}
	
	public void updatePower(int powerChange)
	{
		curPower = curPower + powerChange;
		
		if(curPower > maxPower)
		{
			curPower = maxPower;
		}
		else if(curPower < 0)
		{
			curPower = 0;
		}
	}
	
	public int getMaxPower()
	{
		return maxPower;
	}
	
	public int getCurPower()
	{
		return curPower;
	}
	
	public double getCurPowerRelative()
	{
		return (double)curPower / (double)maxPower;
	}	
}
