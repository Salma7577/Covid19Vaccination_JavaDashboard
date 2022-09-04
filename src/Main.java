

public class Main {

	private static <T> void print(DataFrame df) {
		System.out.println("--------------------");
		for (int j = 0; j < df.getNbCols(); j++) {
			System.out.print(df.getColName(j) + "\t");
		}
		System.out.println();
		for (int i = 0; i < df.getNbRows(); i++) {
			Array<Object> row = df.getRow(i);
			for (int j = 0; j < df.getNbCols(); j++) {
				System.out.print(row.get(j).toString() + "\t");
			}
			System.out.println();
		}
		System.out.println("--------------------");
	}

	private static <T> void print(Array<T> da) {
		System.out.println("--------------------");
		for (int i = 0; i < da.size(); i++) {
			System.out.println(da.get(i));
		}
		System.out.println("--------------------");
	}

	private static <T> void print(List<T> l) {
		System.out.println("--------------------");
		if (!l.empty()) {
			l.findFirst();
			while (!l.last()) {
				System.out.println(l.retrieve());
				l.findNext();
			}
			System.out.println(l.retrieve());
		}
		System.out.println("--------------------");
	}

	public static void testDataFrame() {
		DataFrame df = new DataFrameImp();
		df.loadCSV("Vaccination.csv");
		System.out.println(df.getNbCols() + " coumns\t" + df.getNbRows() + " rows");
		DataFrame fdf = df.filterRows(new CountryNameCond("Italy"));
		System.out.println(fdf.mean("People Vaccinated"));
		System.out.println(fdf.mean("Percentage of People Fully Vaccinated"));
		print(fdf);
	}

	public static void testCOVID19Vaccination() {
		COVID19Vaccination vac = new COVID19VaccinationImp();
		DataFrame df = new DataFrameImp();
		df.loadCSV("Vaccination.csv");
		vac.setData(df);
		List<String> res = vac.getVaccines("World").getKeys();
		print(res);
	}

	public static void testDashboard() {
		COVID19Vaccination vac = new COVID19VaccinationImp();
		DataFrame df = new DataFrameImp();
		df.loadCSV("Vaccination.csv");
		vac.setData(df);
		Dashboard db = new Dashboard(vac, "Italy");
	}

	public static void main(String[] args) {
		testDataFrame();
		testCOVID19Vaccination();	
		testDashboard();
	}
}
