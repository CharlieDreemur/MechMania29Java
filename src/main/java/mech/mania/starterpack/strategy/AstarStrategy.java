// package mech.mania.starterpack.strategy;

// import mech.mania.starterpack.game.util.Position;
// import mech.mania.starterpack.game.terrain.Terrain;
// import mech.mania.starterpack.game.GameState;


// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.HashSet;
// import java.util.PriorityQueue;
// import java.util.Comparator;
// import java.util.Set;
// import java.util.List;
// import java.util.Collections;

// public class AstarStrategy {

//     public static class Node {
//         public Position position;
//         public Node parent;
//         public int g; // cost from start to this node
//         public int h; // heuristic cost from this node to end
//         public int f; // total cost (g + h)

//         public Node(Position position, Node parent, int g, int h) {
//             this.position = position;
//             this.parent = parent;
//             this.g = g;
//             this.h = h;
//             this.f = g + h;
//         }
//     }

//     public int getPathDistance(Position a, Position b) {
//         return getManhattanDistance(a, b);
//     }

//     public Terrain getTerrainAtPosition(GameState gameState, Position pos) {
//         for (Terrain terrain : gameState.terrains().values()) {
//             if (terrain.position().equals(pos)) {
//                 return terrain;
//             }
//         }
//         return null; // No terrain at the given position
//     }

//     public int getPathCost(GameState gameState, Position a, Position b) {
//         // Retrieve the Terrain at position b
//         Terrain terrain = getTerrainAtPosition(gameState, b);

//         if (terrain == null) {
//             // If there's no specific terrain, assume it's an empty field.
//             return 1;
//         }

//         String terrainType = terrain.get(terrain.id()).type(); // might be buggy because there isn't type() and get().

//         switch(terrainType) { // not sure if the terrainType is correct, NEED TESTING
//             case "WALL":
//             case "BARRICADE":
//             case "TREE":
//                 // These obstacles have to be destroyed first.
//                 // The cost could be proportional to their health, or infinite for simplicity.
//                 return terrain.health();

//             case "WATER":
//                 // Zombies can't pass water at all
//                 return Integer.MAX_VALUE;

//             default:
//                 // If terrain can be attacked through or if it's an unknown type, assume it's passable
//                 return terrain.canAttackThrough() ? 1 : Integer.MAX_VALUE;
//         }
//     }



//     public List<Position> getPath(Position a, Position b) { // if cannot arrive b, check if neighbor can arrive
//         Set<Position> closedSet = new HashSet<>();
//         PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));

//         Node startNode = new Node(a, null, 0, getPathDistance(a, b));
//         openSet.add(startNode);

//         while (!openSet.isEmpty()) {
//             Node currentNode = openSet.poll();

//             if (currentNode.position.equals(b)) {
//                 return constructPath(currentNode);
//             }

//             closedSet.add(currentNode.position);

//             for (Position neighbor : getNeighbors(gameState, currentNode.position)) {
//                 if (closedSet.contains(neighbor)) {
//                     continue;
//                 }

//                 int tentativeG = currentNode.g + getPathCost(currentNode.position, neighbor);

//                 Node neighborNode = new Node(neighbor, currentNode, tentativeG, getPathDistance(neighbor, b));

//                 if (openSet.stream().anyMatch(node -> node.position.equals(neighbor) && tentativeG >= node.g)) {
//                     continue;
//                 }

//                 openSet.add(neighborNode);
//             }
//         }

//         return new ArrayList<>(); // Return an empty list if no path is found
//     }

//     private List<Position> constructPath(Node node) {
//         List<Position> path = new ArrayList<>();
//         while (node != null) {
//             path.add(node.position);
//             node = node.parent;
//         }
//         Collections.reverse(path);
//         return path;
//     }

//     private int getManhattanDistance(Position a, Position b) {
//         return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
//     }

//     private List<Position> getNeighbors(GameState gameState, Position position) {
//         List<Position> potentialNeighbors = Arrays.asList(
//                 new Position(position.x() - 1, position.y()),
//                 new Position(position.x() + 1, position.y()),
//                 new Position(position.x(), position.y() - 1),
//                 new Position(position.x(), position.y() + 1)
//         );
        
//         List<Position> validNeighbors = new ArrayList<>();

//         for (Position neighbor : potentialNeighbors) {
//             // Ensure the position is within game boundaries (if such boundaries exist)
//             // For this example, let's assume the game grid is a 100x100 grid.
//             if (neighbor.x() < 0 || neighbor.x() >= 100 || neighbor.y() < 0 || neighbor.y() >= 100) {
//                 continue; // Skip this neighbor
//             }

//             // Check the path cost to ensure the position isn't blocked by an obstacle
//             if (getPathCost(gameState, position, neighbor) != Integer.MAX_VALUE) {
//                 validNeighbors.add(neighbor);
//             }
//         }

//         return validNeighbors;
//     }

// }
