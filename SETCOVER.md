### Example: Set Cover

Given a city, which contains a list of neighborhood $N = \{n_1,n_2 \cdots n_k\}$, and a function $ADJ : N \mapsto \mathcal P (N)$, where $\mathcal P (N)$ is the power set of N, and the function ADJ returns a set of neighborhood that is adjacent to the given one.

And the goal is to build subway station on a subset of the neighborhood, such that for any neighborhood n in the city, either n has a station, or one of its neighborhood has a station. 


Assume we are given this implementation of Neighborhood and a reader. 
```java
    public static class Neighborhood {

        private static final Map<String, Neighborhood> NeighborhoodMap = new HashMap<>();

        public static Neighborhood makeNeighborhood(String line) {
            if (line.contains(" ")) {
                String id = line.split(" ")[0].trim();
                String[] adjacent = line.substring(1).trim().split(" ");
                return new Neighborhood(id, adjacent);
            } else {
                return NeighborhoodMap.get(line.trim());
            }
        }

        private String[] adjacent;
        private String id;

        private Neighborhood(String id, String[] adjacent) {
            this.id = id;
            this.adjacent = adjacent;
            NeighborhoodMap.put(id, this);
        }

        public List<Neighborhood> getAdjacent() {
            return Arrays.stream(this.adjacent).map(x -> makeNeighborhood(x))
                .collect(Collectors.toList());
        }

        public String getId() {
            return id;
        }

    }
```

Note that the adjacent function returns the exact Neighborhood instead of creating on the fly. However, user can safely ignore this, and just create an Neighborhood with desired ID and empty `adjacent` list for the problem, and the given example will still works.

Then to solve the problem, we can do it in just a few line of Java:

```java
// The original data
// First number of each line is the Neighborhood id, and the rest is the ids of its adjacent Neighborhood.
 String[] cityData = ("1 2 3 4 5\n"
                      + "2 1\n"
                      + "3 1\n"
                      + "4 1\n"
                      + "5 1\n"
                      + "6 7 8 9\n"
                      + "7 6\n"
                      + "8 6\n"
                      + "9 6").split("\n");

// Now we created a list of Neighborhood from data.
List<Neighborhood> city =
            Arrays.stream(cityData).map(Neighborhood::makeNeighborhood)
                .collect(Collectors.toList());

// Register the class and provide a Function to create an unique Variable name in ILP.
Register(Neighborhood.class, Neighborhood::getId);

// Create an Predicate, we set the score to 1.0 as prefer each Neighborhood equally.
// If the problem is weighted, e.g. the cost for constructing a station for each Neighborhood is different,
// Then we can easily change the score 1.0 to its cost, and find the minimal cost plan.
CCMPredicate<Neighborhood> hasStation = makePredicate("hasStation", x -> 1.0);

// Now we state the constraints.
ConstraintFunction<Neighborhood> coverageConstraints =
    new ConstraintFunction<>(x ->{
    // T(x) is a helper function to transfer an object X to a constant term in the logic expression,
    // we get the term name from the method registered before.
        FolFormula hasStationOnX = hasStation.on(T(x));
        FolFormula hasStationOnAdjacents =
                  exist(x.getAdjacent(), z -> hasStation.on(T(z)));
        return or(hasStationOnX, hasStationOnAdjacents);
});

// State the problem and data.
ILPBaseCCMProblem problem = argmin(Objective.sum(hasStation, city)).
    subjectTo(coverageConstraints.of(city)).getProblem();

// Solve this problem.
problem.solve();

// Now we can query the solution.
System.out.println("Solution : ");
 for (Neighborhood n : city) {
     if (problem.isAssigned(hasStation, T(n))) {
         System.out.println("Should select Neighborhood " + n.getId());
     }
 }

```