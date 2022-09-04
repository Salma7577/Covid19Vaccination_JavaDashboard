

public class CountryNameCond implements Condition{
	
	

	private String  countryName;
	
	
	public CountryNameCond(String countryName) {
		this.countryName = countryName;
	}
	@Override
	public boolean test(Array<Object> row) {
		return row.get(0).equals(countryName);
	}

}
