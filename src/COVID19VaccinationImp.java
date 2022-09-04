

public class COVID19VaccinationImp implements COVID19Vaccination {
	public DataFrame df;

	@Override
	public void setData(DataFrame df) {
		this.df = df;
	}

	@Override
	public DataFrame getPeopleVaccinated(String countryName) {
		Array<String> colNames = new DynamicArray<String>();
		colNames.add("Date");
		colNames.add("People Vaccinated");
		DataFrame PeopleVaccinated = df.filterRows(new CountryNameCond(countryName));
		PeopleVaccinated = PeopleVaccinated.filterCols(colNames);
		return PeopleVaccinated;
	}

	@Override
	public DataFrame getPeopleFullyVaccinated(String countryName) {
		Array<String> colNames = new DynamicArray<String>();
		colNames.add("Date");
		colNames.add("People Fully Vaccinated");
		DataFrame PeopleVaccinated = df.filterRows(new CountryNameCond(countryName));
		PeopleVaccinated = PeopleVaccinated.filterCols(colNames);
		return PeopleVaccinated;
	}

	@Override
	public DataFrame getPeopleVaccinatedPerHundred(String countryName) {
		Array<String> colNames = new DynamicArray<String>();
		colNames.add("Date");
		colNames.add("Percentage of People Vaccinated");
		DataFrame PeopleVaccinated = df.filterRows(new CountryNameCond(countryName));
		PeopleVaccinated = PeopleVaccinated.filterCols(colNames);
		return PeopleVaccinated;
	}

	@Override
	public DataFrame getPercentageOfPeopleFullyVaccinated(String countryName) {
		Array<String> colNames = new DynamicArray<String>();
		colNames.add("Date");
		colNames.add("Percentage of People Fully Vaccinated");
		DataFrame PeopleVaccinated = df.filterRows(new CountryNameCond(countryName));

		PeopleVaccinated = PeopleVaccinated.filterCols(colNames);
		return PeopleVaccinated;
	}

	@Override
	public Set<String> getVaccines(String countryName) {
		Array<String> colNames = new DynamicArray<String>();
		colNames.add("Vaccines");
		DataFrame PeopleVaccinated = df.filterRows(new CountryNameCond(countryName));
		BSTSet<String> set = new BSTSet<String>();

		if (PeopleVaccinated.getNbRows() <= 0)
			return set;
		String vaccine = (String) PeopleVaccinated.filterCols(colNames).getCol("Vaccines").get(0);
		String vaccines[] = vaccine.split("-");
		for (String cell : vaccines)
			set.insert(cell);

		return set;
	}

	@Override
	public double getAvgVaccinatedPerDay(String countryName) {
		return this.getPeopleVaccinated(countryName).mean("People Vaccinated");
	}

	@Override
	public double getAvgFullyVaccinatedPerDay(String countryName) {
		return this.getPeopleFullyVaccinated(countryName).mean("People Fully Vaccinated");
	}

}
