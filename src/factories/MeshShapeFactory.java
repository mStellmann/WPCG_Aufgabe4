package factories;

import interfaces.ITriangleMesh;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import classes.AppearanceHelper;
import classes.Triangle;

import com.sun.j3d.utils.geometry.GeometryInfo;

/**
 * Factory for converting a ITriangleMesh into a Shape3D.
 * 
 * @author Matthias Stellmann & Grzegorz Markiewicz
 * 
 */
public class MeshShapeFactory {
	/**
	 * Creates a Shape3D-Object out of an ITriangleMesh.
	 * 
	 * @param mesh
	 *            ITriangleMesh to create the Shape3D-Object.
	 * @return Shape3D-Object
	 */
	public static Shape3D createMeshShape(ITriangleMesh mesh) {
		TriangleArray triAry = new TriangleArray(mesh.getNumberOfVertices(), TriangleArray.COORDINATES | TriangleArray.NORMALS);

		int i = 0;
		for (Triangle elem : mesh.getTriangleList()) {
			Point3d[] coords = { mesh.getVertex(elem.getI()), mesh.getVertex(elem.getJ()), mesh.getVertex(elem.getK()) };
			// Setting the coordinates and the normals of the triangle
			triAry.setCoordinates(i, coords);
			triAry.setNormal(i++, elem.getNormalVectorAsFloat());
			triAry.setNormal(i++, elem.getNormalVectorAsFloat());
			triAry.setNormal(i++, elem.getNormalVectorAsFloat());
		}
		
		GeometryInfo geoInfo = new GeometryInfo(triAry);
		GeometryArray geoAry = geoInfo.getGeometryArray();

		Shape3D resultShape = new Shape3D(geoAry, new Appearance());
		AppearanceHelper.setColor(resultShape, new Color3f(0.2f, 0.7f, 0f));
		return resultShape;

	}
}
