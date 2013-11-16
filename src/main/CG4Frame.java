package main;

import interfaces.IHalfEdgeDatastructure;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.PointLight;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import classes.Triangle;
import classes.TriangleMesh;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

import factories.HalfEdgeDatastructureConverter;
import factories.MeshShapeFactory;

public class CG4Frame extends JFrame {

	/**
	 * Gernerated serialnumber.
	 */
	private static final long serialVersionUID = -6619879728982489623L;

	/**
	 * Canvas object for the 3D content.
	 */
	private Canvas3D canvas3D;

	/**
	 * Simple universe (provides reasonable default values).
	 */
	protected SimpleUniverse universe;

	/**
	 * Scene graph for the 3D content scene.
	 */
	protected BranchGroup scene = new BranchGroup();

	/**
	 * Default constructor.
	 */
	public CG4Frame() {
		// Create canvas object to draw on
		canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

		// The SimpleUniverse provides convenient default settings
		universe = new SimpleUniverse(canvas3D);
		universe.getViewingPlatform().setNominalViewingTransform();

		// Setup lighting
		addLight(universe);

		// Allow for mouse control
		OrbitBehavior ob = new OrbitBehavior(canvas3D);
		ob.setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), Double.MAX_VALUE));
		universe.getViewingPlatform().setViewPlatformBehavior(ob);

		// Set the background color
		Background background = new Background(new Color3f(0.9f, 0.9f, 0.9f));
		BoundingSphere sphere = new BoundingSphere(new Point3d(0, 0, 0), 100000);
		background.setApplicationBounds(sphere);
		scene.addChild(background);

		// Setup frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Aufgabe 4 - HalfEdgeDatastructure");
		setSize(500, 500);
		getContentPane().add("Center", canvas3D);
		setVisible(true);
	}

	/**
	 * Setup the lights in the scene. Attention: The light need to be added to
	 * the scene before the scene in compiled (see createSceneGraph()).
	 */
	private void addLight(SimpleUniverse universe) {
		addPointLight(new Point3f(10, 10, 10));
		addPointLight(new Point3f(-10, -10, -10));
		addPointLight(new Point3f(10, -10, 10));
		addDirectionalLight(new Vector3f(0, 0, 1));
	}

	void addPointLight(Point3f position) {
		PointLight light = new PointLight();
		light.setPosition(position);
		light.setColor(new Color3f(1, 1, 1));
		light.setInfluencingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		scene.addChild(light);
	}

	void addDirectionalLight(Vector3f direction) {
		DirectionalLight light = new DirectionalLight();
		light.setDirection(direction);
		light.setColor(new Color3f(1, 1, 1));
		light.setInfluencingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		scene.addChild(light);
	}

	/**
	 * Create the default scene graph.
	 */
	protected void createSceneGraph() {
		TriangleMesh tetrahedron = createTetrahedron();
		// Adding mesh to our scene
		scene.addChild(MeshShapeFactory.createMeshShape(tetrahedron));

		IHalfEdgeDatastructure hed = HalfEdgeDatastructureConverter.convert(tetrahedron);
		System.out.println(hed);

		// Assemble scene
		scene.compile();
		universe.addBranchGraph(scene);
	}

	/**
	 * Private method to create a TriangleMesh of a tetrahedron.
	 * 
	 * @return TriangleMesh of a tetrahedron.
	 */
	private TriangleMesh createTetrahedron() {
		TriangleMesh mesh = new TriangleMesh();
		double h = Math.sqrt(0.75);
		double xOffset = -0.5;
		double yOffset = -Math.sqrt(1 - 0.1875) / 2;
		double zOffset = -(h / 2);
		Point3d p0 = new Point3d(0.0 + xOffset, 0.0 + yOffset, 0.0 + zOffset);
		Point3d p1 = new Point3d(1.0 + xOffset, 0.0 + yOffset, 0.0 + zOffset);
		Point3d p2 = new Point3d(0.5 + xOffset, 0.0 + yOffset, -h + zOffset);
		Point3d p3 = new Point3d(0.5 + xOffset, Math.sqrt(1 - 0.1875) + yOffset, -(h / 2) + zOffset);

		addTriangleToMesh(p2, p1, p0, mesh);
		addTriangleToMesh(p0, p1, p3, mesh);
		addTriangleToMesh(p1, p2, p3, mesh);
		addTriangleToMesh(p2, p0, p3, mesh);

		return mesh;
	}

	/**
	 * Private methode to add a triangle to a TriangleMesh.
	 * 
	 * @param p1
	 *            First vertex of the triangle.
	 * @param p2
	 *            Second vertex of the triangle.
	 * @param p3
	 *            Third vertex of the triangle.
	 * @param mesh
	 *            Given mesh to add the triangle.
	 */
	private void addTriangleToMesh(Point3d p1, Point3d p2, Point3d p3, TriangleMesh mesh) {
		int vert1 = mesh.addVertex(p1);
		int vert2 = mesh.addVertex(p2);
		int vert3 = mesh.addVertex(p3);
		Triangle triangle = new Triangle(vert1, vert2, vert3);
		triangle.computeNormal(p1, p2, p3);
		mesh.addTriangle(triangle);
	}

	/**
	 * Starting method.
	 * 
	 * @param args
	 *            Program arguments.
	 */
	public static void main(String[] args) {
		// Create the central frame
		CG4Frame frame = new CG4Frame();
		// Add content to the scene graph
		frame.createSceneGraph();
	}

}
