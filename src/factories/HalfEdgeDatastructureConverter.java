package factories;

import interfaces.IHalfEdgeDatastructure;
import interfaces.IHalfEdgeFacet;
import interfaces.ITriangleMesh;

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Point3f;

import classes.HalfEdge;
import classes.HalfEdgeDatastructure;
import classes.HalfEdgeTriangle;
import classes.HalfEdgeVertex;
import classes.Triangle;

/**
 * Converts a given Datastructure into a Half-Edge-Datastructure.
 * 
 * @author Grzegorz Markiewicz and Matthias Stellmann
 * 
 */
public class HalfEdgeDatastructureConverter {
	/**
	 * Index representing the halfedge name
	 */
	private static int halfedgenameIndex = 0;

	/**
	 * Index representing the vertex name
	 */
	private static int vertexnameIndex = 0;

	/**
	 * private constructor, static class
	 */
	private HalfEdgeDatastructureConverter() {
	}

	/**
	 * This method converts a given ITriangleMesh into a HalfEdgeDatastructure.
	 * 
	 * @param triangleMesh
	 *            Mesh to be converted.
	 * @return Converted Mesh as IHalfEdgeDatastructure.
	 */
	public static IHalfEdgeDatastructure convert(ITriangleMesh triangleMesh) {
		IHalfEdgeDatastructure halfEdgeDatastructure = new HalfEdgeDatastructure();
		Map<Point3f, HalfEdgeVertex> vertexMap = new HashMap<Point3f, HalfEdgeVertex>();

		// creating the halfedges, vertices and facets and adding them to the
		// datastructure
		for (Triangle elem : triangleMesh.getTriangleList()) {
			// creating the halfedges
			HalfEdge halfEdgeI = new HalfEdge();
			HalfEdge halfEdgeJ = new HalfEdge();
			HalfEdge halfEdgeK = new HalfEdge();

			// creating the vertices
			Point3f keyI = new Point3f(triangleMesh.getVertex(elem.getI()));
			HalfEdgeVertex halfEdgeVertexI;
			Point3f keyJ = new Point3f(triangleMesh.getVertex(elem.getI()));
			HalfEdgeVertex halfEdgeVertexJ;
			Point3f keyK = new Point3f(triangleMesh.getVertex(elem.getI()));
			HalfEdgeVertex halfEdgeVertexK;

			if (vertexMap.containsKey(keyI)) {
				halfEdgeVertexI = vertexMap.get(keyI);
			} else {
				halfEdgeVertexI = new HalfEdgeVertex(keyI);
				halfEdgeVertexI.setName(getNextVertexName());
				halfEdgeVertexI.setHalfEdge(halfEdgeI);
			}

			if (vertexMap.containsKey(keyJ)) {
				halfEdgeVertexJ = vertexMap.get(keyJ);
			} else {
				halfEdgeVertexJ = new HalfEdgeVertex(keyJ);
				halfEdgeVertexJ.setName(getNextVertexName());
				halfEdgeVertexJ.setHalfEdge(halfEdgeJ);
			}

			if (vertexMap.containsKey(keyK)) {
				halfEdgeVertexK = vertexMap.get(keyK);
			} else {
				halfEdgeVertexK = new HalfEdgeVertex(keyK);
				halfEdgeVertexK.setName(getNextVertexName());
				halfEdgeVertexK.setHalfEdge(halfEdgeK);
			}

			// setting halfedgenames
			halfEdgeI.setName(getNextHalfEdgeName());
			halfEdgeJ.setName(getNextHalfEdgeName());
			halfEdgeK.setName(getNextHalfEdgeName());

			// setting vertices in halfedges
			halfEdgeI.setVertex(halfEdgeVertexI);
			halfEdgeJ.setVertex(halfEdgeVertexJ);
			halfEdgeK.setVertex(halfEdgeVertexK);

			// setting nexts in halfedges
			halfEdgeI.setNext(halfEdgeJ);
			halfEdgeJ.setNext(halfEdgeK);
			halfEdgeK.setNext(halfEdgeI);

			// setting prevs in halfedges
			halfEdgeI.setPrev(halfEdgeK);
			halfEdgeJ.setPrev(halfEdgeI);
			halfEdgeK.setPrev(halfEdgeJ);

			// creating facet
			IHalfEdgeFacet facet = new HalfEdgeTriangle();
			facet.setHalfEdge(halfEdgeI);

			// setting facet in halfedges
			halfEdgeI.setFacet(facet);
			halfEdgeJ.setFacet(facet);
			halfEdgeK.setFacet(facet);

			// adding components to the HalfEdgeDatastructure
			halfEdgeDatastructure.addFacet(facet);
			halfEdgeDatastructure.addHalfEdge(halfEdgeI);
			halfEdgeDatastructure.addHalfEdge(halfEdgeJ);
			halfEdgeDatastructure.addHalfEdge(halfEdgeK);
		}

		// adding the vertices to the datastructure
		for (HalfEdgeVertex elem : vertexMap.values()) {
			halfEdgeDatastructure.addVertex(elem);
		}

		// Setting all opposite-halfedges
		for (int i = 0; i < halfEdgeDatastructure.getNumberOfHalfEdges(); i++) {
			HalfEdge halfEdge = halfEdgeDatastructure.getHalfEdge(i);
			HalfEdgeVertex startVertex = halfEdge.getVertex();
			HalfEdgeVertex nextStartVertex = halfEdge.getNext().getVertex();
			halfEdge.setOpposite(findOpposite(0, startVertex, nextStartVertex, halfEdgeDatastructure));
		}

		return halfEdgeDatastructure;
	}

	/**
	 * Recursive methode to find the opposite halfedge of a halfedge.
	 * startVertex(i) == startVertex(x) && nextStartVertex(i) == prevStartVertex(x)
	 * 
	 * @param x
	 *            current halfEdgeIndex
	 * @param startVertex
	 *            StartVertex of the searching halfedge
	 * @param nextStartVertex
	 *            StartVertex of the searching halfedge.next()
	 * @param halfEdgeDatastructure
	 *            HalfEdgeDatastructure
	 * @return The opposite halfedge.
	 */
	private static HalfEdge findOpposite(int x, HalfEdgeVertex startVertex, HalfEdgeVertex nextStartVertex, IHalfEdgeDatastructure halfEdgeDatastructure) {
		HalfEdgeVertex startVertex_X = halfEdgeDatastructure.getHalfEdge(x).getVertex();
		HalfEdgeVertex prevStartVertex_X = halfEdgeDatastructure.getHalfEdge(x).getPrev().getVertex();
		if (startVertex == startVertex_X && nextStartVertex == prevStartVertex_X) {
			return halfEdgeDatastructure.getHalfEdge(x);
		} else {
			return findOpposite(x + 1, prevStartVertex_X, nextStartVertex, halfEdgeDatastructure);
		}
	}

	/**
	 * This method creates the next name of a halfedge.
	 * 
	 * @return Next halfedgename.
	 */
	private static String getNextHalfEdgeName() {
		return "e" + halfedgenameIndex++;
	}

	/**
	 * This method creates the next name of a vertex.
	 * 
	 * @return Next vertexname.
	 */
	private static String getNextVertexName() {
		return "v" + vertexnameIndex++;
	}
}
