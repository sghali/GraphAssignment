package main;

 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
 
class Neighbor {
    public int vertexNum;
    public Neighbor next;
    boolean isVisisted;
    public Neighbor(int vnum,Neighbor nbr,boolean isVisisted) {
            this.vertexNum = vnum;
            next = nbr;
            this.isVisisted =false;
            
    }
}
 
class Vertex {
    String name;
    Neighbor adjList;
    boolean isVertexVisisted;
    Vertex(String name, Neighbor neighbors,boolean isVertexVisisted) {
            this.name = name;
            this.adjList = neighbors;
            this.isVertexVisisted = false;
    }
}


public class Graph {
 
    Vertex[] adjLists;
     
    public Graph(String file,boolean insertionOrder)  {
       try{
        Scanner sc = new Scanner(new File(file));
        adjLists = new Vertex[26]; 
        // read vertices
        char alphabet = 'A';
        for (int v=0; v < adjLists.length; v++) {
            adjLists[v] = new Vertex(Character.toString(alphabet), null,false);
            alphabet++;
        }
 
        // read edges
        while (sc.hasNext()) {
            // read vertex names and translate to vertex numbers  
            String[]  input = sc.next().split("->");
            int v1 = indexForName(input[0]);
            int v2 = indexForName(input[1]);
		    if(insertionOrder){
		         if(adjLists[v1].adjList==null){
		             adjLists[v1].adjList = new Neighbor(v2,adjLists[v1].adjList,false);
		          }else{
		          	Vertex temp = adjLists[v1];
		          	Neighbor tempNeighbor = temp.adjList; 
		          	while(tempNeighbor.next!=null){
		          		tempNeighbor = tempNeighbor.next;
		          	}
		          	tempNeighbor.next=new Neighbor(v2,null,false);
		          }
         }else{
        	 adjLists[v1].adjList = new Neighbor(v2,adjLists[v1].adjList,false);
         }
          
           
        }
       }catch(Exception e){
    	   System.out.println("Exception in Building Graph:");
       }
    }
 
     
    int indexForName(String name) {
        for (int v=0; v < adjLists.length; v++) {
            if (adjLists[v].name.equals(name)) {
                return v;
            }
        }
        return -1;
    }   
     
    /*
     * 
     */
    private void printGraph(int v,int pos) {
        for (Neighbor nbr=adjLists[v].adjList; nbr != null; nbr=nbr.next) {
        	if(!nbr.isVisisted){
        		for(int i=0;i<=pos;i++){
            		System.out.print("|_");
            	}
            	System.out.println(""+adjLists[nbr.vertexNum].name);
            	adjLists[nbr.vertexNum].isVertexVisisted=true;
            	if(adjLists[nbr.vertexNum].adjList!=null){
            		 nbr.isVisisted = true;
            		 printGraph(nbr.vertexNum,pos+1);
            	}
            	 nbr.isVisisted = true;
        	}
            }
    }
     
    public void traverseGraph() {
        int pos =0;
        for (int v=0; v < adjLists.length; v++) {
            	if(adjLists[v].adjList!=null && adjLists[v].isVertexVisisted ==false ){
            		System.out.println(adjLists[v].name);
            	}
            	printGraph(v,pos);
        }
    }
     
     
    public void print() {
        System.out.println();
        for (int v=0; v < adjLists.length; v++) {
            System.out.print(adjLists[v].name);
            for (Neighbor nbr=adjLists[v].adjList; nbr != null;nbr=nbr.next) {
                System.out.print(" --> " + adjLists[nbr.vertexNum].name);
            }
            System.out.println("\n");
        }
    }
   
    
   public boolean confirmGraphHierarchy(String file,String delimiter){
    	try{
    	 Scanner ch = new Scanner(new File(file));
    	 while(ch.hasNext()){
    		 String[] in = ch.next().split(delimiter);
    		 String source = in[0];
    		 int currentNode = indexForName(source);
    		 if(adjLists[currentNode]==null || adjLists[currentNode].adjList==null){
    			 return false;
    		 }else{
             	Neighbor tempNeighbor = adjLists[currentNode].adjList; 
             	while(tempNeighbor!=null){
         		   if(adjLists[tempNeighbor.vertexNum].name.equals(in[1])){
         			   if(tempNeighbor.isVisisted==true){
         				   return false;
         			   }else{
         				  tempNeighbor.isVisisted =true;
         				  break;
         			   }
         		   }else{
         			  tempNeighbor.isVisisted =true;
         			  tempNeighbor = tempNeighbor.next;
         		   }
         }   
             	
    		 }	 	
    	 }
    	}catch(Exception e){
    		System.out.println("Error in Confirming the graph input hirerachy");
    		return false;
    	}
    	return true;
    }
   
   private int searchPaths(int v1,int v2) {
	   int totalPaths=0;
	   if(adjLists[v1].name.equals(adjLists[v2].name)){
		   totalPaths= totalPaths+1;
	   }
	  for (Neighbor nbr=adjLists[v1].adjList; nbr != null;nbr=nbr.next)
		  if(adjLists[nbr.vertexNum].name.equals(adjLists[v2].name)){
			  totalPaths= totalPaths+1; 
          }else{
        	  totalPaths= totalPaths + searchPaths(nbr.vertexNum,v2);  
          }
      return totalPaths;
   }
    
   public int countPossiblePaths(String source, String destination) {
	   int v1 = indexForName(source);
       int v2 = indexForName(destination);
       int totalPaths=0;
       if(v1==v2){
    	   return totalPaths;
       } else if(adjLists[v1]==null || adjLists[v2]==null){
    	    return -1;
       }else{
    	   totalPaths= searchPaths(v1,v2);
       }
       return totalPaths;
   }
}