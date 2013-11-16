package classes;

import java.util.ArrayList;
import java.util.List;

import interfaces.IHalfEdgeDatastructureOperations;
import interfaces.IHalfEdgeFacet;

public class HalfEdgeDatastructureOperations implements IHalfEdgeDatastructureOperations {

	@Override
	public List<HalfEdgeVertex> getAdjacentVertices(HalfEdgeVertex vertex) {
		List<HalfEdgeVertex> halfEdgeVertexList = new ArrayList<HalfEdgeVertex>();
		HalfEdge prevHalfEdge = vertex.getHalfEdge().getPrev();
		HalfEdgeVertex startVertex = prevHalfEdge.getVertex();
		halfEdgeVertexList.add(startVertex);
		prevHalfEdge = prevHalfEdge.getOpposite().getPrev();
		return getAdjacentVerticesRecursive(startVertex, prevHalfEdge, prevHalfEdge.getVertex(), halfEdgeVertexList);
	}

	/**
	 * Private recursive methode to get all adjacent vertices of a given vertex.
	 * 
	 * @param startVertex
	 *            Starting vertex of the algorithm.
	 * @param prevHalfEdge
	 *            Next halfedge to check in the algorithm.
	 * @param prevVertex
	 *            Next vertex to check in the algorithm.
	 * @param halfEdgeVertexList
	 *            List to fill with every adjacent vertex found.
	 * @return List of all adjacent vertices.
	 */
	private List<HalfEdgeVertex> getAdjacentVerticesRecursive(HalfEdgeVertex startVertex, HalfEdge prevHalfEdge, HalfEdgeVertex prevVertex, List<HalfEdgeVertex> halfEdgeVertexList) {
		if (startVertex.getName().equals(prevVertex.getName())) {
			return halfEdgeVertexList;
		} else {
			halfEdgeVertexList.add(prevVertex);
			prevHalfEdge = prevHalfEdge.getOpposite().getPrev();
			return getAdjacentVerticesRecursive(startVertex, prevHalfEdge, prevHalfEdge.getVertex(), halfEdgeVertexList);
		}
	}

	@Override
	public List<IHalfEdgeFacet> getIncidentFacets(HalfEdgeVertex vertex) {
		List<IHalfEdgeFacet> halfEdgeFacetList = new ArrayList<IHalfEdgeFacet>();
		HalfEdge startEdge = vertex.getHalfEdge();
		halfEdgeFacetList.add(startEdge.getFacet());
		return getIncidentFacetsRecursive(startEdge, startEdge.getPrev().getOpposite(), halfEdgeFacetList);
	}

	/**
	 * Private recursive methode to get all incident facets of a given vertex.
	 * 
	 * @param startEdge
	 *            Starting edge of the algorithm.
	 * @param prevEdge
	 *            Next edge to check in the algorithm.
	 * @param halfEdgeFacetList
	 *            List to fill with every incident facet found.
	 * @return List of all incident facets.
	 */
	private List<IHalfEdgeFacet> getIncidentFacetsRecursive(HalfEdge startEdge, HalfEdge prevEdge, List<IHalfEdgeFacet> halfEdgeFacetList) {
		if (startEdge.getName().equals(prevEdge.getName())) {
			return halfEdgeFacetList;
		} else {
			halfEdgeFacetList.add(prevEdge.getFacet());
			return getIncidentFacetsRecursive(startEdge, prevEdge.getPrev().getOpposite(), halfEdgeFacetList);
		}
	}

	@Override
	public List<HalfEdge> getIncidetEdges(HalfEdgeVertex vertex) {
		List<HalfEdge> halfEdgeList = new ArrayList<HalfEdge>();
		HalfEdge startEdge = vertex.getHalfEdge();
		halfEdgeList.add(startEdge);
		HalfEdge oppositeEdge = startEdge.getOpposite();
		halfEdgeList.add(oppositeEdge);
		return getIncidetEdgesRecursive(startEdge, oppositeEdge.getNext(), halfEdgeList);
	}

	/**
	 * Private recursive methode to get all incident edges of a given vertex.
	 * 
	 * @param startEdge
	 *            Starting edge of the algorithm.
	 * @param nextEdge
	 *            Next edge to check in the algorithm.
	 * @param halfEdgeList
	 *            List to fill with every incident edge found.
	 * @return List of all incident edges.
	 */
	private List<HalfEdge> getIncidetEdgesRecursive(HalfEdge startEdge, HalfEdge nextEdge, List<HalfEdge> halfEdgeList) {
		if (startEdge.getName().equals(nextEdge.getName())) {
			return halfEdgeList;
		} else {
			halfEdgeList.add(nextEdge);
			HalfEdge oppositeEdge = nextEdge.getOpposite();
			halfEdgeList.add(oppositeEdge);
			return getIncidetEdgesRecursive(startEdge, oppositeEdge.getNext(), halfEdgeList);
		}
	}

	@Override
	public List<HalfEdgeVertex> getIncidentVertices(IHalfEdgeFacet facet) {
		List<HalfEdgeVertex> halfEdgeVertexList = new ArrayList<HalfEdgeVertex>();
		HalfEdge edge = facet.getHalfEdge();
		halfEdgeVertexList.add(edge.getVertex());
		halfEdgeVertexList.add(edge.getNext().getVertex());
		halfEdgeVertexList.add(edge.getPrev().getVertex());
		return halfEdgeVertexList;
	}

	@Override
	public List<IHalfEdgeFacet> getIncidentFacets(IHalfEdgeFacet facet) {
		List<IHalfEdgeFacet> halfEdgeFacetList = new ArrayList<IHalfEdgeFacet>();
		HalfEdge edge = facet.getHalfEdge();
		halfEdgeFacetList.add(edge.getOpposite().getFacet());
		halfEdgeFacetList.add(edge.getNext().getOpposite().getFacet());
		halfEdgeFacetList.add(edge.getPrev().getOpposite().getFacet());
		return halfEdgeFacetList;
	}

	@Override
	public List<HalfEdge> getIncidentHalfEdges(IHalfEdgeFacet facet) {
		List<HalfEdge> halfEdgeList = new ArrayList<HalfEdge>();
		HalfEdge edge = facet.getHalfEdge();
		halfEdgeList.add(edge);
		halfEdgeList.add(edge.getPrev());
		halfEdgeList.add(edge.getNext());
		return halfEdgeList;
	}

}
