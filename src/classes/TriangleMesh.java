package classes;

import interfaces.ITriangleMesh;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;

/**
 * This class stores the information about a TriangleMesh.
 * 
 * @author Matthias Stellmann & Grzegorz Markiewicz
 *
 */
public class TriangleMesh implements ITriangleMesh {

	/**
	 *  List of all Vertices
	 */
	private List<Point3d> verticiesList;
	/**
	 *  List of all Triangles
	 */
	private List<Triangle> triangleList;

	public TriangleMesh() {
		this.verticiesList = new ArrayList<Point3d>();
		this.triangleList = new ArrayList<Triangle>();
	}

	@Override
	public void addTriangle(Triangle t) {
		triangleList.add(t);
	}

	@Override
	public int addVertex(Point3d v) {
		verticiesList.add(v);
		return verticiesList.size() - 1;
	}

	@Override
	public int getNumberOfTriangles() {
		return triangleList.size();
	}

	@Override
	public int getNumberOfVertices() {
		return verticiesList.size();
	}

	@Override
	public Triangle getTriangle(int index) {
		return triangleList.get(index);
	}

	@Override
	public Point3d getVertex(int index) {
		return verticiesList.get(index);
	}

	@Override
	public void clear() {
		triangleList.clear();
		verticiesList.clear();
	}

	@Override
	public List<Point3d> getVerticiesList() {
		return verticiesList;
	}

	@Override
	public List<Triangle> getTriangleList() {
		return triangleList;
	}

}
