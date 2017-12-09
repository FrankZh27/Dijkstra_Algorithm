import java.util.*;

public class Vertex {
	public String id;
	public ArrayList<Edge> nb;
	public Vertex(String id) {
		this.id = id;
		this.nb = new ArrayList<Edge>();
	}
	public void addEdge(Edge e) {
		nb.add(e);
	}
}
