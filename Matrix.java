public class Matrix {

	private float[][] data;
	public Matrix(float[][] dat) {
		data = dat;
	}
	public Matrix(int w, int h) {
		if (w > 0 && h > 0) {
			data = new float[h][w];
		} else {
			data = null;
			throw new IllegalArgumentException("incorrect w,h");
		}
	}
	public int getW() {
		return data == null ?0: data[0].length;
	}
	public int getH() {
		return data == null ?0: data.length;
	}
	public Matrix inverse() {
		Matrix rev= mergeHorizen(this, E(getH()));
		data = rev.data;

		int index=0;
		int row=0;
		int column=0;
		for (;index < getH() - 1;index++) {

			//System.out.println(rev);
			column = index;
			for (row = index + 1;row < getH();row++) {
				if (data[index][column] != 0) {
					//System.out.printf("[%d]+  %.2f/%.2f  [%d]\n",row+1,data[row][column],data[index][column],index+1);
					addRow(row + 1, -data[row][column] / data[index][column], index + 1);
				}
			}


		}
		for (;index > 0;index--) {

			//System.out.println(rev);
			column = index;

			for (row = index - 1;row >= 0 ;row--) {

				if (data[index][column] != 0) {

					addRow(row + 1, -data[row][column] / data[index][column], index + 1);
				}
			}
		}
		for (;index < getH();index++) {
			scaleRow(index + 1, 1 / data[index][index]);
		}
		//System.out.println(rev);
		rev = rev.sub(getH() + 1, getW() - getH(), 1, getH());
		data = rev.data;
		return this;
	}

	public Matrix addRow(int mainRow, float k, int subRow) {
		mainRow--;
		subRow--;
		for (int x=0;x < getW();x++) {
			data[mainRow][x] += k * data[subRow][x];
		}
		return this;
	}
	public Matrix scaleRow(int row, float scale) {
		row--;
		for (int x=0;x < getW();x++) {
			data[row][x] *= scale;
		}
		return this;
	}
	public Matrix sub(int l, int wid, int t, int hei) {
		l--;
		t--;
		//System.out.printf("sub(%d,%d)", wid, hei);
		Matrix mat=new Matrix(wid, hei);
		for (int y=0;y < mat.data.length;y++) {
			for (int x=0;x < mat.data[y].length;x++) {
				mat.data[y][x] = data[y + t][x + l];

			}
		}
		return mat;
	}
	public boolean isZero() {
		for (int y=0;y < data.length;y++) {
			for (int x=0;x < data[y].length;x++) {
				if (data[y][x] != 0) {
					return false;
				}
			}
		}
		return true;
	}
	public boolean isE() {

		for (int y=0;y < data.length;y++) {
			for (int x=0;x < data[y].length;x++) {
				if (x == y) {
					if (data[y][x] != 1) {
						return false;
					}
				} else {
					if (data[y][x] != 0) {
						return false;
					}
				}
			}
		}
		return true;
	}



	@Override
	public Matrix clone()  {
		Matrix mat=new Matrix(getW(), getH());
		for (int y=0;y < data.length;y++) {
			for (int x=0;x < data[y].length;x++) {
				mat.data[y][x] = data[y][x];
			}
		}
		return mat;
	}
	public Matrix translate() {
		float[][] old=data;
		data = new float[getW()][getH()];
		for (int y=0;y < data.length;y++) {
			for (int x=0;x < data[y].length;x++) {
				//System.out.println("x:"+x+" y:"+y);
				data[y][x] = old[x][y];
			}
		}
		return this;
	}
	public Matrix scale(float scale) {
		for (int y=0;y < data.length;y++) {
			for (int x=0;x < data[y].length;x++) {
				data[y][x] *= scale;
			}
		}
		return this;
	}
	public float get(int row, int column) {
		return data[row - 1][column - 1];
	}
	public void set(int row, int column, float value) {
		data[row - 1][column - 1] = value;
	}
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append(String.format("(w=%d,h=%d)\n", getW(), getH()));
		for (int y=0;y < data.length;y++) {
			sb.append("|");
			for (int x=0;x < data[y].length;x++) {
				sb.append(String.format("%.2f", data[y][x]));
				if (x != data[y].length - 1) {
					sb.append("  ");
				}
			}
			sb.append("|\n");
		}

		return sb.toString();
	}

	public static Matrix E(int f) {
		Matrix mat=new Matrix(f, f);
		for (int y=0;y < mat.data.length;y++) {
			mat.data[y][y] = 1;
		}
		return mat;
    }
	public static Matrix mergeHorizen(Matrix a, Matrix b) {
		if (a.getH() != b.getH()) {
			throw new IllegalArgumentException("height not fit");
		}

		int w=a.getW();
		Matrix mat=new Matrix(a.getW() + b.getW(), a.getH());
		for (int y=0;y < mat.data.length;y++) {
			for (int x=0;x < mat.data[y].length;x++) {
				mat.data[y][x] = x < w ?a.data[y][x]: b.data[y][x - w];
			}
		}
		return mat;
    }
}
