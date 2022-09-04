
import java.io.*;

import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Scanner;

public class DataFrameImp implements DataFrame {
	public Array<Array<Object>> dataFrame;

	public Map<String, Pair<Integer, Class<?>>> colInfo;

	public DataFrameImp() {

		this.dataFrame = new DynamicArray<Array<Object>>();
		this.colInfo = new BSTMap<String, Pair<Integer, Class<?>>>();
	}

	@Override
	public int getNbCols() {

		return this.dataFrame.size();
	}

	@Override
	public boolean addCol(String colName, Class<?> colType) {

		if (!colInfo.insert(colName, new Pair<Integer, Class<?>>(this.dataFrame.size(), colType)))
			return false;
		this.dataFrame.add(new DynamicArray<Object>());
		return true;
	}

/////////////////////////////////////////////////////////////
	@Override
	public boolean addCol(String colName, Class<?> colType, Array<Object> col) {
		if (!colInfo.insert(colName, new Pair<Integer, Class<?>>(this.dataFrame.size(), colType))
				&& col.size() != this.getNbRows())
			return false;
		Array<Object> temp = new DynamicArray<Object>();

		for (int i = 0; i < col.size(); i++)
			temp.add(col.get(i));
		this.dataFrame.add(temp);
		return true;
	}

	@Override
	public boolean isCol(String colName) {
		return this.colInfo.retrieve(colName).first;
	}

	@Override
	public int getColInd(String colName) throws IllegalArgumentException {

		Pair<Boolean, Pair<Integer, Class<?>>> col = this.colInfo.retrieve(colName);
		if (!col.first)
			throw new IllegalArgumentException();
		return col.second.first;
	}

	@Override
	public String getColName(int j) throws ArrayIndexOutOfBoundsException {

		if (this.dataFrame.size() <= j || j < 0)
			throw new ArrayIndexOutOfBoundsException();
		List<String> list = this.colInfo.getKeys();
		list.findFirst();

		while (!list.last()) {

			if (this.colInfo.retrieve(list.retrieve()).second.first == j)
				return list.retrieve();
			list.findNext();
		}
		if (this.colInfo.retrieve(list.retrieve()).second.first == j)
			return list.retrieve();
		
		
		
		throw new ArrayIndexOutOfBoundsException();

	}

	/////////////////////////////////////////////////////////////////////////////////
	@Override
	public Map<String, Pair<Integer, Class<?>>> getColInfo() {
		return this.colInfo;
	}

	/*
	 * public Map<String, Pair<Integer, Class<?>>> getColInfo() { Map<String,
	 * Pair<Integer, Class<?>>> temp = new BSTMap<String, Pair<Integer,
	 * Class<?>>>(); List<String> keys = this.colInfo.getKeys(); keys.findFirst();
	 * while (keys.last()) { Pair<Integer,Class<?>> node=
	 * this.colInfo.retrieve(keys.retrieve()).second; temp.insert(keys.retrieve(),
	 * new Pair<Integer,Class<?>>(node.first,node.second)); keys.findNext(); }
	 * Pair<Integer,Class<?>> node= this.colInfo.retrieve(keys.retrieve()).second;
	 * temp.insert(keys.retrieve(), new
	 * Pair<Integer,Class<?>>(node.first,node.second)); return temp; }
	 * 
	 */

	@Override
	public Array<Object> getCol(int j) throws ArrayIndexOutOfBoundsException {
		if (this.dataFrame.size() <= j || j < 0)
			throw new ArrayIndexOutOfBoundsException();

		Array<Object> temp = new DynamicArray<Object>();

		for (int i = 0; i < this.getNbRows(); i++)
			temp.add(this.dataFrame.get(j).get(i));
		return temp;
	}

	@Override
	public Array<Object> getCol(String colName) throws IllegalArgumentException {

		Pair<Boolean, Pair<Integer, Class<?>>> col = this.colInfo.retrieve(colName);
		if (!col.first)
			throw new IllegalArgumentException();

		Array<Object> temp = new DynamicArray<Object>();

		for (int i = 0; i < this.getNbRows(); i++)
			temp.add(this.dataFrame.get(col.second.first).get(i));
		return temp;
	}

	@Override
	public int getNbRows() {

		if (this.dataFrame.size() == 0)
			return 0;
		return this.dataFrame.get(0).size();
	}

	@Override
	public void addRow(Array<Object> row) throws IllegalArgumentException {
		if (row.size() != this.dataFrame.size() || row.size() == 0)
			throw new IllegalArgumentException();
		//
		List<String> temp = this.colInfo.getKeys();

		int i = 0;
		temp.findFirst();
		while (!temp.last()) {
			if (!this.colInfo.retrieve(temp.retrieve()).second.second.isInstance(row.get(i++)))
				throw new IllegalArgumentException();

			temp.findNext();
		}

		if (!this.colInfo.retrieve(temp.retrieve()).second.second.isInstance(row.get(i)))
			throw new IllegalArgumentException();
		//
		for (i = 0; i < this.dataFrame.size(); i++)
			this.dataFrame.get(i).add(row.get(i));
	}

	@Override
	public Array<Object> getRow(int i) throws ArrayIndexOutOfBoundsException {
		if (i >= this.getNbRows() || i < 0)
			throw new ArrayIndexOutOfBoundsException();
		Array<Object> temp = new DynamicArray<Object>();
		for (int j = 0; j < this.dataFrame.size(); j++)
			temp.add(this.dataFrame.get(j).get(i));

		return temp;
	}

	@Override
	public boolean loadCSV(String fileName) {

		this.colInfo.clear();

		this.dataFrame = new DynamicArray<Array<Object>>();

		List<String> keys =new LinkedList<String>();
		try {
			File file = new File(fileName);
			Scanner FileInputStream = new Scanner(file);
			String[] nextRecord;
			DataFrameImp temp = new DataFrameImp();

			if (FileInputStream.hasNext()) {
				String firstLine = FileInputStream.nextLine();
				nextRecord = firstLine.split(",");
				for (String cell : nextRecord) {
					keys.insert(cell);
					temp.addCol(cell, Class.forName("java.lang.String"));

				}
			} else {
				FileInputStream.close();
				return false;
			}
			int j = 0;
			keys.findFirst();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Class<?> classes[];
			if (FileInputStream.hasNext()) {
				String secandLine = FileInputStream.nextLine();
				nextRecord = secandLine.split(",");
				classes = new Class<?>[nextRecord.length];
				for (String cell : nextRecord) {
					try {
						int i = Integer.parseInt(cell);

						classes[j] = temp.colInfo.retrieve(keys.retrieve()).second.second = Class
								.forName("java.lang.Integer");
						temp.dataFrame.get(j++).add(i);

					} catch (NumberFormatException e) {
						try {
							double i = Double.parseDouble(cell);
							classes[j] = temp.colInfo.retrieve(keys.retrieve()).second.second = Class
									.forName("java.lang.Double");
							temp.dataFrame.get(j++).add(i);
						} catch (NumberFormatException e1) {
							try {
								Date date = format.parse(cell);
								classes[j] = temp.colInfo.retrieve(keys.retrieve()).second.second = Class
										.forName("java.util.Date");
								temp.dataFrame.get(j++).add(date);
							} catch (Exception e2) {
								classes[j] = Class.forName("java.lang.String");
								temp.dataFrame.get(j++).add(cell);
							}
						}
					}
					keys.findNext();
				}
			} else {
				this.dataFrame = temp.dataFrame;
				this.colInfo = temp.colInfo;
				FileInputStream.close();
				return true;
			}
			keys.findFirst();

			Date d = format.parse("2021-01-12");
			while (FileInputStream.hasNext()) {

				String Line = FileInputStream.nextLine();
				nextRecord = Line.split(",");
				if (temp.dataFrame.size() != nextRecord.length) {

					FileInputStream.close();
					return false;
				}
				for (int i = 0; i < nextRecord.length; i++) {
					if (classes[i].isInstance(1)) {
						int integer = Integer.parseInt(nextRecord[i]);
						temp.dataFrame.get(i).add(integer);

					} else if (classes[i].isInstance(1.0)) {
						double dbl = Double.parseDouble(nextRecord[i]);
						temp.dataFrame.get(i).add(dbl);
					} else if (classes[i].isInstance(d)) {
						Date date = format.parse(nextRecord[i]);
						temp.dataFrame.get(i).add(date);
					} else if (classes[i].isInstance(""))
						temp.dataFrame.get(i).add(nextRecord[i]);

				}
			}

			this.dataFrame = temp.dataFrame;
			this.colInfo = temp.colInfo;
			FileInputStream.close();
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	@Override
	public DataFrame filterCols(Array<String> colNames) throws IllegalArgumentException {
		DataFrameImp temp = new DataFrameImp();

		for (int i = 0; i < colNames.size(); i++) {

			Pair<Boolean, Pair<Integer, Class<?>>> data = this.colInfo.retrieve(colNames.get(i));
			if (!data.first)
				throw new IllegalArgumentException();
			temp.addCol(colNames.get(i), data.second.second, this.dataFrame.get(data.second.first));
		}

		return temp;
	}

	@Override
	public DataFrame filterRows(Condition cond) {
		DataFrameImp dataframe = new DataFrameImp();
		List<String> keys = this.colInfo.getKeys();
		keys.findFirst();
		Pair<Integer, Class<?>> node = null;
		for (int i = 0; i < keys.size(); i++) {
			keys.findFirst();

			while (!keys.last()) {
				node = this.colInfo.retrieve(keys.retrieve()).second;
				if (node.first == i) {
					dataframe.addCol(keys.retrieve(), node.second);
					break;
				}
				keys.findNext();
			}

			node = this.colInfo.retrieve(keys.retrieve()).second;
			if (node.first == i)
				dataframe.addCol(keys.retrieve(), node.second);
		}
		if (keys.size() > 0) {
			keys.findFirst();
			while (!keys.last()) {
				node = this.colInfo.retrieve(keys.retrieve()).second;
				if (node.first == keys.size() - 1) {
					dataframe.addCol(keys.retrieve(), node.second);
					break;
				}
				keys.findNext();
			}

			node = this.colInfo.retrieve(keys.retrieve()).second;
			if (node.first == keys.size() - 1)
				dataframe.addCol(keys.retrieve(), node.second);
		}
		for (int j = 0; j < this.getNbRows(); j++) {
			Array<Object> temp = this.getRow(j);

			if (cond.test(temp))
				dataframe.addRow(temp);

		}

		return dataframe;
	}

	@Override
	public double mean(String colName) throws IllegalArgumentException {
		Pair<Boolean, Pair<Integer, Class<?>>> colInfo = this.colInfo.retrieve(colName);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = format.parse("2021-01-12");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (!colInfo.first || colInfo.second.second.isInstance("") || colInfo.second.second.isInstance(d))
			throw new IllegalArgumentException();

		Array<Object> array = this.dataFrame.get(colInfo.second.first);
		if (array.size() == 0)
			return 0;
		if (array.get(0).getClass().isInstance(0.0)) {
			double sum = 0;
			for (int i = 0; i < this.getNbRows(); i++) {

				sum += (Double) array.get(i);

			}
			return sum / array.size();
		} else {
			int sum = 0;

			for (int i = 0; i < this.getNbRows(); i++) {

				sum += (Integer) array.get(i);

			}
			return sum / array.size();

		}

	}

}
