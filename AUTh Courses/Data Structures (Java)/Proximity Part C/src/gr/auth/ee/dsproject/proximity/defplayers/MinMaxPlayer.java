package gr.auth.ee.dsproject.proximity.defplayers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import gr.auth.ee.dsproject.proximity.board.Board;
import gr.auth.ee.dsproject.proximity.board.ProximityUtilities;
import gr.auth.ee.dsproject.proximity.board.Tile;
import gr.auth.ee.dsproject.proximity.defplayers.Node81918309;


/**
 * Simulates a DS-Proximity player that plays using a Min-Max algorithm
 * @author Mamalakis Evangelos <mamalakis@auth.gr> <+306970489632>
 * @author Evangelou Alexandros <alexandre@auth.com> <+306980297466>
 */
public class MinMaxPlayer implements AbstractPlayer
{

  int score;
  int id;
  String name;
  int numOfTiles;
  int[] nextNumbersToBePlayed; // here?absolete
  private Object root;

  public MinMaxPlayer (Integer pid)
  {
    id = pid;
    name = "MinMaxPlayer";
    
    Node81918309.playerId = id;
    Node81918309.enemyId = id == 1 ? 2 : 1;
  }

  public String getName ()
  {

    return name;

  }

  public int getNumOfTiles ()
  {
    return numOfTiles;
  }

  public void setNumOfTiles (int tiles)
  {
    numOfTiles = tiles;
  }

  public int getId ()
  {
    return id;
  }

  public void setScore (int score)
  {
    this.score = score;
  }

  public int getScore ()
  {
    return score;
  }

  public void setId (int id)
  {

    this.id = id;

  }

  public void setName (String name)
  {
    this.name = name;

  }


	/**
	 * Selects the next move using the chooseMinMaxMove function
	 * @param board
	 * @param randomNumber
	 * @return the best next move
	 */
	public int[] getNextMove (Board board , int randomNumber) {
		Node81918309 rootNode = new Node81918309(board, board.getOpponentsLastMove());
		return chooseMinMaxMove(rootNode);
	}
	
	/**
	 * uses the recursive createSubtree() to implement a MinMax algorithm
	 * @param rootNode
	 * @return
	 */
	private int[] chooseMinMaxMove(Node81918309 rootNode) {
		// Get the move with the maximum evaluation
		Node81918309 bestMove = createSubtree(rootNode, 2);
		return bestMove.getNodeMove();
	}
	
	
	static final int N_BEST_NODES_TO_EXAMINE = 2;
	/**
	 * Recursive function. Creates the subtree for the parent node and calls itself for children up to maxDepth
	 * @param parent
	 * @param maxDepth
	 * @return the node with the best evaluationSum
	 */
	private Node81918309 createSubtree(Node81918309 parent, int maxDepth) {
		for ( int x = 0; x < ProximityUtilities.NUMBER_OF_COLUMNS; x++ ) {
			for ( int y = 0; y < ProximityUtilities.NUMBER_OF_ROWS; y++ ) {
				if ( !isTaken(parent.nodeBoard, x, y) ) {
					Node81918309 node = new Node81918309(x, y, parent);
					node.setNodeEvaluationSum(node.getNodeEvaluation());
				}
			}
		}
		
		ArrayList<Node81918309> nodesToExamine = parent.children;
		if ( parent.nodeDepth + 1 < maxDepth ) { // check if children reached maxDepth
			// Examine only the N_BEST_NODES_TO_EXAMINE nodes with the higher evaluation
			Collections.sort(parent.children, Node81918309.EVALUATIONSUM_ORDER);
			nodesToExamine = new ArrayList<Node81918309>(parent.children.subList(0, N_BEST_NODES_TO_EXAMINE));
			for (Node81918309 node:nodesToExamine) {
				node.setNodeEvaluationSum(node.getNodeEvaluationSum() - createSubtree(node, maxDepth).getNodeEvaluationSum());
			}
		}
		Node81918309 maxMove = Collections.max(nodesToExamine, Node81918309.EVALUATIONSUM_ORDER);
		System.out.println(parent.getNodeMove()[0] + " " + parent.getNodeMove()[1] + " " + parent.getNodeMove()[2]
				+ " " + maxMove.getNodeMove()[0] + " " + maxMove.getNodeMove()[1] + " " + maxMove.getNodeMove()[2]
						+ " " + parent.getNodeEvaluation() + " " + parent.getNodeEvaluationSum()
						+ " | " + maxMove.getNodeEvaluation() + " " + maxMove.getNodeEvaluationSum());
		return maxMove;
	}
	
	
	

	/**
	 * Specify if the given Tile is taken
	 * @param board
	 * @param x
	 * @param y
	 * @return Boolean true if Tile taken, else false
	 */
	private boolean isTaken(Board board, int x, int y) {
		return board.getTile(x, y).getColor() != 0;
	}


}
