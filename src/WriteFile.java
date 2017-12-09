import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class WriteFile {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String out = "";
		int length = 1000;
		String[] input = new String[length];
		for (int i = 0; i < length; i++) {
			input[i] = "A" + i;
			if (i < length - 1) {
				out += "A"+i+";";
			}
			else {
				out += "A"+i;
			}
		}
		PrintWriter writer = new PrintWriter("input_vertex.txt", "UTF-8");
		writer.println(out);
		writer.close();
		
		
		String[] edges = new String[length * 10];
		Random r = new Random();
		for (int i = 0; i < length * 10; i++) {
			int r1 = r.nextInt(length - 1);
			int r2 = r.nextInt(length - 1);
			int weight = r.nextInt(100);
			edges[i] = "A"+r1+","+"A"+r2+","+weight;
		}
		writer = new PrintWriter("input_edges.txt", "UTF-8");
		for (int i = 0; i < edges.length; i++) {
			writer.println(edges[i]);
		}
		writer.close();
		System.out.println("finish");
	}

}
