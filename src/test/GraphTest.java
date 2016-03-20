package test;


import java.io.FileNotFoundException;

import main.Graph;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
 

public class GraphTest {
	
	String FILE_URL ="/Users/sghali/Documents/workspace/TestNGDemo/src/testData/graph2";
	Graph graph;

	@Parameters({"url"})
	@BeforeSuite
	public void GenerateGraph(){
		graph = new Graph(FILE_URL,true);
		//graph.print();
	}
	
	@Parameters({ "file" })
	@Test
	/*
	 * Positive Case - As in before test graph is build based on insertion order below test will make sure dependencies are right hierarchy
	 */
	public void IsGraphHierarchyCorrectForInsertionOrder(String file) {
		graph = new Graph(file,true);
		Assert.assertTrue(graph.confirmGraphHierarchy(file, "->"));
	}
	
	@Parameters({ "file" })
	@Test
	/*
	 * Negative Case - As graph is build not based on insertion order , below test should fail to make sure dependencies are not in right hierarchy
	 */
	public void IsGraphHierarchyFalseForNonInsertionOrgerGraph(String file) {
		graph = new Graph(file,false);
		Assert.assertFalse( graph.confirmGraphHierarchy(FILE_URL, "->"));
	}
	
	@Parameters({ "file" })
	@Test
	/*
	 * Display the o/p with required indentation in Hierarchical fashion
	 */
	public void DisplayGraphDependanciesIndentedPattern(String file) {
		graph = new Graph(file,true);
		graph.dfs();
		Assert.assertTrue(true);
	}
	
	@Parameters({ "source","destination","expectedCount","file2"})
	@Test
	public void ValidatePossiblePaths(String source,String destination,int expectedCount,String file) {
		graph = new Graph(file,true);
		int actual = graph.countPossiblePaths("A", "Z");
		System.out.println("the paths between A & C "+actual);
		Assert.assertEquals(actual, expectedCount);
	}
	
	
}
