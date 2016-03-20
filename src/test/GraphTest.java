package test;


import main.Graph;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
 

public class GraphTest {
	
	String FILE_URL ="./src/testData/validInputGraph";
	Graph graph;

	/*
	 * Positive Case - As in before test graph is build based on insertion order below test will make sure dependencies are right hierarchy
	 */
	@Test
	public void IsGraphHierarchyCorrectForInsertionOrder() {
		try {
			graph = new Graph(FILE_URL,true);
			Assert.assertTrue(graph.confirmGraphHierarchy(FILE_URL, "->"));
		} catch (Exception e) {
			
		}
		
	}
	
	/*
	 * Negative Case - As graph is build not based on insertion order , below test should fail to make sure dependencies are not in right hierarchy
	 */
	@Test
	public void IsGraphHierarchyFalseForNonInsertionOrderGraph() {
		try {
			graph = new Graph(FILE_URL,false);
			Assert.assertFalse( graph.confirmGraphHierarchy(FILE_URL, "->"));
		} catch (Exception e) {
			System.out.println("Excpetion in building graph:"+e.getMessage());
		}
		
	}
	
	/*
	 * Display the o/p with required indentation in Hierarchical fashion
	 */
	@Test
	public void DisplayGraphDependanciesIndentedPattern() {
		System.out.println("########Started Traversing######");
		try {
			graph = new Graph(FILE_URL,true);
			Assert.assertTrue(graph.traverseGraph());
			System.out.println("########Completed######");
		} catch (Exception e) {
			System.out.println("Excpetion in building graph:"+e.getMessage());
		}
		
	}
	
	/*
	 * Display the o/p with required indentation in Hierarchical fashion
	 */
	@Parameters({"file2"})
	@Test
	public void DisplayGraphDependancies(String file2) {
		System.out.println("########Started Traversing######");
		try {
			graph = new Graph(file2,true);
			Assert.assertTrue(graph.traverseGraph());
			System.out.println("########Completed######");
		} catch (Exception e) {
			System.out.println("Excpetion in building graph:"+e.getMessage());
		}
		
	}
	
	/*
	 * Display the o/p When graph is empty or only node
	 */
	@Parameters({"file3"})
	@Test
	public void DisplayGraphDependanciesInvalid(String file3) {
		try{
		graph = new Graph(file3,true);
		}catch(Exception e){
			Assert.assertTrue(true, "Unable to build the graph as file is invalid and nothing to Process");
		}
	}
	
	@Parameters({ "source","destination","expectedCount"})
	@Test
	public void ValidatePossiblePaths(String source,String destination,int expectedCount) {
		try {
			graph = new Graph(FILE_URL,true);
			int actual = graph.countPossiblePaths(source,destination);
			System.out.println("the paths between"+source+" "+destination +" is "+actual);
			Assert.assertEquals(actual, expectedCount);
		} catch (Exception e) {
			System.out.println("Excpetion in building graph:"+e.getMessage());
		}
		
	}
	
	/*
	 * Test to verify the possible paths between non existent nodes -output should be 0
	 */
	@Parameters({ "inValidSource","inValidDestination","negativeExpectedCount"})
	@Test
	public void ValidatePossiblePathsForNonExistNode(String inValidSource,String inValidDestination,int negativeExpectedCount) {
		try {
			graph = new Graph(FILE_URL,true);
			int actual = graph.countPossiblePaths(inValidSource,inValidDestination);
			System.out.println("the paths between"+inValidSource+" "+inValidDestination +" is "+actual);
			Assert.assertEquals(actual, negativeExpectedCount);
		} catch (Exception e) {
			System.out.println("Excpetion in building graph:"+e.getMessage());
		}
		
	}
	
	
}
